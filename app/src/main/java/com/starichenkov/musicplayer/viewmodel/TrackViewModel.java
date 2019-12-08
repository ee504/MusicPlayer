package com.starichenkov.musicplayer.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.starichenkov.musicplayer.adapter.TrackListAdapter;
import com.starichenkov.musicplayer.R;
import com.starichenkov.musicplayer.playerService.PlayerService;
import com.starichenkov.musicplayer.retrofit.ItunesResponse;
import com.starichenkov.musicplayer.retrofit.ItunesSongApi;
import com.starichenkov.musicplayer.retrofit.RetrofitClient;
import com.starichenkov.musicplayer.retrofit.Track;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class TrackViewModel extends AndroidViewModel implements LifecycleObserver{

    private final String TAG = "myTag";

    private MutableLiveData<List<Track>> tracks;
    private TrackListAdapter adapter;
    private MutableLiveData<Track> selected;
    public ObservableInt showEmpty;

    private PlayerService mService;
    private boolean mBound = false;

    private boolean isPlaying = false;

    public ObservableField<String> currentTrackTime = new ObservableField<>("00:00");
    public ObservableField<String> trackTime = new ObservableField<>("30:00");
    public ObservableInt sizeBarMax = new ObservableInt(30);
    public ObservableInt currentSizeBarPosition = new ObservableInt(0);;

    public TrackViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        tracks = new MutableLiveData<List<Track>>();
        adapter = new TrackListAdapter(R.layout.item_track, this);
        selected = new MutableLiveData<>();
        showEmpty = new ObservableInt(View.GONE);
    }
    //поиск при изменении текста
    public void onTextChanged(CharSequence s, int start, int before, int count){

        if(s.length() > 4){
            ItunesSongApi apiService = RetrofitClient.getClient().create(ItunesSongApi.class);
            apiService.fetchTracks(s.toString(), "music", "200")
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
            clearAdapter();
        }
    }

    public MutableLiveData<List<Track>> getTracks() {
        return tracks;
    }

    public TrackListAdapter getAdapter() {
        return adapter;
    }

    //установка новых данных
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
    //получение трека в позиции
    public Track getTrackAt(Integer index) {
        if (tracks.getValue() != null &&
                index != null &&
                tracks.getValue().size() > index) {
            return tracks.getValue().get(index);
        }
        return null;

    }
    //очистка списка
    public void clearAdapter() {
        Log.d(TAG, "here: ");
        tracks.setValue(new ArrayList<Track>());
        this.adapter.setTrackList(tracks.getValue());
        this.adapter.notifyDataSetChanged();

    }

    public void clickOnPlayButton(View view){
        ImageView imageView = (ImageView)view;
        if(isPlaying){
            Log.d(TAG, "isPlaying true ");
            imageView.setImageResource(R.drawable.ic_play_circle_filled_black_90dp);
            mService.setPlayPausePlayer(false);
            isPlaying = false;
        }else{
            Log.d(TAG, "isPlaying false ");
            imageView.setImageResource(R.drawable.ic_pause_circle_filled_black_90dp);
            mService.setPlayPausePlayer(true);
            isPlaying = true;
        }
    }

    public void startService() {
        Intent intent = new Intent(getApplication(), PlayerService.class);
        intent.putExtra("inputExtra", selected.getValue().getTrackUrl());
        getApplication().bindService(intent, connection, Context.BIND_AUTO_CREATE);
        isPlaying = true;

    }

    public void stopService(){
        getApplication().unbindService(connection);
        mBound = false;
    }

    //создает сервис при открытии фрагмента с проигрыванием музыки
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onLifecycleStart() {
        startService();
    }

    //привязка сервиса
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    //для отображения времени
    private String stringForTime(int timeMs) {
        StringBuilder mFormatBuilder;
        Formatter mFormatter;
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds =  timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public void setPlayerTo(int value) {
        mService.setPlayerTo(value);
    }

    public boolean checkForPlaying() {
        if(mService != null && isPlaying == true){
            return true;
        }
        return false;
    }

    public void updateProgress() {

        currentTrackTime.set(stringForTime((int) mService.getTrackCurrentPosition()));
        currentSizeBarPosition.set((int) mService.getTrackCurrentPosition()/1000);
    }
}
