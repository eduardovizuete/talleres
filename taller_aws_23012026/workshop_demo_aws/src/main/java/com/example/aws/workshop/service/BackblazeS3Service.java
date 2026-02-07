package com.example.aws.workshop.service;

import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * Servicio dedicado para la subida de archivos a S3 (Backblaze).
 * Principios SOLID: Single Responsibility (solo S3), Dependency Injection, Open/Closed (fácil de extender).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BackblazeS3Service {
    private final AmazonS3 backblazeS3Client;
    @Value("${spring.cloud.aws.s3.bucket-name}")
    private String bucketName;
    @Value("${spring.cloud.aws.s3.invoice-prefix}")
    private String invoicePrefix;

    /**
     * Sube un archivo de factura a Backblaze S3.
     * @param invoiceContent contenido de la factura
     * @return nombre del archivo subido
     */
    public String uploadInvoice(String invoiceContent) {
        String fileName = invoicePrefix + "invoice-" + UUID.randomUUID() + ".txt";
        InputStream inputStream = new java.io.ByteArrayInputStream(invoiceContent.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        backblazeS3Client.putObject(bucketName, fileName, inputStream, null);
        log.info("Invoice uploaded to Backblaze S3: {}/{}", bucketName, fileName);
        return fileName;
    }
}
