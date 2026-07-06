# Imagen Java
FROM openjdk:22-jdk

# Directorio del contenedor
WORKDIR /app

# copiado del archivo jar al contenedor
COPY banquito-0.0.1-SNAPSHOT.jar /app/banquito.jar

# exposicion del puerto que se va a escuchar
EXPOSE 8080
EXPOSE 3306

# comando par ejecutar la aplicacion 
ENTRYPOINT ["java", "-jar", "banquito.jar"]