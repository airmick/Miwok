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

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    MediaPlayer mediaPlayer;

    /** Handles audio focus when playing a sound file */
    private AudioManager audioManager;

    public ColorsFragment() {
        // Required empty public constructor
    }

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeLister = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange){
                case AudioManager.AUDIOFOCUS_GAIN:
                    // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                    mediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                    // Stop playback and clean up resources
                    releaseMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                    // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                    // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                    // our app is allowed to continue playing sound but at a lower volume. We'll treat
                    // both cases the same way because our app is playing short sound files.

                    // Pause playback and reset player to the start of the file. That way, we can
                    // play the word from the beginning when we resume playback.
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewView = inflater.inflate(R.layout.word_list, container, false);
        // Create and setup the {@link AudioManager} to request audio focus
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("red", "weṭeṭṭi",
                R.drawable.color_red, R.raw.color_red));
        words.add(new Word("green", "chokokki",
                R.drawable.color_green, R.raw.color_gray));
        words.add(new Word("brown", "ṭakaakki",
                R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi",
                R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli",
                R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli",
                R.drawable.color_white, R.raw.color_white));
        words.add(new Word("dusty yellow", "ṭopiisә",
                R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭә",
                R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        WordAdapter itemsAdapter =
                new WordAdapter(getActivity(), words, R.color.category_colors);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) viewView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        listView.setAdapter(itemsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                Word word = words.get(position);
                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                int res = audioManager.requestAudioFocus(mOnAudioFocusChangeLister, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    // We have audio focus now.
                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());

                    // Start the audio file,
                    mediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return viewView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(mOnAudioFocusChangeLister);
        }
    }
}