package com.example.nadia.test_db;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Nadia on 10/11/2016.
 */

public class SearchIsbn extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_isbn);
        final EditText text=(EditText) findViewById(R.id.isbn);
        final EditText titre=(EditText) findViewById(R.id.edit_titre);
        final EditText auteur=(EditText) findViewById(R.id.edit_auteur);
        Button isbnButton=(Button)findViewById(R.id.buttonSearchIsbn);
        Button auteurButton=(Button)findViewById(R.id.buttonSearchAuteur);
        Button titreButton=(Button)findViewById(R.id.buttonSearchTitre);
        isbnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchIsbn.this,SearchIsbnDisplayActivity.class);
                intent.putExtra("isbn",text.getText().toString());
                startActivity(intent);
            }
        });
        auteurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchIsbn.this,SearchAuteurDisplayActivity.class);
                intent.putExtra("auteur",auteur.getText().toString());
                startActivity(intent);
            }
        });
        titreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchIsbn.this,SearchTitleDisplayActivity.class);
                intent.putExtra("titre",titre.getText().toString());
                startActivity(intent);
            }
        });
    }


    }


