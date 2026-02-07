package com.example.aws.workshop.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.cloud.aws")
@Getter
@Setter
public class AwsProperties {
    private Credentials credentials = new Credentials();
    private Region region = new Region();
    private Sqs sqs = new Sqs();

    @Getter
    @Setter
    public static class Credentials {
        private String accessKey;
        private String secretKey;
    }

    @Getter
    @Setter
    public static class Region {
        private String static_;
    }

    @Getter
    @Setter
    public static class Sqs {
        private String queueName;
        private String region;
    }
}
