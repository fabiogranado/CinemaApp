package com.example.fabiogranado.cineapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fabiogranado.cineapp.Model.Cart;
import com.example.fabiogranado.cineapp.Model.Films;
import com.example.fabiogranado.cineapp.Model.Orders;
import com.example.fabiogranado.cineapp.Model.Products;
import com.example.fabiogranado.cineapp.RememberMe.RememberMe;
import com.example.fabiogranado.cineapp.ViewHolder.CartViewHolder;
import com.example.fabiogranado.cineapp.ViewHolder.FilmViewHolder;
import com.example.fabiogranado.cineapp.ViewHolder.OrdersViewHolder;
import com.example.fabiogranado.cineapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class OrderDetailActivity extends AppCompatActivity {


    // declared variables
    private Button finish;
    private TextView totatPrice, name, date;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference orderRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);


        // assign variable to xml id from activity_cart
        finish = findViewById(R.id.finish_btn);
        recyclerView = findViewById(R.id.order_list);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        totatPrice = findViewById(R.id.total_price);
        name = findViewById(R.id.order_name);
        date = findViewById(R.id.order_date);



        // add listener to button and reveal total bill
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(OrderDetailActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        FirebaseRecyclerOptions<Orders> options = new FirebaseRecyclerOptions.Builder<Orders>().setQuery(orderRef.child("User View")
                 ,Orders.class).build();

        //access database
        FirebaseRecyclerAdapter<Orders, OrdersViewHolder> adapter = new FirebaseRecyclerAdapter<Orders, OrdersViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull OrdersViewHolder holder, int position, @NonNull final Orders model) {
                holder.txtOrderName.setText(model.getName());
                holder.txtOrderPrice.setText("£ " + model.getTotalAmount());
                holder.txtOrderDate.setText("Purchase Date " + model.getDate());


            }

            @NonNull
            @Override
            public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_in_order_layout, parent, false);
                OrdersViewHolder holder = new OrdersViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


}



