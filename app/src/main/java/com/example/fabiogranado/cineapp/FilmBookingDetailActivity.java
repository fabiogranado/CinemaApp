package com.example.fabiogranado.cineapp;


        import android.os.Bundle;
        import android.widget.ImageView;
        import android.widget.TextView;
        import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
        import com.example.fabiogranado.cineapp.Model.Films;
        import com.google.android.youtube.player.YouTubeBaseActivity;
        import com.google.android.youtube.player.YouTubePlayer;
        import com.google.android.youtube.player.YouTubePlayerView;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.squareup.picasso.Picasso;


public class FilmBookingDetailActivity extends YouTubeBaseActivity {

    //    private FloatingActionButton addToCart;
//    private Button addToCartButton;
    private ImageView productImage1;
    private ElegantNumberButton numberButton1;
    private TextView moviePrice, movieName, movieTime;
    private String movieID = "";
    private YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_booking_detail);



        movieID = getIntent().getStringExtra("fid");

//        addToCartButton = findViewById(R.id.pd_add_to_cart_button);
        numberButton1 = findViewById(R.id.number_btn1);
        productImage1 = findViewById(R.id.product_image_details1);
        movieName = findViewById(R.id.film_name_details);
        movieTime = findViewById(R.id.film_time_details);
        moviePrice = findViewById(R.id.film_price_details);

        getProductDetails(movieID);

    }


    private void getProductDetails(String movieID) {
        //access database
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Films");
        //check if data exisits
        productRef.child(movieID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Films films = dataSnapshot.getValue(Films.class);

                    movieName.setText(films.getFilmName());
                    movieTime.setText(films.getTime1());
                    Picasso.get().load(films.getImage()).into(productImage1);
                }

                else if(dataSnapshot.exists()){
                    Films films = dataSnapshot.getValue(Films.class);

                    movieName.setText(films.getFilmName());
                    movieTime.setText(films.getTime2());
                    Picasso.get().load(films.getImage()).into(productImage1);
                }
                else if(dataSnapshot.exists()){
                    Films films = dataSnapshot.getValue(Films.class);

                    movieName.setText(films.getFilmName());
                    movieTime.setText(films.getTime3());
                    Picasso.get().load(films.getImage()).into(productImage1);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
