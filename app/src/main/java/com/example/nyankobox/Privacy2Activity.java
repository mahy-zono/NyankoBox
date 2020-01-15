package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Privacy2Activity extends AppCompatActivity {
    // Sound
    private SoundPlayer soundPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy2);


        //サウンド
        soundPlayer = new SoundPlayer(this);
        //カスタムフォント
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

        TextView set = findViewById(R.id.textView);
        set.setTypeface(customFont);

        Button passBtn = (Button)findViewById(R.id.passBtn);
        passBtn.setTypeface(customFont);

        Button passDeleteBtn = (Button)findViewById(R.id.passDeleteBtn);
        passDeleteBtn.setTypeface(customFont);


        //パスコード登録画面に遷移
        Button passlocksend = findViewById(R.id.passBtn);
        passlocksend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), PassLockActivity.class);
                startActivity(intent);
            }
        });

        //パスコード変更画面に遷移
/*        Button passchengsend = findViewById(R.id.passChangeBtn);
        passchengsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), PassChangeActivity.class);
                startActivity(intent);
            }
        });
*/
        //パスコード削除画面に遷移
        Button passdeletesend = findViewById(R.id.passDeleteBtn);
        passdeletesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), PassDeleteActivity.class);
                startActivity(intent);
            }
        });

        //戻るボタン押下で設定画面に戻る
        ImageButton backBtn  = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
