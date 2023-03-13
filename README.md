# assets-service

A RESTful microservice with endpoints that allows you to manage assets and perform promotion of those assets

## Tech
- `Docker` (and `docker-compose`)
- Java 17
- `Springboot 3` as microservice framework
- `rabbitmq` for event broadcasting
- `postgresql` for persistency

## Local start up
This is a dockerized microservice that can be bootstrapped locally with the help of `docker-compose`

Steps:
1. `./gradlew build`
2. `docker-compose build`
3. `docker-compose up -d`

## Rest endpoints:
Swagger documentation can be found at: http://127.0.0.1:8080/swagger-ui/index.html#/
It follows openapi 3.0 standard: http://127.0.0.1:8080/api-docs


- ### Create a new asset:
```curl
curl --location 'http://localhost:8080/asset' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Asset E",
    ["parent": 4]

}'
```
- ### Fetch an asset:
```curl
curl --location 'http://localhost:8080/asset/2'
```
- ### Updates an asset
```curl
curl --location --request PUT 'http://localhost:8080/asset/1' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Asset A or B"
}'
```

- ### Deletes an asset:
```curl
curl --location --request DELETE 'http://localhost:8080/asset/2'
```

- ### Promotes an asset, its children and its ancestors:
```curl
curl --location --request PUT 'http://localhost:8080/asset/4/promote'
```


# To be improved:
- `fill_tables.sql` is not working correctly, needs to be investigated.
- Test coverage to models and other classes.
- Integration tests
- Table constraints
- Better exception handler with ControllerAdvice?