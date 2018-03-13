package com.privalia.som.kafka.streams.consumer;

import com.privalia.som.kafka.streams.KafkaStreamsConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Properties;

public class KafkaConsumerConfig {

    private final static String BOOTSTRAP_SERVERS = KafkaStreamsConfig.BOOTSTRAP_SERVERS;
    private final static String ENABLE_AUTO_COMMIT_VALUE = "true";
    private final static String AUTO_COMMIT_INTERVAL_MS_VALUE = "1000";
    private final static String AUTO_OFFSET_RESET_VALUE = "earliest";
    private final static String GROUP_ID_VALUE = "consumers-test";

    public final static String USERS_TABLE = KafkaStreamsConfig.USERS_TABLE;
    public final static String PRODUCTS_TABLE = KafkaStreamsConfig.PRODUCTS_TABLE;
    public final static String PURCHASES_INPUT_TOPIC = KafkaStreamsConfig.PURCHASES_INPUT_TOPIC;
    public final static String PURCHASES_OUTPUT_TOPIC = KafkaStreamsConfig.PURCHASES_OUTPUT_TOPIC;
    public final static String PURCHASES_ERROR_TOPIC = KafkaStreamsConfig.PURCHASES_ERROR_TOPIC;

    /**
     * @return the properties to configure the Kafka Consumer
     */
    public static Properties getConfig() {

        // Kafka consumer properties
        Properties config = new Properties();

        // Kafka bootstrap server property
        config.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConsumerConfig.BOOTSTRAP_SERVERS);
        config.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        config.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Enable auto commit offsets every 1 second
        config.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, KafkaConsumerConfig.ENABLE_AUTO_COMMIT_VALUE);
        config.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, KafkaConsumerConfig.AUTO_COMMIT_INTERVAL_MS_VALUE);

        // What to do when there is no initial offset in Kafka
        config.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaConsumerConfig.AUTO_OFFSET_RESET_VALUE);

        // The consumer group id identifies the consumer group this consumer belongs to
        config.setProperty(ConsumerConfig.GROUP_ID_CONFIG, KafkaConsumerConfig.GROUP_ID_VALUE);

        return config;
    }
}
