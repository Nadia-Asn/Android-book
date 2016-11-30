package com.example.nadia.test_db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.nadia.test_db.com.google.zxing.integration.android.Scanne;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonDisplay).setOnClickListener(this);
        findViewById(R.id.buttonSearchIsbn).setOnClickListener(this);
        findViewById(R.id.buttonScanIsbn).setOnClickListener(this);

    }
    // Lunching a new activity : Adding a book - Displaying a the map of the books
    //@Override
    public void onClick(View v) {

        // if the button add
        if (v.getId() == R.id.buttonAdd) {
            // Jamais de else onClick Benjamin
            Intent intent = new Intent(this, AddBook.class);
            startActivity(intent);
        }
        // If the button Diplay
        if (v.getId() == R.id.buttonDisplay) {
            Intent intent = new Intent(this, DisplayListBooks.class);
            startActivity(intent);
        }
        //If button Search clicked
        if (v.getId() == R.id.buttonSearchIsbn){
            Intent intent = new Intent(this, SearchIsbn.class);
            startActivity(intent);
        }
        //If button scan clicked
        if (v.getId() == R.id.buttonScanIsbn){
            Intent intent = new Intent(this, Scanne.class);
            startActivity(intent);
        }
    }



}