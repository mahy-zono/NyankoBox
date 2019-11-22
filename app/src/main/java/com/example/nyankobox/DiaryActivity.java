package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryActivity extends AppCompatActivity {

    // MemoOpenHelperクラスを定義
    dbData helper = null;
    // 新規フラグ
    //boolean newFlag = false;
    // date
    String date = "";
    String emo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        //現在日時の取得
        Date d = new Date();

        //書式の作成
        SimpleDateFormat sdf = new SimpleDateFormat("MM月 dd日 (E) ");

        //指定書式に変換して表示
        TextView dt = (TextView)findViewById(R.id.dateText);

        dt.setText(sdf.format(d));

        //ホーム画面に遷移
        ImageButton homesend = findViewById(R.id.homeBtn);
        homesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        //ログ画面に遷移
        ImageButton logsend = findViewById(R.id.logBtn);
        logsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LogActivity.class);
                startActivity(intent);
            }
        });

        //着せ替え画面に遷移
        ImageButton dresssend = findViewById(R.id.dressBtn);
        dresssend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DressActivity.class);
                startActivity(intent);
            }
        });

        //日付の取得
        SimpleDateFormat dDate = new SimpleDateFormat("YYYY/MM/dd ");
        date = dDate.format(d);

        // データベースから値を取得する
        /*if(helper == null){
            helper = new dbData(DiaryActivity.this);
        }*/

        /**
         * 送信ボタン処理
         */
        ImageButton sendButton = findViewById(R.id.sendBtn);
        ImageButton wkwkButton = findViewById(R.id.sendBtn);
        ImageButton irirButton = findViewById(R.id.sendBtn);
        ImageButton skskButton = findViewById(R.id.sendBtn);

        // わくわくclick
        wkwkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 入力内容を取得する
                emo = "わくわく";

                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    // 新規作成の場合

                    // INSERT
                    db.execSQL("insert into NYANKO_TABLE(emo) VALUES('"+ emo +"')");

                } finally {
                    // finallyは、tryの中で例外が発生した時でも必ず実行される
                    // dbを開いたら確実にclose
                    db.close();
                }

            }
        });

        // いらいらclick
        irirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 入力内容を取得する
                emo = "いらいら";

                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    // 新規作成の場合

                    // INSERT
                    db.execSQL("insert into NYANKO_TABLE(emo) VALUES('"+ emo +"')");

                } finally {
                    // finallyは、tryの中で例外が発生した時でも必ず実行される
                    // dbを開いたら確実にclose
                    db.close();
                }

            }
        });

        // しくしくclick
        skskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 入力内容を取得する
                emo = "しくしく";

                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    // 新規作成の場合

                    // INSERT
                    db.execSQL("insert into NYANKO_TABLE(emo) VALUES('"+ emo +"')");

                } finally {
                    // finallyは、tryの中で例外が発生した時でも必ず実行される
                    // dbを開いたら確実にclose
                    db.close();
                }

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 入力内容を取得する
                EditText diary = findViewById(R.id.editText);
                String diaryStr = diary.getText().toString();

                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                        // 新規作成の場合

                        // INSERT
                        db.execSQL("insert into NYANKO_TABLE(date,diary,emo) VALUES('"+ date +"','"+ diaryStr +"','"+ emo +"')");

                } finally {
                    // finallyは、tryの中で例外が発生した時でも必ず実行される
                    // dbを開いたら確実にclose
                    db.close();
                }

            }
        });

    }
}
