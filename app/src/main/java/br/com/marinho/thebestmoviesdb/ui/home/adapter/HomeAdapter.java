package br.com.marinho.thebestmoviesdb.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.marinho.thebestmoviesdb.R;
import br.com.marinho.thebestmoviesdb.infra.App;
import br.com.marinho.thebestmoviesdb.infra.DeviceHelper;
import br.com.marinho.thebestmoviesdb.repository.Model.Movie;
import br.com.marinho.thebestmoviesdb.repository.Util.FormatStringUtil;
import br.com.marinho.thebestmoviesdb.ui.Constants;
import br.com.marinho.thebestmoviesdb.ui.movieDetail.MovieDetailActivity;

/**
 * Created by Marinho on 24/09/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Movie> movieList;
    private View parentView;
    private Context context;

    public HomeAdapter(ArrayList<Movie> movieList, final  View parentView, Context activityContext) {
        this.movieList = movieList;
        this.parentView = parentView;
        this.context = activityContext;
    }

    public static class ViewHolderMovie extends RecyclerView.ViewHolder {

        public ImageView poster;
        public TextView title;
        public TextView subtitle;
        public TextView year;
        public TextView vote;

        private ViewHolderMovie(View v){
            super(v);
            this.poster = (ImageView) v.findViewById(R.id.image_poster);
            this.title = (TextView) v.findViewById(R.id.text_title);
            this.subtitle = (TextView) v.findViewById(R.id.text_subtitle);
            this.year = (TextView) v.findViewById(R.id.text_year);
            this.vote = (TextView) v.findViewById(R.id.text_vote);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_movie, parent, false);
        return new ViewHolderMovie(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }

        final Movie movie = movieList.get(position);
        final ViewHolderMovie holderMovie = (ViewHolderMovie) holder;

        Picasso.with(App.getContext()).load(FormatStringUtil.formatURLImage(movie.getPosterPath(),
                false)).into(holderMovie.poster);
        holderMovie.title.setText(movie.getTitle());
        holderMovie.subtitle.setText(movie.getOriginalTitle());
        holderMovie.year.setText(FormatStringUtil.formatYearMovie(movie.getReleaseDate()));
        holderMovie.vote.setText("Nota: " + String.valueOf(movie.getVoteAverage()));

        holderMovie.itemView.setClickable(true);
        holderMovie.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DeviceHelper.isNetworkAvailable(App.getContext())){
                    Intent intent = new Intent(App.getContext(), MovieDetailActivity.class);
                    intent.putExtra(Constants.MOVIE, movie);
                    context.startActivity(intent);
                }else{
                    Snackbar.make(parentView,
                            App.getContext().getString(R.string.noNetworOfflinekMsg),
                            Snackbar.LENGTH_LONG).show();
                }
                Snackbar.make(parentView,
                            "Clicado filme: " + movie.getTitle(),
                            Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        movieList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(ArrayList<Movie> newMovieList) {
        this.movieList.addAll(newMovieList);
        notifyDataSetChanged();
    }
}




