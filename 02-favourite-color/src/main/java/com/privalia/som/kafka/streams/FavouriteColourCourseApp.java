package com.privalia.som.kafka.streams;

import java.util.Arrays;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;

public class FavouriteColourCourseApp {

    public static void main(String[] args) {

        // Kafka Streams topology builder
        KStreamBuilder builder = new KStreamBuilder();

        // Step 1: We create the topic of users keys to colours
        KStream<String, String> textLines = builder.stream(KafkaStreamsConfig.INPUT_TOPIC);

        // Filter commas, select the name as key, map the color as value, filter non-valid colors
        KStream<String, String> usersAndColours = textLines

                // 1 - we ensure that a comma is here as we will split on it
                .filter((key, value) -> value.contains(","))

                // 2 - we select a key that will be the user id (lowercase for safety)
                .selectKey((key, value) -> value.split(",")[0].toLowerCase())

                // 3 - we get the colour from the value (lowercase for safety)
                .mapValues(value -> value.split(",")[1].toLowerCase())

                // 4 - we filter undesired colours (could be a data sanitization step
                .filter((user, colour) -> Arrays.asList("green", "blue", "red").contains(colour));

        // Write to intermediate log-compacted topic in Kafka
        usersAndColours.to(KafkaStreamsConfig.INTERMEDIATE_TOPIC);

        // step 2 - we read that topic as a KTable so that updates are read correctly
        KTable<String, String> usersAndColoursTable = builder.table(KafkaStreamsConfig.INTERMEDIATE_TOPIC);

        // step 3 - we count the occurrences of colours
        KTable<String, Long> favouriteColours = usersAndColoursTable

                // 5 - we group by colour within the KTable
                .groupBy((user, colour) -> new KeyValue<>(colour, colour))
                .count("CountsByColours");

        // 6 - we output the results to a Kafka Topic - don't forget the serializers
        favouriteColours.to(Serdes.String(), Serdes.Long(),"favourite-colour-output");

        // Create the streams object
        KafkaStreams streams = new KafkaStreams(builder, KafkaStreamsConfig.getConfig());

        // only do this in dev - not in prod
        streams.cleanUp();
        streams.start();

        // print the topology
        System.out.println(streams.toString());

        // shutdown hook to correctly close the streams application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}
