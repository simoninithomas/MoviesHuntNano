package com.movieshunt.simonini.movieshunt.network;

import com.movieshunt.simonini.movieshunt.models.MoviesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET("movie/top_rated")
    Call<MoviesList> getTopRated(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MoviesList> getPopular(@Query("api_key") String apiKey);




    /*@GET("movie/{id}/reviews")
    Call<ReviewList> getReviews(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailerList> getTrailers(@Path("id") String id, @Query("api_key") String apiKey);*/

}
