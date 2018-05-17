package com.gmail.yunussimulya.ghibli.repository;

import com.gmail.yunussimulya.ghibli.api.service.FilmService;
import com.gmail.yunussimulya.ghibli.model.Film;

import java.util.List;

import retrofit2.Call;

public class FilmRepository {

    private FilmService filmService;

    public FilmRepository(FilmService filmService) {
        this.filmService = filmService;
    }

    public Call<List<Film>> getFilms() {
        return filmService.getFilms();
    }

}
