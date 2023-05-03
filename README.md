Requisitos de despliegue
-	Tener instalado Docker compose

Instrucciones de Despliegue
1.	Clonar el proyecto de github:  https://github.com/jorg3lui5/demo

2.	Ejecutar scripts de base de datos en caso de que no estén creadas las tablas. El archivo con los scripts tiene el nombre: BaseDatos.sql

3.	Ir a la carpeta raíz del proyecto y ejecutar: 
docker-compose up

Nota: Con Docker se creó también una base de datos, para que la api se conecte a dicha base de datos.

Consumo de servicios
- Para el consumo de servicios se tiene el contrato Open Api, donde se puede consumir dichos servicios. Estos archivos tienen los nombres openapi.yaml o openapi.json.
Estos se puede mirar de la siguiente manera: 
 
La ruta base de los servicios son: 
http://localhost:8080/v1/test/

