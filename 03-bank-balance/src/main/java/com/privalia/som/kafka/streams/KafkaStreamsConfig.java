package com.privalia.som.kafka.streams;

import com.privalia.som.kafka.streams.serdes.ObjectSerde;
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
    public final static String APPLICATION_ID = "bank-balance-app";
    public final static String AUTO_OFFSET_RESET = "earliest";
    public final static String INPUT_TOPIC = "bank-transactions";
    public final static String OUTPUT_TOPIC = "bank-balance";
    public final static String CACHE_DISABLED = "0";

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
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, ObjectSerde.TransactionSerde.class);

        // Disable the cache to demonstrate all the "steps" involved in the transformation - not recommended in prod
        config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, KafkaStreamsConfig.CACHE_DISABLED);

        // Enable "exactly once" behaviour
        config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);

        return config;
    }
}
