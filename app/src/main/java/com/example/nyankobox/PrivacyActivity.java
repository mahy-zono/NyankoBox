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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        CompoundButton sw;
        final Button passChangeBtn;
        final View line4;

        //スイッチのインスタンス生成
        sw = (CompoundButton) findViewById(R.id.passSw);
        passChangeBtn = (Button) findViewById(R.id.passChangeBtn);
        line4 =  (View) findViewById(R.id.line4);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    //switchがONのとき
                    passChangeBtn.setVisibility(View.VISIBLE);
                    line4.setVisibility(View.VISIBLE);
                    //パスコード設定画面表示
                    Intent intent = new Intent(getApplication(), PassLockActivity.class);
                    startActivity(intent);

                }else if(isChecked == false){
                    //switchがOFFのとき
                    passChangeBtn.setVisibility(View.INVISIBLE);
                    line4.setVisibility(View.INVISIBLE);
                }
            }
        });

        //戻るボタン押下で前画面に戻る
        ImageButton backBtn  = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
