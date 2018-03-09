package com.privalia.som.kafka.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;

/**
 * Favourite colour application
 *
 * @author david.amigo
 */
public class FavouriteColourMyApp {

    /**
     * Entry point of the FavouriteColourMyApp
     *
     * @param args program arguments
     */
    public static void main(String[] args) {

        // Kafka Streams topology builder
        KStreamBuilder builder = new KStreamBuilder();

        // Create a stream from a the Kafka input topic
        KStream<String, String> inputStream = builder
                .stream(KafkaStreamsConfig.INPUT_TOPIC);

        // Convert to lowercase and filter non-valid colours. Only "red", "green" or "blue" allowed
        KStream<String, String> userAndColorStream = inputStream
                .filter((dummy, textLine) -> textLine.contains(","))
                .map((dummy, textLine) -> {
                    String[] texts = textLine.split(",");
                    return KeyValue.pair(texts[0].toLowerCase(), texts[1].toLowerCase());
                })
                .filter((user, colour) -> (colour.equals("red") || colour.equals("green") || colour.equals("blue")));

        // Write to intermediate log-compacted topic in Kafka
        userAndColorStream.to(KafkaStreamsConfig.INTERMEDIATE_TOPIC);

        // Create a KTable from the intermediate topic
        KTable<String, String> filteredTable = builder.table(KafkaStreamsConfig.INTERMEDIATE_TOPIC);

//        // Convert to stream, change the key to the colour, group by the new key and count
//        KTable<String, Long> coloursTable = filteredTable
//                .toStream()
//                .selectKey((key, value) -> value)
//                .groupByKey()
//                .count("ColorsCount");

        // Convert to stream, change the key to the colour, group by the new key and count
        KTable<String, Long> coloursTable = filteredTable
                .groupBy((user, color) -> KeyValue.pair(color, color))
                .count("ColorsCount");

        // Write the colours count KTable into the output topic
        coloursTable.to(
            Serdes.String(),
            Serdes.Long(),
            KafkaStreamsConfig.OUTPUT_TOPIC
        );

        // Create the Kafka Streams object
        KafkaStreams streams = new KafkaStreams(builder, KafkaStreamsConfig.getConfig());

        // Clean Kafka Streams - only do this in dev - not in prod
        streams.cleanUp();

        // Start Kafka Streams app
        streams.start();

        // Print the topology
        System.out.println(streams.toString());

        // Add shutdown hook to stop the Kafka Streams threads.
        // Tou can optionally provide a timeout to `close`
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
