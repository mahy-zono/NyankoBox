package com.example.nyankobox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.icu.text.CaseMap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TitleActivity extends AppCompatActivity {

    // 再生の準備
    private MediaPlayer p;
    // Sound
    private SoundPlayer soundPlayer;

    // MemoOpenHelperクラスを定義
    dbData helper = null;

    //データ
    String name = "";
    String bd = "";
    String dispBd = "";
    String checkBd = "";
    String nowDate = "";

    private void blinkText(ImageButton touchButton, long duration, long offset){
        Animation anm = new AlphaAnimation(0.0f, 1.0f);
        anm.setDuration(duration);
        anm.setStartOffset(offset);
        anm.setRepeatMode(Animation.REVERSE);
        anm.setRepeatCount(Animation.INFINITE);
        touchButton.startAnimation(anm);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        //サウンド
        soundPlayer = new SoundPlayer(this);

        //ナビゲーションバー非表示
        findViewById(R.id.titleLayer).setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        final Handler mHandler = new Handler();
        ScheduledExecutorService mScheduledExecutor;

        p= MediaPlayer.create(getApplicationContext(), R.raw.bgm4);
        p.setLooping(true); //    ループ設定
        p.start();

        // 音楽の読み込み
        //p = MediaPlayer.create(getApplicationContext(), R.raw.bgm);
        // 連続再生設定
        //p.setLooping(true);


        //現在日時の取得
        Date d = new Date();

        //書式の作成
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY 年 MM 月 dd 日");
        nowDate = sdf.format(d);

        SimpleDateFormat dbd = new SimpleDateFormat("MM月dd日");
        dispBd = dbd.format(d);

        SimpleDateFormat cbd = new SimpleDateFormat("MM 月 dd 日");
        checkBd = cbd.format(d);

        // データベースから値を取得する
        if(helper == null){
            helper = new dbData(TitleActivity.this);
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


        //ホーム画面に遷移
        final ImageButton touchButton = findViewById(R.id.touchBtn);
        blinkText(touchButton, 300,400);
        touchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.enter();
              try {
                    //誕生日チェック
                    if (bd.equals(checkBd)) {
                        //Sound
                        soundPlayer.hbd();
                        //カスタムフォント
                        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

                        // カスタムレイアウトの用意
                        LayoutInflater layoutInflater = getLayoutInflater();
                        View customAlertView = layoutInflater.inflate(R.layout.happybirthday_dialog, null);

                        AlertDialog.Builder builder = new AlertDialog.Builder(TitleActivity.this);
                        builder.setView(customAlertView);

                        // タイトルの変更
                        TextView title = customAlertView.findViewById(R.id.title);
                        title.setText("にゃんこぼっくすより");
                        title.setTypeface(customFont);

                        //ImageView imageView = findViewById(R.id.ImageView);
                        //imageView.setImageResource(R.drawable.hbd);

                        // メッセージの変更
                        TextView message = customAlertView.findViewById(R.id.message);
                        if(name.equals("")){
                            message.setText("今日は" + dispBd + "！！お誕生日にゃ！！！めるがたくさんお祝いするにゃ～！");
                        }else {
                            message.setText("今日は" + dispBd + "！！" + name + "のお誕生日にゃ！！めるがたくさんお祝いするにゃ～！");
                        }
                        message.setTypeface(customFont);

                        final AlertDialog alertDialog = builder.create();

                        // ボタンの設定
                        Button alertBtn = customAlertView.findViewById(R.id.btnPositive);
                        alertBtn.setTypeface(customFont);
                        alertBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // ボタンを押した時の処理を書く
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivity(intent);
                                // ダイアログを閉じる
                                alertDialog.dismiss();
                            }
                        });

                        // ダイアログ表示
                        alertDialog.show();
                    } else if(bd.equals("")) {
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                    }else{
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent);
                        }
                }catch(NullPointerException e){
                  Intent intent = new Intent(getApplication(), MainActivity.class);
                  startActivity(intent);
                }
            }
        });
    }
    // 画面が非表示に実行
    /*@Override
    protected void onPause() {
        super.onPause();
        p.pause(); // 一時停止
    }*/



    // アプリ終了時に実行
    @Override
    protected void onDestroy() {
        super.onDestroy();
        p.release();// メモリの解放
        p = null; // 音楽プレーヤーを破棄
    }
}
