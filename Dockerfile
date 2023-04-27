# Utiliza una imagen de Heroku para Java
FROM heroku/jvm

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de compilación de Gradle al contenedor
COPY build/libs/demo-1.0.0.jar app.jar

# Expone el puerto en el que se ejecuta la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "app.jar"]