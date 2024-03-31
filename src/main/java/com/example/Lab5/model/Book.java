package com.example.Lab5.model;

import java.util.List;

public class Book {
    private int id;
    private String title;
    private String subtitle;
    private List<String> authors;
    int yearOfPublication;

    public Book(int id, String title, String subtitle, List<String> authors, int yearOfPublication) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.yearOfPublication = yearOfPublication;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
    public List<String> getAuthors() { return authors; }
    public void setAuthors(List<String> authors) { this.authors = authors; }
    public int getYearOfPublication() { return yearOfPublication; }
    public void setYearOfPublication(int yearOfPublication) { this.yearOfPublication = yearOfPublication; }

}
