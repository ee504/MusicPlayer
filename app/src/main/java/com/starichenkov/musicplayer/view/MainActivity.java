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

    private Fragment mTrackListFragment;
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
        //getSupportFragmentManager().popBackStackImmediate();
        getSupportFragmentManager().executePendingTransactions();
    }

    private void setBindings(Bundle savedInstanceState){
        //ActivityMainBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.fragment_song_list);
        viewModel = ViewModelProviders.of(this).get(TrackViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        //activityBinding.setViewModel(viewModel);
        setupListClick();
    }

    private void setupListClick() {
        viewModel.getSelected().observe(this, new Observer<Track>() {
            @Override
            public void onChanged(Track track) {
                Log.d(TAG, "onChanged(): activity");
                if (track != null) {
                    //Toast.makeText(MainActivity.this, "Main Activity You selected a " + track.getTrackName(), Toast.LENGTH_SHORT).show();
                    FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.frgmMain, mPlayerFragment)
                            .addToBackStack(null)
                            .commit();
                    //FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
                    //fTrans.add(R.id.frgmPlayer, mPlayerFragment)
                            //.show(mPlayerFragment)
                            //.commit();
                    //getSupportFragmentManager().popBackStackImmediate();
                    //getSupportFragmentManager().executePendingTransactions();
                    //FrameLayout frame = (FrameLayout) findViewById(R.id.frgmMain);
                    //commonCardContainer.setLayoutParams(new FrameLayout.LayoutParams(100,100));
                    //LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(400);
                    //frame.setLayoutParams(lp);
                }
            }
        });
    }

    public void openTrack(){
        Log.d(TAG, "OpenPlaceAutocomplete");
        //currentFragment = "bookMarksListFragment";
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        //fTrans.add(R.id.frgmCreateEvent, bookMarksListFragment)
        //.addToBackStack(null)
        //.hide(mapFragment)
        //.show(bookMarksListFragment)
        //.commit();
        //fTrans.replace(R.id.frgmCreateEvent, bookMarksListFragment)
        //.addToBackStack(null)
        //.commit();
        //presenter.getEventsFromBookmarks();
        fTrans.replace(R.id.frgmMain, mPlayerFragment)
                .addToBackStack(null)
                .commit();
    };

}
