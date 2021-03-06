package com.example.nadia.test_db;

/**
 * Created by Nadia on 09/11/2016.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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


import static com.example.nadia.test_db.com.google.zxing.integration.android.Scanne.APP_PATH_SD_CARD;
import static com.example.nadia.test_db.com.google.zxing.integration.android.Scanne.APP_THUMBNAIL_PATH_SD_CARD;


public class DisplayListBooks extends AppCompatActivity  {

    private ListView mListView;
    private String labels ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_list_books);

        mListView = (ListView) findViewById(R.id.display_book_listview);

        final MySQLiteHelper db = new MySQLiteHelper(this);

        final List<Book> listBook = db.getAllBooks();

        List<Map<String, String>> listOfBook = new ArrayList<>();


        for (int i = 0; i < listBook.size(); i++) {

            Book myBook = listBook.get(i);


            // A REVOIIIIIIIIR
            /*String myImg = myBook.getImage1();

            private void loadImageFromStorage(String myImg)
            {

                try {
                    File f=new File(myImg, "desiredFilename.png");
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                    ImageView img=(ImageView)findViewById(R.id.imageLivre);
                    img.setImageBitmap(b);
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }

            }*///////////////////////////////

            Map<String, String> bookMap = new HashMap<>();
            bookMap.put("id" , myBook.getId()+"");
            bookMap.put("image", String.valueOf(R.mipmap.livretest));
            bookMap.put("isbn", myBook.getIsbn());
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



        /*******************************************************/

        // get details of the book clicked

        mListView.setAdapter(myListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, String> map = (HashMap<String, String>) mListView.getItemAtPosition(position);

                String title = map.get("title");
                Book b = (Book) db.getBook(title);

                Intent i = new Intent(DisplayListBooks.this, DisplayDetailsBook.class);
                //Toast.makeText(DisplayListBooks.this, "Le livre : " + b.getTitle() + " son id : " + b.getId(), Toast.LENGTH_LONG).show();
                i.putExtra("id", b.getId());
                //Log.i("id",id);
                startActivity(i);

            }
        });


        /*******************************************************/

        // Delete the book selected with a LongClick

        mListView.setAdapter(myListAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                final TextView idBook=(TextView) view.findViewById(R.id.id);

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        DisplayListBooks.this);
                alert.setTitle("Alert !!");
                alert.setMessage("Voulez-vous vraiment supprimer ce livre ?");
                alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here


                        int idB = Integer.valueOf((idBook.getText()).toString());
                        db.removeWithID(idB);
                        //Toast.makeText(DisplayListCollectionActivity.this, "Collection :  " + idCol + " supprimée", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(DisplayListBooks.this, MainActivity.class);
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




    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        /*public boolean isSdReadable(){

            boolean mExternalStorageAvailable = false;
            String state = Environment.getExternalStorageState();

            if (Environment.MEDIA_MOUNTED.equals(state)) {
// We can read and write the media
                mExternalStorageAvailable = true;
                Log.i("isSdReadable", "External storage card is readable.");
            } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
// We can only read the media
                Log.i("isSdReadable", "External storage card is readable.");
                mExternalStorageAvailable = true;
            } else {
// Something else is wrong. It may be one of many other
// states, but all we need to know is we can neither read nor write
                mExternalStorageAvailable = false;
            }

            //return mExternalStorageAvailable;
        }*/

        /*public Bitmap getThumbnail(String filename) {

            String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + APP_PATH_SD_CARD + APP_THUMBNAIL_PATH_SD_CARD;
            Bitmap thumbnail = null;

        thumbnail = BitmapFactory.decodeFile(fullPath + "/" + filename);

// Look for the file on the external storage
           /* try {
                if (tools.isSdReadable() == true) {

                }
            } catch (Exception e) {
                //Log.e("getThumbnail() on external storage", e.getMessage());
            }*/

        //Context context = getApplicationContext();
// If no file on external storage, look in internal storage
           /* if (thumbnail == null) {
                try {
                    File filePath = context.getFileStreamPath(filename);
                    FileInputStream fi = new FileInputStream(filePath);
                    thumbnail = BitmapFactory.decodeStream(fi);
                } catch (Exception ex) {
                    //Log.e("getThumbnail() on internal storage", ex.getMessage());
                }
            }*/
          //  return thumbnail;
        //}

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