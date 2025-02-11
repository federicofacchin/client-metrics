# Client Metrics Challenge

## Breve descripción:

Se ha solicitado el diseño de un sistema para la creacion de clientes , obtencion de metricas como desviacion estandar y promedio de edad.
Ademas se solicito el calculo de la expectativa de vida:
---

## Las herramientas a utilizar serán:

- IntelliJ IDEA Ultimate
- JDK versión 17
- Spring Boot
- JPA/Hibernate
- Mockito
- JUnit 5
- Postman
- Lombok
- Swagger
- PostgreSql
- Git (Github) / Git Bash
- Jackson
- Docker / Docker Compose
- TestContainer
- AWS (RDS, Elastic Beanstalk , EC2 , Cognito)
---
---
## Proceso de desarrollo

Se utilizo Lombock para agilizar el proceso de desarrollo y no tener tanto codigo boiler-plate 

Para levantar el proyecto es necesario tener Docker instalado. Y ademas contar con git instalado.

## Arquitectura
Se decidio optar por una estructura Controller → Service → Repository → Docker Container → Database
Priorizando una arquitectura limpia y separando las responsabilidades de cada capa manteniendo la abstraccion y reduciendo el acoplamiento.

Se utiliza Docker Compose configurado con Springboot para levantar una instancia de la base de datos auto contenida que inicia junto con el proyecto de Spring, de esta manera se puede simular el uso de una base real sin necesidad de tener un gestor de BBDD instalado.
Cabe destacar que se intento separar las configuraciones segun cada ambiente , siendo el ejemplo anterior el usado para Dev de forma local , conectandose a cognito de forma remota.
Mientras que para produccion se utilizo una instancia deployada en EC2 y RDS para la conexion a la BBDD.

### Dto's
Objetos de transferencia de datos, su uso fue mayormente para evitar exponer datos sensibles al front en caso de no ser necesarios.

### Manejador global de excepciones
Proporciona un codigo mas limpio ya que separa de la capa de logica de negocio todo el manejo de excepciones, en el se tratan las excepciones y se muestra un mensaje personalizado.


### ORM - JPA/HIBERNATE
Al usar un ORM se permite cambiar de base de datos segun las necesidades del negocio cambiando unas simples configuraciones.
Por ende es una herramienta muy practica para facilitar el cambio de Base de datos, ademas de la posibilida de mapear las clases en tablas
y los atributos en campos gracias al tag _"@Entity"_

### Unit Testing y Mocks (Junit - Mockito) - Integration test / Test Containers
Se decidio realizar algunos test para comprobar el correcto funcionamiento de ciertos casos de uso utilizando test unitarios con Junit para realizar estos test
y Mockito para inyectar simulaciones de estos y poder probar de forma aislada.

En cuanto a Test de integracion se decidio usar Test Containers, una herramienta muy practica que permite levantar una BBDD en un contenedor docker para realizar pruebas
en una base de datos que replicaria en este caso el ambiente productivo/desarrollo/QA. Con el proposito de hacer las pruebas lo mas real posible y eliminando la posibilidad de sobreescribir la base de datos productiva.

### Descarga del repositorio:
Para esto debemos tener instaladas las herramientas necesarias, pasamos al forkeo del repositorio.Le damos clic derecho a la carpeta donde queramos descargar el proyecto y seleccionamos la opción **GIT BASH
HERE**.Esto lo que hace es abrir la terminal de Git posicionada en la carpeta antes seleccionada. Ejecutamos el
comando *git clone* “url del repositorio”, cambiando lo que está entre comillas por el comando obtenido.

### Intellij Idea:

Una vez instalado IntelliJ IDEA Ultimate, debemos ingresar una licencia de estudiante o una licencia de paga. También
podemos optar por la versión gratuita Community.

Una vez instalado, abrimos el proyecto desde IntelliJ y debemos esperar a que se instalen las dependencias de Maven,
mientras esto sucede, si no tenemos un JDK instalado, en la parte superior del IDE nos aparecerá una barra azul con un
mensaje: “Setup SDK”. Desde aquí ingresamos la versión 17, para la correcta ejecución del proyecto. Una vez instalado
todo, nos movemos al archivo y damos en “Run”.

## Base de Datos:

Para la correcta ejecucion de la base de datos es necesario tener Docker previamente instalado , de esta manera la BBDD se levanta en un contenedor de docker de forma automatica al correr el proyecto.
Simulando un entorno lo mas real posible y esta base se reinicia luego de finalizar la ejecucion.

