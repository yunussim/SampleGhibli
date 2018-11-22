package com.gmail.yunussimulya.ghibli.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.yunussimulya.ghibli.R;
import com.gmail.yunussimulya.ghibli.adapter.FilmAdapter;
import com.gmail.yunussimulya.ghibli.api.ApiClient;
import com.gmail.yunussimulya.ghibli.api.service.FilmService;
import com.gmail.yunussimulya.ghibli.common.listener.EndlessScrollListener;
import com.gmail.yunussimulya.ghibli.repository.FilmRepository;
import com.gmail.yunussimulya.ghibli.viewmodel.FilmViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    FilmViewModel viewModel;
    FilmAdapter adapter;
    EndlessScrollListener scrollListener;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
            unbinder = null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(getActivity()).get(FilmViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        initiateView();
        return view;
    }

    private void initiateView() {
        viewModel.setRepository(new FilmRepository(ApiClient.getClient().create(FilmService.class)));

        attachObserver();
        swipeRefresh.setOnRefreshListener(() -> viewModel.loadFilms());
        adapter = new FilmAdapter();
        adapter.setOnItemClickListener((object, pos) -> {
            Log.e("pos", pos + "");
            Log.e("film", object.toString());
        });
        initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
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
