package com.example.movies.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//создаём класс адаптера
//общий класс с элементами
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;

    //конструкторв  который передаем поля
    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }



    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, parent, false);

        return new MovieViewHolder(view);
    }

    //получаем элементы класса муви
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentMovie = movies.get(position);

        //получаем элементы
        String title = currentMovie.getTitle();
        String year = currentMovie.getYear();
        String posterUrl = currentMovie.getPosterUrl();

        //помещаем полученные значения
        holder.titleTextView.setText(title);
        holder.yearTextView.setText(year);

        //используем агрузку через библиотеку
        Picasso.get().load(posterUrl).fit().centerInside().into(holder.posterImageView);

    }

    //озвращаем все элементы спика фильмов
    @Override
    public int getItemCount() {
        return movies.size();
    }


    //получаем доступ к разметкиб класс для конкретного элемента
    public class MovieViewHolder extends RecyclerView.ViewHolder {

        //создаём переменные и сылаемся на элементы разметки
        ImageView posterImageView;
        TextView titleTextView;
        TextView yearTextView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            //связываем
            posterImageView = itemView.findViewById(R.id.posterImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            yearTextView = itemView.findViewById(R.id.yearTextView);
        }
    }

}