## Cómo usar postman

Postman nos ayuda a ser más eficientes durante todo el ciclo de vida de desarrollo de una API. Nos permite crear
rápidamente solicitudes con el método HTTP necesario y parámetros en caso de necesitarlo, enviar la solicitud e
inspeccionar fácilmente los resultados.

### Instalando POSTMAN:

Para instalar postman lo podemos descargar desde su página web, postman
https://www.getpostman.com/
Una vez instalado, tenemos la lista de métodos HTTP, regularmente las más utilizadas serán GET, POST, PUT, DELETE.

## Cómo usar Swagger
Para el testeo de endpoints recomiendo usar swagger ya que brinda la facilidad de tener todo precargado y listo para usar.
Al igual que postman, Swagger nos ayuda a ser más eficientes durante todo el ciclo de vida de desarrollo de una API.
Ademas, nos permite generar documentacion automatica con solo unas pocas configuraciones y sin necesidad de descargar nada extra.
La facilidad es que permite documentar endpoints y ademas pueden visualizarse sus estructuras de request/response con ejemplos.
Para acceder a swagger es necesario ingresar una vez levantado el proyecto a : http://localhost:8080/swagger-ui/index.html .
donde podras visualizar todos los endpoints y para poder realizar un request es necesario que clickees en el boton _"try it out"_ 
*tuve inconvenientes utilizando la version de spring boot junto con la configuracion de openApi*
---

### Instrucciones Dev:
En caso de querer utilizar la aplicacion de forma local es necesario clonar el repositorio como se indico anteriormente, ir a las configuraciones dentro de application.yml
![image](https://github.com/user-attachments/assets/5b604a91-274d-4163-a1f5-91d8a157846b)
y cambiar el profile a dev.

al momento de levantar la aplicacion se ejecutara el docker compose el cual levantar la base de datos local y apuntara a cognito.
para poder acceder a cognito es necesario acceder a esta url 
![image](https://github.com/user-attachments/assets/e806ce29-6f21-4eda-a999-09ca7d8f2283)
luego, puede crear su propia cuenta 
![image](https://github.com/user-attachments/assets/c49e7da5-7ef8-47d7-9be8-b03c40d4324f)

una vez creada y validado el mail. Es redirigido al endpoint /clients para ver los clientes creados , tambien se puede reemplazar la url para poder ver las metricas por ejemplo.
![image](https://github.com/user-attachments/assets/6875e93b-176e-4e5e-a83b-0137aa446aae)


### Instrucciones Prod:

Solamente es necesario acceder a esta url:
http://challenge-env.eba-s2sqww3q.us-east-2.elasticbeanstalk.com/ especificando en postman el HTTP Request , en este caso Post para crear clientes. o Get para obtener metricas o clientes
Este se conectara al EC2 y luego este consultara en la base de datos deployada en RDS.


### Posibles mejoras

Utilizar dos DTOs diferentes para los Request / Response los Dtos para esconder los datos inecesarios.

Realizar todos los test necesarios para mejorar el coverage.

Utilizar algun servicio para manejo de secrets, puede ser un archivo .env , integrar Vault para manejo de secrets o el mismo secrets manager de amazon.

Al tener en cuenta un servicio de alta concurrencia si bien el paginado para obtener los resultados es de ayuda para limiar la cantidad de informacion que se envia al front.
Es recomendable utilizar cache para reducir el overhead del aplicativo. Particularmente me gustaria haber intentado incorporar Redis , ya que tiene muchas ventajas.
Para empezar es un sistema de cache distribuido el cual puede ser dockerizado, tiene una base de datos no relacional la cual sirve en caso de una interrupcion inesperada para no perder la informacion cacheada.

establecer un sistema de monitoreo , pensaba en un principio incorporar toda la suite de Aws para esto ya que tiene muchas facilidades. Como por ejemplo Cloudwatch para ver metricas y establecer alarmas.

Integrar Cognito en la version deployada en EC2.

Otra consideracion , al ser deployado en AWS permite facilmente escalarlo , ya que podria en teoria levantar mas instancias del micro-servicio , o aumentar los recursos de la maquina virtual. 

###Aclaracion importante
Al ser una aplicacion de demo/challenge me olvide de comentar que otra buena practica recomendada es importar manualmente las dependencias que estan marcadas con warning ya que tienen vulnerabiliades, se podria por ejemplo importar la dependencia particular que presenta estas vulnerabilidades con una version mas actualizada para asi evitar comprometer el aplicativo en terminos de seguridad.

