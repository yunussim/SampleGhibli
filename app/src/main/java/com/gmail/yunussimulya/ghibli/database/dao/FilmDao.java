package com.gmail.yunussimulya.ghibli.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gmail.yunussimulya.ghibli.model.Film;

@Dao
public interface FilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFilms(Film... films);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFilm(Film film);

    @Query("SELECT * FROM Film")
    LiveData<Film> getFilms();

}