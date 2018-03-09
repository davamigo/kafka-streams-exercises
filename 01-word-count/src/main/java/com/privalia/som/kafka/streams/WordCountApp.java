package com.privalia.som.kafka.streams;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Arrays;
import java.util.Properties;

/**
 * WordCountApp
 *
 * @author david.amigo
 */
public class WordCountApp {

    /**
     * Entry point of the WordCountApp
     *
     * @param args program arguments
     */
    public static void main(String[] args) {

        // Kafka Streams configuration properties
        Properties streamsConfig = new Properties();
        streamsConfig.put(StreamsConfig.APPLICATION_ID_CONFIG, "word-count-app");
        streamsConfig.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        streamsConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        streamsConfig.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        streamsConfig.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // Kafka Streams topology builder
        KStreamBuilder builder = new KStreamBuilder();

        // 1 - Create a stream from a Kafka topic
        KStream<String, String> wordCountInput =  builder.stream("word-count-input");

        KTable<String, Long> wordCounts = wordCountInput

                // 2 - Map values to lowercase
                .mapValues(value -> value.toLowerCase())

                // 3 - Flatmap values to split by space
                .flatMapValues(value -> Arrays.asList(value.split(" ")))

                // 4 - Select key to apply a key (we discard the old key)
                .selectKey((key, value) -> value)

                // 5 - Group by key before aggregation
                .groupByKey()

                // 6 - Count occurrences
                .count("Counts");

        // 7 - Write the results back to a Kafka topic
        wordCounts.to(Serdes.String(), Serdes.Long(), "word-count-output");

        // Create the streams object and start it
        KafkaStreams streams = new KafkaStreams(builder, streamsConfig);
        streams.start();

        // Print the topology
        System.out.println(streams.toString());

        // Add shutdown hook to stop the Kafka Streams threads.
        // Tou can optionally provide a timeout to `close`
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
