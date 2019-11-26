package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    EditText nameText;
    androidx.constraintlayout.widget.ConstraintLayout mainLayout;
    InputMethodManager inputMethodManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameText = (EditText)findViewById(R.id.nameText);
        mainLayout = (androidx.constraintlayout.widget.ConstraintLayout)findViewById(R.id.mainLayout);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        nameText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            //Enterキーが押された時の処理
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // nameTextに名前を表示
                String text = nameText.getText().toString();  //名前取得
                if(!text.equals("")){
                    nameText.setText(text); //名前をセット
                }
                return false;
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
