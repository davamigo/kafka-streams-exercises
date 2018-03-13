package com.privalia.som.kafka.streams.entity;

/**
 * Entity User
 *
 * @author david.amigo
 */
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    private static long newID = 1L;

    /**
     * Default constructor
     */
    public User() {
        this.id = 0L;
        this.firstName = "";
        this.lastName = "";
        this.email = "";
    }

    /**
     * Constructor
     *
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param email     the email of the user
     */
    public User(String firstName, String lastName, String email) {
        this.id = newID++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "[#" + this.id
                + ", " + this.firstName
                + " " + this.lastName
                + "]";
    }
}
