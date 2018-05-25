package com.example.philip.lebibliotheque;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class BookDetailActivity extends AppCompatActivity {

    public static final String BOOK = "com.example.philip.lebibliotheque.BOOK";
    public static final String LOG_TAG = BookDetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Get the intent
        Intent intent = getIntent();
        if (intent == null)
            return;

        // Get the extra data passed with the intent
        Bundle extras = intent.getExtras();
        if (extras == null)
            return;

        Book book = (Book)extras.getSerializable(BOOK);
        if (book != null) {

            // Set the title in the action bar
            setTitle(book.getTitle());

            // Load the image
            ImageView bookImageView = findViewById(R.id.book_image_view);
            if (book.hasImageURL()) {
                Ion.with(bookImageView)
                        .placeholder(R.drawable.bookshelf)
                        .load(book.getImageURL());
            }

            // Show the title
            TextView bookTitleTextView = findViewById(R.id.book_title_text_view);
            bookTitleTextView.setText(book.getTitle());

            // Show the authors
            TextView bookAuthorsTextView = findViewById(R.id.book_authors_text_view);
            ArrayList<String> authors = book.getAuthors();
            
            if(authors.isEmpty()) {
                bookAuthorsTextView.setVisibility(View.GONE);
            }
            else {
                String bookAuthors;
                if (authors.size() > 2) {
                    String lastAuthor = authors.remove(authors.size()-1);
                    bookAuthors = TextUtils.join(", ", authors) + ", and " + lastAuthor;
                }
                else {
                    bookAuthors = TextUtils.join(" and ", authors);
                }
                bookAuthorsTextView.setText(bookAuthors);
            }

            // Show the publisher
            TextView publisherTextView = findViewById(R.id.book_publisher_text_view);
            if (!TextUtils.isEmpty(book.getPublisher())) {
                publisherTextView.setText(book.getPublisher());
            }
            else {
                publisherTextView.setVisibility(View.GONE);
            }

            // Show the published year
            TextView publishedOnTextView = findViewById(R.id.book_published_year_text_view);
            if (!TextUtils.isEmpty(book.getPublishedYear())) {
                publishedOnTextView.setText(book.getPublishedYear());
            }
            else {
                publishedOnTextView.setVisibility(View.GONE);
            }

            // Show the description
            TextView descriptionTextView = findViewById(R.id.book_description_text_view);
            if (!TextUtils.isEmpty(book.getDescription())) {
                descriptionTextView.setText(book.getDescription());
            }
            else {
                descriptionTextView.setVisibility(View.GONE);
            }


        }
    }
}
