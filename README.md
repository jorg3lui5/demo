Instrucciones de Despliegue
1.	Clonar el proyecto de github:  https://github.com/jorg3lui5/demo
2.	Ejecutar scripts de base de datos en caso de que no estén creadas las tablas. El archivo con los scripts tiene el nombre: BaseDatos.sql
3.	Contar con la versión de Gradle 7.4.2
4.	Tener java versión 11.
5.	Ejecutar la aplicación con IntelliJ IDEA u otro framework
6.	Compilar con : gradle clean build


Consumo de servicios

Para el consumo de servicios se tiene el contrato Open Api, donde se puede consumir dichos servicios. Estos archivos tienen los nombres openapi.yaml o openapi.json


La ruta base de los servicios son: 
http://localhost:8080/v1/test/
