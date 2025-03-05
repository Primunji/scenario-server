FROM openjdk:17-jdk

LABEL authors="tkddmskr"

ARG JAR_FILE=./build/libs/fishingstop-server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

WORKDIR /app
COPY .env .env

EXPOSE 443

ENTRYPOINT ["java","-jar","/app.jar"]