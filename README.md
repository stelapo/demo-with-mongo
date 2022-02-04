## git repo ##
https://github.com/stelapo/demo-with-mongo.git


## maven install ##
mvn install

## docker build and run ONLY MONGO STACK ##
docker-compose -f mongo-stack.yml up -d

## docker force rebuild and run ONLY MONGO STACK ##
docker-compose -f mongo-stack.yml up -d --build


## docker build and run ##
docker-compose up -d

## docker force rebuild and run ##
docker-compose up -d --build

# Application components #
1. db: Mongo
2. app: Spring Boot