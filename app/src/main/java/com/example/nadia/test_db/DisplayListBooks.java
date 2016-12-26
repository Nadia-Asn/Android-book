package com.example.nadia.test_db;

/**
 * Created by Nadia on 09/11/2016.
 */

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.nadia.test_db.Collection.Book;
import com.example.nadia.test_db.DataBase.MySQLiteHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.AdapterView.OnItemClickListener;


public class DisplayListBooks extends AppCompatActivity {

    private ListView mListView;
    private String labels ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_list_books);

        mListView = (ListView) findViewById(R.id.display_book_listview);

        final MySQLiteHelper db = new MySQLiteHelper(this);

        // DELETE FOR TEST

        /*List<Book> listBookToDelete = db.getAllBooks();
        for (int i = 0; i < listBookToDelete.size(); i++) {
            db.deleteBook(listBookToDelete.get(i));
        }*/

        /**
         * CRUD Operations
         * */

        // get all books
/*
        private void loadImageFromStorage(String path)
        {

            try {
                File f=new File(path, "photo1.jpg");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                ImageView img=(ImageView)findViewById(R.id.imageLivre);
                img.setImageBitmap(b);
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }
*/

        final List<Book> listBook = db.getAllBooks();

        List<Map<String, String>> listOfBook = new ArrayList<>();

        for (int i = 0; i < listBook.size(); i++) {

            Book myBook = listBook.get(i);

            Map<String, String> bookMap = new HashMap<>();
            bookMap.put("image",String.valueOf(R.mipmap.livretest) );
            bookMap.put("isbn",myBook.getIsbn());
            bookMap.put("author", myBook.getAuthor());
            bookMap.put("title", myBook.getTitle());
            // mettre tous les dictionnaires , un par un,  dans ma liste
            listOfBook.add(bookMap);

        }

        SimpleAdapter myListAdapter = new SimpleAdapter(
                this,
                listOfBook,
                R.layout.book_item,
                new String[]{
                        "id",
                        "image",
                        "isbn",
                        "author",
                        "title"},
                new int[]{
                        R.id.id,
                        R.id.imageLivre,
                        R.id.isbn,
                        R.id.author,
                        R.id.title});


        mListView.setAdapter(myListAdapter);

        // get details of the book clicked
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map =(HashMap<String,String>)mListView.getItemAtPosition(position);

                //Toast.makeText(DisplayListBooks.this, "ID '" + map.get("title") + "' was clicked.", Toast.LENGTH_SHORT).show();
                String title = map.get("title");
                Book b=(Book)db.getBook(title);
                //Toast.makeText(DisplayListBooks.this,b.getId()+" "+b.getAuthor(),Toast.LENGTH_LONG).show();

                Intent i=new Intent(DisplayListBooks.this,DisplayDetailsBook.class);
                i.putExtra("id",b.getId());
                startActivity(i);

            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.buttonAdd) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}