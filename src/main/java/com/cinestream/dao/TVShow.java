package com.cinestream.models;

public class TVShow extends Media {
    public TVShow(int id, String title, String genre, int stock, String description, String imageUrl, String backdropUrl, String trailerId, String type, double price) {
        super(id, title, genre, stock, description, imageUrl, backdropUrl, trailerId, type, price);
    }

    @Override
    public int getCheckoutDuration() {
        return 7;
    }
}
