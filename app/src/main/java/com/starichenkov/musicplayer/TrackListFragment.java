package com.starichenkov.musicplayer;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.starichenkov.musicplayer.databinding.FragmentSongListBinding;

import java.util.List;


public class TrackListFragment extends Fragment {

    //private final String TAG = getResources().getString(R.string.log_name);

    private TrackViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentSongListBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_song_list, container, false);
        //FragmentSongListBinding binding = DataBindingUtil.setContentView(this, R.layout.fragment_song_list);
        View view = binding.getRoot();

        viewModel = ViewModelProviders.of(this).get(TrackViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);
        setupListUpdate();


        return view;
    }


    /*private void setupBindings(Bundle savedInstanceState) {
        ActivityDogBreedsBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.fragment_song_list);
        viewModel = ViewModelProviders.of(this).get(TrackViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        activityBinding.setModel(viewModel);
        //setupListUpdate();

    }*/

    private void setupListUpdate() {
        //viewModel.loading.set(View.VISIBLE);
        viewModel.fetchList();
        MutableLiveData<List<Track>> tracks = new MutableLiveData<List<Track>>();
        tracks.setValue(viewModel.getTracks());
        tracks.observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                //viewModel.loading.set(View.GONE);
                if (tracks.size() == 0) {
                    //viewModel.showEmpty.set(View.VISIBLE);
                } else {
                    //viewModel.showEmpty.set(View.GONE);
                    viewModel.setTracksInAdapter(tracks);
                }
            }
        });
        setupListClick();
    }

    private void setupListClick() {
        MutableLiveData<Track> track = new MutableLiveData<Track>();
        track.setValue(viewModel.getSelected());
        track.observe(this, new Observer<Track>() {
            @Override
            public void onChanged(Track track1) {
                if (track1 != null) {
                    Toast.makeText(getContext(), "You selected a " + track1.getTrackName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
