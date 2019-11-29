package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TitleActivity extends AppCompatActivity {

    // 再生の準備
    private MediaPlayer p;

    // MemoOpenHelperクラスを定義
    //dbData helper = null;

    //データ
    /*String name = "";
    String bd = "";
    String userBD = "";*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        p= MediaPlayer.create(getApplicationContext(), R.raw.bgm4);
        p.setLooping(true); //    ループ設定
        p.start();

        // 音楽の読み込み
        //p = MediaPlayer.create(getApplicationContext(), R.raw.bgm);
        // 連続再生設定
        //p.setLooping(true);

        //誕生日チェック
        /*
        // データベースから値を取得する
        if(helper == null){
            helper = new dbData(ProfileActivity.this);
        }

        // データベースを取得する
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            // rawQueryというSELECT専用メソッドを使用してデータを取得する
            Cursor c = db.rawQuery("select * from PROFILE_TABLE where id = '1'", null);

            // Cursorの先頭行があるかどうか確認
            boolean next = c.moveToFirst();


            // 取得した全ての行を取得
            while (next) {
                // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                name = c.getString(1); // 名前を取得
                bd = c.getString(2); // 誕生日を取得
                // 次の行が存在するか確認
                next = c.moveToNext();
            }


        } finally {
            // finallyは、tryの中で例外が発生した時でも必ず実行される
            // dbを開いたら確実にclose
            db.close();
        }
         */

        //ホーム画面に遷移
        ImageButton touchButton = findViewById(R.id.touchBtn);
        touchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    // アプリ終了時に実行
    @Override
    protected void onDestroy() {
        super.onDestroy();
        p.release();// メモリの解放
        p = null; // 音楽プレーヤーを破棄
    }
}
