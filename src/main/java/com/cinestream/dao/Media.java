package com.cinestream.models;

public abstract class Media {
    private int id;
    private String title;
    private String genre;
    private int stock;
    private String description;
    private String imageUrl;
    private String backdropUrl;
    private String trailerId;
    private String type;
    private double price;

    public Media(int id, String title, String genre, int stock, String description, String imageUrl, String backdropUrl, String trailerId, String type, double price) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.stock = stock;
        this.description = description;
        this.imageUrl = imageUrl;
        this.backdropUrl = backdropUrl;
        this.trailerId = trailerId;
        this.type = type;
        this.price = price;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getStock() { return stock; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getBackdropUrl() { return backdropUrl; }
    public String getTrailerId() { return trailerId; }
    public String getType() { return type; }
    public double getPrice() { return price; }

    public void setStock(int stock) { this.stock = stock; }
    public void setPrice(double price) { this.price = price; }

    public abstract int getCheckoutDuration();

    @Override
    public String toString() {
        String cleanTitle = (title != null) ? title.replace("|", " ") : "";
        String cleanDesc = (description != null) ? description.replace("|", " ") : "";
        String safeGenre = (genre != null) ? genre : "";
        String safeImage = (imageUrl != null) ? imageUrl : "";
        String safeBackdrop = (backdropUrl != null) ? backdropUrl : "";
        String safeTrailer = (trailerId != null) ? trailerId : "";
        String safeType = (type != null) ? type : "Movie";
        return id + "|" + cleanTitle + "|" + safeGenre + "|" + stock + "|" + cleanDesc + "|" + safeImage + "|" + safeBackdrop + "|" + safeTrailer + "|" + safeType + "|" + price;
    }
}
