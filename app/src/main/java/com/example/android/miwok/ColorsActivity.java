package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    ListView listView;
    WordAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("red", "weṭeṭṭi", "color_red"));
        words.add(new Word("mustard yellow", "chiwiiṭә", "color_mustard_yellow"));
        words.add(new Word("dusty yellow", "ṭopiisә", "color_dusty_yellow"));
        words.add(new Word("green", "chokokki", "color_green"));
        words.add(new Word("brown", "ṭakaakki", "color_brown"));
        words.add(new Word("gray", "ṭopoppi", "color_gray"));
        words.add(new Word("black", "kululli", "color_black"));
        words.add(new Word("white", "kelelli", "color_white"));

        itemsAdapter = new WordAdapter(this,words,R.color.category_colors);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String stringId = itemsAdapter.getItem(i).getResourceIdS();
                int resId = getResources().getIdentifier(stringId,"raw",getPackageName());

                MediaPlayer mediaPlayer = MediaPlayer.create(ColorsActivity.this,resId);
                mediaPlayer.start();
            }
        });
    }
}
