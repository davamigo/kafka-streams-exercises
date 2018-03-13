#!/bin/bash

KAFKA_CONTAINER="docker_kafka_dev"

echo -e "kafka-console-consumer.sh --topic error-purchases"
docker exec -it $KAFKA_CONTAINER kafka-console-consumer.sh \
    --bootstrap-server 127.0.0.1:9092 \
    --from-beginning \
    --property 'print.key=true' \
    --property 'print.value=true' \
    --property 'key.separator=,' \
    --property 'key.deserializer=org.apache.kafka.common.serialization.LongDeserializer' \
    --property 'value.deserializer=org.apache.kafka.common.serialization.StringDeserializer' \
    --topic error-purchases

