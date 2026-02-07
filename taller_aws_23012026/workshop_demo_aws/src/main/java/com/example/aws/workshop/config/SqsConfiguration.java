package com.example.aws.workshop.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SqsConfiguration {

    private final AwsProperties awsProperties;

    @Bean
    @ConditionalOnProperty(name = "spring.cloud.aws.sqs.enabled", havingValue = "true", matchIfMissing = true)
    public SqsAsyncClient sqsAsyncClient() {
        String regionName = awsProperties.getSqs().getRegion();
        log.info("Configurando cliente SQS con región: {}", regionName);

        String accessKey = awsProperties.getCredentials().getAccessKey();
        String secretKey = awsProperties.getCredentials().getSecretKey();

        if (accessKey == null || accessKey.isEmpty() || secretKey == null || secretKey.isEmpty()) {
            log.warn("Las credenciales de AWS no están configuradas. El cliente SQS usará las credenciales por defecto del sistema.");
            return SqsAsyncClient.builder()
                    .region(Region.of(regionName))
                    .build();
        }

        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);

        return SqsAsyncClient.builder()
                .region(Region.of(regionName))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();
    }
}
