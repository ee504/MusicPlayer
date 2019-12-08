package com.starichenkov.musicplayer.playerService;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayer {

    private final static String TAG = "myTag";

    private SimpleExoPlayer exoPlayer;
    private TrackSelector trackSelector;
    private LoadControl loadControl;
    private DefaultDataSourceFactory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;

    private long currPosition = 0;

    private boolean isPlaying = false;

    public ExoPlayer(Context context, String track){

        Uri uri = Uri.parse(track);

        trackSelector = new DefaultTrackSelector();
        loadControl = new DefaultLoadControl();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
        dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "MusicPlayer"), null);
        extractorsFactory = new DefaultExtractorsFactory();
        MediaSource audioSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
        exoPlayer.addListener(eventListener);

        exoPlayer.prepare(audioSource);
        setPlayPause(true);
    }

    public void setPlayPause(boolean play){
        isPlaying = play;
        exoPlayer.setPlayWhenReady(play);
    }

    private com.google.android.exoplayer2.ExoPlayer.EventListener eventListener = new com.google.android.exoplayer2.ExoPlayer.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {
            Log.i(TAG,"onTimelineChanged");
        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            Log.i(TAG,"onTracksChanged");
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.i(TAG,"onLoadingChanged");
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState){
                case com.google.android.exoplayer2.ExoPlayer.STATE_ENDED:
                    Log.i(TAG,"Playback ended!");
                    setPlayPause(false);
                    exoPlayer.seekTo(0);
                    break;
                case com.google.android.exoplayer2.ExoPlayer.STATE_READY:
                    Log.i(TAG,"ExoPlayer ready! pos: "+ exoPlayer.getCurrentPosition());
                    Log.i(TAG,"playWhenReady: "+ playWhenReady);

                    break;
                case com.google.android.exoplayer2.ExoPlayer.STATE_BUFFERING:
                    Log.i(TAG,"Playback buffering!");
                    break;
                case com.google.android.exoplayer2.ExoPlayer.STATE_IDLE:
                    Log.i(TAG,"ExoPlayer idle!");
                    break;
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            Log.i(TAG,"onPlaybackError: "+error.getMessage());
        }

        @Override
        public void onPositionDiscontinuity() {
            Log.i(TAG,"onPositionDiscontinuity");
        }
    };
}
