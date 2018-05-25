package com.example.philip.lebibliotheque;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int LOADER_ID = MainActivity.class.hashCode();

    private TextView noNetworkTextView;
    private ConstraintLayout searchLayout;

    private EditText searchTextBox;
    private Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the search text box, go button, and loading spinner
        searchTextBox = findViewById(R.id.search_text_box);
        goButton = findViewById(R.id.search_button);
        if (searchTextBox.getText().toString().length() == 0) {
            goButton.setEnabled(false);
        }

        final Context thisActivity = this;

        // Set a click listener on the go button
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Grab the search term
                String searchTerm = searchTextBox.getText().toString();
                if (searchTerm != null && searchTerm.length() > 0) {

                    Intent intent = new Intent(thisActivity, BookshelfActivity.class);
                    intent.putExtra(BookshelfActivity.SEARCH_KEY, searchTerm);

                    startActivity(intent);
                }
            }
        });

        // Set up a TextWatcher to watch for changes in the search text box
        searchTextBox.addTextChangedListener(new TextWatcher() {
            private Button searchButton = findViewById(R.id.search_button);

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Disable the search button if the search text box is empty
                if (s == null || s.length() == 0) {
                    searchButton.setEnabled(false);
                }
                else {
                    searchButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        showViewsByNetworkAvailability();
    }

    /*
     * Shows the appropriate views based on network availability
     */
    @Override
    protected void showViewsByNetworkAvailability() {
        // Find views in the content view
        noNetworkTextView = findViewById(R.id.no_network_text_view);

        // Show appropriate views if the network is available or not
        if (isConnectedToNetwork()) {
            noNetworkTextView.setVisibility(View.GONE);
        }
        else {
            noNetworkTextView.setVisibility(View.VISIBLE);
        }
    }
}
