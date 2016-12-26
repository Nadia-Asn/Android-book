package com.example.nadia.test_db.com.google.zxing.integration.android;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.nadia.test_db.Collection.Book;
import com.example.nadia.test_db.DataBase.MySQLiteHelper;
import com.example.nadia.test_db.DisplayListBooks;
import com.example.nadia.test_db.R;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Scanne extends AppCompatActivity  {


    // CLE d'API : AIzaSyAKipAT4jhpxgqXb_9QWN6-lEuAoc97IYI
    private Button scan_btn, ajouterBtn;
    private TextView formatTxt, contentTxt;
    private TextView  ratingCountText;
    private EditText titleText ,authorText, descriptionText, dateText, isbnText;
    private LinearLayout starLayout;
    private ImageView thumbView;
    private String photoBook;
    private ImageView[] starViews;

    private Bitmap thumbImg;

    MySQLiteHelper db = new MySQLiteHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scan_isbn);

        scan_btn = (Button)findViewById(R.id.buttonScan);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);

        ajouterBtn = (Button)findViewById(R.id.ajouter_btn);

        ajouterBtn.setVisibility(View.GONE);
        //ajouterBtn.setOnClickListener(this);

        authorText = (EditText) findViewById(R.id.book_author);
        titleText = (EditText) findViewById(R.id.book_title);
        descriptionText = (EditText) findViewById(R.id.book_description);
        dateText = (EditText) findViewById(R.id.book_date);
        isbnText = (EditText) findViewById(R.id.isbn);
        starLayout = (LinearLayout)findViewById(R.id.star_layout);
        ratingCountText = (TextView)findViewById(R.id.book_rating_count);
        thumbView = (ImageView)findViewById(R.id.thumb);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.buttonScan){

                    // lancer le scanner au clic sur le boutton scan
                    IntentIntegrator scanIntegrator = new IntentIntegrator(Scanne.this);
                    scanIntegrator.initiateScan();
                }
            }
        });

        ajouterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.ajouter_btn){

                    // ajouter les informations du livre
                    Context context = getApplicationContext();

                    String author = authorText.getText().toString();
                    String title = titleText.getText().toString();
                    String description = descriptionText.getText().toString();
                    String date = dateText.getText().toString();
                    String isbn = "9796586909";
                    try {
                        if((author != null && author.length() > 0) && (title != null && title.length() > 0)){
                            db.addBook(new Book(isbn,title,author,date, description));

                            Intent i = new Intent(context, DisplayListBooks.class);
                            startActivity(i);
                        }
                        else {Toast.makeText(context,"Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();}
                    }
                    catch (Exception e) {
                        Toast.makeText(context,"Veuillez remplir tous les champs 2 ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        starViews=new ImageView[5];
        for(int s=0; s<starViews.length; s++){
            starViews[s]=new ImageView(this);
        }



    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {



        // Parser le résultat du scan
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

            // Recup le contenu du code barre
            String scanContent = scanningResult.getContents();
            //String scanContent = "9782844859181";
            // Recup le format du code barre

            String scanFormat = "EAN_13";
            //String scanFormat = scanningResult.getFormatName();


            //formatTxt.setText("FORMAT: " + scanFormat);
            //contentTxt.setText("CONTENT: " + scanContent);


                if(scanContent!=null && scanFormat!=null && scanFormat.equalsIgnoreCase("EAN_13")){
                //book search
                    //created the search query string
                    String bookSearchString = "https://www.googleapis.com/books/v1/volumes?"+
                            "q=isbn:"+scanContent;//+"&key=AIzaSyAKipAT4jhpxgqXb_9QWN6-lEuAoc97IYI";

                            new GetBookInfo().execute(bookSearchString);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Scan non valable!", Toast.LENGTH_SHORT);
                    toast.show();
                }

        }
        else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Aucune donnée reçu!", Toast.LENGTH_SHORT);
                toast.show();
        }
    }

    //request and retrieve the book search results
    private class GetBookInfo extends AsyncTask<String, Void, String> {

        //fetch book info

        //receive the URL string and return a string representing the search results
        @Override
        protected String doInBackground(String... bookURLs) {
        //request book info
            StringBuilder bookBuilder = new StringBuilder();

            for (String bookSearchURL : bookURLs) {
            //search urls
                HttpClient bookClient = new DefaultHttpClient();

                try {
                    //get the data
                    HttpGet bookGet = new HttpGet(bookSearchURL);
                    HttpResponse bookResponse = bookClient.execute(bookGet);
                    StatusLine bookSearchStatus = bookResponse.getStatusLine();
                    if (bookSearchStatus.getStatusCode()==200) {
                        //we have a result
                        HttpEntity bookEntity = bookResponse.getEntity();

                        // read the content into a Buffered Reader so that we can process it
                        InputStream bookContent = bookEntity.getContent();
                        InputStreamReader bookInput = new InputStreamReader(bookContent);
                        BufferedReader bookReader = new BufferedReader(bookInput);

                        String lineIn;
                        while ((lineIn=bookReader.readLine())!=null) {
                            bookBuilder.append(lineIn);
                        }
                    }
                }
                catch(Exception e){ e.printStackTrace(); }

            }
            return bookBuilder.toString();

        }

        protected void onPostExecute(String result) {
            //parse search results

            try{
            //parse results

                JSONObject resultObject = new JSONObject(result);
                JSONArray bookArray = resultObject.getJSONArray("items");

                JSONObject bookObject = bookArray.getJSONObject(0);

                JSONObject volumeObject = bookObject.getJSONObject("volumeInfo");

                try{
                    titleText.setText("Titre : "+volumeObject.getString("title")); }
                catch(JSONException jse){
                    titleText.setText("");
                    jse.printStackTrace();
                }

                StringBuilder authorBuild = new StringBuilder("");
                try{
                    JSONArray authorArray = volumeObject.getJSONArray("authors");
                    for(int a=0; a<authorArray.length(); a++){
                        if(a>0) authorBuild.append(", ");
                        authorBuild.append(authorArray.getString(a));
                    }
                    authorText.setText("Auteur : "+authorBuild.toString());
                }
                catch(JSONException jse){
                    authorText.setText("");
                    jse.printStackTrace();
                }


                try{
                    dateText.setText("Publié le : "+volumeObject.getString("publishedDate")); }
                catch(JSONException jse){
                    dateText.setText("");
                    jse.printStackTrace();
                }

                try{
                    descriptionText.setText("Description : "+volumeObject.getString("description")); }
                catch(JSONException jse){
                    descriptionText.setText("");
                    jse.printStackTrace();
                }

                try{
                //set stars
                    double decNumStars = Double.parseDouble(volumeObject.getString("averageRating"));
                    int numStars = (int)decNumStars;

                    starLayout.setTag(numStars);
                    starLayout.removeAllViews();

                    for(int s=0; s<numStars; s++){
                        starViews[s].setImageResource(R.drawable.star);
                        starLayout.addView(starViews[s]);
                    }
                }
                catch(JSONException jse){
                    starLayout.removeAllViews();
                    jse.printStackTrace();
                }

                try{ ratingCountText.setText(" - "+volumeObject.getString("ratingsCount")+" ratings"); }
                catch(JSONException jse){
                    ratingCountText.setText("");
                    jse.printStackTrace();
                }





                try{
                    ajouterBtn.setTag(volumeObject.getString("infoLink"));
                    ajouterBtn.setVisibility(View.VISIBLE);
                }
                catch(JSONException jse){
                    ajouterBtn.setVisibility(View.GONE);
                    jse.printStackTrace();
                }

                try{
                    JSONObject imageInfo = volumeObject.getJSONObject("imageLinks");
                    new GetBookThumb().execute(imageInfo.getString("smallThumbnail"));
                    saveToInternalStorage(thumbImg,photoBook);
                }
                catch(JSONException jse){
                    thumbView.setImageBitmap(null);
                    jse.printStackTrace();
                }



            }


            catch (Exception e) {
            //no result
                e.printStackTrace();
                titleText.setText("NOT FOUND ! ! !");
                authorText.setText("");
                descriptionText.setText("");
                dateText.setText("");
                starLayout.removeAllViews();
                ratingCountText.setText("");
                thumbView.setImageBitmap(null);
                ajouterBtn.setVisibility(View.GONE);
            }

        }


    }




    private class GetBookThumb extends AsyncTask<String, Void, String> {
        //get thumbnail
        @Override
        protected String doInBackground(String... thumbURLs) {
        //attempt to download image

            try{
                //try to download
                URL thumbURL = new URL(thumbURLs[0]);
                URLConnection thumbConn = thumbURL.openConnection();
                thumbConn.connect();

                InputStream thumbIn = thumbConn.getInputStream();
                BufferedInputStream thumbBuff = new BufferedInputStream(thumbIn);

                thumbImg = BitmapFactory.decodeStream(thumbBuff);

                thumbBuff.close();
                thumbIn.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return "";

        }


        protected void onPostExecute(String result) {
            thumbView.setImageBitmap(thumbImg);
            saveToInternalStorage(thumbImg,"photoBook");

    }



    }
    private String saveToInternalStorage(Bitmap thumbImg,String photoBook){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,photoBook+".png");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            thumbImg.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }






}
