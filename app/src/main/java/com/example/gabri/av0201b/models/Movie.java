package com.example.gabri.av0201b.models;

/**
 * Created by gabri on 17/09/2016.
 */


import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Movie extends SugarRecord {
   public int movie_id;
   public String title;
   public String year;
   public String rated;
   public String released;
   public String genre;
   public String director;
   public String writer;
   public String actors;
   public String plot;
   public String poster;
   public String runtime;
   public String rating;
   public String votes;
   public String imdb;
   public String tstamp;

    public Movie(){ }

    public Movie(String title, String imdb){
        this.title = title;
        this.imdb = imdb;
        this.tstamp = getDateTime();
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public String toString() {
        return "Movie - " + this.movie_id + " - " + this.title + " /n " ;
    }
}
