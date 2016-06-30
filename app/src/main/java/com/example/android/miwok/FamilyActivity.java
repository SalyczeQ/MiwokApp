package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    WordAdapter itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("father", "әpә", "family_father"));
        words.add(new Word("mother", "әṭa","family_mother"));
        words.add(new Word("son", "angsi", "family_son"));
        words.add(new Word("daughter", "tune", "family_daughter"));
        words.add(new Word("older brother", "taachi", "family_older_brother"));
        words.add(new Word("younger brother", "chalitti", "family_younger_brother"));
        words.add(new Word("older sister", "teṭe", "family_older_sister"));
        words.add(new Word("younger sister", "kolliti", "family_younger_sister"));
        words.add(new Word("grandmother ", "ama", "family_grandmother"));
        words.add(new Word("grandfather", "paapa", "family_grandfather"));

        itemsAdapter = new WordAdapter(this,words,R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String stringId = itemsAdapter.getItem(i).getResourceIdS();
                int resId = getResources().getIdentifier(stringId,"raw",getPackageName());

                MediaPlayer mediaPlayer = MediaPlayer.create(FamilyActivity.this,resId);
                mediaPlayer.start();
            }
        });

    }
}
