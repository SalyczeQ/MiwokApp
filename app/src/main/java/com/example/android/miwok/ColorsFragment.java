package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {
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
            releaseMediaPlayer();
        }
    };

    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);

        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("red", "weṭeṭṭi", "color_red"));
        words.add(new Word("mustard yellow", "chiwiiṭә", "color_mustard_yellow"));
        words.add(new Word("dusty yellow", "ṭopiisә", "color_dusty_yellow"));
        words.add(new Word("green", "chokokki", "color_green"));
        words.add(new Word("brown", "ṭakaakki", "color_brown"));
        words.add(new Word("gray", "ṭopoppi", "color_gray"));
        words.add(new Word("black", "kululli", "color_black"));
        words.add(new Word("white", "kelelli", "color_white"));


        itemsAdapter = new WordAdapter(getActivity(),words,R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                int result = am.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    releaseMediaPlayer();
                    String stringId = itemsAdapter.getItem(i).getResourceIdS();
                    int resId = getResources().getIdentifier(stringId,"raw",getActivity().getPackageName());

                    mediaPlayer = MediaPlayer.create(getActivity(),resId);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });


        return rootView;
    }

    @Override
    public void onStop() {
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
