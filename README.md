# service-digital-warehouse
A service for importing, browsing and selling different wares.

## Requirements
- Docker
- Maven

## Install and run
Build java service
```bash
$ mvn clean install
```
## 
Build images, set up network and open ports
```bash
$ docker-compose build
```
## 
Start service and database
```bash
$ docker-compose up
```