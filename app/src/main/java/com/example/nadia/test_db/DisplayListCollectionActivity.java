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

import com.example.nadia.test_db.Collection.Collection;
import com.example.nadia.test_db.DataBase.MySQLiteHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayListCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_list_collection);
        final MySQLiteHelper db = new MySQLiteHelper(this);
        ListView mListView = (ListView) findViewById(R.id.listViewCollection);
        List<Collection> collections = db.getAllCollection();

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
                Intent intent=new Intent(DisplayListCollectionActivity.this,ListBookCollectionDisplayActivity.class);
                intent.putExtra("id",idCollection.getText().toString());
                startActivity(intent);
            }
        });

        mListView.setLongClickable(true);


        /*******************************************************/

        // DELETE THE COLLECTION SELECTED

        mListView.setAdapter(myListAdapter);
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                final TextView idCollection=(TextView) view.findViewById(R.id.idCollection);

                AlertDialog.Builder alert = new AlertDialog.Builder(
                        DisplayListCollectionActivity.this);
                alert.setTitle("Alert !!");
                alert.setMessage("Voulez-vous supprimer cette collection ?");
                //si l'utilisateur confirme la suppression
                alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int idCol = Integer.valueOf((idCollection.getText()).toString());
                        db.removeCollection(idCol);
                        //Toast.makeText(DisplayListCollectionActivity.this, "Collection :  " + idCol + " supprimée", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(DisplayListCollectionActivity.this, CollectionActivity.class);
                        startActivity(i);
                        dialog.dismiss();

                    }
                });
                //si l'utilisateur annule la suppression
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
