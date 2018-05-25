package com.example.philip.lebibliotheque;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.Future;

public class BookshelfActivity extends BaseActivity {

    private static final String LOG_TAG = BookshelfActivity.class.getName();
    public static final String SEARCH_KEY = "com.example.philip.lebibliotheque.SEARCH_KEY";

    private GridView gridView;
    private ConstraintLayout noNetworkLayout;

    private Future<JsonObject> loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        String searchTerm = extras.getString(SEARCH_KEY);
        if (searchTerm != null) {
            setTitle(searchTerm);
        }

        showViewsByNetworkAvailability();
        if (isConnectedToNetwork()) {

            String googleAPIURL = GoogleBooksAPIQueryBuilder.getQueryWithParams(
                    searchTerm, Constants.MAX_BOOK_RESULTS);

            getBooks(googleAPIURL);
        }
    }

    private void getBooks(String url) {
        if (loading != null && !loading.isDone() && !loading.isCancelled()) {
            return;
        }

        // Show the loading spinner
        final ProgressBar loadingSpinner = findViewById(R.id.book_list_loading_spinner);
        loadingSpinner.setVisibility(View.VISIBLE);

        // Request books from google books API using Ion
        loading = Ion.with(this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        loadingSpinner.setVisibility(View.GONE);

                        // this is called back onto the ui thread, no Activity.runOnUiThread or Handler.post necessary.
                        if (e != null) {
                            Toast.makeText(BookshelfActivity.this, R.string.error_loading_books, Toast.LENGTH_LONG).show();
                            return;
                        }

                        // Convert the results to a list of books
                        ArrayList<Book> books = GoogleBooksAPIResponseToBookConverter
                                .convertFromJSONObject(result);
                        updateBookshelf(books);

                    }
                });
    }

    private void updateBookshelf(final ArrayList<Book> books) {

        BookArrayAdapter adapter = new BookArrayAdapter(this, books);
        gridView.setAdapter(adapter);

        ConstraintLayout emptyStateLayout = findViewById(R.id.empty_state_layout);
        gridView.setEmptyView(emptyStateLayout);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = books.get(position);

                Intent intent = new Intent(BookshelfActivity.this, BookDetailActivity.class)
                    .putExtra(BookDetailActivity.BOOK, book);

                startActivity(intent);
            }
        });

    }

    /*
     * Shows the appropriate views based on network availability
     */
    @Override
    protected void showViewsByNetworkAvailability() {
        // Find layouts in the content view
        noNetworkLayout = findViewById(R.id.no_network_layout);
        gridView = findViewById(R.id.book_list);

        // Show appropriate views if the network is available or not
        if (isConnectedToNetwork()) {
            noNetworkLayout.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        }
        else {
            noNetworkLayout.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }
    }
}
