package com.starichenkov.musicplayer.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.starichenkov.musicplayer.R;
import com.starichenkov.musicplayer.databinding.FragmentSongListBinding;
import com.starichenkov.musicplayer.retrofit.Track;
import com.starichenkov.musicplayer.viewmodel.TrackViewModel;

import java.util.List;


public class TrackListFragment extends Fragment {

    private final String TAG = "myTag";

    private TrackViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentSongListBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_song_list, container, false);
        View view = binding.getRoot();

        viewModel = ViewModelProviders.of(getActivity()).get(TrackViewModel.class);
        if (savedInstanceState == null) {
            //viewModel.init();
        }
        binding.setViewModel(viewModel);
        setupListUpdate();
        //setupListClick();
        return view;
    }
    //отслеживание изменения списка
    private void setupListUpdate() {
        //viewModel.loading.set(View.VISIBLE);
        //viewModel.fetchList();
        viewModel.getTracks().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                Log.d(TAG, "getTracks().observe: " + tracks.size());
                if(tracks.size() != 0) {
                    viewModel.setTracksInAdapter(tracks);
                }
            }
        });

    }
    //отслеживание клика(перенес в активность)
    private void setupListClick() {
        viewModel.getSelected().observe(this, new Observer<Track>() {
            @Override
            public void onChanged(Track track) {
                Log.d(TAG, "onChanged() fragment:");
                if (track != null) {
                    Toast.makeText(getContext(), "You selected a " + track.getTrackName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
