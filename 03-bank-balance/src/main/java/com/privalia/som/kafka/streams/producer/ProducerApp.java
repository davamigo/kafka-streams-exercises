package com.privalia.som.kafka.streams.producer;

import com.privalia.som.kafka.streams.entity.Transaction;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Random;

/**
 * Kafka Producer application - produces many random transactions to the Kafka input topic
 *
 * @author david.amigo
 */
public class ProducerApp {

    /** Number of transactions to create */
    private static final int ITERATIONS = 100;

    /** Max amount for a single transaction */
    private static final int MAX_AMOUNT = 100;

    /** The names of the accounts */
    private static final String[] accounts = {
            "Alfred",
            "Charlie",
            "John",
            "Richard",
            "Alice",
            "Carol",
            "Elisa",
            "Mary"
    };

    /**
     * Entry point of the ProducerApp
     *
     * @param args the program arguments
     */
    public static void main(String[] args) {

        // The producer is the object used to write messages to Kafka
        Producer<String, Transaction> producer =
                new org.apache.kafka.clients.producer.KafkaProducer<>(KafkaProducerConfig.getConfig());

        for (int i = 0; i < ITERATIONS; ++i) {

            // Build random transaction data
            Transaction transaction = newRandomTransaction();

            // The producer record contains the message to send to Kafka
            ProducerRecord<String, Transaction> message =
                    new ProducerRecord<>(KafkaProducerConfig.TOPIC, transaction);

            // Send the message to Kafka
            producer.send(message);
        }

        // Close the connection with Kafka
        producer.close();
    }

    /**
     * @return a random account name
     */
    private static String getRandomAccountName() {
        return accounts[(new Random()).nextInt(accounts.length)];
    }

    /**
     * @return a random amount
     */
    private static long getRandomAmount() {
        return (long)(new Random()).nextInt(MAX_AMOUNT);
    }

    /**
     * @return a random transaction
     */
    static Transaction newRandomTransaction() {

        String name = getRandomAccountName();
        long amount = getRandomAmount();
        Date time = new Date();

        return new Transaction(name, amount, time);
    }

    /**
     * @return the accounts
     */
    static String[] getAccounts() {
        return accounts;
    }
}
