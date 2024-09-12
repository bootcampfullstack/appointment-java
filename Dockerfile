FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/agenda-0.0.1-SNAPSHOT.jar /app/agenda.jar

# Defina o comando para executar o JAR
ENTRYPOINT ["java", "-jar", "/app/agenda.jar"]

# Exponha a porta que a aplicação usa
EXPOSE 8080