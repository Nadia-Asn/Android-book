package com.example.nadia.test_db;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadia.test_db.Collection.Book;
import com.example.nadia.test_db.DataBase.MySQLiteHelper;
import android.support.v7.widget.Toolbar;


/**
 * Created by Nadia on 09/11/2016.
 */

public class DisplayDetailsBook extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_details_book);

        final int id=(int)getIntent().getIntExtra("id",-1);
        Button buttonCollection=(Button)findViewById(R.id.addCategoryLivre);
        buttonCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(DisplayDetailsBook.this,ListToAddBookCollectionActivity.class);
                //Log.i("id",id);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        //bouton delete book
        Button b= (Button)findViewById(R.id.delete_btn);

        TextView auteur=(TextView)findViewById(R.id.author);
        TextView title= (TextView)findViewById(R.id.title);
        TextView isbn=(TextView)findViewById(R.id.isbn);
        TextView date=(TextView)findViewById(R.id.date);
        TextView description=(TextView)findViewById(R.id.description);

        final MySQLiteHelper d = new MySQLiteHelper(this);

        final Book book=(Book)d.getBook(id);

        // Display the detail of the book selected
        auteur.setText(book.getAuthor());
        title.setText(book.getTitle());
        isbn.setText(book.getIsbn());
        date.setText(book.getDate());
        description.setText(book.getDescription());

        //Delete of the book
        b.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View v){
                d.removeWithID(id);
                Toast.makeText(DisplayDetailsBook.this, "Le livre " + book.getTitle() + " à été supprimé de la bibliothèque", Toast.LENGTH_LONG).show();
                Intent i = new Intent(DisplayDetailsBook.this, DisplayListBooks.class);
                startActivity(i);

            }
        });





    }


}
