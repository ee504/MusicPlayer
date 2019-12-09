package com.starichenkov.musicplayer.playerService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.starichenkov.musicplayer.R;
import com.starichenkov.musicplayer.view.MainActivity;
import com.starichenkov.musicplayer.view.PlayerFragment;

public class PlayerService extends Service {

    private final String TAG = "myTag";

    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    private ExoPlayer exo;
    private final IBinder binder = new LocalBinder();


    public class LocalBinder extends Binder {
        public PlayerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return PlayerService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    //получить длительность трека
    public long getTrackDuration() {
        return exo.exoPlayer.getDuration();
    }
    //получить текущую позицию
    public long getTrackCurrentPosition() {
        return exo.exoPlayer.getCurrentPosition();
    }
    //пуск/пауза
    public void setPlayPausePlayer(boolean value){
        exo.exoPlayer.setPlayWhenReady(value);
    }

    //перемотка трека
    public void setPlayerTo(int value) {
        exo.exoPlayer.seekTo(value);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        exo.exoPlayer.setPlayWhenReady(false);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");

        //получение данных из интент
        String trackUrl = intent.getStringExtra("trackUrl");
        String trackName = intent.getStringExtra("trackName");
        String artistName = intent.getStringExtra("artistName");

        createNotificationChannel();

        //создание padding чтобы при клике на notification открывалось последнее открытое окно приложения
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setAction(Intent.ACTION_MAIN);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(trackName)
                .setContentText(artistName)
                .setSmallIcon(R.drawable.ic_play_circle_filled_black_24dp)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        exo = new ExoPlayer(getApplicationContext(), trackUrl);
        setPlayPausePlayer(true);

        return binder;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Music player foreground service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

}
