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

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.util.Util;


public class PlayerFragment extends Fragment implements View.OnClickListener{

    private final static String TAG = "myTag";

    private Button button;

    private SimpleExoPlayer exoPlayer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player, null);

        //button = (Button) view.findViewById(R.id.button);
        //button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.button:
                //startTrack();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void startTrack(){
        Log.d(TAG, "startTrack");
        Uri uri = Uri.parse("https://audio-ssl.itunes.apple.com/itunes-assets/AudioPreview118/v4/9a/ab/9f/9aab9f41-0821-de62-78cf-43e0c08add62/mzaf_6566316355195832.plus.aac.p.m4a");

        TrackSelector trackSelector = new DefaultTrackSelector();

        //LoadControl loadControl = new DefaultLoadControl();

        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), "exoplayer2example");
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource audioSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
        //exoPlayer.addListener(eventListener);

        exoPlayer.prepare(audioSource);
        exoPlayer.setPlayWhenReady(true);
    }

}
