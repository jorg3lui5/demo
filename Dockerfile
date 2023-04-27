# Utilizar una imagen base de Java
FROM openjdk:11-jre-slim

# Copiar el archivo JAR generado por Gradle al contenedor
COPY build/libs/demo-1.0.0.jar demo-1.0.0.jar

# Exponer el puerto en el que se ejecuta la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "demo-1.0.0.jar"]

# Utiliza una imagen de Heroku para Java

# Utiliza una imagen de OpenJDK
FROM openjdk:11-jdk

FROM heroku/jvm

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado por Gradle al contenedor
COPY build/libs/demo-1.0.0.jar app.jar

# Expone el puerto en el que se ejecuta la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "app.jar"]