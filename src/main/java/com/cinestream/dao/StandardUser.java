package com.cinestream.models;

public class Review {
    private int movieId;
    private int userId;
    private String username;
    private int rating;
    private String date;
    private String text;

    public Review(int movieId, int userId, String username, int rating, String date, String text) {
        this.movieId = movieId;
        this.userId = userId;
        this.username = username;
        this.rating = rating;
        this.date = date;
        this.text = text;
    }

    public int getMovieId() { return movieId; }
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public int getRating() { return rating; }
    public String getDate() { return date; }
    public String getText() { return text; }

    @Override
    public String toString() {
        // Safe delimiting for text files
        String safeText = (text != null) ? text.replace("|", "").replace("\n", " ") : "";
        return movieId + "|" + userId + "|" + username + "|" + rating + "|" + date + "|" + safeText;
    }
}
