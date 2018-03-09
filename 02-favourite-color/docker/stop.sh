#!/bin/bash

YELLOW='\033[0;33m'
REGULAR='\033[0m'

echo -e "${YELLOW}Stoping the Kafka + ZooKeeper docker container...${REGULAR}"

docker stop spotify_kafka_dev
