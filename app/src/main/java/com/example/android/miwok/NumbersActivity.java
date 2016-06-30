package com.example.android.miwok;

import android.app.VoiceInteractor;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private AudioManager am;

    private AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                //resume play
                mediaPlayer.start();
            }
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                //stop play a release resources
                mediaPlayer.stop();
                releaseMediaPlayer();

            }
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                //pause play
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
        }
    };


    private WordAdapter itemsAdapter;
    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Toast.makeText(NumbersActivity.this,"Done",Toast.LENGTH_SHORT).show();
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);



        ArrayList<Word> engWords = new ArrayList<>();
        engWords.add(new Word("one","lutti","number_one"));
        engWords.add(new Word("two","otiiko","number_two"));
        engWords.add(new Word("three","tolookosu","number_three"));
        engWords.add(new Word("four","oyyisa","number_four"));
        engWords.add(new Word("five","massokka","number_five"));
        engWords.add(new Word("six","temmokka","number_six"));
        engWords.add(new Word("seven","kenekaku","number_seven"));
        engWords.add(new Word("eight","kawinta","number_eight"));
        engWords.add(new Word("nine","wo’e","number_nine"));
        engWords.add(new Word("ten","na’aacha","number_ten"));




        itemsAdapter = new WordAdapter(this,engWords,R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                int result = am.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    releaseMediaPlayer();
                    String stringId = itemsAdapter.getItem(i).getResourceIdS();
                    int resId = getResources().getIdentifier(stringId,"raw",getPackageName());

                    mediaPlayer = MediaPlayer.create(NumbersActivity.this,resId);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }



            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
            am.abandonAudioFocus(audioFocusChangeListener);
        }


    }




}
