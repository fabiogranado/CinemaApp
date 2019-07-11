package com.example.fabiogranado.cineapp.MovieDetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fabiogranado.cineapp.MovieComponents.Reviews;
import com.example.fabiogranado.cineapp.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{

    @SuppressWarnings("unused")
    private final static String LOG_TAG = ReviewAdapter.class.getSimpleName();

    private final ArrayList<Reviews> mReviews;
    private final OnItemClickListener onItemClickListener;

    public ReviewAdapter(ArrayList<Reviews> reviews, OnItemClickListener listener) {
        mReviews = reviews;
        onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void read_reviews(Reviews review, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_list_container, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Reviews review = mReviews.get(position);

        holder.mReview = review;
        holder.mContentView.setText(review.getmContent());
        holder.mAuthorView.setText(review.getmAuthor());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.read_reviews(review, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        @BindView(R.id.review_content)
        TextView mContentView;
        @BindView(R.id.review_author)
        TextView mAuthorView;
        public Reviews mReview;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mView = view;
        }
    }

    public void add(List<Reviews> reviews) {
        mReviews.clear();
        mReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    public ArrayList<Reviews> getReviews() {
        return mReviews;
    }
}

/***************************************************************************************
 *    Title: Developing a Movies App with Picasso and themoviedb.org API using Fragments.
 *    Author: Dipro Chowdhury
 *    Date: 21 May 2018
 *    Code version: 1.0
 *    Availability: https://medium.com/@diprochowdhury/developing-a-movies-app-with-picasso-and-themoviedb-org-api-using-fragments-eb1bd19cf572
 *
 ***************************************************************************************/