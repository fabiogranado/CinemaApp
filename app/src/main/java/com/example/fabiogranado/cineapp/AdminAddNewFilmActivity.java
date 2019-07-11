package com.example.fabiogranado.cineapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewFilmActivity extends AppCompatActivity {

    private String CategoryName, saveCurrentDate, saveCurrentTime, FilmName, Time1, Time2, Time3, filmKey, downloadImageUrl;
    private Button AddNewProductButton;
    private ImageView InputFilmPoster;
    private EditText InputFilmName, InputFilmTime1, InputFilmTime2, InputFilmTime3;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_film);


        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Film Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Films");

        // assign variable to an xml id
        AddNewProductButton = findViewById(R.id.add_new_product);
        InputFilmPoster = findViewById(R.id.select_product_image);
        InputFilmName = findViewById(R.id.film_name);
        InputFilmTime1 = findViewById(R.id.film_time1);
        InputFilmTime2 = findViewById(R.id.film_time2);
        InputFilmTime3 = findViewById(R.id.film_time3);


        loadingBar = new ProgressDialog(this);

        // add click listener to image and open picture gallery
        InputFilmPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });


        // add click listener to button and validate data passing the ValidateProductData function
        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
    }

    // open image gallery function, load all images
    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            InputFilmPoster.setImageURI(ImageUri);
        }

    }


    private void ValidateProductData() {

        // change input data to string
        FilmName = InputFilmName.getText().toString();
        Time1 = InputFilmTime1.getText().toString();
        Time2 = InputFilmTime2.getText().toString();
        Time3 = InputFilmTime3.getText().toString();


        // add condition, if any field is left blank a message is displayed asking to fill the blank field
        if (ImageUri == null) {
            Toast.makeText(this, "Image is mandatory...", Toast.LENGTH_SHORT).show();
            }
              else if (TextUtils.isEmpty(Time1)) {
                Toast.makeText(this, "Enter film time...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(Time2)) {
                Toast.makeText(this, "Enter film time.....", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(Time3)) {
                Toast.makeText(this, "Enter film time...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(FilmName)) {
                Toast.makeText(this, "Enter film name...", Toast.LENGTH_SHORT).show();
            } else {
                StoreFilmInformation();
            }
        }


        private void StoreFilmInformation () {

            // status message
            loadingBar.setTitle("Add New Film");
            loadingBar.setMessage("Adding the new film.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            Calendar calendar = Calendar.getInstance();
            // set data format for database entry
            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());
            // set time format for database entry
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calendar.getTime());
            //store the current data and time to a variable
            filmKey = saveCurrentDate + saveCurrentTime;

            //set path to be stored image as jpeg in the database, with current date and time.
            final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + filmKey + ".jpg");

            final UploadTask uploadTask = filePath.putFile(ImageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                    Toast.makeText(AdminAddNewFilmActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AdminAddNewFilmActivity.this, "Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImageUrl = task.getResult().toString();

                                Toast.makeText(AdminAddNewFilmActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                                SaveFilmInfoToDatabase();
                            }
                        }
                    });
                }
            });
        }


        private void SaveFilmInfoToDatabase ()
        {
            // create hash function to store data as (key, value) in the database
            HashMap<String, Object> filmMap = new HashMap<>();
            filmMap.put("fid", filmKey);
            filmMap.put("category", CategoryName);
            filmMap.put("time1", Time1);
            filmMap.put("time2", Time2);
            filmMap.put("time3", Time3);
            filmMap.put("filmName", FilmName);
            filmMap.put("image", downloadImageUrl);
            filmMap.put("date", saveCurrentDate);
            filmMap.put("time", saveCurrentTime);

            // redirects the user to to admin main menu once new item has been successfully added or display error otherwise
            ProductsRef.child(filmKey).updateChildren(filmMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(AdminAddNewFilmActivity.this, AdminCategoryHomeActivity.class);
                                startActivity(intent);

                                loadingBar.dismiss();
                                Toast.makeText(AdminAddNewFilmActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                            } else {
                                loadingBar.dismiss();
                                String message = task.getException().toString();
                                Toast.makeText(AdminAddNewFilmActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


}
