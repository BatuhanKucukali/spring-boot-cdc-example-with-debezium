# Spring Boot Change Data Capture Example with Debezium (PostgreSQL + Debezium + Kafka)

## Technologies

* [Spring Boot](https://spring.io/)

## Prerequisites

* [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* [JDK](https://openjdk.java.net/)
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)

## Run this project

1 . Clone project on your machine

```shell
git@github.com:BatuhanKucukali/spring-boot-cdc-example-with-debezium.git
```

2 . Change directory

```shell
cd spring-boot-cdc-example-with-debezium
```

3 . Run Dependencies

```shell
docker-compose up
```

4 . Run Projects

```shell
cd customer-api
./mvnw spring-boot:run

cd customer-projection-consumer
./mvnw spring-boot:run
```

5. Create Connector

````shell
curl --location --request POST 'http://localhost:8083/connectors' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "customer-connector",
    "config": {
        "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
        "tasks.max": "1",
        "database.hostname": "postgres",
        "database.port": "5432",
        "database.user": "postgres",
        "database.password": "postgres",
        "database.dbname": "user",
        "database.server.name": "master-db"
    }
}'
````

6. Create User with API

```bash
curl --location --request POST 'http://localhost:7010' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@doe.test"
}'
```

7. Check Database

customer-api database (PostgreSQL)

| id          | first_name  | last_name   | email           |
| ----------- | ----------- | ----------- | --------------- |
| 1           | John        | Doe         | john@doe.test   |

customer-projection-consumer database (MariaDB)

| id          | first_name  | last_name   | email           |
| ----------- | ----------- | ----------- | --------------- |
| 1           | John        | Doe         | john@doe.test   |

## Sources

https://github.com/debezium/debezium-examples/tree/master/tutorial
https://github.com/codefreshdemo/example-springboot-kafka
https://reflectoring.io/spring-boot-kafka/

## Getting help

If you're having trouble getting this project running, feel free
to [open an issue](https://github.com/BatuhanKucukali/spring-boot-cdc-example-with-debezium/issues/new)
or [email me](mailto:mail@batuhankucukali.com)!