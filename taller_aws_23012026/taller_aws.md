Taller 23/01/2026

Icebreaker

Problema:
	La banca deja de funcionar, cuales son los pasos para abordar el tema a nivel de institucion, sistemas,   
	legislacion y governanza

Propuesta y que puede causar esa propuesta:
	- monitoreo
			determinar que esta causando el problema para buscar la solucion mas rapida posible
			se pierde trazabilidad del problema real
	- reversar a version anterior
			se pierde los nuevos cambios implementados y se retrasan tiempo de roadmap de versiones
	- plan de gestion o restauracion
	- comunicacion de la afectacion a clientes internos y externos

Consultas
	que es war room

Workshop AWS
	- crear cuenta aws
	- Arquitectura basica - tecnologias
			spring boot
			Amazon SQS Un servicio de cola de mensajes
				1 millón de solicitudes de Amazon SQS en forma gratuita cada mes
				crear una nueva cola con nombre workshop-demo
					url de acceso a la nueva cola https://sqs.us-east-1.amazonaws.com/476207618351/workshop-demo
					us-east-1
			Aurora DSQL Base de datos SQL distribuida sin servidor
				crear cluster
					workshop-demo-1
			almacenamiento en disco
			notificaciones correo
			Panel de IAM 
				crear un grupo aws-workshop-group
				crear un user aws-workshop-user
				clave de acceso
					ACCESS_KEY_ID	SECRET_ACCESS_KEY (usar valores ficticios o variables de entorno para pruebas)
			micro spring

- Tarea: 
		Disponibilizar los componentes en sus nubes propias, configurarlos de acuerdo a lo explicado en la sesión, e implementar el microservicio que cumpla la arquitectura de tipo "demo", con las funcionales descritas en los siguientes prompts:
				Prompt 1:

				Crea este microservicio "workshop-demo" en la raíz de ésta carpeta con Spring Boot Java usando Gradle y Lombok, agregando las dependencias e implementando las clases necesarias para lo siguiente:
				  - El microservicio es asíncrono y lee de una cola SQS, escribiendo el contenido de cada mensaje en consola.
				  - El paquete base debe ser "com.example.aws.workshop"
				  - Utiliza las dependencias de Cloud AWS.
				- Se autentica usando un par de access key y secret key (usar variables de entorno o valores ficticios, nunca claves reales en código o documentación).
				  - Las llaves, la región y la cola deben estar configuradas como propiedades de aplicación.

				------------------------------------------------------------------------------------------------------------------------------------------------
				Initializar base de datos con scripts sencillos:

				CREATE SCHEMA IF NOT EXISTS workshop;

				CREATE TABLE workshop.orders (
				    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),  
				    email VARCHAR(50) NOT NULL,
				    products VARCHAR(255) NOT NULL,  
				    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  
				);
				------------------------------------------------------------------------------------------------------------------------------------------------
				Prompt 2:

				Ahora necesito que el mensaje sea guardado en una base de datos Aurora DSQL. Agrega las dependencias e implementa la siguiente funcionalidad:
				- El mensaje recibido debe ser un JSON con campos "email" y "products" de tipo string, ambos obligatorios. De lo contrario, debe arrojar un IllegalArgumentException en consola.
				- Al recibir cada mensaje, se debe insertar una linea en la tabla "orders". Tomar como referencia su DDL descrito a continuación.
				- La tabla y schema ya existen. El micro no debe crear ningún objeto de BD.

				DDL de la tabla "orders":
				'''
				CREATE TABLE workshop.orders (
				    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),  
				    email VARCHAR(50) NOT NULL,
				    products VARCHAR(255) NOT NULL,  
				    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  
				);
				'''
				------------------------------------------------------------------------------------------------------------------------------------------------

				Prompt 3:

				Ahora necesito que el servicio conecte con un Bucket S3. Agrega las dependencias e implementa la siguiente funcionalidad:
				- Después de escribir en la BD, el servicio debe generar un archivo .txt representando una factura con el contenido del mensaje inicial.
				- El archivo no se debe escribir en la máquina local. Debe escribir en un Bucket S3, y la dirección tendrá un prefijo parametrizado como propiedad de aplicación.
 
				------------------------------------------------------------------------------------------------------------------------------------------------

				Prompt 4:

				Ahora necesito que el servicio utilice SNS. Agrega las dependencias y haz que el servicio tome el archivo .txt generado para el Bucket S3 y lo envíe como el contenido de un mensaje hacia un tópico SNS, el cuál debe estar parametrizado como propiedad de aplicacion.
				 

Prompt 5:
Quiero incorporar terraform en éste proyecto. Para ello:
- Inicializa éste proyecto para utilizar terraform dentro de una carpeta "infra/terraform".
- Utiliza el proveedor de AWS.
- Usaremos un usuario de servicio AWS que ya se encuentra creado, del cuál usaremos un accessKey y secretKey que debes dejar como propiedad parametrizable.
- Para los nombres de todos los recursos que crees utiliza el prefijo "workshop-demo-aws-eduviz".
- Como primer componente crea una Lambda con URL de función activa, la cuál debe exponer una pantalla que permita colocar mensajes en la cola SQS preexistente y usada por el microservicio.
- Finalmente, crea un readme con los pasos para configurar y ejecutar correctamente el plan de terraform que preparaste, así como acceder a la URL de la lambda creada.

Prompt 6:
Ahora crea una segunda lambda que tenga la misma funcionalidad que el servicio actual. Queremos migrar el servicio Spring Boot en su totalidad. Considera:
- Continúa usando usando el prefijo "workshop-demo-aws-eduviz".
- La nueva lambda debe usar los mismos componentes que existen actualmente. Es decir, debe escuchar a la cola SQS y ejecutar las acciones iguales al microservicio en el Aurora DSQL, Bucket S3 y tópico SNS.
- Las propiedades de aplicación ahora deben ser variables para terraform.