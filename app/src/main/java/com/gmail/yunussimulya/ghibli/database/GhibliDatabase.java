package com.gmail.yunussimulya.ghibli.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gmail.yunussimulya.ghibli.database.dao.FilmDao;
import com.gmail.yunussimulya.ghibli.model.Film;

@Database(version = 1, entities = {Film.class})
public abstract class GhibliDatabase extends RoomDatabase {

    //DAO
    public abstract FilmDao filmDao();

}
