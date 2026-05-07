package com.cinestream.models;

public class Rental {
    private int userId;
    private int movieId;
    private int daysRemaining;

    public Rental(int userId, int movieId, int daysRemaining) {
        this.userId = userId;
        this.movieId = movieId;
        this.daysRemaining = daysRemaining;
    }

    public int getUserId() {
        return userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getDaysRemaining() {
        return daysRemaining;
    }

    @Override
    public String toString() {
        return userId + "|" + movieId + "|" + daysRemaining;
    }
}
