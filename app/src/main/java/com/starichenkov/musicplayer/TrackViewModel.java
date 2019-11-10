package com.starichenkov.musicplayer;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

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

    public void init(){
        tracks = new MutableLiveData<List<Track>>();
        adapter = new TrackListAdapter(R.layout.item_track, this);
        selected = new MutableLiveData<>();
    }

    public void fetchList() {
        /*List<Track> listTracks = new ArrayList<>();
        listTracks.add(new Track("Dancing Queen","ABBA", "https://is1-ssl.mzstatic.com/image/thumb/Music128/v4/88/92/4c/88924c01-6fb3-8616-f0b3-881b1ed09e03/source/30x30bb.jpg"));
        listTracks.add(new Track("Take a Chance On Me","ABBA", "https://is1-ssl.mzstatic.com/image/thumb/Music128/v4/88/92/4c/88924c01-6fb3-8616-f0b3-881b1ed09e03/source/30x30bb.jpg"));
        listTracks.add(new Track("Waterloo","ABBA", "https://is1-ssl.mzstatic.com/image/thumb/Music128/v4/88/92/4c/88924c01-6fb3-8616-f0b3-881b1ed09e03/source/30x30bb.jpg"));*/
        ItunesSongApi apiService = RetrofitClient.getClient().create(ItunesSongApi.class);
        String abba = "ABBA";
        String type = "musicTrack";
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
                });
    }

    public MutableLiveData<List<Track>> getTracks() {
        return tracks;
    }

    public TrackListAdapter getAdapter() {
        return adapter;
    }

    public void setTracksInAdapter(List<Track> tracks) {
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

    public void onButClick() {
        //ItunesSongApi apiService = RetrofitClient.getService();
        ItunesSongApi apiService = RetrofitClient.getClient().create(ItunesSongApi.class);
        String abba = "ABBA";
        String type = "musicTrack";
        // Fetching all notes
        apiService.fetchTracks5(abba, "music", "200")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ItunesResponse>() {
                    @Override
                    public void onSuccess(ItunesResponse itunesResponse) {
                        Log.d(TAG, "resultCount: " + itunesResponse.getResultCount());
                        ListIterator<Track>
                                iterator = itunesResponse.getListOfTracks().listIterator();
                        // Printing the iterated value
                        while (iterator.hasNext()) {
                            Log.d(TAG, "track: " + iterator.next().getTrackName());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // Network error
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
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

    //public void fetchTrackImagesAt(Integer position) {
    //}
}
