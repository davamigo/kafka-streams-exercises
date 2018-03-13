package com.privalia.som.kafka.streams.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity purchase
 *
 * @author david.amigo
 */
public class Purchase {
    private long id;
    private long userId;
    private long productId;
    private long quantity;
    private double unitPrice;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String productName;
    private String productDescription;

    @JsonIgnore
    private boolean error;

    @JsonIgnore
    private static long newID = 1L;

    /**
     * Constructor
     */
    public Purchase() {
        this.id = 0L;
        this.userId = 0L;
        this.productId = 0L;
        this.quantity = 0L;
        this.unitPrice = 0.0;
        this.userFirstName = "";
        this.userLastName = "";
        this.userEmail = "";
        this.productName = "";
        this.productDescription = "";
        this.error = false;
    }

    /**
     * Constructor
     *
     * @param userId    the user id
     * @param productId the product id
     * @param quantity  the quantity purchased
     * @param unitPrice the price per unit
     */
    public Purchase(long userId, long productId, long quantity, double unitPrice) {
        this.id = newID++;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.userFirstName = "";
        this.userLastName = "";
        this.userEmail = "";
        this.productName = "";
        this.productDescription = "";
        this.error = false;
    }

    /**
     * Enriches the entity with the user data
     *
     * @param user the user data
     * @return this
     */
    public Purchase enrich(User user) {
        this.userId = user.getId();
        this.userFirstName = user.getFirstName();
        this.userLastName = user.getLastName();
        this.userEmail = user.getEmail();
        return this;
    }

    /**
     * Enriches the entity with the produyct data
     *
     * @param product the product data
     * @return this
     */
    public Purchase enrich(Product product) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.productDescription = product.getDescription();
        return this;
    }

    public long getId() {
        return id;
    }

    public Purchase setId(long id) {
        this.id = id;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public Purchase setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public long getProductId() {
        return productId;
    }

    public Purchase setProductId(long productId) {
        this.productId = productId;
        return this;
    }

    public long getQuantity() {
        return quantity;
    }

    public Purchase setQuantity(long quantity) {
        this.quantity = quantity;
        return this;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public Purchase setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public Purchase setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
        return this;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public Purchase setUserLastName(String userLastName) {
        this.userLastName = userLastName;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Purchase setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public Purchase setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public Purchase setProductDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public boolean isError() {
        return error;
    }

    public Purchase setError(boolean error) {
        this.error = error;
        return this;
    }
}
