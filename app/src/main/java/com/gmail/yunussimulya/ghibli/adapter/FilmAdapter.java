package com.gmail.yunussimulya.ghibli.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.yunussimulya.ghibli.R;
import com.gmail.yunussimulya.ghibli.model.Film;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {

    private List<Film> data;

    public void setData(final List<Film> films) {
        if (data == null) {
            Log.e("data", "null");
            data = films;
            notifyItemRangeInserted(0, data.size());
        } else {
            Log.e("data", "diff util");
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return data.size();
                }

                @Override
                public int getNewListSize() {
                    return films.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return data.get(oldItemPosition).getId().equalsIgnoreCase(films.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Film oldFilm = data.get(oldItemPosition);
                    Film newFilm = films.get(newItemPosition);
                    return oldFilm.getId().equalsIgnoreCase(newFilm.getId()) && oldFilm.getTitle().equalsIgnoreCase(newFilm.getTitle());
                }
            });
            data = films;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public FilmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_film, parent, false);
        return new FilmHolder(view);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    class FilmHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvTitle;

        FilmHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

        void bind(Film film) {
            if (film != null) tvTitle.setText(film.getTitle());
        }
    }
}
