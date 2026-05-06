package com.cinestream.dao;

import com.cinestream.models.*;
import com.cinestream.api.MovieApiScraper;
import java.io.*;
import java.util.*;

public class DatabaseHandler {

    private static final String MOVIES_FILE = "movies.txt";
    private static final String USERS_FILE = "users.txt";
    private static final String RENTALS_FILE = "rentals.txt";
    private static final String REVIEWS_FILE = "reviews.txt";
    private static final String FAVORITES_FILE = "favorites.txt";

    private static final long AUTO_HEAL_INTERVAL_MS = 21600000;
    private static final int DEFAULT_RENTAL_DAYS = 3;
    private static final double DEFAULT_PRICE = 3.99;

    private String dataDir;
    private static List<Media> movieCache = null;
    private static Map<Integer, List<Review>> reviewCache = new HashMap<Integer, List<Review>>();
    private static Map<Integer, String> ratingCache = new HashMap<Integer, String>();
    private static boolean allReviewsLoaded = false;
    private static long lastMovieSync = 0;
    private static boolean isSyncing = false;

    public DatabaseHandler(String realPathToWebapp) {
        if (realPathToWebapp == null) {
            this.dataDir = "WEB-INF" + File.separator + "data" + File.separator;
        } else {
            this.dataDir = realPathToWebapp + "WEB-INF" + File.separator + "data" + File.separator;
        }
        File directory = new File(this.dataDir);
        if (!directory.exists()) directory.mkdirs();
        ensureFileExists(MOVIES_FILE); ensureFileExists(USERS_FILE); ensureFileExists(RENTALS_FILE);
        ensureFileExists(REVIEWS_FILE); ensureFileExists(FAVORITES_FILE);
    }

    private void ensureFileExists(String fileName) {
        File f = new File(dataDir + fileName);
        if (!f.exists()) { try { f.createNewFile(); } catch (Exception e) { e.printStackTrace(); } }
    }

    public synchronized void syncLibrary() {
        List<Media> currentMovies = getAllMovies();
        List<Media> updatedMovies = new ArrayList<Media>();

        for (Media m : currentMovies) {
            MovieApiScraper.MovieData freshData = MovieApiScraper.fetchMedia(m.getTitle(), m.getType());
            if (freshData != null) {
                Media refreshed = "TV".equalsIgnoreCase(m.getType()) ? 
                    new TVShow(m.getId(), freshData.title, freshData.genre, m.getStock(), freshData.description, freshData.imageUrl, freshData.backdropUrl, freshData.trailerId, m.getType(), m.getPrice()) : 
                    new Movie(m.getId(), freshData.title, freshData.genre, m.getStock(), freshData.description, freshData.imageUrl, freshData.backdropUrl, freshData.trailerId, m.getType(), m.getPrice());
                updatedMovies.add(refreshed);
            } else { updatedMovies.add(m); }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataDir + MOVIES_FILE))) {
            for (Media m : updatedMovies) { bw.write(m.toString()); bw.newLine(); }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void autoHealLibrary() {
        long now = System.currentTimeMillis();
        if (isSyncing || (now - lastMovieSync < AUTO_HEAL_INTERVAL_MS)) return;

        List<Media> movies = getAllMovies();
        boolean needsHeal = false;
        for (Media m : movies) {
            if (m.getImageUrl() == null || m.getImageUrl().contains("/w500/") || m.getBackdropUrl() == null || m.getType() == null) { needsHeal = true; break; }
        }
        if (needsHeal) {
            isSyncing = true; lastMovieSync = now;
            Thread healThread = new Thread(new Runnable() {
                @Override public void run() { try { syncLibrary(); lastMovieSync = System.currentTimeMillis(); } finally { isSyncing = false; movieCache = null; } }
            });
            healThread.setDaemon(true); healThread.start();
        }
    }
}