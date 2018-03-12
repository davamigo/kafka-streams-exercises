#!/bin/bash

YELLOW='\033[0;33m'
REGULAR='\033[0m'

echo -e "${YELLOW}Starting the Kafka + ZooKeeper docker container...${REGULAR}"

KAFKA_CONTAINER="docker_kafka_dev"

#docker run --rm -d \
#    -p 2181:2181 -p 3030:3030 \
#    -p 8081:8081 -p 8082:8082 \
#    -p 8083:8083 -p 9092:9092 \
#    -e ADV_HOST=127.0.0.1 \
#    --name $KAFKA_CONTAINER \
#    landoop/fast-data-dev

#docker run --rm -d \
#    -p 2181:2181 \
#    -p 9092:9092 \
#    -e ADVERTISED_HOST=127.0.0.1 \
#    -e ADVERTISED_PORT=9092 \
#    --name $KAFKA_CONTAINER \
#    spotify/kafka

# https://hub.docker.com/r/blacktop/kafka/
docker run --rm -d \
    -p 9092:9092 \
    -e KAFKA_ADVERTISED_HOST_NAME=localhost \
    --name $KAFKA_CONTAINER \
    blacktop/kafka
