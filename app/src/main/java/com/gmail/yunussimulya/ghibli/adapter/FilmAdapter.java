package com.gmail.yunussimulya.ghibli.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.yunussimulya.ghibli.R;
import com.gmail.yunussimulya.ghibli.common.listener.OnItemClickListener;
import com.gmail.yunussimulya.ghibli.model.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmHolder> {

    private List<Film> data;
    private OnItemClickListener<Film> onItemClickListener;

    public void setData(@NonNull final List<Film> films) {
        if (data == null) {
            data = new ArrayList<>();
            data.addAll(films);
            notifyItemRangeInserted(0, data.size());
        } else {
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
                    return data.get(oldItemPosition).getId().equals(films.get(newItemPosition).getId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Film oldFilm = data.get(oldItemPosition);
                    Film newFilm = films.get(newItemPosition);
                    return oldFilm.getId().equals(newFilm.getId()) && oldFilm.getTitle().equals(newFilm.getTitle());
                }

            });
            data.clear();
            data.addAll(films);
            result.dispatchUpdatesTo(this);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<Film> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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

    class FilmHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        AppCompatTextView tvTitle;

        FilmHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(this);
        }

        void bind(Film film) {
            if (film != null) tvTitle.setText(film.getTitle());
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                Film film = data.get(getAdapterPosition());
                onItemClickListener.onClick(film, getAdapterPosition());
            }
        }
    }
}
