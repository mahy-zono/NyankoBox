package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class DressActivity extends AppCompatActivity {
    // MemoOpenHelperクラスを定義
    dbData helper = null;

    //データ
    String id="";
    String select="";
    String selectDress="";

    int dress1=0;
    int dress2=0;
    int dress3=0;

    // 新規フラグ
    boolean newFlag = true;

    // Sound
    private SoundPlayer soundPlayer;

    String dress="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dress);

        //サウンド
        soundPlayer = new SoundPlayer(this);

        //ホーム画面に遷移
        ImageButton homesend = findViewById(R.id.homeBtn);
        homesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
            }
        });

        //日記記入画面に遷移
        ImageButton diarysend = findViewById(R.id.diaryBtn);
        diarysend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DiaryActivity.class);
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

        //設定画面に遷移
        ImageButton setsend = findViewById(R.id.settingBtn);
        setsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                startActivity(intent);
            }
        });

        //衣装非表示
        findViewById(R.id.dressView).setVisibility(View.INVISIBLE);

        //final ChangeActivity CA = new ChangeActivity();

        final ImageView dressView = findViewById(R.id.dressView);

        //衣装1
        final ImageButton dress1Button = findViewById(R.id.dress1Btn);
        final ImageButton dress2Button = findViewById(R.id.dress2Btn);
        final ImageButton dress3Button = findViewById(R.id.dress3Btn);

                // データベースから値を取得する
                if (helper == null) {
                    helper = new dbData(DressActivity.this);
                }

                // データベースを取得する
                SQLiteDatabase db = helper.getWritableDatabase();

                try {
                    // rawQueryというSELECT専用メソッドを使用してデータを取得する
                    Cursor c = db.rawQuery("select * from PROFILE_TABLE where id = '1' ", null);

                    // Cursorの先頭行があるかどうか確認
                    boolean next = c.moveToFirst();


                    // 取得した全ての行を取得
                    while (next) {
                        // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                        select = c.getString(5); // 洋服を取得
                        // 次の行が存在するか確認
                        next = c.moveToNext();
                        //フラグを変更
                        newFlag = false;
                    }
                } finally {
                    // finallyは、tryの中で例外が発生した時でも必ず実行される
                    // dbを開いたら確実にclose
                    db.close();
                }

                switch(select){
                    case "dress1":
                        findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                        dressView.setImageResource(R.drawable.dress1);
                        dress1Button.setImageResource(R.drawable.dress1ch);
                        dress1=1;
                        break;
                    case "dress2":
                        findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                        dressView.setImageResource(R.drawable.dress2);
                        dress2Button.setImageResource(R.drawable.dress2ch);
                        dress2=1;
                        break;
                    case "dress3":
                        findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                        dressView.setImageResource(R.drawable.dress3);
                        dress3Button.setImageResource(R.drawable.dress3ch);
                        dress3=1;
                        break;
                }
                //dress1
        dress1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                if(dress1==0) {
                    dress1=1;
                    dress2=0;
                    dress3=0;
                    findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                    dressView.setImageResource(R.drawable.dress1);
                    dress1Button.setImageResource(R.drawable.dress1ch);
                    dress2Button.setImageResource(R.drawable.dress2btn);
                    dress3Button.setImageResource(R.drawable.dress3btn);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    //新規作成
                    if (newFlag == true) {
                        // INSERT
                        db.execSQL("insert into PROFILE_TABLE(id,choice) VALUES('1','" + "dress1" + "')");
                    } else {
                        //UPDATE
                        db.execSQL("update PROFILE_TABLE set choice = '" + "dress1" + "'");
                    }
                }else{
                    dress1=0;
                    findViewById(R.id.dressView).setVisibility(View.INVISIBLE);
                    dress1Button.setImageResource(R.drawable.dress1btn);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("update PROFILE_TABLE set choice = '" + "" + "'");

                }
                //CA.dressSave("dress1");
            }
        });
                //dress2
        dress2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                if(dress2==0) {
                    dress2=1;
                    dress1=0;
                    dress3=0;
                    findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                    dressView.setImageResource(R.drawable.dress2);
                    dress1Button.setImageResource(R.drawable.dress1btn);
                    dress2Button.setImageResource(R.drawable.dress2ch);
                    dress3Button.setImageResource(R.drawable.dress3btn);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    //新規作成
                    if (newFlag == true) {
                        // INSERT
                        db.execSQL("insert into PROFILE_TABLE(id,choice) VALUES('1','" + "dress2" + "')");
                    } else {
                        //UPDATE
                        db.execSQL("update PROFILE_TABLE set choice = '" + "dress2" + "'");
                    }
                }else{
                    dress2=0;
                    findViewById(R.id.dressView).setVisibility(View.INVISIBLE);
                    dress2Button.setImageResource(R.drawable.dress2btn);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("update PROFILE_TABLE set choice = '" + "" + "'");

                }
                //CA.dressSave("dress1");
            }
        });
        //dress3
        dress3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                if(dress3==0) {
                    dress3=1;
                    dress2=0;
                    dress1=0;
                    findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                    dressView.setImageResource(R.drawable.dress3);
                    dress3Button.setImageResource(R.drawable.dress3ch);
                    dress2Button.setImageResource(R.drawable.dress2btn);
                    dress1Button.setImageResource(R.drawable.dress1btn);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    //新規作成
                    if (newFlag == true) {
                        // INSERT
                        db.execSQL("insert into PROFILE_TABLE(id,choice) VALUES('1','" + "dress3" + "')");
                    } else {
                        //UPDATE
                        db.execSQL("update PROFILE_TABLE set choice = '" + "dress3" + "'");
                    }
                }else{
                    dress3=0;
                    findViewById(R.id.dressView).setVisibility(View.INVISIBLE);
                    dress3Button.setImageResource(R.drawable.dress3btn);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("update PROFILE_TABLE set choice = '" + "" + "'");

                }
                //CA.dressSave("dress1");
            }
        });
    }
}
