package com.privalia.som.kafka.streams.producer;

import com.privalia.som.kafka.streams.entity.Product;
import com.privalia.som.kafka.streams.entity.Purchase;
import com.privalia.som.kafka.streams.entity.User;
import com.privalia.som.kafka.streams.serdes.CustomSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Random;

/**
 * Kafka producer stress test application - produces random data to the input topics to make a stress test
 *
 * @author david.amigo
 */
public class KafkaProducerStressTestApp {

    private static final long MAX_USERS = 1000000;

    private static final long MAX_PRODUCTS = 5000000;

    /**
     * Entry point of the KafkaProducerStressTestApp
     *
     * @param args the program arguments
     */
    public static void main(String[] args) {

        produceUsersTable();

        produceProductsTable();

        producePurchasesStream();
    }

    /**
     * Creates the GlobalKTable of users by publishing the users in a Kafka topic
     */
    private static void produceUsersTable() {

        // The producer is the object used to write messages to Kafka
        Producer<Long, User> producer = new KafkaProducer<>(
                KafkaProducerConfig.getConfig(),
                new LongSerializer(),
                new CustomSerializer.ForUser()
        );

        // Produce 1,000,000 random users
        for (long i = 1; i <= MAX_USERS; i++) {
            producer.send(createUserRecord(new User(
                    "First#" + i,
                    "Last#" + i,
                    "email_" + i + "@example.com"
            )));
        }

        // Close the connection with Kafka
        producer.close();
    }

    /**
     * Creates the GlobalKTable of products by publishing the products in a Kafka topic
     */
    private static void produceProductsTable() {

        // The producer is the object used to write messages to Kafka
        Producer<Long, Product> producer = new KafkaProducer<>(
                KafkaProducerConfig.getConfig(),
                new LongSerializer(),
                new CustomSerializer.ForProduct()
        );

        // Produce 5,000,000 random PRODUCTS
        for (long i = 1; i <= MAX_PRODUCTS; i++) {
            producer.send(createProductRecord(new Product(
                    "Name#" + i,
                    "Description#" + i,
                    ((double) Math.round(Math.random() * 100000)) / 100
            )));
        }

        // Close the connection with Kafka
        producer.close();
    }

    /**
     * Creates the stream of raw purchases
     */
    private static void producePurchasesStream() {

        // The producer is the object used to write messages to Kafka
        Producer<Long, Purchase> producer = new KafkaProducer<>(
                KafkaProducerConfig.getConfig(),
                new LongSerializer(),
                new CustomSerializer.ForPurchase()
        );

        // Produce infinite random purchases
        while (true) {
            long userId = Math.round(Math.random() * MAX_USERS);
            long productId = Math.round(Math.random() * MAX_PRODUCTS);
            long quantity = Math.round(Math.random() * 10);
            double price = ((double) Math.round(Math.random() * 100000)) / 100;
            producer.send(createPurchaseRecord(new Purchase(userId, productId, quantity, price)));
            try {
                Thread.sleep(10);
            } catch (InterruptedException exc) {
                exc.printStackTrace();
            }
        }
    }

    /**
     * Creates the record to write an user to the Kafka topic
     *
     * @param user the user
     * @return the record
     */
    private static ProducerRecord<Long, User> createUserRecord(User user) {
        return new ProducerRecord<>(KafkaProducerConfig.USERS_TABLE, user.getId(), user);
    }

    /**
     * Creates the record to write an product to the Kafka topic
     *
     * @param product the product
     * @return the record
     */
    private static ProducerRecord<Long, Product> createProductRecord(Product product) {
        return new ProducerRecord<>(KafkaProducerConfig.PRODUCTS_TABLE, product.getId(), product);
    }

    /**
     * Creates the record to write an purchase to the Kafka topic
     *
     * @param purchase the purchase
     * @return the record
     */
    private static ProducerRecord<Long, Purchase> createPurchaseRecord(Purchase purchase) {
        return new ProducerRecord<>(KafkaProducerConfig.PURCHASES_STREAM, purchase.getId(), purchase);
    }
}
