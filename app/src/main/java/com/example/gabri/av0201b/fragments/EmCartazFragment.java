package com.example.gabri.av0201b.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gabri.av0201b.DetalheActivity;
import com.example.gabri.av0201b.R;
import com.example.gabri.av0201b.adapters.MovieAdapter;
import com.example.gabri.av0201b.models.Movie;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmCartazFragment extends Fragment {
    ListView movie_listView;
    List<Movie> movies = new ArrayList<Movie>();
    MovieAdapter movieAdapter;
    /*Webservice*/
    String token = "246bf886104d519a1d2bf62aef1054ff";
    String service_uri = "http://api.themoviedb.org/3/movie/";
    String image_uri = "https://image.tmdb.org/t/p/w370";
    String url;
    Movie movie;

    JsonObject filmeJson;
    public EmCartazFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View tela = inflater.inflate(R.layout.fragment_em_cartaz, container, false);
        movie_listView = (ListView)tela.findViewById(R.id.movies_listView);
        movieAdapter = new MovieAdapter(getActivity(),movies);
        movie_listView.setAdapter(movieAdapter);
        Bundle parametros = getArguments();

        //System.out.println("param+arg " + getArguments());
        if (parametros!=null) {
            url = parametros.getString("url");

        }
        return tela;
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.print("param+START" + url);
        System.out.print("aray "+Ion.with(getContext()).load(url).asJsonObject()

                );
        Ion.with(getActivity()).load(url).asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {


                        JsonArray filmes = result.getAsJsonArray("results");
                            /*  Global List<Movie> listaFilmes;   */
                        for (int i = 0; i < filmes.size(); i++) {
                            JsonObject filmeJson = filmes.get(i).getAsJsonObject();
                            Movie movie = new Movie();
                            if (filmeJson.has("title"))
                                movie.title = filmeJson.get("title").getAsString();
                            if (filmeJson.has("poster_path") && filmeJson.get("poster_path").isJsonNull()==false) {
                                System.out.print(filmeJson.get("poster_path"));
                                movie.poster = image_uri + filmeJson.get("poster_path").getAsString();
                                movies.add(movie);
                            }
                            if (filmeJson.has("id"))
                                movie.movie_id = filmeJson.get("id").getAsInt();
                        }

                        MovieAdapter mvAdapter = new MovieAdapter(
                                getActivity().getApplicationContext(),
                                movies
                        );
                        movie_listView.setAdapter(mvAdapter);
                        //observar data bindings google

                    }
                });
        Ion.with(getContext())
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e !=null) {
                            Log.e("main movies", "erro ao recuperar os filmes " + e.getLocalizedMessage());
                            return;
                        }
                        System.out.println("param+JSON");
                        JsonArray filmes = result.getAsJsonArray("results");
                        int totalFilmes = filmes.size();
                        Log.v("Main Movies", "Numero de filmes: " + totalFilmes);
                        for (int i = 0; i<totalFilmes; i++) {
                            filmeJson = (JsonObject) filmes.get(i).getAsJsonObject();
                            movie = new Movie();
                            if (filmeJson.has("title")) {
                                movie.title = filmeJson.get("title").getAsString();
                                movie.movie_id=filmeJson.get("id").getAsInt();
                                Log.v("Main-", "" + movie.movie_id);

                            }
                            if (filmeJson.has("poster_path") && !filmeJson.get("poster_path").isJsonNull()) {
                                System.out.print(filmeJson.get("poster_path"));
                                //movie.poster = image_uri + filmeJson.get("poster_path").getAsString();
                                movies.add(movie);

                            }

                        }
                        movieAdapter.notifyDataSetChanged();
                        movie_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                                Log.v("Main-", "entrou");
                                Intent intent = new Intent();
                                intent.setClass(getContext(), DetalheActivity.class);
                                Log.v("Main-", "position = "+position);
                                Log.v("Main-", movies.get(position).toString());
                                Bundle args = new Bundle();
                                args.putString("filme_id",  "" + movies.get(position).movie_id);
                                Log.v("Main- FILME_ID = ", "" + movies.get(position).movie_id);
                                args.putString("title", movies.get(position).title);
                                //args.put("movies", movies.toArray());
                                intent.putExtras(args);
                                startActivity(intent);
                            }
                        });

                    }
                });

    }

}


