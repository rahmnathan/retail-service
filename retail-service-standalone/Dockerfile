FROM openjdk:8-jre-slim

RUN mkdir /opt/retail-service && mkdir /opt/retail-service/config

COPY src/main/resources/application.properties /opt/retail-service/application.properties

ARG JAR_FILE
COPY target/$JAR_FILE /opt/retail-service/retail-service.jar

WORKDIR /opt/retail-service/
ENTRYPOINT java -jar retail-service.jar
