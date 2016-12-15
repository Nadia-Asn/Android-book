package com.example.nadia.test_db;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nadia.test_db.Collection.Book;
import com.example.nadia.test_db.DataBase.MySQLiteHelper;

/**
 * Created by Nadia on 09/11/2016.
 */

public class AddBook extends AppCompatActivity {

    Button b;
    EditText t1;
    EditText t2;
    EditText t3;
    EditText t4;
    EditText t5;

    MySQLiteHelper db = new MySQLiteHelper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book);

        b = (Button) findViewById(R.id.buttonAdd);
        t1 = (EditText) findViewById(R.id.isbn);
        t2 = (EditText) findViewById(R.id.title);
        t3 = (EditText) findViewById(R.id.author);
        t4 = (EditText) findViewById(R.id.date);
        t5 = (EditText) findViewById(R.id.description);


        final Context context = this;





        b.setOnClickListener(new View.OnClickListener()

                             {
                                 @Override
                                 public void onClick(View v) {

                                     Context context = getApplicationContext();

                                     String s = t1.getText().toString();
                                     String s1 = t2.getText().toString();
                                     String s2 = t3.getText().toString();
                                     String s3 = t4.getText().toString();
                                     String s4 = t5.getText().toString();
                                     try {
                                         if((s != null && s.length() > 0) && (s1 != null && s.length() > 0) && (s2 != null && s.length() > 0)){
                                             db.addBook(new Book(s,s1,s2,s3,s4));
                                             Intent i = new Intent(context, DisplayListBooks.class);
                                             startActivity(i);
                                             }
                                         else {Toast.makeText(context,"Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();}
                                     }
                                     catch (Exception e) {
                                         Toast.makeText(context,"Veuillez remplir tous les champs 2 ", Toast.LENGTH_SHORT).show();
                                     }


                                 //db.execSQL("INSERT INTO log VALUES (s)");


                                     //db.addBook(new Book(s,s1,s2));
                                     /*
                                     ContentValues values = new ContentValues();
                                     values.put("isbn", s);
                                     values.put("title", s1);
                                     values.put("author", s2);
                                     if ((db.insert("book", null, values)) != -1) {
                                         Toast.makeText(Create.this, "Inserted...", 2000).show();
                                     } else {
                                         Toast.makeText(Create.this, "Error...", 2000).show();
                                     }
                                     t1.setText("");
                                     t2.setText("");
                                     t3.setText("");

                                     */
                                    // Intent i = new Intent(context, DisplayListBooks.class);
                                     //startActivity(i);

                                 }
                             }

        );
    }
}

