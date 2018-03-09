# Kafka and Kafka Streams Proofs of Concept

## kafka-poc/kafka-streams-exercises

### 02 - Favourite Colour

#### Statement:

* Take a comma delimited topic of user id and colour: <userid,colour>.
* Filter out bar data. Keep only "red", "green" and "blue" colours.
* A user's favourite colour can change.
* Get the count of the favourite colours: <colour,count>
* Output this to a topic.

#### Configuration:

* brokers.list: **localhost:9092**
* input.topic: **favourite-colour-input**
* output.topic: **favourite-colour-output**

#### Command line:

Run the container with Kafka and ZooKeeper:
```
$ docker run --rm -d \
      -p 2181:2181 \
      -p 9092:9092 \
      -e ADVERTISED_HOST=127.0.0.1 \
      -e ADVERTISED_PORT=9092 \
      --name spotify_kafka_dev \
      spotify/kafka
```

Enter in a bash session in the Kafka container:
```    
$ docker exec -it spotify_kafka_dev bash
```

Create the input, intermediate and output topics. Note the intermediate and output topics are og compacted:
```
# kafka-topics.sh \
    --create \
    --zookeeper 127.0.0.1:2181 \
    --partitions 3 \
    --replication-factor 1 \
    --topic favourite-colour-input
    
# kafka-topics.sh \
    --create \
    --zookeeper 127.0.0.1:2181 \
    --partitions 3 \
    --replication-factor 1 \
    --config cleanup.policy=compact \
    --topic favourite-colour-intermediate
    
# kafka-topics.sh \
    --create \
    --zookeeper 127.0.0.1:2181 \
    --partitions 3 \
    --replication-factor 1 \
    --config cleanup.policy=compact \
    --topic favourite-colour-output
```

List if the topics have been created:
```
# kafka-topics.sh --list --zookeeper 127.0.0.1:2181 | grep "favourite-colour"
    
favourite-colour-input
favourite-colour-intermediate
favourite-colour-output
```

Produce the messages to the input topic:
```
# kafka-console-producer.sh \
    --broker-list 127.0.0.1:9092 \
    --topic favourite-colour-input
    
> stephane,blue
> john,green
> stephane,red
> alice,red
<Ctrl+C>
```

Read all the messages in the input topic:
```
# kafka-console-consumer.sh \
    --bootstrap-server 127.0.0.1:9092 \
    --from-beginning \
    --topic favourite-colour-input

alice,red
stephane,blue
stephane,red
john,green
<Ctrl+C>
```

Read all the messages in the intermediate topic (after executing the Kafka Streams application):
```
# kafka-console-consumer.sh \
    --bootstrap-server 127.0.0.1:9092 \
    --from-beginning \
    --property print.key=true \
    --property print.value=true \
    --property "key.separator=," \
    --topic favourite-colour-intermediate

alice,red
john,green
stephane,blue
stephane,red
<Ctrl+C>
```

Read all the messages in the output topic (after executing the Kafka Streams application):
```
# kafka-console-consumer.sh \
    --bootstrap-server 127.0.0.1:9092 \
    --from-beginning \
    --property 'print.key=true' \
    --property 'print.value=true' \
    --property 'key.separator=,' \
    --property 'key.deserializer=org.apache.kafka.common.serialization.StringDeserializer' \
    --property 'value.deserializer=org.apache.kafka.common.serialization.LongDeserializer' \
    --topic favourite-colour-output

red,2
green,1
<Ctrl+C>
```

List if the topics have been created:
```
# kafka-topics.sh --list --zookeeper 127.0.0.1:2181 | grep "favourite-colour"
    
favourite-colour-app-KSTREAM-AGGREGATE-STATE-STORE-0000000009-changelog
favourite-colour-app-KSTREAM-AGGREGATE-STATE-STORE-0000000009-repartition
favourite-colour-input
favourite-colour-intermediate
favourite-colour-output
```

Stop the Kafka and ZooKeeper container:
```
$ docker stop spotify_kafka_dev
```
