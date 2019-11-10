package com.starichenkov.musicplayer;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.starichenkov.musicplayer.databinding.FragmentSongListBinding;

import okhttp3.internal.Util;

import static android.content.Context.AUDIO_SERVICE;

public class PlayerFragment extends Fragment implements MediaPlayer.OnPreparedListener, View.OnClickListener, MediaPlayer.OnCompletionListener {

    private final static String TAG = "myTag";

    private Button button;
    MediaPlayer mediaPlayer;
    AudioManager am;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, null);

        button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                startTrack();
        }
    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //Log.d(LOG_TAG, "onCompletion");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMP();
    }

    public void startTrack(){
        releaseMP();
        final String song = "https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview118/v4/9a/ab/9f/9aab9f41-0821-de62-78cf-43e0c08add62/mzaf_6566316355195832.plus.aac.p.m4a";
        Log.d(TAG, "plau");
        /*TrackSelector trackSelector = new DefaultTrackSelector();

        LoadControl loadControl = new DefaultLoadControl();

        SimpleExoPlayer exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoplayer2example"), null);
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource audioSource = new ExtractorMediaSource(Uri.parse(song), dataSourceFactory, extractorsFactory, null, null);
        //exoPlayer.addListener(eventListener);

        exoPlayer.prepare(audioSource);*/
        try {

            am = (AudioManager) getActivity().getSystemService(AUDIO_SERVICE);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(song);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //Log.d(LOG_TAG, "prepareAsync");
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();

            mediaPlayer.setLooping(true);
            mediaPlayer.setOnCompletionListener(this);
            Log.d(TAG, "end");
        }catch(Exception ex){
            Log.d(TAG, "Exception: " + ex);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        //Log.d(LOG_TAG, "onPrepared");
        mp.start();
    }
}
