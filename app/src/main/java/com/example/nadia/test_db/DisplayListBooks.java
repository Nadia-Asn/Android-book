package com.example.nadia.test_db;

/**
 * Created by Nadia on 09/11/2016.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.nadia.test_db.DataBase.MySQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DisplayListBooks extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_list_books);

        mListView = (ListView) findViewById(R.id.display_book_listview);


        MySQLiteHelper db = new MySQLiteHelper(this);


        // FOR TEST
/*
        List<Book> listBookToDelete = db.getAllBooks();
        for (int i = 0; i < listBookToDelete.size(); i++) {
            db.deleteBook(listBookToDelete.get(i));
        }
*/

        // add Books : TEST
        //db.addBook(new Book("1983973", "Sans famille", "Victor Hugo","12-2013", "Un matin comme les autres, Marc prépare le petit-déjeuner en famille. L'instant d'après, il se réveille sur un lit d'hôpital après douze jours de coma. Ce qui s'est passé entre-temps, deux inspecteurs de police venus à son chevet lui annoncent"));
        //db.addBook(new Book("6397974", "La prisonière", "Naima Oufkir","09-2002", "Un matin comme les autres, Marc prépare le petit-déjeuner en famille. L'instant d'après, il se réveille sur un lit d'hôpital après douze jours de coma. Ce qui s'est passé entre-temps, deux inspecteurs de police venus à son chevet lui annoncent"));
        //db.addBook(new Book("0099993", "La peste", "Albert Camus","03-2010", "Un matin comme les autres, Marc prépare le petit-déjeuner en famille. L'instant d'après, il se réveille sur un lit d'hôpital après douze jours de coma. Ce qui s'est passé entre-temps, deux inspecteurs de police venus à son chevet lui annoncent"));
        //db.addBook(new Book("1234222", "La vie devant soi", " Romain Gary"));
        //db.addBook(new Book("8795302", "Madame Bovary", "Gustave Flaubert"));
        //db.addBook(new Book("5397027", "La promesse de l'aube", "Romain Gary"));
        //db.addBook(new Book("425397", "L'inconnu", "Jean-Marc"));
        //db.addBook(new Book("6349732", "A la recherche du succès", "Albert Paul"));
        //db.addBook(new Book("5329708", "Madame Bovary", "Paul Renard"));
        //db.addBook(new Book("5329795", "A ne pas oublier", "Julien Le Comte"));


        /**
         * CRUD Operations
         * */

        // get all books

        List<Book> listBook = db.getAllBooks();

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

        /*for (int i = 0; i < listBook.size(); i++) {
            db.deleteBook(listBook.get(i));
        }*/
        // delete one book

        // get all books
        //db.getAllBooks();

    }
}