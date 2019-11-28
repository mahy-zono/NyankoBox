package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

public class PassChangeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_change);
        final EditText passChangeText;

        //登録するパスコード入力後Enterキー押したとき
        passChangeText = (EditText) findViewById(R.id.passcChangeText);
        passChangeText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(passChangeText.length() == 4) {
                    //パスコード確認画面に遷移
                    Intent intent = new Intent(getApplication(), PassLock2Activity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}
