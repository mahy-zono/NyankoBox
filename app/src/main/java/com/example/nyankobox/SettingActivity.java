package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {
    // Sound
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //サウンド
        soundPlayer = new SoundPlayer(this);
        //カスタムフォント
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

        TextView set = findViewById(R.id.textView);
        set.setTypeface(customFont);

        //プロフィール画面に遷移
        Button prosend = findViewById(R.id.proBtn);
        prosend.setTypeface(customFont);
        prosend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        //プライバシー画面に遷移
        Button prisend = findViewById(R.id.priBtn);
        prisend.setTypeface(customFont);
        prisend.setEnabled(false);
        /*prisend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PrivacyActivity.class);
                startActivity(intent);
            }
        });*/

        //使い方画面に遷移
        Button howtosend = findViewById(R.id.howtoBtn);
        howtosend.setTypeface(customFont);
        howtosend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), HowtoActivity.class);
                startActivity(intent);
            }
        });

        //戻るボタン押下で前画面に戻る
        ImageButton backsend  = findViewById(R.id.backBtn);
        backsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.back();
                finish();
            }
        });
    }
}
