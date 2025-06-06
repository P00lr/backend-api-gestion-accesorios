#!/bin/bash
./mvnw clean package
java -jar target/api-gestion-accesorios-0.0.1-SNAPSHOT.jar
