# Kafka and Kafka Streams Proofs of Concept

## kafka-poc/kafka-streams-exercises

### 04 - User Enrichment

#### Configuration:

* brokers list: `localhost:9092`
* input topics:
    * `raw-purchases`
    * `users-table`
    * `products-table`
* output topics:
    * `enriched-purchases`
    * `error-purchases`

#### Command line:

Run the container with Kafka and ZooKeeper:
```
$ docker run --rm -d \
      -p 9092:9092 \
      -e KAFKA_ADVERTISED_HOST_NAME=localhost \
      --name docker_kafka_dev \
      blacktop/kafka
```

Enter in a bash session in the Kafka container:
```    
$ docker exec -it docker_kafka_dev bash
```

Create the input and output topics:
```    
# kafka-topics.sh \
    --create \
    --zookeeper 127.0.0.1:2181 \
    --partitions 3 \
    --replication-factor 1 \
    --config cleanup.policy=compact \
    --topic users-table
    
# kafka-topics.sh \
    --create \
    --zookeeper 127.0.0.1:2181 \
    --partitions 3 \
    --replication-factor 1 \
    --config cleanup.policy=compact \
    --topic products-table
    
# kafka-topics.sh \
    --create \
    --zookeeper 127.0.0.1:2181 \
    --partitions 3 \
    --replication-factor 1 \
    --topic raw-purchases
    
# kafka-topics.sh \
    --create \
    --zookeeper 127.0.0.1:2181 \
    --partitions 3 \
    --replication-factor 1 \
    --topic enriched-purchases
    
# kafka-topics.sh \
    --create \
    --zookeeper 127.0.0.1:2181 \
    --partitions 3 \
    --replication-factor 1 \
    --topic error-purchases
```

List if the topics have been created:
```
# kafka-topics.sh --list --zookeeper 127.0.0.1:2181
    
enriched-purchases
error-purchases
products-table
raw-purchases
users-table
```

Read all the messages in the **output topic** (after executing the Kafka Streams application):
```

# kafka-console-consumer.sh \
    --bootstrap-server 127.0.0.1:9092 \
    --from-beginning \
    --property 'print.key=true' \
    --property 'print.value=true' \
    --property 'key.separator=,' \
    --property 'key.deserializer=org.apache.kafka.common.serialization.LongDeserializer' \
    --property 'value.deserializer=org.apache.kafka.common.serialization.StringDeserializer' \
    --topic enriched-purchases
    
<Ctrl+C>
```

Read all the messages in the **error topic** (after executing the Kafka Streams application):
```

# kafka-console-consumer.sh \
    --bootstrap-server 127.0.0.1:9092 \
    --from-beginning \
    --property 'print.key=true' \
    --property 'print.value=true' \
    --property 'key.separator=,' \
    --property 'key.deserializer=org.apache.kafka.common.serialization.LongDeserializer' \
    --property 'value.deserializer=org.apache.kafka.common.serialization.StringDeserializer' \
    --topic error-purchases
    
<Ctrl+C>
```

Stop the Kafka and ZooKeeper container:
```
$ docker stop docker_kafka_dev
```
