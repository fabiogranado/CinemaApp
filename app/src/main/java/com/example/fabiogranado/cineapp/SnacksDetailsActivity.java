package com.example.fabiogranado.cineapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.fabiogranado.cineapp.Model.Products;
import com.example.fabiogranado.cineapp.RememberMe.RememberMe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SnacksDetailsActivity extends YouTubeBaseActivity {

    // declared variables
    private FloatingActionButton addToBasket;
    private Button addToBasketButton;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice, productDescription, productName, productSize;
    private String productID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks_details);

        productID = getIntent().getStringExtra("rid");

        // assign variable to xml id from activity_snacks_details
        addToBasketButton = findViewById(R.id.retail_add_to_basket_button);
        numberButton = findViewById(R.id.number_btn);
        productImage = findViewById(R.id.product_image_details);
        productName = findViewById(R.id.product_name_details);
        productDescription = findViewById(R.id.product_description_details);
        productPrice = findViewById(R.id.product_price_details);
        productSize = findViewById(R.id.product_size_details);



        getProductDetails(productID);

        // add listener to button and pass add to cart method
        addToBasketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCartList();
            }
        });




    }   //add to the basket function
        private void addToCartList() {

            String saveCurrentDate, saveCurrentTime;
            // set data format for database entry
            Calendar callForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat( "MMM dd, yyyy");
            saveCurrentDate = currentDate.format(callForDate.getTime());
            // set time format for database entry
            SimpleDateFormat currentTime = new SimpleDateFormat( "HH:mm:ss a");
            saveCurrentTime = currentDate.format(callForDate.getTime());
            // access database
            final DatabaseReference basketListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
            final HashMap<String, Object> cartMap = new HashMap<>();
            // create hash function to store data as (key, value) in the database
            cartMap.put("rid", productID);
            cartMap.put("prodName", productName.getText().toString());
            cartMap.put("price", productPrice.getText().toString());
            cartMap.put("size", productSize.getText().toString());
            cartMap.put("description", productDescription.getText().toString());
            cartMap.put("date", saveCurrentDate);
            cartMap.put("time", saveCurrentTime);
            cartMap.put("quantity", numberButton.getNumber());

            //items added to the basket will be under a child node User View
            //an acknowledment message is displayed and redirected to the menu screen
            basketListRef.child("User View").child(RememberMe.currentOnlineUser.getPhone()).child("Products").child(productID)
                    .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){

                        Toast.makeText(SnacksDetailsActivity.this, "Add To Cart List", Toast.LENGTH_SHORT);

                        Intent intent = new Intent(SnacksDetailsActivity.this, RetailActivity.class);
                        startActivity(intent);

                    }
                }


            });

        }



    private void getProductDetails(String productID) {
        //access the database
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            //retrieve data and display in the cart activity
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Products products = dataSnapshot.getValue(Products.class);
                    productName.setText(products.getProdName());
                    productDescription.setText(products.getDescription());
                    productSize.setText(products.getSize());
                    productPrice.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(productImage);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
