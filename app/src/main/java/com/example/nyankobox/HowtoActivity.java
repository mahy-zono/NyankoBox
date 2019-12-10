package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class HowtoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howto);

        //最前面に持ってくる
        TextView textView2 = (TextView)findViewById(R.id.textView2);
        View view = (View)findViewById(R.id.view);
        textView2.bringToFront();
        view.bringToFront();

        TextView textView3 = (TextView)findViewById(R.id.textView3);
        View view2 = (View)findViewById(R.id.view);
        textView3.bringToFront();
        view2.bringToFront();

        //カスタムフォント


        //戻るボタン押下で前画面に戻る
        ImageButton backsend  = findViewById(R.id.backBtn);
        backsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
