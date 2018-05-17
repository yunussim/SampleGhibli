package com.gmail.yunussimulya.ghibli.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class Film {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private String id = "";

    @SerializedName("title")
    @Expose
    private String title = "";

    @SerializedName("description")
    @Expose
    private String description = "";

    @SerializedName("director")
    @Expose
    private String director = "";

    @SerializedName("producer")
    @Expose
    private String producer = "";

    @SerializedName("release_date")
    @Expose
    @ColumnInfo(name = "release_date")
    private String releaseDate = "";

    @SerializedName("url")
    @Expose
    private String url = "";

    public Film(@NonNull  String id, String title, String description, String director, String producer, String releaseDate, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.director = director;
        this.producer = producer;
        this.releaseDate = releaseDate;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDirector() {
        return director;
    }

    public String getProducer() {
        return producer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getUrl() {
        return url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}