package com.privalia.som.kafka.streams;

import com.privalia.som.kafka.streams.entity.Product;
import com.privalia.som.kafka.streams.entity.Purchase;
import com.privalia.som.kafka.streams.entity.User;
import com.privalia.som.kafka.streams.serdes.CustomSerde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

/**
 * User enrichment Kafka Streams application
 *
 * @author david.amigo
 */
public class KafkaStreamsUserEnrichmentApp {

    /**
     * Entry point of the KafkaStreamsUserEnrichmentApp
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

        // Get a global table out of Kafka. This table will be replicated on each Kafka Streams application
        // The Key of our GlobalKTable for users is the user ID.
        GlobalKTable<Long, User> usersGlobalTable = builder.globalTable(
                Serdes.Long(),
                CustomSerde.forUser(),
                KafkaStreamsConfig.USERS_TABLE
        );

        // The Key of our GlobalKTable for products is the product ID.
        GlobalKTable<Long, Product> productsGlobalTable = builder.globalTable(
                Serdes.Long(),
                CustomSerde.forProduct(),
                KafkaStreamsConfig.PRODUCTS_TABLE
        );

        // Get the stream of purchases
        KStream<Long, Purchase> purchasesStrem = builder.stream(
                Serdes.Long(),
                CustomSerde.forPurchase(),
                KafkaStreamsConfig.PURCHASES_INPUT_TOPIC
        );

        // Enrich the purchases stream with a left join with user data and then with product data
        KStream<Long, Purchase> purchasesEnrichedStream = purchasesStrem.leftJoin(
                usersGlobalTable,
                (purchaseId, purchase) -> purchase.getUserId(),
                (purchase, user) -> {
                    if (user != null) {
                        purchase.enrich(user);
                    } else {
                        purchase.setError(true);
                    }
                    return purchase;
                }
        ).leftJoin(
                productsGlobalTable,
                (purchaseId, purchase) -> purchase.getProductId(),
                (purchase, product) -> {
                    if (product != null) {
                        purchase.enrich(product);
                    } else {
                        purchase.setError(true);
                    }
                    return purchase;
                }
        );

        // Filter valid purchases to the output topic
        KStream<Long, Purchase> purchaseValidStream = purchasesEnrichedStream.filter(
                (purchaseId, purchase) -> !purchase.isError()
        );

        purchaseValidStream.to(
                Serdes.Long(),
                CustomSerde.forPurchase(),
                KafkaStreamsConfig.PURCHASES_OUTPUT_TOPIC
        );

        // Filter purchases with errors to an error topic
        KStream<Long, Purchase> purchaseErrorsStream = purchasesEnrichedStream.filter(
                (purchaseId, purchase) -> purchase.isError()
        );

        purchaseErrorsStream.to(
                Serdes.Long(),
                CustomSerde.forPurchase(),
                KafkaStreamsConfig.PURCHASES_ERROR_TOPIC
        );

        return builder;
    }
}
