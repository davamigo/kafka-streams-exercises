package com.privalia.som.kafka.streams.consumer;

import com.privalia.som.kafka.streams.entity.Product;
import com.privalia.som.kafka.streams.entity.Purchase;
import com.privalia.som.kafka.streams.entity.User;
import com.privalia.som.kafka.streams.serdes.CustomDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.LongDeserializer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * Kafka consumer application - consumes one topic
 *
 * @author david.amigo
 */
public class KafkaConsumerExampleApp {

    /**
     * Entry point of the KafkaConsumerExampleApp
     *
     * @param args the program arguments
     */
    public static void main(String[] args) {

//        consumeTopic(
//                new Consumer<Long, User>(new LongDeserializer(), new CustomDeserializer.ForUser()),
//                KafkaConsumerConfig.USERS_TABLE
//        );

//        consumeTopic(
//                new Consumer<Long, Product>(new LongDeserializer(), new CustomDeserializer.ForProduct()),
//                KafkaConsumerConfig.PRODUCTS_TABLE
//        );

//        consumeTopic(
//                new Consumer<Long, Purchase>(new LongDeserializer(), new CustomDeserializer.ForPurchase()),
//                KafkaConsumerConfig.PURCHASES_INPUT_TOPIC
//        );

//        consumeTopic(
//                new Consumer<Long, Purchase>(new LongDeserializer(), new CustomDeserializer.ForPurchase()),
//                KafkaConsumerConfig.PURCHASES_OUTPUT_TOPIC
//        );

        consumeTopic(
                new Consumer<Long, Purchase>(new LongDeserializer(), new CustomDeserializer.ForPurchase()),
                KafkaConsumerConfig.PURCHASES_ERROR_TOPIC
        );
    }

    private static void consumeTopic(Consumer consumer, String topic) {
        consumer.consumeTopic(topic);
    }

    private static class Consumer<K, V> extends KafkaConsumer<K, V> {

        public Consumer(Deserializer<K> keyDeserializer, Deserializer<V> valueDeserializer) {
            super(KafkaConsumerConfig.getConfig(), keyDeserializer, valueDeserializer);
        }

        public void consumeTopic(String topic) {

            // Subscribe to the topic
            this.subscribe(Collections.singletonList(topic));

            // Infinite loop
            long count = 0;
            while (true) {
                ConsumerRecords<K, V> consumerRecords = this.poll(100);
                for (ConsumerRecord<K, V> consumerRecord : consumerRecords) {
                    ++count;
                    System.out.println(count + ": " + consumerRecord.value().toString());
                }
            }
        }
    }
}
