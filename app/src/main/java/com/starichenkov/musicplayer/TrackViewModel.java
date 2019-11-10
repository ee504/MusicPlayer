package com.starichenkov.musicplayer;

import android.arch.lifecycle.ViewModel;

import com.starichenkov.musicplayer.TrackListAdapter;

import java.util.ArrayList;
import java.util.List;


public class TrackViewModel extends ViewModel {

    public List<Track> tracks;
    public TrackListAdapter adapter;
    public Track selected;

    public void init(){
        tracks = new ArrayList<Track>();
        adapter = new TrackListAdapter(R.layout.item_track, this);
    }

    public void fetchList() {
        tracks.add(new Track("Dancing Queen","ABBA", "https://is1-ssl.mzstatic.com/image/thumb/Music128/v4/88/92/4c/88924c01-6fb3-8616-f0b3-881b1ed09e03/source/30x30bb.jpg"));
        tracks.add(new Track("Take a Chance On Me","ABBA", "https://is1-ssl.mzstatic.com/image/thumb/Music128/v4/88/92/4c/88924c01-6fb3-8616-f0b3-881b1ed09e03/source/30x30bb.jpg"));
        tracks.add(new Track("Waterloo","ABBA", "https://is1-ssl.mzstatic.com/image/thumb/Music128/v4/88/92/4c/88924c01-6fb3-8616-f0b3-881b1ed09e03/source/30x30bb.jpg"));
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public TrackListAdapter getAdapter() {
        return adapter;
    }

    public void setTracksInAdapter(List<Track> tracks) {
        this.adapter.setTrackList(tracks);
        this.adapter.notifyDataSetChanged();
    }

    public Track getSelected() {
        return selected;
    }

    public void onItemClick(Integer index) {
        Track track = getTrackAt(index);
        selected = track;
    }

    public Track getTrackAt(Integer index) {
        if (index != null &&
                tracks.size() > index) {
            return tracks.get(index);
        }
        return null;
    }

    //public void fetchTrackImagesAt(Integer position) {
    //}
}
