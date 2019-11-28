package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class PassLock2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_lock2);
        final EditText passRegiText2;
        //キーボード表示
        InputMethodManager manager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(1, InputMethodManager.SHOW_IMPLICIT);

        //登録するパスコード入力後Enterキー押したとき
        passRegiText2 = (EditText) findViewById(R.id.passcRegiText2);
        passRegiText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(passRegiText2.length() == 4) {
                    //登録パスコード(確認)を取得
                    String passRegiStr2 = passRegiText2.getText().toString();
                    int passRegiNum2 = Integer.parseInt(passRegiStr2);
                    //設定画面に戻る
                    Intent intent = new Intent(getApplication(), PrivacyActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });
    }
}
