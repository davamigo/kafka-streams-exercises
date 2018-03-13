package com.privalia.som.kafka.streams.producer;

import com.privalia.som.kafka.streams.entity.Product;
import com.privalia.som.kafka.streams.entity.Purchase;
import com.privalia.som.kafka.streams.entity.User;
import com.privalia.som.kafka.streams.serdes.CustomSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

/**
 * Kafka producer example application - produces the example data to the input topics
 *
 * @author david.amigo
 */
public class KafkaProducerExampleApp {

    /**
     * Entry point of the KafkaProducerExampleApp
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

        // Produce the users
        producer.send(createUserRecord(new User("Clark", "Kent", "iamsuperman@dailyplanet.com")));
        producer.send(createUserRecord(new User("Bruce", "Wayne", "batman@batcave.com")));
        producer.send(createUserRecord(new User("Diana", "Prince", "wonderwoman@amazon.com")));
        producer.send(createUserRecord(new User("Barry", "Allen", "_@theflash.com")));
        producer.send(createUserRecord(new User("Tony", "Stark", "ironamn@avengers.com")));
        producer.send(createUserRecord(new User("Peter", "Parker", "spriderman@avengers.com")));
        producer.send(createUserRecord(new User("Bruce", "Banner", "hulk@avengers.com")));
        producer.send(createUserRecord(new User("Natasha", "Romanoff", "blackwidow@avengers.com")));
        producer.send(createUserRecord(new User("Steve", "Rogers", "oldman@avengers.com")));

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

        // Produce the products
        producer.send(createProductRecord(new Product("Sunglasses", "Unisex sunglasses", 15.0)));
        producer.send(createProductRecord(new Product("Blue pen", "Bic blue pen", 5.0)));
        producer.send(createProductRecord(new Product("Liquid web", "Chemical components to make liquid web", 56.12)));
        producer.send(createProductRecord(new Product("Sword", "Bastard sworkd", 199.99)));
        producer.send(createProductRecord(new Product("Shield", "Vibranium Shield", 1000000.0)));

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

        // Produce the purchases
        producer.send(createPurchaseRecord(new Purchase(8, 1, 1, 25.0)));
        producer.send(createPurchaseRecord(new Purchase(20, 1, 5, 25.0)));
        producer.send(createPurchaseRecord(new Purchase(6, 3, 100, 74.49)));
        producer.send(createPurchaseRecord(new Purchase(1, 25, 1, 99.99)));
        producer.send(createPurchaseRecord(new Purchase(20, 25, 5, 99.99)));
        producer.send(createPurchaseRecord(new Purchase(9, 5, 1, 99999999.49)));


        // Close the connection with Kafka
        producer.close();
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
