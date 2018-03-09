package com.privalia.som.kafka.streams;

import com.privalia.som.kafka.streams.entity.Balance;
import com.privalia.som.kafka.streams.entity.Transaction;
import com.privalia.som.kafka.streams.serdes.ObjectSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;

/**
 * Bank balance application
 *
 * @author david.amigo
 */
public class BankBalanceStreamsApp {

    /**
     * Entry point of the BankBalanceStreamsApp
     *
     * @param args the program arguments
     */
    public static void main(String[] args) {

        // Create Kafka Streams topology
        KStreamBuilder builder = createTopology();

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

    /**
     * Creates the topology for this KafkaStreams app
     *
     * @return the builder object for the topology
     */
    private static KStreamBuilder createTopology() {

        // Kafka Streams topology builder
        KStreamBuilder builder = new KStreamBuilder();

        // Create a stream from a the Kafka input topic
        KStream<String, Transaction> transactionsStream = builder
                .stream(KafkaStreamsConfig.INPUT_TOPIC);

        transactionsStream
                // Map bank transactions to extract the account name as the key
                .map(
                        (dummy, transaction) -> KeyValue.pair(transaction.getName(), transaction)
                )

                // Group by key
                .groupByKey()

                // Aggregate the grouped KStream
                .aggregate(
                        () -> new Balance(),
                        (key, transaction, balance) -> balance.applyTransaction(transaction),
                        new ObjectSerde.BalanceSerde(),
                        "aggregate"
                )

                // Write the result to the Kafka output topic
                .to(
                        Serdes.String(),
                        new ObjectSerde.BalanceSerde(),
                        KafkaStreamsConfig.OUTPUT_TOPIC
                );

        return builder;
    }
}
