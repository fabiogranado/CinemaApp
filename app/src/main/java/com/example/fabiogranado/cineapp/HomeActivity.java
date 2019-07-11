package com.example.fabiogranado.cineapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.fabiogranado.cineapp.Model.Films;
import com.example.fabiogranado.cineapp.RememberMe.RememberMe;
import com.example.fabiogranado.cineapp.ViewHolder.FilmViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    //declared variables
    private Button time1, time2, time3;
    private ImageView info, poster;
    private DatabaseReference FilmsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //initialise Firebase database
        FilmsRef = FirebaseDatabase.getInstance().getReference().child("Films");

        time1 = findViewById(R.id.film_time1);
        time2 = findViewById(R.id.film_time2);
        time3 = findViewById(R.id.film_time3);
        info = findViewById(R.id.film_info);
        poster = findViewById(R.id.film_poster);


        Paper.init(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Select Menu Options");
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setText(RememberMe.currentOnlineUser.getName());
        Picasso.get().load(RememberMe.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Films> options =
                new FirebaseRecyclerOptions.Builder<Films>()
                        .setQuery(FilmsRef, Films.class)
                        .build();

        //access database
        FirebaseRecyclerAdapter<Films, FilmViewHolder> adapter = new FirebaseRecyclerAdapter<Films, FilmViewHolder>(options) {
            //displays retails products on the main retail menu, added from the admin user
            @Override
            protected void onBindViewHolder(@NonNull FilmViewHolder holder, int position, @NonNull final Films model)
            {
                holder.txtFilmName.setText(model.getFilmName());
                holder.btnTime1.setText(model.getTime1());
                holder.btnTime2.setText(model.getTime2());
                holder.btnTime3.setText(model.getTime3());
                Picasso.get().load(model.getImage()).into(holder.imagePosterView);
                Picasso.get().load(model.getInfo()).into(holder.info);

                holder.imagePosterView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, Dashboard.class);
                        intent.putExtra("fid",model.getFid());
                        startActivity(intent);
                    }
                });

                holder.info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, Dashboard.class);
                        intent.putExtra("fid",model.getFid());
                        startActivity(intent);
                    }
                });

                holder.btnTime1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, SeatPlan.class);
                        intent.putExtra("fid",model.getFid());
                        startActivity(intent);
                    }
                });

                holder.btnTime2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, SeatPlan.class);
                        intent.putExtra("fid",model.getFid());
                        startActivity(intent);
                    }
                });

                holder.btnTime3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this, SeatPlan.class);
                        intent.putExtra("fid",model.getFid());
                        startActivity(intent);
                    }
                });



            }

            @NonNull
            @Override
            public FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_time, parent, false);
                FilmViewHolder holder = new FilmViewHolder(view);
                return holder;
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

//        if (id == R.id.action_settings)
//        {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_retail)
        {
            Intent intent = new Intent(HomeActivity.this, RetailActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_cart)
        {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_orders)
        {

            Intent intent = new Intent(HomeActivity.this, OrderDetailActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_favourite)
        {

        }
        else if (id == R.id.nav_settings)
        {
            Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_logout)
        {
            Paper.book().destroy();

            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}