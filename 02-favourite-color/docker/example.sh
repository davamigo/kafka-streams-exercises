#!/bin/bash

YELLOW='\033[0;33m'
CYAN='\033[0;36m'
REGULAR='\033[0m'

KAFKA_IMAGE="spotify_kafka_dev"
KAFKA_BIN="/opt/kafka_2.11-0.10.1.0/bin"

echo -e "${YELLOW}Creating the input topic...${REGULAR}"

docker exec -t \
    $KAFKA_IMAGE \
    $KAFKA_BIN/kafka-topics.sh \
        --zookeeper 127.0.0.1:2181 \
        --create \
        --partitions 3 \
        --replication-factor 1 \
        --topic favourite-colour-input


echo -e "\n${YELLOW}Creating the intermediate topic...${REGULAR}"

docker exec -t \
    $KAFKA_IMAGE \
    $KAFKA_BIN/kafka-topics.sh \
        --zookeeper 127.0.0.1:2181 \
        --create \
        --partitions 3 \
        --replication-factor 1 \
        --config cleanup.policy=compact \
        --topic favourite-colour-intermediate


echo -e "\n${YELLOW}Creating the output topic...${REGULAR}"

docker exec -t \
    $KAFKA_IMAGE \
    $KAFKA_BIN/kafka-topics.sh \
        --zookeeper 127.0.0.1:2181 \
        --create \
        --partitions 3 \
        --replication-factor 1 \
        --config cleanup.policy=compact \
        --topic favourite-colour-output


echo -e "\n${CYAN}List topics created:${REGULAR}"

docker exec -t \
    $KAFKA_IMAGE \
    $KAFKA_BIN/kafka-topics.sh \
        --zookeeper 127.0.0.1:2181 \
        --list \
        | grep "favourite-colour"


echo -e "\n${YELLOW}Producing data to the input topic...${REGULAR}"

echo "stephane,blue" > input.dat
echo "john,green"   >> input.dat
echo "stephane,red" >> input.dat
echo "alice,red"    >> input.dat

cat input.dat | docker exec -i \
    $KAFKA_IMAGE \
    bash -c "$KAFKA_BIN/kafka-console-producer.sh \
        --broker-list 127.0.0.1:9092 \
        --topic favourite-colour-input"

cat input.dat
rm -f input.dat

echo -e "\n${YELLOW}Listening the output consumer...${REGULAR}"
echo -e "${CYAN}Press <Ctrl+C> to stop.${REGULAR}"

docker exec -t \
    $KAFKA_IMAGE \
    bash -c "$KAFKA_BIN/kafka-console-consumer.sh \
        --bootstrap-server 127.0.0.1:9092 \
        --from-beginning \
        --property 'print.key=true' \
        --property 'print.value=true' \
        --property 'key.separator=,' \
        --property 'key.deserializer=org.apache.kafka.common.serialization.StringDeserializer' \
        --property 'value.deserializer=org.apache.kafka.common.serialization.LongDeserializer' \
        --topic favourite-colour-output"
