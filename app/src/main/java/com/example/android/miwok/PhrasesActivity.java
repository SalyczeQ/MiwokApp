package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
    WordAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("Where are you going?", "minto wuksus","phrase_where_are_you_going"));
        words.add(new Word("What is your name?", "tinnә oyaase'nә","phrase_what_is_your_name"));
        words.add(new Word("My name is...", "oyaaset...","phrase_my_name_is"));
        words.add(new Word("How are you feeling?", "michәksәs?","phrase_how_are_you_feeling"));
        words.add(new Word("I’m feeling good.", "kuchi achit","phrase_im_feeling_good"));
        words.add(new Word("Are you coming?", "әәnәs'aa?","phrase_are_you_coming"));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm","phrase_yes_im_coming"));
        words.add(new Word("I’m coming.", "әәnәm","phrase_im_coming"));
        words.add(new Word("Let’s go.", "yoowutis","phrase_lets_go"));
        words.add(new Word("Come here.", "әnni'nem","phrase_come_here"));

        itemsAdapter = new WordAdapter(this,words,R.color.category_phrases);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String stringId = itemsAdapter.getItem(i).getResourceIdS();
                int resId = getResources().getIdentifier(stringId,"raw",getPackageName());

                MediaPlayer mediaPlayer = MediaPlayer.create(PhrasesActivity.this,resId);
                mediaPlayer.start();
            }
        });
    }
}
