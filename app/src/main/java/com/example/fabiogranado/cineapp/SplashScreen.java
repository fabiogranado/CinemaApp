package com.example.fabiogranado.cineapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



import gr.net.maroulis.library.EasySplashScreen;

//  create a class called Splash Screen
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //create a new splashscreen object
        EasySplashScreen config = new EasySplashScreen (SplashScreen.this)
                //set to full screen
                .withFullScreen()
                //set to load main activity next
                .withTargetActivity(MainActivity.class)
                //stay active for five seconds
                .withSplashTimeOut(5000)
                // set background colour to white
                .withBackgroundColor(Color.parseColor("#FFFFFF"))
                // add XCiNE logo
                .withLogo(R.drawable.logo);



        View view = config.create();
        setContentView(view);
    }
}
