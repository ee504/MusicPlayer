package com.starichenkov.musicplayer;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableInt;
import android.util.Log;
import android.view.View;

import com.starichenkov.musicplayer.TrackListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class TrackViewModel extends ViewModel {

    private final String TAG = "myTag";

    private MutableLiveData<List<Track>> tracks;
    private TrackListAdapter adapter;
    private MutableLiveData<Track> selected;
    public ObservableInt showEmpty;

    public void init(){
        tracks = new MutableLiveData<List<Track>>();
        adapter = new TrackListAdapter(R.layout.item_track, this);
        selected = new MutableLiveData<>();
        showEmpty = new ObservableInt(View.GONE);
    }

    public void onTextChanged(CharSequence s, int start, int before, int count){
        Log.d(TAG, "s: " + s);
        Log.d(TAG, "start: " + start);
        Log.d(TAG, "before: " + before);
        Log.d(TAG, "count: " + count);

        if(s.length() > 4){
            ItunesSongApi apiService = RetrofitClient.getClient().create(ItunesSongApi.class);
            //String abba = "ABBA";
            // Fetching all notes
            apiService.fetchTracks5(s.toString(), "music", "200")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<ItunesResponse>() {
                        @Override
                        public void onSuccess(ItunesResponse itunesResponse) {
                            Log.d(TAG, "resultCount: " + itunesResponse.getResultCount());
                            tracks.setValue(itunesResponse.getListOfTracks());
                        }

                        @Override
                        public void onError(Throwable e) {
                            // Network error
                            Log.d(TAG, "onError: " + e.getMessage());
                        }
                    });
        }
        if(s.length() < 5 && before > 4){
            Log.d(TAG, "here: ");
            //tracks.setValue(new ArrayList<Track>());
            clearAdapter();
        }
    }

    public void fetchList() {
        /*ItunesSongApi apiService = RetrofitClient.getClient().create(ItunesSongApi.class);
        String abba = "ABBA";
        // Fetching all notes
        apiService.fetchTracks5(abba, "music", "200")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ItunesResponse>() {
                    @Override
                    public void onSuccess(ItunesResponse itunesResponse) {
                        Log.d(TAG, "resultCount: " + itunesResponse.getResultCount());
                        tracks.setValue(itunesResponse.getListOfTracks());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // Network error
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });*/
    }

    public MutableLiveData<List<Track>> getTracks() {
        return tracks;
    }

    public TrackListAdapter getAdapter() {
        return adapter;
    }

    public void setTracksInAdapter(List<Track> tracks) {
        Log.d(TAG, "setTracksInAdapter");
        this.adapter.setTrackList(tracks);
        this.adapter.notifyDataSetChanged();
    }

    public MutableLiveData<Track> getSelected() {
        return selected;
    }

    public void onItemClick(Integer index) {
        Log.d(TAG, "onItemClick: " + index);
        Track track = getTrackAt(index);
        Log.d(TAG, "track: " + track.getTrackName());
        selected.setValue(track);
        //selected = track;
    }

    public Track getTrackAt(Integer index) {
        if (tracks.getValue() != null &&
                index != null &&
                tracks.getValue().size() > index) {
            return tracks.getValue().get(index);
        }
        return null;
        /*if (index != null &&
                tracks.size() > index) {
            return tracks.get(index);
        }
        return null;*/
    }

    public void clearAdapter() {
        Log.d(TAG, "here: ");
        tracks.setValue(new ArrayList<Track>());
        this.adapter.setTrackList(tracks.getValue());
        this.adapter.notifyDataSetChanged();

    }

    //public void fetchTrackImagesAt(Integer position) {
    //}
}
