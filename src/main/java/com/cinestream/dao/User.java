package com.cinestream.models;

public abstract class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String tier;

    public User(int id, String username, String email, String password, String tier) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.tier = tier;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getTier() { return tier; }

    public abstract int getRentalLimit();

    @Override
    public String toString() {
        return id + "|" + username + "|" + email + "|" + password + "|" + tier;
    }
}
