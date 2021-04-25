https://github.com/debezium/debezium-examples/tree/master/tutorial
https://github.com/codefreshdemo/example-springboot-kafka
https://reflectoring.io/spring-boot-kafka/


````postgresql
CREATE TABLE customers
(
    id         SERIAL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
````

```bash
docker-compose -f docker-compose.yml exec kafka /kafka/bin/kafka-console-consumer.sh \
    --bootstrap-server kafka:9092 \
    --from-beginning \
    --property print.key=true \
    --topic dbserver1.public.customers
```