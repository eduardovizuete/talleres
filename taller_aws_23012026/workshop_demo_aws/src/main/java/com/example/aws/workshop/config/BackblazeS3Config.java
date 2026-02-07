package com.example.aws.workshop.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración dedicada para el cliente S3 de Backblaze B2.
 * Permite usar credenciales y endpoint independientes de los servicios AWS nativos.
 * Principios SOLID: Single Responsibility (configura solo S3), Dependency Injection (bean AmazonS3), Open/Closed (fácil de extender para otros proveedores S3).
 */
@Configuration
public class BackblazeS3Config {

    @Value("${backblaze.s3.access-key}")
    private String accessKey;

    @Value("${backblaze.s3.secret-key}")
    private String secretKey;

    @Value("${spring.cloud.aws.s3.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    /**
     * Bean AmazonS3 dedicado a Backblaze B2, con endpoint y credenciales propios.
     * @return AmazonS3 client configurado para Backblaze
     */
    @Bean(name = "backblazeS3Client")
    public AmazonS3 backblazeS3Client() {
        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration(endpoint, region))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withPathStyleAccessEnabled(true) // Requerido por la mayoría de S3 compatibles
                .build();
    }
}
