package com.example.nadia.test_db;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nadia.test_db.Collection.Collection;
import com.example.nadia.test_db.DataBase.MySQLiteHelper;

public class NewCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_collection);
        final Context context = getApplicationContext();
        Button bouton=(Button)findViewById(R.id.buttonCreerCollection);
        final EditText  name=((EditText)findViewById(R.id.nomCollection));
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewCollectionActivity.this,CollectionActivity.class);
                MySQLiteHelper db = new MySQLiteHelper(NewCollectionActivity.this);
                db.addCollection(name.getText().toString());
                Toast.makeText(context,"Nouvelle Collection creer", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}
