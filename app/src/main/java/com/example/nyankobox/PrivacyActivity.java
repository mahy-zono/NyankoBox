package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

public class PrivacyActivity extends AppCompatActivity {
    int passsw = 1; //0:OFF 1:ON
    CompoundButton sw;
    Button passChangeBtn;
    View line4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        //スイッチのインスタンス生成
        sw = (CompoundButton) findViewById(R.id.passSw);
        passChangeBtn = (Button) findViewById(R.id.passChangeBtn);
        line4 =  (View) findViewById(R.id.line4);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true ){
                    if(passsw == 0) {
                        //switchがONのとき
                        passsw = 1;
                        //パスコード変更ボタン表示
                        passChangeBtn.setVisibility(View.VISIBLE);
                        line4.setVisibility(View.VISIBLE);
                        //パスコード設定画面表示
                        Intent intent = new Intent(getApplication(), PassLockActivity.class);
                        startActivity(intent);
                    }

                }else if(!isChecked){
                    if(passsw == 1) {
                        //switchがOFFのとき
                        passsw = 0;
                        //パスコード変更のボタン非表示
                        passChangeBtn.setVisibility(View.INVISIBLE);
                        line4.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        //スイッチをオンにする
        if(passsw == 1){
            sw.setChecked(true);
            passChangeBtn.setVisibility(View.VISIBLE);
            line4.setVisibility(View.VISIBLE);
        }



        //パスコードの変更
        passChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PassChangeActivity.class);
                startActivity(intent);
            }
        });

        //戻るボタン押下で前画面に戻る
        ImageButton backBtn  = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
