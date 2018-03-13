package com.privalia.som.kafka.streams.entity;

/**
 * Entity Product
 *
 * @author david.amigo
 */
public class Product {
    private long id;
    private String name;
    private String description;
    private double cost;

    private static long newID = 1L;

    /**
     * Constructor
     */
    public Product() {
        this.id = 0L;
        this.name = "";
        this.description = "";
        this.cost = 0.0;
    }

    public Product(String name, String description, double cost) {
        this.id = newID++;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public Product setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getCost() {
        return cost;
    }

    public Product setCost(double cost) {
        this.cost = cost;
        return this;
    }

    @Override
    public String toString() {
        return "[#" + this.id
                + ", " + this.name
                + "]";
    }
}
