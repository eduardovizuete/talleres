package com.example.aws.workshop;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

import com.example.aws.workshop.model.Order;
import com.example.aws.workshop.repository.OrderRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
// import io.awspring.cloud.s3.S3Template;
import io.awspring.cloud.sns.core.SnsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SqsMessageListener {
    private final OrderRepository orderRepository;
    // private final S3Template s3Template;
    private final com.example.aws.workshop.service.BackblazeS3Service backblazeS3Service;
    private final SnsTemplate snsTemplate;
    // ...existing code...
    @Value("${spring.cloud.aws.sns.topic-arn}")
    private String snsTopicArn;
    @Value("${feature.s3.enabled:true}")
    private boolean s3Enabled;
    @Value("${feature.sns.enabled:true}")
    private boolean snsEnabled;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SqsListener("${spring.cloud.aws.sqs.queue-name}")
    public void receiveMessage(String message) {
        log.info("Received message: {}", message);
        try {
            JsonNode node = validateAndParseMessage(message);
            Order order = saveOrder(node);
            String invoiceContent = generateInvoiceContent(node);
            if (s3Enabled) {
                String fileName = uploadInvoiceToS3(invoiceContent);
            } else {
                log.info("S3 upload is disabled by configuration.");
            }
            if (snsEnabled) {
                sendInvoiceToSns(invoiceContent);
            } else {
                log.info("SNS send is disabled by configuration.");
            }
            log.info("Order processed and invoice handled for email: {}", order.getEmail());
        } catch (IllegalArgumentException e) {
            log.error("Validation error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Failed to process message: {}", e.getMessage(), e);
        }
    }

    private JsonNode validateAndParseMessage(String message) throws Exception {
        JsonNode node = objectMapper.readTree(message);
        String email = node.hasNonNull("email") ? node.get("email").asText() : null;
        String products = node.hasNonNull("products") ? node.get("products").asText() : null;
        if (email == null || email.isBlank() || products == null || products.isBlank()) {
            throw new IllegalArgumentException("Invalid message: 'email' and 'products' are required and must be non-empty strings.");
        }
        return node;
    }

    private Order saveOrder(JsonNode node) {
        Order order = new Order();
        order.setEmail(node.get("email").asText());
        order.setProducts(node.get("products").asText());
        orderRepository.save(order);
        return order;
    }

    private String generateInvoiceContent(JsonNode node) {
        return String.format("Factura para: %s\nProductos: %s\n", node.get("email").asText(), node.get("products").asText());
    }

    private String uploadInvoiceToS3(String invoiceContent) {
        return backblazeS3Service.uploadInvoice(invoiceContent);
    }

    private void sendInvoiceToSns(String invoiceContent) {
        snsTemplate.convertAndSend(snsTopicArn, invoiceContent);
        log.info("Invoice content sent to SNS topic: {}", snsTopicArn);
    }
}