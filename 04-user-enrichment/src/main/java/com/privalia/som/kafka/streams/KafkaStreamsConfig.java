package com.privalia.som.kafka.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

/**
 * Configuration of the Kafka Streams for this APP
 *
 * @author david.amigo
 */
public class KafkaStreamsConfig {

    public final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private final static String APPLICATION_ID = "user-enrichment-app";
    private final static String AUTO_OFFSET_RESET = "earliest";
    private final static String CACHE_DISABLED = "0";

    public final static String USERS_TABLE = "users-table";
    public final static String PRODUCTS_TABLE = "products-table";
    public final static String PURCHASES_INPUT_TOPIC = "raw-purchases";
    public final static String PURCHASES_OUTPUT_TOPIC = "enriched-purchases";
    public final static String PURCHASES_ERROR_TOPIC = "error-purchases";

    /**
     * Gets the Kafka Streams configuration properties.
     *
     * @return the properties to configure Kafka Streams for this app
     */
    public static Properties getConfig() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, KafkaStreamsConfig.APPLICATION_ID);
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaStreamsConfig.BOOTSTRAP_SERVERS);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaStreamsConfig.AUTO_OFFSET_RESET);
//        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
//        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Disable the cache to demonstrate all the "steps" involved in the transformation - not recommended in prod
        config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, KafkaStreamsConfig.CACHE_DISABLED);

        return config;
    }
}
