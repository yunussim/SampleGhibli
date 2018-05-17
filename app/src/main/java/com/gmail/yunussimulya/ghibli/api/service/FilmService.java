package com.gmail.yunussimulya.ghibli.api.service;

import com.gmail.yunussimulya.ghibli.model.Film;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FilmService {

    @GET("/films")
    Call<List<Film>> getFilms();

}