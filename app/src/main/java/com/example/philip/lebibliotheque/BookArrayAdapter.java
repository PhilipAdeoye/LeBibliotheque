package com.example.philip.lebibliotheque;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class BookArrayAdapter extends ArrayAdapter<Book> {

    public BookArrayAdapter(@NonNull Activity context, @NonNull ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }

        Book currentBook = getItem(position);

        TextView titleTextView = listItemView.findViewById(R.id.title_text_view);
        titleTextView.setText(currentBook.getTitle());

        TextView authorTextView = listItemView.findViewById(R.id.author_text_view);
        authorTextView.setText(currentBook.getFirstAuthor());

        ImageView imageView = listItemView.findViewById(R.id.cover_thumbnail);
        if (currentBook.hasImageURL()) {
            Ion.with(imageView)
                    .placeholder(R.drawable.bookshelf)
                    .load(currentBook.getImageURL());

        }



        return listItemView;
    }
}
