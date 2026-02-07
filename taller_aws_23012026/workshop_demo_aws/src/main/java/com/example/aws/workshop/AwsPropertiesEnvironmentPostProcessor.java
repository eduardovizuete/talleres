package com.example.aws.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class AwsPropertiesEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String region = environment.getProperty("cloud.aws.region.static");
        String accessKey = environment.getProperty("cloud.aws.credentials.access-key");
        String secretKey = environment.getProperty("cloud.aws.credentials.secret-key");

        if (region != null && !region.isBlank()) {
            System.setProperty("aws.region", region);
            // also set common env-style property names in case some providers check them
            System.setProperty("AWS_REGION", region);
        }

        if (accessKey != null && !accessKey.isBlank()) {
            System.setProperty("aws.accessKeyId", accessKey);
            System.setProperty("AWS_ACCESS_KEY_ID", accessKey);
        }

        if (secretKey != null && !secretKey.isBlank()) {
            System.setProperty("aws.secretAccessKey", secretKey);
            System.setProperty("AWS_SECRET_ACCESS_KEY", secretKey);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
