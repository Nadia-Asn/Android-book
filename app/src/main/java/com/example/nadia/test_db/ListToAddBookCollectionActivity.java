package com.example.nadia.test_db;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nadia.test_db.Collection.Collection;
import com.example.nadia.test_db.DataBase.MySQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListToAddBookCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getApplicationContext();
        setContentView(R.layout.activity_list_to_add_book_collection);

        final MySQLiteHelper db = new MySQLiteHelper(this);
        final int id =getIntent().getIntExtra("id",0);
        ListView mListView = (ListView) findViewById(R.id.listToAddBookCollection);
        List<Collection> collections = db.getAllCollection();
        final String id2 =getIntent().getStringExtra("id");
        List<Map<String, String>> listOfCollection = new ArrayList<>();

        for (int i = 0; i < collections.size(); i++) {

            Collection collection = collections.get(i);

            Map<String, String> collectionMap = new HashMap<>();
            collectionMap.put("id",collection.getId_col()+"");
            Log.i("nom",collection.getCollection()) ;
            collectionMap.put("nom", collection.getCollection());
            listOfCollection.add(collectionMap);
        }

        SimpleAdapter myListAdapter = new SimpleAdapter(
                this,
                listOfCollection,
                R.layout.catalogue_item,
                new String[]{
                        "id",
                        "nom"},
                new int[]{
                        R.id.idCollection,
                        R.id.titreCollection});
        mListView.setAdapter(myListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView idCollection=(TextView) view.findViewById(R.id.idCollection);
                //Log.i("valeur",id);
                db.addBookInCollection(id,idCollection.getText().toString());
                //Toast.makeText(context,"Livre ajouter à la collection", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ListToAddBookCollectionActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
