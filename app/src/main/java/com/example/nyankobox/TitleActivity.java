package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TitleActivity extends AppCompatActivity {

    // 再生の準備
    private MediaPlayer p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        p= MediaPlayer.create(getApplicationContext(), R.raw.bgm);
        p.setLooping(true); //    ループ設定
        p.start();

        // 音楽の読み込み
        //p = MediaPlayer.create(getApplicationContext(), R.raw.bgm);
        // 連続再生設定
        //p.setLooping(true);

        //ホーム画面に遷移
        ImageButton touchButton = findViewById(R.id.touchBtn);
        touchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    // アプリ終了時に実行
    @Override
    protected void onDestroy() {
        super.onDestroy();
        p.release();// メモリの解放
        p = null; // 音楽プレーヤーを破棄
    }
}
