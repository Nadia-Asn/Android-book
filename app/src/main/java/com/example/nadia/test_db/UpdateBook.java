package com.example.nadia.test_db;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadia.test_db.Collection.Book;
import com.example.nadia.test_db.DataBase.MySQLiteHelper;
import com.example.nadia.test_db.R;

/**
 * Created by Nadia on 09/01/2017.
 */

public class UpdateBook extends AppCompatActivity{
    Button updateBtn;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        setContentView(R.layout.update_book);
        updateBtn = (Button) findViewById(R.id.update_btn);

        // réccuperer l'id du livre selectionné pour la modification
        final int id =getIntent().getIntExtra("id",0);

        final EditText auteur=(EditText) findViewById(R.id.author);
        final EditText titre= (EditText) findViewById(R.id.title);
        final EditText isbn=(EditText) findViewById(R.id.isbn);
        final EditText date=(EditText) findViewById(R.id.date);
        final EditText description=(EditText) findViewById(R.id.description);

        //récupérer les informations du livre pour les modifier

        final MySQLiteHelper db = new MySQLiteHelper(this);

        final Book book=(Book)db.getBook(id);

        isbn.setText(book.getIsbn());
        auteur.setText(book.getAuthor());
        titre.setText(book.getTitle());
        date.setText(book.getDate());
        description.setText(book.getDescription());


        //Enregistrer les informations modifié
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.update_btn){

                    // ajouter les informations du livre
                    Context context = getApplicationContext();

                    String author = auteur.getText().toString();
                    String title = titre.getText().toString();
                    String description_ = description.getText().toString();
                    String date_ = date.getText().toString();
                    String isbn_ = isbn.getText().toString();
                    db.updateBook(id,isbn_,title,author,date_, description_);

                    Intent i = new Intent(context, MainActivity.class);

                    startActivity(i);
                }
            }
        });


    }

}
