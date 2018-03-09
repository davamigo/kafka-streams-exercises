package com.privalia.som.kafka.streams.producer;

import com.privalia.som.kafka.streams.entity.Transaction;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test for ProducerApp class
 *
 * @author david.amigo
 */
public class ProducerAppTest {

    @Test
    public void newRandomTransactionTests() {

        Transaction transaction = ProducerApp.newRandomTransaction();

        assertTrue(Arrays.asList(ProducerApp.getAccounts()).contains(transaction.getName()));
        assertTrue(transaction.getAmount() >= 0 && transaction.getAmount() < 100);
        assertNotNull(transaction.getTime());
    }
}
