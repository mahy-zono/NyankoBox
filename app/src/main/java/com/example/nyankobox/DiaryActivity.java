package com.example.nyankobox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryActivity extends AppCompatActivity {

    // MemoOpenHelperクラスを定義
    dbData helper = null;
    // 新規フラグ
    boolean newFlag = true;
    // データ
    String nowdate = "";
    String emo = "";
    int sk=0;
    int ir=0;
    int wk=0;

    // 最終的に表示する文字列
    String dispDiary = "";
    String dispEmo ="";

    //カーソル用の変数定義
    EditText editDiary;
    androidx.constraintlayout.widget.ConstraintLayout mainLayout;
    InputMethodManager inputMethodManager;

    // Sound
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        //サウンド
        soundPlayer = new SoundPlayer(this);

        //カスタムフォント
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");


        //感情ボタン設定
        ImageButton sendButton = findViewById(R.id.sendBtn);
        final ImageButton wkwkButton = findViewById(R.id.wkwkBtn);
        final ImageButton irirButton = findViewById(R.id.irirBtn);
        final ImageButton skskButton = findViewById(R.id.skskBtn);

        //現在日時の取得
        Date d = new Date();

        //書式の作成
        SimpleDateFormat sdf = new SimpleDateFormat("MM月 dd日 (E) ");

        //指定書式に変換して表示
        TextView dt = (TextView)findViewById(R.id.dateText);
        dt.setTypeface(customFont);
        dt.setText(sdf.format(d));

        //ホーム画面に遷移
        ImageButton homesend = findViewById(R.id.homeBtn);
        homesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        //ログ画面に遷移
        ImageButton logsend = findViewById(R.id.logBtn);
        logsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), LogActivity.class);
                startActivity(intent);
            }
        });

        //着せ替え画面に遷移
        ImageButton dresssend = findViewById(R.id.dressBtn);
        dresssend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), DressActivity.class);
                startActivity(intent);
            }
        });

        //設定画面に遷移
        ImageButton setsend = findViewById(R.id.settingBtn);
        setsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                startActivity(intent);
            }
        });

        //日付の取得
        SimpleDateFormat dDate = new SimpleDateFormat("YYYY/MM/dd ");
        nowdate = dDate.format(d);

        // データベースから値を取得する
        if(helper == null){
            helper = new dbData(DiaryActivity.this);
        }

        // データベースを取得する
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            // rawQueryというSELECT専用メソッドを使用してデータを取得する
            Cursor c = db.rawQuery("select * from NYANKO_TABLE where date = '"+nowdate+"'", null);

            // Cursorの先頭行があるかどうか確認
            boolean next = c.moveToFirst();


            // 取得した全ての行を取得
            while (next) {
                // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                dispDiary = c.getString(1); // 日記の内容を取得
                dispEmo = c.getString(2); // 感情を取得
                // 次の行が存在するか確認
                next = c.moveToNext();
                //フラグを変更
                newFlag = false;
            }
            //日記内容表示
            EditText dd = findViewById(R.id.editDiary);
            dd.setTypeface(customFont);
            dd.setText(dispDiary);
            try {
                //感情ボタン表示
                if (dispEmo.equals("わくわく")) {
                    wk=1;
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wk);
                    irirButton.setImageResource(R.drawable.irir);

                } else if (dispEmo.equals("いらいら")) {
                    ir=1;
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.ir);

                } else if (dispEmo.equals("しくしく")) {
                    sk=1;
                    skskButton.setImageResource(R.drawable.sk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.irir);
                }
            }catch(NullPointerException e){

            }


        } finally {
            // finallyは、tryの中で例外が発生した時でも必ず実行される
            // dbを開いたら確実にclose
            db.close();
        }


        // わくわくclick
        wkwkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                //指定書式に変換して表示
                TextView mt = (TextView) findViewById(R.id.message);
                // 入力内容を取得する
                emo = "わくわく";
                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();

                //ボタンの画像変更
                if(wk == 1){
                    //選択→未選択
                    wk = 0;
                    wkwkButton.setImageResource(R.drawable.wkwk);

                    try {
                        // 空白
                        db.execSQL("update NYANKO_TABLE set emo = '"+ emo +"' where date = '"+nowdate+"'");
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }

                }else{
                    //未選択→選択
                    sk = 0;
                    wk = 1;
                    ir = 0;
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wk);
                    irirButton.setImageResource(R.drawable.irir);
                    try {
                        if(newFlag==false){
                            //編集の場合
                            // UPDATE
                            db.execSQL("update NYANKO_TABLE set emo = '"+ emo +"' where date = '"+nowdate+"'");
                        }else {
                            // 新規作成の場合

                            // INSERT
                            db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('"+ nowdate +"','"+ "" +"','" + emo + "','"+ "" +"','')");
                        }

                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }



            }
        });

        // いらいらclick
        irirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                //指定書式に変換して表示
                TextView mt = (TextView) findViewById(R.id.message);
                // 入力内容を取得する
                emo = "いらいら";
                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();

                //ボタンの画像変更
                if(ir == 1){
                    //選択→未選択
                    ir = 0;
                    irirButton.setImageResource(R.drawable.irir);
                    try {
                        // 空白
                        db.execSQL("update NYANKO_TABLE set emo = '"+ emo +"' where date = '"+nowdate+"'");
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }else{
                    //未選択→選択
                    sk = 0;
                    wk = 0;
                    ir = 1;
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.ir);
                    try {
                        if(newFlag==false){
                            //編集の場合
                            // UPDATE
                            db.execSQL("update NYANKO_TABLE set emo = '"+ emo +"' where date = '"+nowdate+"'");
                        }else {
                            // 新規作成の場合

                            // INSERT
                            db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('"+ nowdate +"','"+ "" +"','" + emo + "','"+ "" +"','')");
                        }
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }



            }
        });

        // しくしくclick
        skskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                //指定書式に変換して表示
                TextView mt = (TextView) findViewById(R.id.message);
                // 入力内容を取得する
                emo = "しくしく";
                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();

                //ボタンの画像変更
                if(sk == 1){
                    //選択→未選択
                    sk = 0;
                    skskButton.setImageResource(R.drawable.sksk);
                    try {
                        // 空白
                        db.execSQL("update NYANKO_TABLE set emo = '"+ emo +"' where date = '"+nowdate+"'");
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }else{
                    //未選択→選択
                    sk = 1;
                    wk = 0;
                    ir = 0;
                    skskButton.setImageResource(R.drawable.sk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.irir);
                    try {
                        if(newFlag==false){
                            //編集の場合
                            // UPDATE
                            db.execSQL("update NYANKO_TABLE set emo = '"+ emo +"' where date = '"+nowdate+"'");
                        }else {
                            // 新規作成の場合

                            // INSERT
                            db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('"+ nowdate +"','"+ "" +"','" + emo + "','"+ "" +"','')");
                        }


                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }




            }
        });

        //送信ボタンを押下

        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 入力内容を取得する
                EditText diary = findViewById(R.id.editDiary);
                String diaryStr = diary.getText().toString();

                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    if(newFlag==false){
                        //編集の場合
                        // UPDATE
                        db.execSQL("update NYANKO_TABLE set diary = '"+ diaryStr +"' where date = '"+nowdate+"'");
                    }else {
                        // 新規作成の場合

                        // INSERT
                        db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('" + nowdate + "','" + diaryStr + "','" + "" + "','" +""+  "','')");
                    }
                } finally {
                    // finallyは、tryの中で例外が発生した時でも必ず実行される
                    // dbを開いたら確実にclose
                    db.close();
                }
                //ダイアログカスタマイズ
