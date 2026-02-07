# Workshop Demo AWS

Este microservicio Spring Boot procesa pedidos recibidos desde una cola SQS de AWS, los almacena en PostgreSQL (Neon), genera facturas y las sube a un bucket S3 (Backblaze B2), y opcionalmente publica el contenido de la factura en un tópico SNS. Todas las integraciones son parametrizables y pueden activarse/desactivarse por propiedades.

## Funcionalidades principales
- Lectura asíncrona de mensajes desde AWS SQS.
- Validación y persistencia de pedidos en PostgreSQL (Neon).
- Generación de factura en texto plano.
- Subida de facturas a un bucket S3 (Backblaze B2, S3-compatible, sin costo para PoC).
- Publicación opcional del contenido de la factura en un tópico SNS (puede desactivarse para evitar costos).
- Configuración de todas las credenciales y endpoints por properties.
- Feature toggles para activar/desactivar S3 y SNS sin cambiar código.

## Requisitos
- Java 21
- Gradle
- Cuenta AWS (solo para SQS/SNS, SNS puede estar desactivado)
- Cuenta Backblaze B2 (solo para S3)
- Cuenta Neon (PostgreSQL cloud)

## Compilación y ejecución

1. Construir el proyecto:
   ```bash
   ./gradlew build
   ```

2. Ejecutar la aplicación:
   ```bash
   ./gradlew bootRun
   ```

## Configuración principal (`src/main/resources/application.properties`)

- Configura las credenciales y endpoints de AWS y Backblaze B2.
- Ejemplo de propiedades relevantes:

```properties
# AWS SQS
spring.cloud.aws.credentials.access-key=... # AWS
spring.cloud.aws.credentials.secret-key=... # AWS
spring.cloud.aws.region.static=us-east-1
spring.cloud.aws.sqs.queue-name=https://sqs.us-east-1.amazonaws.com/123456789012/mi-cola
spring.cloud.aws.sqs.enabled=true

# PostgreSQL Neon
spring.datasource.url=jdbc:postgresql://... # Neon
spring.datasource.username=...
spring.datasource.password=...

# S3 Backblaze
spring.cloud.aws.s3.bucket-name=workshop-demo-bucket
spring.cloud.aws.s3.invoice-prefix=invoices/
spring.cloud.aws.s3.endpoint=https://s3.us-east-005.backblazeb2.com
backblaze.s3.access-key=... # Backblaze
backblaze.s3.secret-key=... # Backblaze
feature.s3.enabled=true

# SNS (opcional)
spring.cloud.aws.sns.topic-arn=... # AWS SNS topic ARN
feature.sns.enabled=false # true para activar, false para desactivar
```

## Ejemplo de mensaje válido para SQS

```json
{
  "email": "cliente@ejemplo.com",
  "products": "Producto A, Producto B, Producto C"
}
```

## Notas importantes
- Si no deseas incurrir en costos, mantén `feature.sns.enabled=false` y usa Backblaze B2 para S3.
- Puedes activar/desactivar S3 y SNS solo cambiando las properties.
- Si no puedes usar Docker/LocalStack, las pruebas de SNS deben hacerse en AWS real (puedes dejarlo desactivado para PoC sin costo).

## Troubleshooting
- Verifica logs para mensajes de error de conexión o credenciales.
- Si usas Backblaze, asegúrate de que el endpoint tenga el esquema `https://`.
- Las credenciales de Backblaze y AWS deben ser distintas y estar correctamente ubicadas en el archivo de properties.

## Autor
- Taller de integración cloud, 2026

## Diagrama de componentes

```mermaid
flowchart TD
    subgraph Cloud
        SQS[SQS (Pedidos)]
        SNS[SNS (Facturas)]
        S3[S3 (Backblaze/LocalStack)]
        DB[(PostgreSQL Neon)]
    end
    App[Spring Boot Microservicio]
    User[Usuario]
    User-- Mensaje pedido -->SQS
    SQS-- Mensaje -->App
    App-- Guarda pedido -->DB
    App-- Sube factura -->S3
    App-- Publica factura -->SNS
```

- El usuario envía un pedido a SQS.
- El microservicio procesa el mensaje, guarda el pedido en la base de datos, genera la factura y la sube a S3.
- Opcionalmente, publica el contenido de la factura en SNS.
- Todos los componentes pueden ser reales (AWS/Backblaze/Neon) o simulados (LocalStack).

---



## ⚠️ Nota importante sobre LocalStack (2026)

A partir de marzo 2026, LocalStack requiere Docker para funcionar, incluso si se instala con pip y entorno virtual de Python. El modo sin Docker ya no es soportado oficialmente y muchas funcionalidades no estarán disponibles o fallarán si Docker no está presente.

### ¿Qué significa esto?
- Instalar LocalStack solo con Python ya no es suficiente.
- Si tu entorno no permite instalar Docker, no podrás usar LocalStack para pruebas locales completas.
- La infraestructura local (SQS, Lambda, S3, SNS) solo funcionará con LocalStack si Docker está instalado y corriendo.

### Alternativas si no puedes usar Docker
- Usa AWS real (con recursos gratuitos y feature toggles para evitar costos).
- Simula partes del flujo usando mocks o pruebas unitarias.
- Cuando tengas acceso a un entorno con Docker, retoma la sección de infraestructura local y sigue los pasos de infra/localstack/README.md.

---

## 🚀 Instalación y uso de LocalStack con Docker (recomendado y oficial)

