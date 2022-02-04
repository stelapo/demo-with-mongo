# git repo 
https://github.com/stelapo/demo-with-mongo.git

* * *

# maven install
mvn install

* * *

# Docker

## docker build and run ONLY MONGO STACK ##
docker-compose -f mongo-stack.yml up -d

## docker force rebuild and run ONLY MONGO STACK ##
docker-compose -f mongo-stack.yml up -d --build

* * *
## docker build and run ##
docker-compose up -d

## docker force rebuild and run ##
docker-compose up -d --build

***
## Test docker with multiple file
docker-compose -f mongo-stack.yml -f app-demo-stack.yml up -d

# Application components
1. db: Mongo
2. app: Spring Boot