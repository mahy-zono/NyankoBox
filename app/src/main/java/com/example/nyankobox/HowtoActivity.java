package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
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

        TextView textView5 = (TextView)findViewById(R.id.textView5);
        View view3 = (View)findViewById(R.id.view);
        textView5.bringToFront();
        view3.bringToFront();

        TextView textView7 = (TextView)findViewById(R.id.textView7);
        View view4 = (View)findViewById(R.id.view);
        textView7.bringToFront();
        view4.bringToFront();

        TextView textView9 = (TextView)findViewById(R.id.textView9);
        View view5 = (View)findViewById(R.id.view);
        textView9.bringToFront();
        view5.bringToFront();

        /* LINEのところ
        TextView textView11 = (TextView)findViewById(R.id.textView11);
        View view6 = (View)findViewById(R.id.view);
        textView11.bringToFront();
        view6.bringToFront();
        */



        //カスタムフォント
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

        TextView textView1 = (TextView)findViewById(R.id.textView1);
        textView1.setTypeface(customFont);

        textView2.setTypeface(customFont);

        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setTypeface(customFont);

        textView3.setTypeface(customFont);

        TextView textView4 = (TextView)findViewById(R.id.textView4);
        textView4.setTypeface(customFont);

        textView5.setTypeface(customFont);

        TextView textView6 = (TextView)findViewById(R.id.textView6);
        textView6.setTypeface(customFont);

        textView7.setTypeface(customFont);

        TextView textView8 = (TextView)findViewById(R.id.textView8);
        textView8.setTypeface(customFont);

        textView9.setTypeface(customFont);

        TextView textView10 = (TextView)findViewById(R.id.textView10);
        textView10.setTypeface(customFont);


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
