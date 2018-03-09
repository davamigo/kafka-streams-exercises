# Kafka and Kafka Streams Proofs of Concept

## kafka-poc/kafka-streams-exercises

### 03 - Bank Balance

#### Configuration:

* brokers.list: **localhost:9092**
* input.topic: **bank-transactions**
* output.topic: **bank-balance**

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

Create the input and output topics. Note the output topic is log compacted:
```
# kafka-topics.sh \
    --create \
    --zookeeper 127.0.0.1:2181 \
    --partitions 3 \
    --replication-factor 1 \
    --topic bank-transactions
    
# kafka-topics.sh \
    --create \
    --zookeeper 127.0.0.1:2181 \
    --partitions 3 \
    --replication-factor 1 \
    --config cleanup.policy=compact \
    --topic bank-balance
```

List if the topics have been created:
```
# kafka-topics.sh --list --zookeeper 127.0.0.1:2181 | grep "bank-"
    
bank-balance
bank-transactions
```

Read all the messages in the input topic:
```
# kafka-console-consumer.sh \
    --bootstrap-server 127.0.0.1:9092 \
    --from-beginning \
    --topic bank-transactions
    
{"name":"Mary","amount":42,"time":"2018-03-09T11:49:22"}
{"name":"Charlie","amount":60,"time":"2018-03-09T11:49:22"}
{"name":"Alice","amount":52,"time":"2018-03-09T11:49:22"}
(...)
{"name":"Alfred","amount":56,"time":"2018-03-09T11:49:22"}
<Ctrl+C>
Processed a total of 100 messages
```

Read all the messages in the output topic (after executing the Kafka Streams application):
```

# kafka-console-consumer.sh \
    --bootstrap-server 127.0.0.1:9092 \
    --from-beginning \
    --property 'print.key=true' \
    --property 'print.value=true' \
    --property 'key.separator=,' \
    --topic bank-balance
    
Alice,{"name":"Alice","amount":52,"transactionCount":1,"maxTransactionAmount":52,"minTransactionAmount":52}
Alfred,{"name":"Alfred","amount":39,"transactionCount":1,"maxTransactionAmount":39,"minTransactionAmount":39}
Alfred,{"name":"Alfred","amount":110,"transactionCount":2,"maxTransactionAmount":71,"minTransactionAmount":39}
(...)
Charlie,{"name":"Charlie","amount":666,"transactionCount":15,"maxTransactionAmount":99,"minTransactionAmount":0}
<Ctrl+C>
Processed a total of 100 messages
```

List if the topics have been created:
```
# kafka-topics.sh --list --zookeeper 127.0.0.1:2181 | grep "bank-"
    
bank-balance
bank-balance-app-aggregate-changelog
bank-balance-app-aggregate-repartition
bank-transactions
```

Stop the Kafka and ZooKeeper container:
```
$ docker stop docker_kafka_dev
```