A partir de 2026, la forma recomendada y soportada oficialmente para usar LocalStack es mediante Docker. Puedes elegir entre varias opciones según tu preferencia y entorno:

### Opción 1: Usar LocalStack CLI (requiere Docker instalado)

1. Instala Docker Desktop: https://docs.docker.com/get-docker/
2. Instala LocalStack CLI (opcional, para gestión más sencilla):
   ```bash
   pip install localstack
   # o descarga el binario oficial: https://github.com/localstack/localstack-cli/releases
   ```
3. Inicia LocalStack:
   ```bash
   localstack start
   ```

### Opción 2: Usar solo Docker (sin Python ni pip)

1. Instala Docker Desktop: https://docs.docker.com/get-docker/
2. Ejecuta LocalStack directamente con Docker:
   ```bash
   docker run --rm -it \
     -p 4566:4566 -p 4510-4559:4510-4559 \
     -v /var/run/docker.sock:/var/run/docker.sock \
     localstack/localstack
   ```
   - Puedes agregar variables de entorno con `-e NOMBRE=valor`.
   - Para persistencia: `-e LOCALSTACK_PERSISTENCE=1`
   - Para logs: `-e DEBUG=1`

### Opción 3: Usar Docker Compose

1. Instala Docker Desktop y Docker Compose.
2. Crea un archivo `docker-compose.yml` con el siguiente contenido:
   ```yaml
   version: '3.8'
   services:
     localstack:
       image: localstack/localstack
       ports:
         - "4566:4566"
         - "4510-4559:4510-4559"
       environment:
         - DEBUG=1
         - LOCALSTACK_PERSISTENCE=1
       volumes:
         - "/var/run/docker.sock:/var/run/docker.sock"
   ```
3. Inicia LocalStack:
   ```bash
   docker compose up
   ```

### Referencias oficiales
- [Documentación oficial LocalStack - Instalación](https://docs.localstack.cloud/aws/getting-started/installation/)
- [Docker Hub LocalStack](https://hub.docker.com/r/localstack/localstack)
- [Quickstart LocalStack](https://docs.localstack.cloud/aws/getting-started/quickstart/)

### Notas importantes
- A partir de marzo 2026, LocalStack requiere autenticación (auth token) para algunas funciones avanzadas y consolidará sus imágenes. Consulta [este blog oficial](https://blog.localstack.cloud/the-road-ahead-for-localstack/) para detalles.
- El modo sin Docker (solo Python/pip) ya no es soportado oficialmente para la mayoría de flujos.
- Si usas Lambda, el volumen `/var/run/docker.sock` es obligatorio.
- Puedes seguir usando la CLI de LocalStack para gestión, pero siempre requiere Docker instalado y corriendo.

---

## Próximos pasos

- Aplica la infraestructura local con Terraform (ver infra/localstack/README.md).
- Si tienes problemas, revisa la sección de troubleshooting o pide soporte.

## Resultados

### lambda creacion

``` bash
lambda_form.html:1 Access to fetch at 'https://q5rsh3azvxyjmwrireu2squ6cu0zmfyo.lambda-url.us-east-1.on.aws/' from origin 'null' has been blocked by CORS policy: Response to preflight request doesn't pass access control check: It does not have HTTP ok status.
```

[aws_lamda_created.png]

### Envio desde el formulario web a Lambda/SQS

``` browser
Enviar mensaje a Lambda/SQS
Email:
cliente@ejemplo.com
Productos (separados por coma):
Producto A, Producto B, Producto C
 Enviar
Respuesta: {"status":"Message sent","email":"cliente@ejemplo.com","products":"Producto A, Producto B, Producto C"}
```

[aws_form_send_message.png]

### mensaje recibido en SQS

Mensaje recibido: c58fae4f-5763-409d-87cb-29b38b0b9ec9

{"email": "cliente@ejemplo.com", "products": "Producto A, Producto B, Producto C"}

[aws_message_received.png]

### mensaje procesado en microservicio

``` log
2026-01-30 12:10:53 - c.e.a.w.WorkshopDemoApplication - Started WorkshopDemoApplication in 8.668 seconds (process running for 10.112)
2026-01-30 12:10:54 - c.e.aws.workshop.SqsMessageListener - Received message: {"email": "cliente@ejemplo.com", "products": "Producto A, Producto B, Producto C"}
Hibernate: insert into orders (created_at,email,products,id) values (?,?,?,?)
2026-01-30 12:10:54 - c.a.services.s3.AmazonS3Client - No content length specified for stream data.  Stream contents will be buffered in memory and could result in out of memory errors.
2026-01-30 12:10:55 - com.amazonaws.util.Base64 - JAXB is unavailable. Will fallback to SDK implementation which may be less performant.If you are using Java 9+, you will need to include javax.xml.bind:jaxb-api as a dependency.
2026-01-30 12:10:55 - c.e.a.w.service.BackblazeS3Service - Invoice uploaded to Backblaze S3: workshop-demo-bucket/invoices/invoice-54391168-9ca3-4e45-a40c-0908c0705a4e.txt
2026-01-30 12:10:55 - c.e.aws.workshop.SqsMessageListener - SNS send is disabled by configuration.
2026-01-30 12:10:55 - c.e.aws.workshop.SqsMessageListener - Order processed and invoice handled for email: cliente@ejemplo.com
```

[aws_message_processed.png]

### Neon - tabla orders

[neon_db_record.png]

### Backblaze B2 - bucket S3

[backblaze_s3_bucket.png]

### SNS - tópico (desactivado)
