package com.privalia.som.kafka.streams.producer;

import com.privalia.som.kafka.streams.KafkaStreamsConfig;

import com.privalia.som.kafka.streams.serdes.ObjectSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Configuration of the Kafka Producer
 *
 * @author david.amigo
 */
public class KafkaProducerConfig {

    private final static String BOOTSTRAP_SERVERS = KafkaStreamsConfig.BOOTSTRAP_SERVERS;
    private final static String ACKS_CONFIG_VALUE = "all";
    private final static String RETRIES_CONFIG_VALUE = "3";
    private final static String LINGER_MS_CONFIG_VALUE = "1";
    private final static String ENABLE_IDEMPOTENCE_VALUE = "true";
    public final static String TOPIC = KafkaStreamsConfig.INPUT_TOPIC;

    /**
     * @return the properties to configure the Kafka Producer
     */
    public static Properties getConfig() {

        // Kafka producer properties
        Properties config = new Properties();

        // Kafka bootstrap server property
        config.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProducerConfig.BOOTSTRAP_SERVERS);
        config.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ObjectSerializer.TransactionSerializer.class.getName());

        // Producer acks & retries property
        config.setProperty(ProducerConfig.ACKS_CONFIG, KafkaProducerConfig.ACKS_CONFIG_VALUE);
        config.setProperty(ProducerConfig.RETRIES_CONFIG, KafkaProducerConfig.RETRIES_CONFIG_VALUE);

        // Auto flush messages every 1 ms
        config.setProperty(ProducerConfig.LINGER_MS_CONFIG, KafkaProducerConfig.LINGER_MS_CONFIG_VALUE);

        // Leverage idempotent producer (Kafka 0.11) - Ensure don't push duplicates
        config.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, KafkaProducerConfig.ENABLE_IDEMPOTENCE_VALUE);

        return config;
    }
}
