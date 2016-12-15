package com.example.nadia.test_db.Collection;

/**
 * Created by Nadia on 14/12/2016.
 */

public class Collection {

    private int id_col;
    private String collection;

    public Collection() {}

    public Collection(int id_col, String collection){
        this.id_col = id_col;
        this.collection = collection;
    }

    public int getId_col() {
        return id_col;
    }

    public void setId_col(int id_col) {
        this.id_col = id_col;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}

