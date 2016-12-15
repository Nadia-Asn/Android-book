package com.example.nadia.test_db.DataBase;

/**
 * Created by Nadia on 04/11/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nadia.test_db.Collection.Book;
import com.example.nadia.test_db.Collection.Categorie;
import com.example.nadia.test_db.Collection.Collection;

import java.util.LinkedList;
import java.util.List;



public class MySQLiteHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "BookDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Books table
    private static final String TABLE_BOOKS = "books";
    private static final String KEY_ID = "id";
    private static final String KEY_ISBN = "isbn";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE="Image";

    //Categorie table
    private static final String TABLE_CATEGORIE="categorie";
    private static final String KEY_ID_CATEGORIE="id_cat";
    private static final String KEY_NAME_CATEGORIE="categorie";

    //Collection table
    private static final String TABLE_COLLECTION="collection";
    private static final String KEY_ID_COLLECTION="id_collection";
    private static final String KEY_NAME_COLLECTION="collection";

    //association table 'book' & 'categorie'
    private static final String TABLE_BC="tableBC";
    private static final String KEY_BC_BOOK="id_book"; // id du livre
    private static final String KEY_BC_COL ="id_col"; // id categorie


    // creation of the book table
    private static final String CREATE_BOOKS="CREATE TABLE " + TABLE_BOOKS + " ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_ISBN + " TEXT NOT NULL, "
            + KEY_TITLE + " TEXT NOT NULL , "+KEY_AUTHOR+ " TEXT NOT NULL ,"+KEY_IMAGE+" BLOB"+", UNIQUE ("+KEY_TITLE+","+KEY_AUTHOR+"));";

    private static final String CREATE_CATEGORIES="CREATE TABLE IF NOT EXISTS "+ TABLE_CATEGORIE+ " ("
            +KEY_ID_CATEGORIE+" INTEGER NOT NULL PRIMARY KEY, "
            +KEY_NAME_CATEGORIE+" TEXT NOT NULL );";

    private static final String CREATE_COLLECTIONS="CREATE TABLE IF NOT EXISTS "+ TABLE_COLLECTION+ " ("
            +KEY_ID_COLLECTION+" INTEGER NOT NULL PRIMARY KEY, "
            +KEY_NAME_COLLECTION+" TEXT NOT NULL );";

    private static final String CREATE_BOOKCOL="CREATE TABLE IF NOT EXISTS "+ TABLE_BC+ " ("
            +KEY_BC_BOOK+ " INTEGER NOT NULL, " +KEY_BC_COL+" INTEGER NOT NULL,"
            +" FOREIGN KEY ("+KEY_BC_BOOK+") REFERENCES "+TABLE_BOOKS+" ("+KEY_ID+"),"
            +" FOREIGN KEY ("+ KEY_BC_COL +") REFERENCES "+TABLE_COLLECTION+" ("+KEY_ID_COLLECTION+"));";

    @Override
    public void onCreate(SQLiteDatabase db) {


        // SQL statement to create book table

        String CREATE_BOOK_TABLE = "CREATE TABLE books ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "isbn TEXT, " +
                "title TEXT, " +
                "author TEXT, " +
                "date TEXT, " +
                "description TEXT, " +
                "image BLOB )";

        // create books table
        db.execSQL(CREATE_BOOK_TABLE);

        //create all the tables of db
        //db.execSQL(CREATE_BOOKS);
        db.execSQL(CREATE_CATEGORIES);
        db.execSQL(CREATE_COLLECTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }



    //---------------------------------------------------------------------

    /**
     * CRUD operations
     */

    private static final String[] COLUMNS = {
            KEY_ID,
            KEY_ISBN,
            KEY_TITLE,
            KEY_AUTHOR,
            KEY_DATE,
            KEY_DESCRIPTION,
            KEY_IMAGE
    };


    // Add of a new book
    public void addBook(Book book){
        Log.d("addBook", book.toString());
        // get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ISBN, book.getIsbn());
        values.put(KEY_TITLE, book.getTitle());
        values.put(KEY_AUTHOR, book.getAuthor());
        values.put(KEY_DATE, book.getDate());
        values.put(KEY_DESCRIPTION, book.getDescription());
        values.put(KEY_IMAGE, book.getImage());

        // insert db
        db.insert(TABLE_BOOKS,
                null,
                values);

        //close db
        db.close();
    }

    // Add of a new collection
    public void addCollection(Collection collection){
        Log.d("addCollection", collection.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_COLLECTION, collection.getId_col());
        values.put(KEY_NAME_COLLECTION, collection.getCollection());

        db.insert(TABLE_COLLECTION,
                null,
                values);

        db.close();
    }

    // Add of a new categorie
    public void addCategorie(Categorie categorie){
        Log.d("addCategorie", categorie.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CATEGORIE, categorie.getId_cat());
        values.put(KEY_NAME_CATEGORIE, categorie.getCategorie());

        db.insert(TABLE_CATEGORIE,
                null,
                values);

        db.close();
    }



    public Book getBook(String title){

        // get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // build query
        Cursor cursor =
                db.query(TABLE_BOOKS, // table
                        COLUMNS, // column names
                        " title = ?", // selections
                        new String[] { String.valueOf(title) }, // selections args
                        null, // group by
                        null, // having
                        null, // order by
                        null); // limit

        // if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // build book object
        Book book = new Book();
        book.setId(Integer.parseInt(cursor.getString(0)));
        book.setIsbn(cursor.getString(1));
        book.setTitle(cursor.getString(2));
        book.setAuthor(cursor.getString(3));
        book.setDate(cursor.getString(4));
        book.setDescription(cursor.getString(5));
        //book.setImage(cursor.getBlob(6));

        Log.d("getBook("+title+")", book.toString());

        // return book
        return book;
    }

    public Book getBook(int id){

        // get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // build query
        Cursor cursor =
                db.query(TABLE_BOOKS, // table
                        COLUMNS, // column names
                        " id = ?", // selections
                        new String[] { String.valueOf(id) }, // selections args
                        null, // group by
                        null, // having
                        null, // order by
                        null); // limit

        // if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // build book object
        Book book = new Book();
        book.setId(Integer.parseInt(cursor.getString(0)));
        book.setIsbn(cursor.getString(1));
        book.setTitle(cursor.getString(2));
        book.setAuthor(cursor.getString(3));
        book.setDate(cursor.getString(4));
        book.setDescription(cursor.getString(5));
        //book.setImage(cursor.getBlob(6));

        Log.d("getBook("+id+")", book.toString());

        // return book
        return book;
    }

    // Get All Books
    public List<Book> getAllBooks() {
        List<Book> books = new LinkedList<Book>();

        // build the query
        String query = "SELECT  * FROM " + TABLE_BOOKS;

        // get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // go over each row, build book and add it to list
        Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setIsbn(cursor.getString(1));
                book.setTitle(cursor.getString(2));
                book.setAuthor(cursor.getString(3));
                book.setDate(cursor.getString(4));
                book.setDescription(cursor.getString(5));
                //book.setImage(cursor.getBlob(6));

                // Add book to books
                books.add(book);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", books.toString());

        // return books
        return books;
    }

    // Deleting a book
    public int removeWithID(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BOOKS, KEY_ID + " = " + id, null);
    }

    // Deleting single book
    public void deleteBook(Book book) {

        // get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // delete
        db.delete(TABLE_BOOKS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(book.getId()) });

        // close
        db.close();

        Log.d("deleteBook", book.toString());

    }

}