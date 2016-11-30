package com.example.nadia.test_db;

/**
 * Created by Nadia on 24/11/2016.
 */

public class Categorie {
    private String name;
    private String author;

    public Categorie() {
    }

    public Categorie(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {return author; }

    public void setAuthor(String author) {
        this.author = author;
    }
}