# quarkus-couchbase-rest-sample
Simple example of REST interface with Quarkus to interact with Couchbase NO-SQL database services

It offers 2 function: 
* insert a file in the database (POST)
* retrive a file by id from the database (GET)

## Quarkus info
Project is created from https://code.quarkus.io/ with maven. It runs with java 17

## Couchbase info
This sample use the Couchbase docker image, for more info: https://hub.docker.com/_/couchbase
You need to create a user and a bucket, you can see more details in the file "application.properties"

It use the quarkus couchbase extension. This plugin is still in alpha version, you can find more info here: https://github.com/quarkiverse/quarkus-couchbase 


## OpenApi
Url for openapi is http://localhost:8080/quarkus/couchbase/swagger/