# Spring Boot Change Data Capture Example with Debezium (PostgreSQL + Debezium + Kafka)

## Debezium

Debezium is an open source distributed platform for change data capture. Start it up, point it at your databases, and
your apps can start responding to all of the inserts, updates, and deletes that other apps commit to your databases.
Debezium is durable and fast, so your apps can respond quickly and never miss an event, even when things go wrong.

## Technologies

* [Spring Boot](https://spring.io/)

## Prerequisites

* [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
* [JDK](https://openjdk.java.net/)
* [Maven](https://maven.apache.org/)
* [Docker](https://www.docker.com/)

## Run this project

### Note

Modify the KAFKA_ADVERTISED_HOST_NAME in docker-compose.yml to match your docker host IP (Note: Do not use localhost or
127.0.0.1 as the host ip if you want to run multiple brokers.)

Set your local ip to env variable.

```shell
export KAFKA_ADVERTISED_HOST_NAME=$(ifconfig | grep "inet " | grep -Fv 127.0.0.1 | awk '{print $2}')
```

Check the env variable

```shell
echo $KAFKA_ADVERTISED_HOST_NAME
```

### Steps

1 . Clone project on your machine

```shell
git clone git@github.com:BatuhanKucukali/spring-boot-cdc-example-with-debezium.git
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

You can also check [api-doc](debezium-rest-doc.http)

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

You can also check [api-doc](customer-api-rest-doc.http)

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

* https://debezium.io
* https://github.com/debezium/debezium-examples/tree/master/tutorial
* https://github.com/codefreshdemo/example-springboot-kafka
* https://reflectoring.io/spring-boot-kafka/
* https://github.com/codefreshdemo/example-springboot-kafka

## Getting help

If you're having trouble getting this project running, feel free
to [open an issue](https://github.com/BatuhanKucukali/spring-boot-cdc-example-with-debezium/issues/new)
or [email me](mailto:mail@batuhankucukali.com)!