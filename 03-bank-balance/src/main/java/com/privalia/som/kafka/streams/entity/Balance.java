package com.privalia.som.kafka.streams.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Bank balance entity
 *
 * @author david.amigo
 */
public class Balance {

    /** The name of the account */
    private String name;

    /** The total amount of the balance */
    private long amount;

    /** The total number of transactions */
    private long transactionCount;

    /** The max transaction amount */
    private long maxTransactionAmount;

    /** The min transaction amount */
    private long minTransactionAmount;

    /** The date and time of the last transaction */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private Date lastTransactionTime;

    /**
     * Default constructor
     */
    public Balance() {
        this.name = "";
        this.amount = 0L;
        this.transactionCount = 0L;
        this.maxTransactionAmount = 0L;
        this.minTransactionAmount = Long.MAX_VALUE;
        this.lastTransactionTime = null;
    }

    /**
     * Constructor
     *
     * @param name                  The name of the account
     * @param amount                The total amount of the balance
     * @param transactionCount      The total number of transactions
     * @param maxTransactionAmount  The max transaction amount
     * @param minTransactionAmount  The min transaction amount
     * @param lastTransactionTime   The date and time of the last transaction
     */
    public Balance(
            String name,
            long amount,
            long transactionCount,
            long maxTransactionAmount,
            long minTransactionAmount,
            Date lastTransactionTime
    ) {
        this.name = name;
        this.amount = amount;
        this.transactionCount = transactionCount;
        this.maxTransactionAmount = maxTransactionAmount;
        this.minTransactionAmount = minTransactionAmount;
        this.lastTransactionTime = lastTransactionTime;
    }

    /**
     * @return The name of the account
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name of the account
     * @return this
     */
    public Balance setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return The total amount of the balance
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @param amount The total amount of the balance
     * @return this
     */
    public Balance setAmount(long amount) {
        this.amount = amount;
        return this;
    }

    /**
     * @return The total number of transactions
     */
    public long getTransactionCount() {
        return transactionCount;
    }

    /**
     * @param transactionCount The total number of transactions
     * @return this
     */
    public Balance setTransactionCount(long transactionCount) {
        this.transactionCount = transactionCount;
        return this;
    }

    /**
     * @return The max transaction amount
     */
    public long getMaxTransactionAmount() {
        return maxTransactionAmount;
    }

    /**
     * @param maxTransactionAmount The max transaction amount
     * @return this
     */
    public Balance setMaxTransactionAmount(long maxTransactionAmount) {
        this.maxTransactionAmount = maxTransactionAmount;
        return this;
    }

    /**
     * @return The min transaction amount
     */
    public long getMinTransactionAmount() {
        return minTransactionAmount;
    }

    /**
     * @param minTransactionAmount The min transaction amount
     * @return this
     */
    public Balance setMinTransactionAmount(long minTransactionAmount) {
        this.minTransactionAmount = minTransactionAmount;
        return this;
    }

    /**
     * @return The date and time of the last transaction
     */
    public Date getLastTransactionTime() {
        return lastTransactionTime;
    }

    /**
     * @param lastTransactionTime The date and time of the last transaction
     * @return this
     */
    public Balance setLastTransactionTime(Date lastTransactionTime) {
        this.lastTransactionTime = lastTransactionTime;
        return this;
    }

    /**
     * Apply a transaction to the balance
     *
     * @param transaction the transaction
     * @return this
     */
    public Balance applyTransaction(Transaction transaction) {
        this.name = transaction.getName();
        this.amount += transaction.getAmount();
        this.transactionCount++;
        if (transaction.getAmount() > this.maxTransactionAmount) {
            this.maxTransactionAmount = transaction.getAmount();
        }
        if (transaction.getAmount() < this.minTransactionAmount) {
            this.minTransactionAmount = transaction.getAmount();
        }
        this.lastTransactionTime = transaction.getTime();
        return this;
    }
}
