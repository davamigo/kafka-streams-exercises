package com.privalia.som.kafka.streams.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Bank transaction entity
 *
 * @author david.amigo
 */
public class Transaction {

    /** The name of the account */
    private String name;

    /** The amount of the transaction */
    private long amount;

    /**
     * Date and time of the transaction
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
    private Date time;

    /**
     * Default constructor
     */
    public Transaction() {
        this.name = "";
        this.amount = 0L;
        this.time = new Date();
    }

    /**
     * Constructor
     *
     * @param name   the name of the account
     * @param amount the amopunt of the transaction
     * @param time   the date and time of the transaction
     */
    public Transaction(String name, long amount, Date time) {
        this.name = name;
        this.amount = amount;
        this.time = time;
    }

    /**
     * @return the name of the account
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name of the account
     * @return this
     */
    public Transaction setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return the amount of the transaction
     */
    public long getAmount() {
        return amount;
    }

    /**
     * @param amount the amount of the transaction
     * @return this
     */
    public Transaction setAmount(long amount) {
        this.amount = amount;
        return this;
    }

    /**
     * @return the date and time of the transaction
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time the date and time of the transaction
     * @return this
     */
    public Transaction setTime(Date time) {
        this.time = time;
        return this;
    }
}
