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

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {


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



    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);

        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
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


        itemsAdapter = new WordAdapter(getActivity(),words,R.color.category_family);
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
