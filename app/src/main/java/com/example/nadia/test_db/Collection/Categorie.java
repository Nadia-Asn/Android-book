package com.example.nadia.test_db.Collection;

/**
 * Created by Nadia on 14/12/2016.
 */

public class Categorie extends Collection {
    private int id_cat;
    private String categorie;

    public Categorie() {
    }

    public Categorie(int id_cat, String categorie) {
        this.id_cat = id_cat;
        this.categorie = categorie;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
