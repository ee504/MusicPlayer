package com.starichenkov.musicplayer.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.starichenkov.musicplayer.R;
import com.starichenkov.musicplayer.retrofit.Track;
import com.starichenkov.musicplayer.viewmodel.TrackViewModel;


public class MainActivity extends FragmentActivity{

    private final static String TAG = "myTag";

    //список треков
    private Fragment mTrackListFragment;
    //воспроизведение трека
    private Fragment mPlayerFragment;
    private TrackViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setFragments();
        setBindings(savedInstanceState);
    }

    private void setFragments(){
        mTrackListFragment = new TrackListFragment();
        mPlayerFragment = new PlayerFragment();

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmMain, mTrackListFragment)
                .show(mTrackListFragment)
                .commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    private void setBindings(Bundle savedInstanceState){
        viewModel = ViewModelProviders.of(this).get(TrackViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        setupListClick();
    }

    //обработчик нажатия на трек в списке
    private void setupListClick() {
        viewModel.getSelected().observe(this, new Observer<Track>() {
            @Override
            public void onChanged(Track track) {
                Log.d(TAG, "onChanged(): activity");
                if (track != null) {
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.frgmMain, mPlayerFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        viewModel.stopService();
    }

}
