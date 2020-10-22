package com.example.vkinfo.models;

public class Seance {

    private long id;
    private int movieId;
    private String startTime;
    private String day;

    public Seance(int movieId, String startTime, String day) {
        this.movieId = movieId;
        this.startTime = startTime;
        this.day = day;
    }
}

