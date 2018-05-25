package com.example.philip.lebibliotheque;

import android.net.Uri;
import android.text.TextUtils;

public final class GoogleBooksAPIQueryBuilder {

    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String QUERY = "q";
    private static final String QUERY_START = "?";
    private static final String MAX_RESULTS = "maxResults";
    private static final String DELIMITER = "&";
    private static final String EQUALS = "=";

    private GoogleBooksAPIQueryBuilder() {
    }

    public static String getQueryWithParams(String searchTerm, int maxResults) {
        if(TextUtils.isEmpty(searchTerm) || maxResults < 1) {
            return "";
        }
        else {
            return API_URL + QUERY_START + QUERY + EQUALS + Uri.encode(searchTerm)
                    + DELIMITER + MAX_RESULTS + EQUALS + maxResults;
        }
    }
}
