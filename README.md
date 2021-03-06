# service-digital-warehouse
A service for importing, browsing and selling different wares.

## Requirements
- [Java 11](https://www.oracle.com/se/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://www.docker.com/products/docker-desktop)

## Install and run
Build java service
```bash
$ mvn clean package
```
## 
Build images, set up network and open ports
```bash
$ docker-compose build --no-cache
```
## 
Start service and database
```bash
$ docker-compose up
```
## 
Swagger Mac/Linux
```bash
$ open http://localhost:8080/swagger-ui.html
```
Swagger Windows
```bash
$ start http://localhost:8080/swagger-ui.html
```
