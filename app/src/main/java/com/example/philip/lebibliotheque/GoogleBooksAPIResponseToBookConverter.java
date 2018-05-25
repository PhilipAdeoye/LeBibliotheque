package com.example.philip.lebibliotheque;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;


import java.util.ArrayList;

public final class GoogleBooksAPIResponseToBookConverter {

    public static final String LOG_TAG = GoogleBooksAPIResponseToBookConverter.class.getName();

    private GoogleBooksAPIResponseToBookConverter() {
    }

    public static ArrayList<Book> convertFromJSONObject(JsonObject rootObject) {

        ArrayList<Book> books = new ArrayList<>();


        try {

            JsonArray items = rootObject.getAsJsonArray("items");
            for (JsonElement item: items) {

                JsonObject volumeInfo = item.getAsJsonObject().getAsJsonObject("volumeInfo");

                // Get the title
                JsonElement jTitle = volumeInfo.get("title");
                String title = jTitle != null ? jTitle.getAsString() : "";

                // Get the authors
                JsonArray authors = volumeInfo.getAsJsonArray("authors");

                // Create an String ArrayList of the authors
                ArrayList<String> bookAuthors = new ArrayList<>();
                if(authors != null) {
                    for (JsonElement name : authors) {
                        bookAuthors.add(name.getAsString());
                    }
                }

                // Get the other particulars
                JsonElement jPublisher = volumeInfo.get("publisher");
                String publisher = jPublisher != null ? jPublisher.getAsString() : "";

                JsonElement jPublishedYear = volumeInfo.get("publishedDate");
                String publishedYear = jPublishedYear != null ? jPublishedYear.getAsString() : "";

                JsonElement jDescription = volumeInfo.get("description");
                String description = jDescription != null ? jDescription.getAsString() : "";

                // Get the image url
                JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
                String imageUrl = "";
                if(imageLinks != null) {
                    JsonElement jThumbnail = imageLinks.get("thumbnail");
                    imageUrl = jThumbnail != null ? jThumbnail.getAsString() : "";
                }

                // Add the book to the list of books
                books.add(new Book(
                        title,
                        bookAuthors,
                        publisher,
                        publishedYear,
                        description,
                        imageUrl
                ));
            }

        }
        catch (JsonParseException ex) {
            Log.e(LOG_TAG, "Problem parsing the Google Books API response");
        }

        return books;
    }
}
