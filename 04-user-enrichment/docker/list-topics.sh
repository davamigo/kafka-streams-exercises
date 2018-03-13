#!/bin/bash

KAFKA_CONTAINER="docker_kafka_dev"

echo -e "kafka-topics.sh --list"
docker exec -it $KAFKA_CONTAINER kafka-topics.sh --list --zookeeper 127.0.0.1:2181
