package com.example.android.miwok;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by salay on 26.06.2016.
 */
public class WordAdapter extends ArrayAdapter<Word> {
    private int ColorResourceId;


    public WordAdapter(Context context, ArrayList<Word> words, int RIdColor) {
        super(context, 0, words);
        this.ColorResourceId = RIdColor;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        Word currentWord = getItem(position);

      //  LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.list_item_text);
      //  linearLayout.setBackgroundColor(ColorResourceId);

      // Set the theme color for the list
        View textContainer = convertView.findViewById(R.id.list_item_text);
               // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), ColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        TextView defaultWordTextView = (TextView) convertView.findViewById(R.id.default_language_text_view);
        defaultWordTextView.setText(currentWord.getDefaultTranslatin());

        TextView miwokWordTextView = (TextView) convertView.findViewById(R.id.miwok_language_text_view);
        miwokWordTextView.setText(currentWord.getMiwokTranslatin());

        ImageView imageView = (ImageView) convertView.findViewById(R.id.image);

       // Log.v(String.valueOf(getContext()), getContext().getClass().getSimpleName());

        if (currentWord.getResourceIdS()== null || getContext().getClass().getSimpleName().equalsIgnoreCase("PhrasesActivity")){
            imageView.setVisibility(View.GONE);
        }else {
            int resId = getContext().getResources().getIdentifier(currentWord.getResourceIdS(),"drawable", getContext().getPackageName());
            imageView.setImageResource(resId);
        }

        return convertView;
    }
}
