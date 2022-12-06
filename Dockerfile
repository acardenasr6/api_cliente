# Imagen base
FROM openjdk:8-jdk-alpine

# Autor
MAINTAINER "desarrollo@acardenas.com.pe"

# Argumentos
ARG JAR_FILE=target/*.jar

# Puertos expuestos
EXPOSE 8081

# Volumen termporal donde Spring Boot crea directorios para Tomcat de forma predeterminada.
VOLUME /tmp

# Agrega la ruta del jar
COPY ${JAR_FILE} /home/app.jar

# Run
ENTRYPOINT java $JAVA_OPTS \
                -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n \
                -Djava.security.egd=file:/dev/./urandom \
                -jar /home/app.jar