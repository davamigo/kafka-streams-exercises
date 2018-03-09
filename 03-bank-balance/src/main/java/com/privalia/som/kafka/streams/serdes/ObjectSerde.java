package com.privalia.som.kafka.streams.serdes;

import com.privalia.som.kafka.streams.entity.Balance;
import com.privalia.som.kafka.streams.entity.Transaction;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Serializer/deserializer for Kafka Streams. Serializes/deserializes an entity to/from a JSON string.
 *
 * @param <T> The entity class
 * @author david.amigo
 */
public class ObjectSerde<T extends Object> implements Serde<T> {

    /** Serializer class */
    private final Serializer<T> serializer;

    /** Deserializer class */
    private final Deserializer<T> deserializer;

    /**
     * Constructor
     *
     * @param serializer   the serializer object
     * @param deserializer the deserializer object
     */
    private ObjectSerde(Serializer<T> serializer, Deserializer<T> deserializer) {
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    /**
     * Configures the serde
     *
     * @param configs the configuration map
     * @param isKey   whether is key or not
     */
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    /**
     * Closes the serde
     */
    @Override
    public void close() {
    }

    /**
     * @return the serializer object
     */
    @Override
    public Serializer<T> serializer() {
        return this.serializer;
    }

    /**
     * @return the deserializer object
     */
    @Override
    public Deserializer<T> deserializer() {
        return this.deserializer;
    }

    /**
     * Serializer/deserializer for Transaction class
     */
    public static class TransactionSerde extends ObjectSerde<Transaction> {
        public TransactionSerde() {
            super(new ObjectSerializer.TransactionSerializer(), new ObjectDeserializer.TransactionDeserializer());
        }
    }

    /**
     * Serializer/deserializer for Balance class
     */
    public static class BalanceSerde extends ObjectSerde<Balance> {
        public BalanceSerde() {
            super(new ObjectSerializer.BalanceSerializer(), new ObjectDeserializer.BalanceDeserializer());
        }
    }
}
