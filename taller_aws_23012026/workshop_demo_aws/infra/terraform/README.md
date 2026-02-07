# Terraform AWS Lambda SQS Publisher

This module provisions an AWS Lambda function with a public Function URL that publishes messages to an SQS queue. All resource names are prefixed with `workshop-demo-aws-eduviz`.

## Prerequisites
- AWS account and credentials

> **Importante:**
> El usuario de AWS que utilices para aplicar esta infraestructura debe pertenecer a un grupo con permisos suficientes para crear roles IAM, funciones Lambda y otros recursos. Para evitar errores de permisos (como AccessDenied al crear roles), adjunta la política **AdministratorAccess** al grupo del usuario (por ejemplo, `aws-workshop-group`).
>
> **¿Cómo hacerlo?**
> 1. Ve a la consola de AWS IAM.
> 2. Selecciona “Grupos” y elige el grupo al que pertenece tu usuario.
> 3. Haz clic en “Adjuntar políticas”.
> 4. Busca y selecciona **AdministratorAccess**.
> 5. Haz clic en “Adjuntar política”.
>
> Esto otorga acceso total a todos los servicios de AWS a los usuarios de ese grupo. Para ambientes productivos, se recomienda crear una política personalizada con los permisos mínimos necesarios.

- Terraform >= 1.3
- Python 3.12+ (for Lambda runtime)
- [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-install.html) (for packaging Lambda)

## Setup


1. **Configure variables:**
   
    You can provide variables in two ways:
   
    - **Option 1: Using a terraform.tfvars file (recommended)**
     
       Create a file named `terraform.tfvars` in this directory with the following content:
     
      ```hcl
      aws_region    = "us-east-1"
      aws_access_key = "TU_ACCESS_KEY"
      aws_secret_key = "TU_SECRET_KEY"
      sqs_queue_url  = "https://sqs.us-east-1.amazonaws.com/123456789012/tu-queue"
      sqs_queue_arn  = "arn:aws:sqs:us-east-1:123456789012:tu-queue"
      # resource_prefix = "workshop-demo-aws-eduviz" # opcional
      ```
     
       Terraform detectará este archivo automáticamente.
   
    - **Option 2: Using CLI arguments**
     
       Pasa las variables con `-var` al ejecutar `terraform apply` (ver paso 3).
   
    > **Nota:** No subas terraform.tfvars a repositorios públicos si contiene credenciales reales.

2. **Package Lambda code:**
   ```bash
   cd infra/terraform
   zip lambda.zip lambda_function.py
   ```

3. **Initialize and apply Terraform:**


   ```bash
   terraform init
   terraform plan
   # Revisa la salida para ver qué cambios se harán. Si todo es correcto:
   terraform apply -auto-approve
   # Si NO tienes un archivo terraform.tfvars, puedes pasar las variables así:
   # terraform apply -auto-approve \
   #   -var="aws_region=us-east-1" \
   #   -var="aws_access_key=YOUR_KEY" \
   #   -var="aws_secret_key=YOUR_SECRET" \
   #   -var="sqs_queue_url=YOUR_SQS_URL"
   ```

   > **Recomendación:** Usa siempre `terraform plan` antes de `terraform apply` para validar los cambios que se aplicarán en tu infraestructura.

    > **Nota:** Si tienes un archivo terraform.tfvars, NO es necesario pasar los parámetros -var. Terraform los toma automáticamente.

4. **Get the Lambda Function URL:**
   - Output will show `lambda_function_url`. Use this URL to POST messages to SQS via Lambda.

## Example Request

## Web Form Example

## ✅ Verificación exitosa

El flujo completo Lambda + SQS + formulario web ha sido validado y funciona correctamente:

- El formulario HTML permite enviar mensajes a la Lambda.
- La Lambda reenvía los mensajes a la cola SQS configurada.
- Los permisos IAM y CORS están correctamente configurados.

Si necesitas cambiar la URL de la Lambda o la cola SQS, recuerda actualizar las variables y redeplegar con Terraform.
> **Nota:** Ahora es necesario definir también la variable `sqs_queue_arn` (ARN de la cola SQS) en terraform.tfvars, ya que las políticas IAM requieren el ARN y no la URL para conceder permisos.

### Permisos para enviar mensajes a SQS

La Lambda requiere permisos explícitos para enviar mensajes a la cola SQS. El archivo main.tf ahora incluye una política IAM adicional para permitir sqs:SendMessage sobre la cola configurada.

**Si modificas los permisos o la política:**
1. Ejecuta:
   ```bash
   cd infra/terraform
   terraform plan
   terraform apply -auto-approve
   ```
2. Luego prueba de nuevo el formulario web.

### Soporte CORS para navegadores

La función Lambda ahora incluye cabeceras CORS para permitir peticiones desde el navegador (por ejemplo, usando el formulario HTML). Si modificas lambda_function.py, recuerda volver a empaquetar y desplegar:

1. Ejecuta:
   ```bash
   cd infra/terraform
   zip lambda.zip lambda_function.py
   terraform apply -auto-approve
   ```
2. Luego recarga el formulario HTML en tu navegador y prueba de nuevo.

Si accedes a la URL de la Lambda directamente desde el navegador, seguirá sin mostrar un formulario (solo responde a POST/JSON), pero ahora el formulario HTML funcionará correctamente.

> **Nota importante:**
> La URL de la Lambda no muestra un formulario web por sí sola. Es un endpoint HTTP que solo acepta peticiones POST con JSON. Si accedes desde el navegador, verás un error como `{ "error": "Missing request body" }`.

Si deseas una interfaz gráfica para enviar mensajes, puedes usar el archivo `lambda_form.html` incluido en esta carpeta. Este archivo HTML muestra un formulario sencillo para ingresar email y productos, y al enviarlo realiza un POST a la Lambda:

1. Abre `lambda_form.html` en tu navegador.
2. Llena los campos y haz clic en "Enviar".
3. Verás la respuesta de la Lambda en pantalla.

Puedes modificar la variable `lambdaUrl` en el HTML si tu URL cambia.


The Lambda expects a JSON body with the following fields:




      - `email` (string, required): User email
      - `products` (string, required): Product list or description
    
      **Lambda Function URL generada:**
    
      ```
      https://q5rsh3azvxyjmwrireu2squ6cu0zmfyo.lambda-url.us-east-1.on.aws/
      ```
    
      **Ejemplo de request:**
    
      ```
      curl -X POST "https://q5rsh3azvxyjmwrireu2squ6cu0zmfyo.lambda-url.us-east-1.on.aws/" \
         -H "Content-Type: application/json" \
         -d '{"email": "user@example.com", "products": "product1,product2"}'
      ```
    
      If any required field is missing or empty, the Lambda will return a 400 error with a descriptive message.
