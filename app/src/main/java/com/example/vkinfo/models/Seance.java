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

    public Seance(long id, int movieId, String startTime, String day) {
        this.movieId = movieId;
        this.startTime = startTime;
        this.day = day;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", startTime='" + startTime + '\'' +
                ", day='" + day + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getDay() {
        return day;
    }
}

