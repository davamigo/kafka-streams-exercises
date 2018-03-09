#!/bin/bash

YELLOW='\033[0;33m'
REGULAR='\033[0m'

echo -e "${YELLOW}Starting the Kafka + ZooKeeper docker container...${REGULAR}"

docker run --rm -d \
    -p 2181:2181 \
    -p 9092:9092 \
    -e ADVERTISED_HOST=127.0.0.1 \
    -e ADVERTISED_PORT=9092 \
    --name spotify_kafka_dev \
    spotify/kafka

#docker run --rm -d \
#    -p 2181:2181 -p 3030:3030 \
#    -p 8081:8081 -p 8082:8082 \
#    -p 8083:8083 -p 9092:9092 \
#    -e ADV_HOST=127.0.0.1 \
#    --name landoop_kafka_dev \
#    landoop/fast-data-dev
