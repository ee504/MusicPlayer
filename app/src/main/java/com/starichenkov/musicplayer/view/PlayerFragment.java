package com.starichenkov.musicplayer.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
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
import com.starichenkov.musicplayer.R;
import com.starichenkov.musicplayer.playerService.PlayerService;
import com.starichenkov.musicplayer.viewmodel.TrackViewModel;
import com.starichenkov.musicplayer.databinding.FragmentPlayerBinding;

import java.util.Formatter;
import java.util.Locale;

public class PlayerFragment extends Fragment{

    private final static String TAG = "myTag";
    private SeekBar seekPlayerProgress;
    private Handler handler;

    private TrackViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentPlayerBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_player, container, false);
        View view = binding.getRoot();

        viewModel = ViewModelProviders.of(getActivity()).get(TrackViewModel.class);
        binding.setViewModel(viewModel);

        getLifecycle().addObserver(viewModel);

        seekPlayerProgress = (SeekBar) view.findViewById(R.id.mediacontroller_progress);

        initSeekBar();

        return view;
    }

    //динамический прогресс бар
    private void setProgress() {
        Log.d(TAG, "setProgress");
        if(handler == null)handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (viewModel.checkForPlaying()) {
                    viewModel.updateProgress();
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void initSeekBar() {

        seekPlayerProgress.requestFocus();

        seekPlayerProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    return;
                }
                viewModel.setPlayerTo(progress*1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        setProgress();
    }

}
