package com.example.nyankobox;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    // 再生の準備
    private MediaPlayer p;
    public MyService() {
    }
    @Override
    public void onCreate() {
        p= MediaPlayer.create(getApplicationContext(), R.raw.bgm4);
        p.setLooping(true);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        p.start(); // 再生
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        p.release();// メモリの解放
        p = null; // 音楽プレーヤーを破棄
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

   /* @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }*/
}