/*                TextView titleView = new TextView(DiaryActivity.this);
                titleView.setText("にゃんこぼっくすより");
                titleView.setTextSize(24);
                titleView.setTextColor(Color.WHITE);
                titleView.setBackgroundColor(getResources().getColor(R.color.alertOrenge));
                titleView.setPadding(20, 20, 20, 20);
                titleView.setGravity(Gravity.CENTER);

               AlertDialog.Builder builder = new AlertDialog.Builder(DiaryActivity.this);
                        builder
                                .setCustomTitle(titleView)
                                .setTitle("にゃんこぼっくすより")
                                .setIcon(R.drawable.sample)
                        .setMessage("日記を受け取ったにゃ！今日も日記を書いてくれてありがとにゃ～")
                        .setPositiveButton("OK",null)
                        .show();


*/
                //カスタムフォント
                Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

                // カスタムレイアウトの用意
                LayoutInflater layoutInflater = getLayoutInflater();
                View customAlertView = layoutInflater.inflate(R.layout.custom_alert_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(DiaryActivity.this);
                builder.setView(customAlertView);

                // タイトルの変更
                TextView title = customAlertView.findViewById(R.id.title);
                title.setText("にゃんこぼっくすより");
                title.setTypeface(customFont);

                if(diaryStr.equals("")){
                    // メッセージの変更
                    TextView message = customAlertView.findViewById(R.id.message);
                    message.setText("まっしろな日記を受けとったにゃ！気が向いたら書いてにゃ～");
                    message.setTypeface(customFont);
                }else {
                    // メッセージの変更
                    TextView message = customAlertView.findViewById(R.id.message);
                    message.setText("日記を受け取ったにゃ！今日も日記を書いてくれてありがとにゃ～");
                    message.setTypeface(customFont);
                }

                final AlertDialog alertDialog = builder.create();

                // ボタンの設定
                Button alertBtn = customAlertView.findViewById(R.id.btnPositive);
                alertBtn.setTypeface(customFont);
                alertBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // ボタンを押した時の処理を書く

                        // ダイアログを閉じる
                        alertDialog.dismiss();
                    }
                });

                // ダイアログ表示
                alertDialog.show();

            }
        });
        //キーボードを閉じたいEditTextオブジェクト
        editDiary = (EditText)findViewById(R.id.editDiary);
        //画面全体のレイアウト
        mainLayout = (androidx.constraintlayout.widget.ConstraintLayout)findViewById(R.id.mainLayout);
        //キーボード表示を制御するためのオブジェクト
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    /**
     * EditText編集時に背景をタップしたらキーボードを閉じるようにするタッチイベントの処理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //背景にフォーカスを移す
        mainLayout.requestFocus();

        return false;
    }
}
