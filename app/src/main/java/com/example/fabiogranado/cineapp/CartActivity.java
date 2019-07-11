package com.example.fabiogranado.cineapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fabiogranado.cineapp.ViewHolder.CartViewHolder;
import com.example.fabiogranado.cineapp.Model.Cart;
import com.example.fabiogranado.cineapp.RememberMe.RememberMe;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {

    // declared variables
    private Button continueBtn;
    private TextView totalPriceOrder;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private double totalOrder = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        // assign variable to xml id from activity_cart
        continueBtn = findViewById(R.id.continue_btn);
        totalPriceOrder = findViewById(R.id.total_price);
        recyclerView = findViewById(R.id.cart_list);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // add listener to button and reveal total bill
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                totalPriceOrder.setText( "Total amount = £" + String.valueOf(totalOrder));

                Intent intent = new Intent(CartActivity.this, ConfirmOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(totalOrder));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        //access database
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("User View")
                        .child(RememberMe.currentOnlineUser.getPhone()).child("Products"), Cart.class).build();
        //retrieve and display data from database
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {

                holder.txtProductQuantity.setText("Quantity =  " + model.getQuantity());
                holder.txtProductPrice.setText("£" + model.getPrice() + " unit");
                holder.txtProductName.setText(model.getProdName());
                holder.txtProductSize.setText(model.getSize());
                holder.txtProductDescription.setText(model.getDescription());

               double singlePrice = ((Double.valueOf(model.getPrice()))) * Double.valueOf(model.getQuantity());
               totalOrder = totalOrder + singlePrice;

               //when click on the item inisde the basket, a menu pop up to either edit or remove the item
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        CharSequence options[] = new CharSequence[] {

                                "Edit",
                                "Remove"

                        };

                        // it takes the user back to the menu if an item is deleted or to change quantity if edit is selected
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options: ");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if (i == 0){

                                    Intent intent = new Intent(CartActivity.this, SnacksDetailsActivity.class);
                                    intent.putExtra("rid", model.getRid());
                                    startActivity(intent);
                                }

                                if (i == 1){

                                    cartListRef.child("User View").child(RememberMe.currentOnlineUser.getPhone())
                                            .child("Products").child(model.getRid()).removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    Toast.makeText(CartActivity.this,"Item removed", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(CartActivity.this, RetailActivity.class);
                                                    startActivity(intent);
                                                }
                                            });

                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_in_cart_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;


            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
