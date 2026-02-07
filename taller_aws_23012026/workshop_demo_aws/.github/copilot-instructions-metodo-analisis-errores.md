# Copilot Instructions for workshop_demo_aws

## Project Overview
- This is a Spring Boot microservice that asynchronously reads messages from an AWS SQS queue and prints them to the console.
- The main entry point is `WorkshopDemoApplication.java`.
- Message consumption logic is in `SqsMessageListener.java` using the `@SqsListener` annotation.
- AWS and SQS configuration is managed via `application.properties` using the `spring.cloud.aws.*` property namespace.
- Custom configuration beans are in `config/` (see `SqsConfiguration.java`, `AwsProperties.java`).

## Key Patterns & Conventions
- **Configuration:**
  - All AWS credentials, region, and SQS queue URL must be set in `src/main/resources/application.properties` using the following keys:
    - `spring.cloud.aws.credentials.access-key`
    - `spring.cloud.aws.credentials.secret-key`
    - `spring.cloud.aws.region.static`
    - `spring.cloud.aws.sqs.queue-name`
    - `spring.cloud.aws.sqs.region`
    - `spring.cloud.aws.sqs.enabled`
- **Listener:**
  - The SQS listener uses `@SqsListener("${spring.cloud.aws.sqs.queue-name}")`.
  - Message handling is asynchronous and logs to console.
- **Build & Run:**
  - Use `./gradlew build` to build and `./gradlew bootRun --console=plain` to run.
  - No environment variables are required if properties are set in `application.properties`.
- **Dependencies:**
  - Uses Spring Boot 3.x, Java 21, Spring Cloud AWS 3.x, Lombok.
- **Logging:**
  - Logging configuration is set in `application.properties` for both application and AWS SDK logs.

## Integration Points
- Integrates with AWS SQS using Spring Cloud AWS auto-configuration and custom beans.
- No direct SDK client instantiation outside of `SqsConfiguration.java`.

## Troubleshooting
- If the app fails to start, check for missing or misnamed properties in `application.properties`.
- Always match the property names to the `spring.cloud.aws.*` convention.
- For SQS listener errors, ensure the queue URL is correct and accessible.

## Example application.properties
```
spring.application.name=workshop-demo
spring.cloud.aws.credentials.access-key=YOUR_ACCESS_KEY
spring.cloud.aws.credentials.secret-key=YOUR_SECRET_KEY
spring.cloud.aws.region.static=us-east-1
spring.cloud.aws.sqs.queue-name=https://sqs.us-east-1.amazonaws.com/123456789012/your-queue
spring.cloud.aws.sqs.region=us-east-1
spring.cloud.aws.sqs.enabled=true
logging.level.root=INFO
logging.level.com.example.aws.workshop=DEBUG
logging.level.software.amazon.awssdk=WARN
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %logger{36} - %msg%n
```

## File Reference
- Main: `src/main/java/com/example/aws/workshop/WorkshopDemoApplication.java`
- Listener: `src/main/java/com/example/aws/workshop/SqsMessageListener.java`
- Config: `src/main/java/com/example/aws/workshop/config/`
- Properties: `src/main/resources/application.properties`

---
If any section is unclear or missing, please provide feedback to improve these instructions.
