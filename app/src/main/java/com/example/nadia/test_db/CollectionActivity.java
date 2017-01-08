package com.example.nadia.test_db;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CollectionActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        findViewById(R.id.addCollection).setOnClickListener(this);
        findViewById(R.id.listCollection).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addCollection) {
            Intent intent = new Intent(this,NewCollectionActivity.class);
            startActivity(intent);
        }
        if ( view.getId() == R.id.cancel){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        if (view.getId() == R.id.listCollection) {
            Intent intent = new Intent(this, DisplayListCollectionActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.cancel) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }
}
