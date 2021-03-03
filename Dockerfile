FROM openjdk:15-alpine
WORKDIR /usr/src/
EXPOSE 8080
COPY target/service-digital-warehouse-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]