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

import java.util.Collections;
import java.util.Date;

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

//        // Consume the USERS topic
//        consumeTopic(
//                new Consumer<Long, User>(new LongDeserializer(), new CustomDeserializer.ForUser()),
//                KafkaConsumerConfig.USERS_TABLE
//        );

//        // Consume the PRODUCTS topic
//        consumeTopic(
//                new Consumer<Long, Product>(new LongDeserializer(), new CustomDeserializer.ForProduct()),
//                KafkaConsumerConfig.PRODUCTS_TABLE
//        );

//        // Consume the RAW PURCHASES topic
//        consumeTopic(
//                new Consumer<Long, Purchase>(new LongDeserializer(), new CustomDeserializer.ForPurchase()),
//                KafkaConsumerConfig.PURCHASES_INPUT_TOPIC
//        );

        // Consume the OUTPUT PURCHASES topic
        consumeTopic(
                new Consumer<Long, Purchase>(new LongDeserializer(), new CustomDeserializer.ForPurchase()),
                KafkaConsumerConfig.PURCHASES_OUTPUT_TOPIC
        );

//        // Consume the ERROR PURCHAES topic
//        consumeTopic(
//                new Consumer<Long, Purchase>(new LongDeserializer(), new CustomDeserializer.ForPurchase()),
//                KafkaConsumerConfig.PURCHASES_ERROR_TOPIC
//        );
    }

    /**
     * Consumers a single topic
     *
     * @param consumer  the Kafka consumer
     * @param topic     The topic to consume
     */
    private static void consumeTopic(Consumer consumer, String topic) {
        consumer.consumeTopic(topic);
    }

    /**
     * Inner private class to extend a Kafka consumer with new functionality
     *
     * @param <K> Key
     * @param <V> Value
     */
    private static class Consumer<K, V> extends KafkaConsumer<K, V> {

        /**
         * Constructor
         *
         * @param keyDeserializer   key deserializer
         * @param valueDeserializer value deserializer
         */
        public Consumer(Deserializer<K> keyDeserializer, Deserializer<V> valueDeserializer) {
            super(KafkaConsumerConfig.getConfig(), keyDeserializer, valueDeserializer);
        }

        /**
         * Consumes the topic - ininite loop
         *
         * @param topic the topic name
         */
        public void consumeTopic(String topic) {

            // Subscribe to the topic
            this.subscribe(Collections.singletonList(topic));

            long lastEventTime = (new Date()).getTime();
            long eventCount = 0;
            long eventDiffSum = 0;
            boolean lastDataShown = false;

            // Infinite loop
            while (true) {
                ConsumerRecords<K, V> consumerRecords = this.poll(100);
                for (ConsumerRecord<K, V> consumerRecord : consumerRecords) {
                    long currentEventTime = (new Date()).getTime();
                    ++eventCount;
                    lastDataShown = false;

                    long diff = currentEventTime - lastEventTime;
                    if (diff > 1000) {
                        eventCount = 0;
                        eventDiffSum = 0;
                        showStats(eventDiffSum, eventCount);
                    } else {
                        eventDiffSum += diff;
//                        System.out.println(eventCount + ": " + consumerRecord.value().toString());
//                        showStats(eventDiffSum, eventCount);
                    }

                    lastEventTime = currentEventTime;
                }

                if (!lastDataShown) {
                    showStats(eventDiffSum, eventCount);
                    lastDataShown = true;
                }
            }
        }

        /**
         * Shows the statistics in the console
         *
         * @param eventDiffSum Sum of time of each event
         * @param eventCount   Event count
         */
        private void showStats(long eventDiffSum, long eventCount) {
            float avgPerEvent = (float) eventDiffSum / (float) eventCount;
            float avgPerSecond = 1000f * (float) eventCount / (float) eventDiffSum;
            System.out.println(String.format("%d - %.2f ms. %.2f ev/sec", eventCount, avgPerEvent, avgPerSecond));
        }
    }
}
