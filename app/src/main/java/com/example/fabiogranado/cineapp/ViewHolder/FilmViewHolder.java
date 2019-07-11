package com.example.fabiogranado.cineapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fabiogranado.cineapp.Interface.ItemClickListener;
import com.example.fabiogranado.cineapp.R;

import org.w3c.dom.Text;

public class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtFilmName, txtSynopsis;
    public Button btnTime1, btnTime2, btnTime3, btnTime4;
    public ImageView imagePosterView, info;
    public ItemClickListener listener;


    public FilmViewHolder(View itemView)
    {
        super(itemView);


        imagePosterView = itemView.findViewById(R.id.film_poster);
        txtFilmName = itemView.findViewById(R.id.film_name);
        btnTime1 =  itemView.findViewById(R.id.film_time1);
        btnTime2 =  itemView.findViewById(R.id.film_time2);
        btnTime3 =  itemView.findViewById(R.id.film_time3);
        info = itemView.findViewById(R.id.film_info);

    }

    public void setItemClickListner(ItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition(), false);
    }
}