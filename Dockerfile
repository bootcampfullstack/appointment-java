#Step 1 - package appointment-backend
FROM maven:3.8.4-openjdk-17-slim AS build

RUN apt-get update && apt-get install -y tzdata
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY . /home/app/appointment-backend
RUN mvn -v
RUN mvn -f /home/app/appointment-backend/pom.xml clean package


#Step 2 - Run appointment-backend
FROM openjdk:17-slim

RUN apt-get update && apt-get install -y tzdata
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY --from=build /home/app/appointment-backend/target/*.jar /usr/local/lib/appointment-backend.jar
VOLUME /tmp
EXPOSE 8080:8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/appointment-backend.jar"]

