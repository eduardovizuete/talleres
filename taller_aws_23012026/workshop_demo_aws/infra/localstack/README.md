
# ⚠️ LocalStack suspendido temporalmente

**A partir de marzo 2026, LocalStack requiere Docker para funcionar.**
Actualmente, este entorno de desarrollo no permite instalar Docker, por lo que la opción de pruebas locales completas con LocalStack queda suspendida hasta que se disponga de un entorno compatible.

## ¿Qué hacer mientras tanto?
- Puedes seguir usando AWS real (con recursos gratuitos y feature toggles para evitar costos).
- Simula partes del flujo usando mocks o servicios alternativos (por ejemplo, una base de datos local, o pruebas unitarias para lógica de negocio).
- Cuando tengas acceso a un entorno con Docker, retoma estos pasos y este README.

---

# Infraestructura LocalStack (Terraform) — [SUSPENDIDO]

> **IMPORTANTE:** El resto de este README queda en pausa hasta que Docker esté disponible en tu entorno.

## Recursos creados
- Cola SQS
- Tópico SNS
- Bucket S3
- Lambda con URL pública

## Requisitos (cuando Docker esté disponible)
- Docker Desktop instalado y corriendo
- Python 3.12.x (instalado con el Python Install Manager: `py install 3.12-64`)
- Entorno virtual creado y activado (`py -3.12 -m venv .venv` y activar)
- LocalStack Community instalado en el entorno virtual (`pip install localstack`)
- Terraform >= 1.3

## Pasos (retomar cuando Docker esté disponible)

1. Inicia Docker Desktop y asegúrate de que esté corriendo.
2. Inicia LocalStack:
   ```bash
   localstack start
   ```
3. Empaqueta la Lambda:
   ```bash
   cd infra/localstack
   zip lambda.zip lambda_function.py
   ```
4. Inicializa y aplica Terraform:
   ```bash
   terraform init
   terraform plan
   terraform apply -auto-approve
   ```
5. Obtén las URLs y ARNs de los recursos en la salida.

## Notas
- Si es tu primer uso, revisa la sección de instalación de Python y LocalStack en el README principal del proyecto.
- Puedes modificar los nombres de recursos en terraform.tfvars.
- El código de la Lambda debe ser compatible con LocalStack (puedes reutilizar lambda_function.py de AWS).
- Si cambias el código de la Lambda, vuelve a empaquetar y aplicar.
- Si no puedes instalar Docker, usa AWS real o mocks hasta que puedas retomar estas pruebas.

## Ejemplo de mensaje válido para SQS

```json
{
  "email": "cliente@ejemplo.com",
  "products": "Producto A, Producto B, Producto C"
}
```
