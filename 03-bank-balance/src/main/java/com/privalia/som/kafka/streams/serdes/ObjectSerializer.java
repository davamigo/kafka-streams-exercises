package com.privalia.som.kafka.streams.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.privalia.som.kafka.streams.entity.Balance;
import com.privalia.som.kafka.streams.entity.Transaction;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * Converts an entity object to a JSON string using Jackson
 *
 * @param <T> The entity class
 * @author david.amigo
 */
public class ObjectSerializer<T extends Object> implements Serializer<T> {

    /** Jackson object mapper */
    private final ObjectMapper objectMapper;

    /**
     * Default constructor
     */
    private ObjectSerializer() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Configures the serializer
     *
     * @param configs the configuration map
     * @param isKey   whether is key or not
     */
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    /**
     * Closes the serializer
     */
    @Override
    public void close() {
    }

    /**
     * Serializes the entity to a JSON string
     *
     * @param topic  the topic name
     * @param entity the entity to serialize
     * @return a string
     */
    @Override
    public byte[] serialize(String topic, T entity) {
        if (entity == null) {
            return null;
        } else {
            try {
                return this.objectMapper.writeValueAsBytes(entity);
            } catch (JsonProcessingException exc) {
                throw new SerializationException("Error serializing an entity object to a JSON string!", exc);
            }
        }
    }

    /**
     * Transaction serializer subclass
     */
    public static class TransactionSerializer extends ObjectSerializer<Transaction> {
        public TransactionSerializer() {
            super();
        }
    }

    /**
     * Balance serializer subclass
     */
    public static class BalanceSerializer extends ObjectSerializer<Balance> {
        public BalanceSerializer() {
            super();
        }
    }
}
