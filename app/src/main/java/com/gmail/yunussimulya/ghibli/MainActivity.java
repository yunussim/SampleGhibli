package com.gmail.yunussimulya.ghibli;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.gmail.yunussimulya.ghibli.api.ApiClient;
import com.gmail.yunussimulya.ghibli.api.service.FilmService;
import com.gmail.yunussimulya.ghibli.model.Film;
import com.gmail.yunussimulya.ghibli.repository.FilmRepository;
import com.gmail.yunussimulya.ghibli.viewmodel.FilmViewModel;

public class MainActivity extends AppCompatActivity {

    FilmViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        viewModel.setRepository(new FilmRepository(ApiClient.getClient().create(FilmService.class)));

        initializeAction();
        attachObserver();
    }

    private void initializeAction() {
        Button bTest = findViewById(R.id.bTest);
        bTest.setOnClickListener((v) -> viewModel.loadFilms());
    }

    private void attachObserver() {
        viewModel.getFilms().observe(this, (films) -> {
            if (films != null && films.size() > 0) {
                for (Film film : films) {
                    Log.e("film", film.getTitle());
                }
            }
        });

    }

}