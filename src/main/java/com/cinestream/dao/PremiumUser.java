package com.cinestream.models;

public class PremiumUser extends User {
    public PremiumUser(int id, String username, String email, String password, String tier) {
        super(id, username, email, password, tier);
    }

    @Override
    public int getRentalLimit() {
        return 10;
    }
}
