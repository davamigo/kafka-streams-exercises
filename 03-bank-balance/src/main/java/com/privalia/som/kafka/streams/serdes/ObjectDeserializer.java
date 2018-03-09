package com.privalia.som.kafka.streams.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.privalia.som.kafka.streams.entity.Balance;
import com.privalia.som.kafka.streams.entity.Transaction;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

/**
 * Converts a JSON string to an entityClass object using Jackson
 *
 * @param <T> The entityClass class
 * @author david.amigo
 */
public class ObjectDeserializer<T extends Object> implements Deserializer<T> {

    /** Jackson object mapper */
    private final ObjectMapper objectMapper;

    /** The destination entityClass */
    private final Class<T> entityClass;

    /**
     * Constructor
     *
     * @param entityClass The destination entity
     */
    private ObjectDeserializer(Class<T> entityClass) {
        this.objectMapper = new ObjectMapper();
        this.entityClass = entityClass;
    }

    /**
     * Configures the deserializer
     *
     * @param configs the configuration map
     * @param isKey   whether is key or not
     */
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    /**
     * Closes the deserializer
     */
    @Override
    public void close() {
    }

    /**
     * Deserializes the JSON string into an entityClass
     *
     * @param topic the topic name
     * @param data  the string to deserialize
     * @return an entityClass
     */
    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        } else {
            try {
                return objectMapper.readValue(data, this.entityClass);
            } catch (IOException exc) {
                throw new SerializationException("Error deserializing a JSON string to an entityClass object!", exc);
            }
        }
    }

    /**
     * Transaction deserializer subclass
     */
    public static class TransactionDeserializer extends ObjectDeserializer<Transaction> {
        public TransactionDeserializer() {
            super(Transaction.class);
        }
    }

    /**
     * Balance deserializer subclass
     */
    public static class BalanceDeserializer extends ObjectDeserializer<Balance> {
        public BalanceDeserializer() {
            super(Balance.class);
        }
    }
}
