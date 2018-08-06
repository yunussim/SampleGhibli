package com.gmail.yunussimulya.ghibli.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.gmail.yunussimulya.ghibli.model.Film;
import com.gmail.yunussimulya.ghibli.repository.FilmRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilmViewModel extends ViewModel {

    private final MutableLiveData<List<Film>> films = new MutableLiveData<>();
    private FilmRepository repository;

    public void setRepository(@NonNull FilmRepository repository) {
        this.repository = repository;
    }

    public void loadFilms() {
        repository.getFilms().enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(@NonNull Call<List<Film>> call, @NonNull Response<List<Film>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    films.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Film>> call, @NonNull Throwable t) {

            }
        });
    }

    public void loadMoreFilms() {
        repository.getFilms().enqueue(new Callback<List<Film>>() {
            @Override
            public void onResponse(@NonNull Call<List<Film>> call, @NonNull Response<List<Film>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    List<Film> oldValue = films.getValue();
                    if (oldValue != null && response.body() != null) {
                        oldValue.addAll(response.body());
                    }
                    films.postValue(oldValue);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Film>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    protected void onCleared() {
        if (!repository.getFilms().isCanceled()) repository.getFilms().cancel();
        super.onCleared();
    }

    public MutableLiveData<List<Film>> getFilms() {
        return films;
    }

}
