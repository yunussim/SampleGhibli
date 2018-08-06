package com.gmail.yunussimulya.ghibli.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.gmail.yunussimulya.ghibli.R;
import com.gmail.yunussimulya.ghibli.adapter.FilmAdapter;
import com.gmail.yunussimulya.ghibli.api.ApiClient;
import com.gmail.yunussimulya.ghibli.api.service.FilmService;
import com.gmail.yunussimulya.ghibli.common.listener.EndlessScrollListener;
import com.gmail.yunussimulya.ghibli.common.listener.OnItemClickListener;
import com.gmail.yunussimulya.ghibli.model.Film;
import com.gmail.yunussimulya.ghibli.repository.FilmRepository;
import com.gmail.yunussimulya.ghibli.viewmodel.FilmViewModel;

public class MainActivity extends AppCompatActivity {

    FilmViewModel viewModel;
    SwipeRefreshLayout swipeRefresh;
    FilmAdapter adapter;
    EndlessScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(FilmViewModel.class);
        viewModel.setRepository(new FilmRepository(ApiClient.getClient().create(FilmService.class)));

        attachObserver();
        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(() -> viewModel.loadFilms());
        adapter = new FilmAdapter();
        adapter.setOnItemClickListener((object, pos) -> {
            Log.e("pos", pos + "");
            Log.e("film", object.toString());
        });
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        scrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                swipeRefresh.setRefreshing(true);
                viewModel.loadMoreFilms();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    private void attachObserver() {
        viewModel.getFilms().observe(this, (films) -> {
            swipeRefresh.setRefreshing(false);
            scrollListener.resetState();
            if (films != null) {
                adapter.setData(films);
            }
        });
    }

}