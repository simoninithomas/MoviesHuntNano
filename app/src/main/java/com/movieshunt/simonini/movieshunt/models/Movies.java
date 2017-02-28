package com.movieshunt.simonini.movieshunt.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movies implements Parcelable {

    //Our class Movie will have
    // id : Id
    // title : Title
    // poster_path: Poster Id (load with Picasso)
    // backdrop_path : Image Id (in the fragment)
    // release_date : Year of publication
    // vote_average : Rating
    // overview : Synopsis

    private int id;
    private String title;
    private String poster_path;
    private String backdrop_path;
    private String release_date;
    private Double vote_average;
    private String overview;

    public Movies(int id, String title, String poster_path, String backdrop_path, String release_date, Double vote_average, String overview) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return "http://image.tmdb.org/t/p/w185/" + poster_path;
    }

    public void setPoster(String poster_path) {

        this.poster_path = "http://image.tmdb.org/t/p/w185/" + poster_path;
    }

    public String getBackdrop() {
        return backdrop_path;
    }

    public void setBackdrop(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public Double getVote() {
        return vote_average;
    }

    public void setVote(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getSynopsis() {
        return overview;
    }

    public void setSynopsis(String overview) {
        this.overview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //Our class Movie will have
    // id : Id
    // title : Title
    // poster_path: Poster Id (load with Picasso)
    // backdrop_path : Image Id (in the fragment)
    // release_date : Year of publication
    // vote_average : Rating
    // overview : Synopsis
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(title);
        out.writeString(poster_path);
        out.writeString(backdrop_path);
        out.writeString(release_date);
        out.writeDouble(vote_average);
        out.writeString(overview);
    }

    // Creator
    public static final Parcelable.Creator<Movies> CREATOR
            = new Parcelable.Creator<Movies>() {

        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    // "De-parcel object
    public Movies(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
        release_date = in.readString();
        vote_average = in.readDouble();
        overview = in.readString();

    }

}






