package com.example.nyankobox;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int pushSound;
    private static int enterSound;
    private static int sendSound;
    private static int dressSound;
    private static int omedetoSound;
    private static int cancelSound;

    private AudioAttributes audioAttributes;

    public SoundPlayer(Context context) {

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        pushSound = soundPool.load(context, R.raw.pom, 1);
        enterSound = soundPool.load(context, R.raw.enter, 1);
        sendSound = soundPool.load(context, R.raw.send, 1);
        dressSound = soundPool.load(context, R.raw.chenge, 1);
        omedetoSound = soundPool.load(context, R.raw.trumpet2, 1);
        cancelSound = soundPool.load(context, R.raw.cancel, 1);
    }

    public void pompom() {
        soundPool.play(pushSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void enter() {
        soundPool.play(enterSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void send() {
        soundPool.play(sendSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void dress() {
        soundPool.play(dressSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void hbd() {
        soundPool.play(omedetoSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
    public void back() {
        soundPool.play(cancelSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
