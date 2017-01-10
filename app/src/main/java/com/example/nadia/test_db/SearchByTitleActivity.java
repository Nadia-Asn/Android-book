package com.example.nadia.test_db;

/**
 * Created by Nadia on 09/01/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.nadia.test_db.Collection.Book;
import com.example.nadia.test_db.DataBase.MySQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchByTitleActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_title_display);
        final MySQLiteHelper db = new MySQLiteHelper(this);
        final ListView mListView = (ListView) findViewById(R.id.display_book_listview_search_title);
        Intent intent=getIntent();
        List<Book> listBook=db.searchByTitle(intent.getStringExtra("titre"));
        List<Map<String, String>> listOfBook = new ArrayList<>();

        for (int i = 0; i < listBook.size(); i++) {

            Book myBook = listBook.get(i);

            Map<String, String> bookMap = new HashMap<>();
            bookMap.put("id",String.valueOf(myBook.getId()));
            bookMap.put("image",String.valueOf(R.mipmap.livretest) );
            bookMap.put("isbn",myBook.getIsbn());
            bookMap.put("author", myBook.getAuthor());
            bookMap.put("title", myBook.getTitle());
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

                Intent i=new Intent(SearchByTitleActivity.this,DisplayDetailsBook.class);
                Toast.makeText(SearchByTitleActivity.this, "Le livre : " + b.getTitle() + " son id : "+ b.getId(), Toast.LENGTH_LONG).show();
                i.putExtra("id",b.getId());
                //Log.i("id",id);
                startActivity(i);

            }
        });

    }
}
