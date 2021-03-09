package com.example.android.miwok;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;

    public WordAdapter(Context context, ArrayList<Word> wordArrayList, int color) {
        super(context, 0, wordArrayList);
        mColorResourceId = color;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        // Check if the existing view is being reused, otherwise inflate the view
        View  listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView englishTextView = (TextView) listItemView.findViewById(R.id.eng_text_id);

        // Get the version name from the current Word object and
        // set this text on the name TextView
        englishTextView.setText(currentWord.getDefaultTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_id);

        // Get the version name from the current Word object and
        // set this text on the name TextView
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the ImageView in the list_item.xml layout with ID image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        if (currentWord.hasImage())
        {
            // Get the Image Resource ID from the current Word object and set the image to imageView
            imageView.setImageResource(currentWord.getImageResourceId());

            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
        }

        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);

        // Find the color that the resource ID maps to
        textContainer.setBackgroundColor(ContextCompat.getColor(getContext(), mColorResourceId));


        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

}
