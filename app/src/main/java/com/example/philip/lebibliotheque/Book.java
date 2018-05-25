package com.example.philip.lebibliotheque;

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements Serializable {

    private String title;
    private ArrayList<String> authors;
    private String publisher;
    private String publishedYear;
    private String description;
    private String imageURL;

    public Book() {
    }

    public Book(String title, ArrayList<String> authors, String publisher, String publishedYear, String description, String imageURL) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.description = description;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public String getDescription() {
        return description;
    }

    public String getFirstAuthor() {
        return authors != null && !authors.isEmpty() ? authors.get(0) : "";
    }

    public boolean hasImageURL() {
        return imageURL != null && imageURL.length() > 0;
    }

    public String getImageURL() {
        return imageURL;
    }
}
