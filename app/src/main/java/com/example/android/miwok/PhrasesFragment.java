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
public class PhrasesFragment extends Fragment {


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


    public PhrasesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);

        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
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


        itemsAdapter = new WordAdapter(getActivity(),words,R.color.category_phrases);
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
