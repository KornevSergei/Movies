package com.example.movies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movies.R;
import com.example.movies.data.MovieAdapter;
import com.example.movies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //поля для отображения
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies;

    //переменная для обработки джсон обьектов
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //связываем
        recyclerView = findViewById(R.id.recyclerView);
        //для улучшения производтельности
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movies = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);


        //вызываем метод
        getMovies();

    }

    //метод для распознования джсон обьектов
    private void getMovies() {

        String url = "http://www.omdbapi.com/?apikey=4f08d879&s=superman";

        //обьект для получения параметровб в котором передаем запрос
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            //распознаем джсон обьект
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Search");

                    //проходим циклом по элементам джсона полученного
                    for (int i = 0; i <jsonArray.length() ; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //следим что бы ключи совпадали с джсоном
                        String title = jsonObject.getString("Title");
                        String year = jsonObject.getString("Year");
                        String posterUrl = jsonObject.getString("Poster");


                        //передаем полученные данные в обьект
                        Movie movie = new Movie();
                        movie.setTitle(title);
                        movie.setYear(year);
                        movie.setPosterUrl(posterUrl);

                        //заполняем элементами
                        movies.add(movie);
                    }

                    //инициализируем адаптер и передаем параметры
                        movieAdapter = new MovieAdapter(MainActivity.this, movies);
                    recyclerView.setAdapter(movieAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            //метод при запросе если будет ощибка - выведим стек ошибок
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        //добавляем полученные результат ы переменную
        requestQueue.add(request);

    }
}