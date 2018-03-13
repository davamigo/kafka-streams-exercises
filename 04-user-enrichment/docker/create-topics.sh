#!/bin/bash

KAFKA_CONTAINER="docker_kafka_dev"

echo -e "kafka-topics.sh --create --topic users-table"
docker exec -it $KAFKA_CONTAINER kafka-topics.sh --create --zookeeper 127.0.0.1:2181 --partitions 3 --replication-factor 1 --config cleanup.policy=compact --topic users-table

echo -e "\nkafka-topics.sh --create --topic products-table"
docker exec -it $KAFKA_CONTAINER kafka-topics.sh --create --zookeeper 127.0.0.1:2181 --partitions 3 --replication-factor 1 --config cleanup.policy=compact --topic products-table

echo -e "\nkafka-topics.sh --create --topic raw-purchases"
docker exec -it $KAFKA_CONTAINER kafka-topics.sh --create --zookeeper 127.0.0.1:2181 --partitions 3 --replication-factor 1 --topic raw-purchases

echo -e "\nkafka-topics.sh --create --topic enriched-purchases"
docker exec -it $KAFKA_CONTAINER kafka-topics.sh --create --zookeeper 127.0.0.1:2181 --partitions 3 --replication-factor 1 --topic enriched-purchases

echo -e "\nkafka-topics.sh --create --topic error-purchases"
docker exec -it $KAFKA_CONTAINER kafka-topics.sh --create --zookeeper 127.0.0.1:2181 --partitions 3 --replication-factor 1 --topic error-purchases

echo -e "\nkafka-topics.sh --list"
docker exec -it $KAFKA_CONTAINER kafka-topics.sh --list --zookeeper 127.0.0.1:2181
