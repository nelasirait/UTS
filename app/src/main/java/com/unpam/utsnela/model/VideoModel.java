package com.unpam.utsnela.model;

public class VideoModel {
    private String id, title, image, time, tagline;
    public VideoModel(String id, String title, String image, String time, String tagline) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.time = time;
        this.tagline = tagline;
    }

    public String getTagline() {
        return tagline;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

}
