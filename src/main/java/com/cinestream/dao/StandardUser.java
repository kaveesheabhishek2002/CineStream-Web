package com.cinestream.models;

public class StandardUser extends User {
    public StandardUser(int id, String username, String email, String password, String tier) {
        super(id, username, email, password, tier);
    }

    @Override
    public int getRentalLimit() {
        return 3;
    }
}
