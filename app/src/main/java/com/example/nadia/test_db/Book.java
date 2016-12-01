package com.example.nadia.test_db;

/**
 * Created by Nadia on 09/11/2016.
 */

public class Book {

    private int id;
    private String isbn;
    private String title;
    private String author;
    private String date;
    private String description;

    public Book() {
    }

    public Book( String isbn, String title, String author, String date, String description) {
        super();
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
    }

    //getters & setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {return date; }

    public void setDate(String date) { this.date = date; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Book [id=" + id + ", isbn=" + isbn + ", title=" + title + ", author=" + author
                + "]";
    }

}
