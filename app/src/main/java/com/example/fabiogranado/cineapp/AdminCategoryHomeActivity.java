package com.example.fabiogranado.cineapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryHomeActivity extends AppCompatActivity {
    //declared variables
    private ImageView film, retail;
    private Button adminLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category_home);

        // assign variable to xml id from activity_admin_category_home
        film = findViewById(R.id.film_category_home);
        retail = findViewById(R.id.retail_category_home);
        adminLogout = findViewById(R.id.logout_btn_admin);


        // add click listener to film icon on the menu
        film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryHomeActivity.this, AdminAddNewFilmActivity.class);
                intent.putExtra("category", "Film");
                startActivity(intent);
            }
        });

        // add click listener to food icon on the menu
        retail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryHomeActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Retail");
                startActivity(intent);
            }
        });

        // add click listener to button and log out admin user redirecting to login page
        adminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

    }


    }
