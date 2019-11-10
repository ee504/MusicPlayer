package com.starichenkov.musicplayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;


public class MainActivity extends FragmentActivity {

    //private final String TAG = getResources().getString(R.string.log_name);

    private Fragment mTrackListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrackListFragment = new TrackListFragment();

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmMain, mTrackListFragment)
                .show(mTrackListFragment)
                .commit();
        //getSupportFragmentManager().popBackStackImmediate();
        getSupportFragmentManager().executePendingTransactions();
    }
}
