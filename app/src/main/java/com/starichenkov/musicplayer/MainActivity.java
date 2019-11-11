package com.starichenkov.musicplayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;


public class MainActivity extends FragmentActivity implements CallBackFromFragments{

    private final static String TAG = "myTag";

    private Fragment mTrackListFragment;
    private Fragment mPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrackListFragment = new TrackListFragment();
        mPlayerFragment = new PlayerFragment();

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmMain, mTrackListFragment)
                .show(mTrackListFragment)
                .commit();
        //getSupportFragmentManager().popBackStackImmediate();
        getSupportFragmentManager().executePendingTransactions();
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

    public void returnToList(){};
}
