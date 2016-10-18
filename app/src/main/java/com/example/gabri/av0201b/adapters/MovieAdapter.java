package com.example.gabri.av0201b.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabri.av0201b.R;
import com.example.gabri.av0201b.models.Movie;
import com.koushikdutta.ion.Ion;

import java.util.List;

/**
 * Created by gabri on 17/09/2016.
 */
public class MovieAdapter extends BaseAdapter {
    List <Movie> movies;
    Context ctx;
    private String TAG = MovieAdapter.class.getSimpleName();
    public MovieAdapter(Context ctx, List <Movie> movies) {
        super();
        this.movies = movies;
        this.ctx = ctx;
        Log.v(TAG, "Construtor");
    }
    @Override
    public int getCount() {
        Log.v(TAG,"getCount : " +  movies.size() );
        return movies.size();
    }
    @Override
    public Movie getItem(int position) {
        Log.v(TAG,"getItem : " + position);
        return movies.get(position);
    }
    @Override
    public long getItemId(int position) {
        Log.v(TAG,"getItemId : " + position);
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.v(TAG,"getView : " + position);
        View rowView = convertView;
        ViewHolder viewHolder;
        // reusa views
        if (rowView == null) {
            Log.v(TAG,"getView ViewHolder: " + position);
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.linha_filme, null);

            // configura view holder
            viewHolder = new ViewHolder();
            viewHolder.nome = (TextView) rowView.findViewById(R.id.titulo);
            viewHolder.foto = (ImageView) rowView.findViewById(R.id.poster);
            rowView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // acessar o dado da posição
        Movie movie = getItem(position);

        // fill data
        viewHolder.nome.setText( movie.title);

        Ion.with(viewHolder.foto)
                .load(movie.poster);
        // retorna o layout preenchido
        return rowView;
    }

    static class ViewHolder {
        TextView nome;
        ImageView foto;
        int position;
    }

}
