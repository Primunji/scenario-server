FROM openjdk:17-jdk

LABEL authors="socury"

ARG JAR_FILE=./build/libs/scenario-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

WORKDIR /app
COPY .env .env

COPY ./src/main/resources/keystore.p12 keystore.p12

EXPOSE 443

ENTRYPOINT ["java","-jar","/app.jar"]