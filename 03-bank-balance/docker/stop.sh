#!/bin/bash

YELLOW='\033[0;33m'
REGULAR='\033[0m'

echo -e "${YELLOW}Stoping the Kafka + ZooKeeper docker container...${REGULAR}"

KAFKA_CONTAINER="docker_kafka_dev"

docker stop $KAFKA_CONTAINER
