package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class PassLockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_lock);
        final EditText passRegiText;

        //キーボード表示
        InputMethodManager manager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(1, InputMethodManager.SHOW_IMPLICIT);

        //登録するパスコード入力後Enterキー押したとき
        passRegiText = (EditText) findViewById(R.id.passcRegiText2);
        passRegiText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(passRegiText.length() == 4) {
                    //パスコード確認画面に遷移
                    Intent intent = new Intent(getApplication(), PassLock2Activity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

    }


}
