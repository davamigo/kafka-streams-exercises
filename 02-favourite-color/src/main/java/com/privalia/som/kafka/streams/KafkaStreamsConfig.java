package com.privalia.som.kafka.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

/**
 * Configuration of the Kafka Streams for this APP
 */
public class KafkaStreamsConfig {

    public final static String APPLICATION_ID = "favourite-colour-app";
    public final static String BOOTSTRAP_SERVERS = "localhost:9092";
    public final static String AUTO_OFFSET_RESET = "earliest";
    public final static String INPUT_TOPIC = "favourite-colour-input";
    public final static String OUTPUT_TOPIC = "favourite-colour-output";
    public final static String INTERMEDIATE_TOPIC = "favourite-colour-intermediate";

    /**
     * Gests the Kafka Streams configuration properties.
     *
     * @return the properties to configure Kafka Streams for this app
     */
    public static Properties getConfig() {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, KafkaStreamsConfig.APPLICATION_ID);
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaStreamsConfig.BOOTSTRAP_SERVERS);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, KafkaStreamsConfig.AUTO_OFFSET_RESET);
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // we disable the cache to demonstrate all the "steps" involved in the transformation - not recommended in prod
        config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0");

        return config;
    }
}
