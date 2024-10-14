# Etapa de build
FROM maven:3.8.4-openjdk-17-slim AS build
RUN apt-get update && apt-get install -y tzdata
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY . /home/app/bchbackend
RUN mvn -v
RUN mvn -f /home/app/bchbackend/pom.xml clean
RUN mvn -f /home/app/bchbackend/pom.xml test 
RUN mvn -f /home/app/bchbackend/pom.xml package -DskipTests

# Etapa de execução
FROM openjdk:17-slim
RUN apt-get update && apt-get install -y tzdata
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY --from=build /home/app/bchbackend/target/*.jar /usr/local/lib/bchbackend.jar
VOLUME /tmp
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/usr/local/lib/bchbackend.jar"]