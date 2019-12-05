package com.example.nyankobox;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int pushSound;

    private AudioAttributes audioAttributes;

    public SoundPlayer(Context context) {

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        pushSound = soundPool.load(context, R.raw.pom, 1);
    }

    public void pompom() {
        soundPool.play(pushSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
