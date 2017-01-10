package com.example.nadia.test_db;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadia.test_db.Collection.Book;
import com.example.nadia.test_db.DataBase.MySQLiteHelper;

import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBookCollectionDisplayActivity extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book_category_display);
         mListView=(ListView)findViewById(R.id.displayListBookALLCategory);



        final MySQLiteHelper db = new MySQLiteHelper(this);


        // get all books

        List<Book> listBook = db.getBooksByCategory(getIntent().getStringExtra("id"));
        Log.i("test",listBook.size()+"");
        List<Map<String, String>> listOfBook = new ArrayList<>();

        for (int i = 0; i < listBook.size(); i++) {

            Book myBook = listBook.get(i);

            Map<String, String> bookMap = new HashMap<>();
            bookMap.put("image",String.valueOf(R.mipmap.livretest) );
            bookMap.put("isbn",myBook.getIsbn());
            bookMap.put("author", myBook.getAuthor());
            bookMap.put("title", myBook.getTitle());
            bookMap.put("date", myBook.getDate());
            bookMap.put("description", myBook.getDescription());
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
                        "title",
                        "date",
                        "description"},
                new int[]{
                        R.id.id,
                        R.id.imageLivre,
                        R.id.isbn,
                        R.id.author,
                        R.id.title,
                        R.id.date,
                        R.id.description});


        mListView.setAdapter(myListAdapter);

        // get details of the book clicked
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map =(HashMap<String,String>)mListView.getItemAtPosition(position);

                String title = map.get("title");
                Book b=(Book)db.getBook(title);
                Intent i=new Intent(ListBookCollectionDisplayActivity.this,DisplayDetailsBook.class);
                Toast.makeText(ListBookCollectionDisplayActivity.this, "Le livre : " + b.getTitle() + " son id : "+ b.getId(), Toast.LENGTH_LONG).show();
                i.putExtra("id",b.getId());
                startActivity(i);

            }
        });


        /************************************************************/

        // Delete the book selected from the collection

        mListView.setAdapter(myListAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                HashMap<String,String> map =(HashMap<String,String>)mListView.getItemAtPosition(position);

                String title = map.get("title");
                final Book b=(Book)db.getBook(title);
                final TextView idBook=(TextView) view.findViewById(R.id.id);

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        ListBookCollectionDisplayActivity.this);
                alert.setTitle("Alert !!");
                alert.setMessage("Voulez-vous supprimer le livre de cette collection ?");
                alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int idCol =Integer.valueOf(getIntent().getStringExtra("id"));

                        db.removeBookCollection(b.getId(),idCol);
                        Intent i = new Intent(ListBookCollectionDisplayActivity.this, MainActivity.class);
                        startActivity(i);
                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;
            }

        });


    }
}
