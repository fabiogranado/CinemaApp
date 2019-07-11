package com.example.fabiogranado.cineapp;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import com.example.fabiogranado.cineapp.Model.Products;
import com.example.fabiogranado.cineapp.RememberMe.RememberMe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmOrderActivity extends AppCompatActivity {
    //declared variables
    private Button PurchaseButton;
    private EditText InputName, InputEmail;
    private String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);


        totalAmount = getIntent().getStringExtra("Total Price");
        // assign variable to xml id from activity_confirm_order
        InputName = findViewById(R.id.nameUser);
        InputEmail = findViewById(R.id.emailUser);
        PurchaseButton = findViewById(R.id.placeOrder);




        //add click listener to purchase button and run the verify function
        PurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verify();
            }

        });

    }

    //function to verify if fields are left empty by the user
    private void verify() {

        String email = InputEmail.getText().toString();

        if (TextUtils.isEmpty(InputName.getText().toString()))
        {
            Toast.makeText(this, "Field Mandatory", Toast.LENGTH_SHORT).show();
        }

        else if (!isEmailValid(email)) {

            Toast.makeText(this, "Please enter a valid email address...", Toast.LENGTH_SHORT).show();
        }

        finishBooking();
    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }
    private void finishBooking() {


        final String saveCurrentDate, saveCurrentTime;
        // set data format for database entry
        Calendar date = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(date.getTime());
        // set time format for database entry
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(date.getTime());

        // access database
        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        // create hash function to store data as (key, value) in the database
        final HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("totalAmount", totalAmount);
        orderMap.put("name", InputName.getText().toString());
        orderMap.put("email", InputEmail.getText().toString());
        orderMap.put("date", saveCurrentDate);
        orderMap.put("time", saveCurrentTime);
        //if booking if successful, add a new entry on the Orders node and update node list
        orderRef.child("User View").child(RememberMe.currentOnlineUser.getPhone())
        .updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    //access database, find and remove item from cart once booking is completed
                    FirebaseDatabase.getInstance().getReference().child("Cart List").
                            child("User View")
                            .child(RememberMe.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        Toast.makeText(ConfirmOrderActivity.this, "Booked Successfully", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ConfirmOrderActivity.this, OrderDetailActivity.class);
                                        startActivity(intent);
                                    }

                                }
                            });
                }
            }
        });

    }

}

