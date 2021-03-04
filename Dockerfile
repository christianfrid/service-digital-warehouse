FROM openjdk:15-alpine

WORKDIR /usr/src/
COPY target/service-digital-warehouse-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]