package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    int dress4=0;
    int dress5=0;
    int dress6=0;

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

        //カスタムフォント
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

        //指定書式に変換して表示
        TextView dressText = (TextView) findViewById(R.id.dressText);

        dressText.setTypeface(customFont);

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

        //日記記入画面に遷移
        ImageButton diarysend = findViewById(R.id.diaryBtn);
        diarysend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), DiaryActivity.class);
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

        //トーク画面に遷移
        ImageButton linesend = findViewById(R.id.lineBtn);
        linesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Uri uri = Uri.parse("http://line.me/ti/p/%40812lgphv");
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);
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
        final ImageButton dress4Button = findViewById(R.id.dress4Btn);
        final ImageButton dress5Button = findViewById(R.id.dress5Btn);
        final ImageButton dress6Button = findViewById(R.id.dress6Btn);

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
                    case "dress4":
                        findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                        dressView.setImageResource(R.drawable.dress4);
                        dress4Button.setImageResource(R.drawable.dress4ch);
                        dress4=1;
                        break;
                    case "dress5":
                        findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                        dressView.setImageResource(R.drawable.dress5);
                        dress5Button.setImageResource(R.drawable.dress5ch);
                        dress5=1;
                        break;
                    case "dress6":
                        findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                        dressView.setImageResource(R.drawable.dress6);
                        dress6Button.setImageResource(R.drawable.dress6ch);
                        dress6=1;
                        break;
                }
                //dress1
        dress1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.dress();
                if(dress1==0) {
                    dress1=1;
                    dress2=0;
                    dress3=0;
                    dress4=0;
                    dress5=0;
                    dress6=0;
                    findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                    dressView.setImageResource(R.drawable.dress1);
                    dress1Button.setImageResource(R.drawable.dress1ch);
                    dress2Button.setImageResource(R.drawable.dress2btn);
                    dress3Button.setImageResource(R.drawable.dress3btn);
                    dress4Button.setImageResource(R.drawable.dress4btn);
                    dress5Button.setImageResource(R.drawable.dress5btn);
                    dress6Button.setImageResource(R.drawable.dress6btn);
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
                soundPlayer.dress();
                if(dress2==0) {
                    dress2=1;
                    dress1=0;
                    dress3=0;
                    dress4=0;
                    dress5=0;
                    dress6=0;
                    findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                    dressView.setImageResource(R.drawable.dress2);
                    dress1Button.setImageResource(R.drawable.dress1btn);
                    dress2Button.setImageResource(R.drawable.dress2ch);
                    dress3Button.setImageResource(R.drawable.dress3btn);
                    dress4Button.setImageResource(R.drawable.dress4btn);
                    dress5Button.setImageResource(R.drawable.dress5btn);
                    dress6Button.setImageResource(R.drawable.dress6btn);
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
                soundPlayer.dress();
                if(dress3==0) {
                    dress3=1;
                    dress2=0;
                    dress1=0;
                    dress4=0;
                    dress5=0;
                    dress6=0;
                    findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                    dressView.setImageResource(R.drawable.dress3);
                    dress3Button.setImageResource(R.drawable.dress3ch);
                    dress2Button.setImageResource(R.drawable.dress2btn);
                    dress1Button.setImageResource(R.drawable.dress1btn);
                    dress4Button.setImageResource(R.drawable.dress4btn);
                    dress5Button.setImageResource(R.drawable.dress5btn);
                    dress6Button.setImageResource(R.drawable.dress6btn);
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
        //dress4
        dress4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.dress();
                if(dress4==0) {
                    dress4=1;
                    dress3=0;
                    dress2=0;
                    dress1=0;
                    dress5=0;
                    dress6=0;
                    findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                    dressView.setImageResource(R.drawable.dress4);
                    dress3Button.setImageResource(R.drawable.dress3btn);
                    dress2Button.setImageResource(R.drawable.dress2btn);
                    dress1Button.setImageResource(R.drawable.dress1btn);
                    dress4Button.setImageResource(R.drawable.dress4ch);
                    dress5Button.setImageResource(R.drawable.dress5btn);
                    dress6Button.setImageResource(R.drawable.dress6btn);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    //新規作成
                    if (newFlag == true) {
                        // INSERT
                        db.execSQL("insert into PROFILE_TABLE(id,choice) VALUES('1','" + "dress4" + "')");
                    } else {
                        //UPDATE
                        db.execSQL("update PROFILE_TABLE set choice = '" + "dress4" + "'");
                    }
                }else{
                    dress4=0;
                    findViewById(R.id.dressView).setVisibility(View.INVISIBLE);
                    dress4Button.setImageResource(R.drawable.dress4btn);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("update PROFILE_TABLE set choice = '" + "" + "'");

                }
                //CA.dressSave("dress1");
            }
        });

        //dress5
        dress5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.dress();
                if(dress5==0) {
                    dress4=0;
                    dress3=0;
                    dress2=0;
                    dress1=0;
                    dress5=1;
                    dress6=0;
                    findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                    dressView.setImageResource(R.drawable.dress5);
                    dress3Button.setImageResource(R.drawable.dress3btn);
                    dress2Button.setImageResource(R.drawable.dress2btn);
                    dress1Button.setImageResource(R.drawable.dress1btn);
                    dress4Button.setImageResource(R.drawable.dress4btn);
                    dress5Button.setImageResource(R.drawable.dress5ch);
                    dress6Button.setImageResource(R.drawable.dress6btn);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    //新規作成
                    if (newFlag == true) {
                        // INSERT
                        db.execSQL("insert into PROFILE_TABLE(id,choice) VALUES('1','" + "dress5" + "')");
                    } else {
                        //UPDATE
                        db.execSQL("update PROFILE_TABLE set choice = '" + "dress5" + "'");
                    }
                }else{
                    dress5=0;
                    findViewById(R.id.dressView).setVisibility(View.INVISIBLE);
                    dress5Button.setImageResource(R.drawable.dress5btn);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("update PROFILE_TABLE set choice = '" + "" + "'");

                }
                //CA.dressSave("dress1");
            }
        });

        //dress6
        dress6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.dress();
                if(dress6==0) {
                    dress4=0;
                    dress3=0;
                    dress2=0;
                    dress1=0;
                    dress5=0;
                    dress6=1;
                    findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                    dressView.setImageResource(R.drawable.dress6);
                    dress3Button.setImageResource(R.drawable.dress3btn);
                    dress2Button.setImageResource(R.drawable.dress2btn);
                    dress1Button.setImageResource(R.drawable.dress1btn);
                    dress4Button.setImageResource(R.drawable.dress4btn);
                    dress5Button.setImageResource(R.drawable.dress5btn);
                    dress6Button.setImageResource(R.drawable.dress6ch);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    //新規作成
                    if (newFlag == true) {
                        // INSERT
                        db.execSQL("insert into PROFILE_TABLE(id,choice) VALUES('1','" + "dress6" + "')");
                    } else {
                        //UPDATE
                        db.execSQL("update PROFILE_TABLE set choice = '" + "dress6" + "'");
                    }
                }else{
                    dress6=0;
                    findViewById(R.id.dressView).setVisibility(View.INVISIBLE);
                    dress6Button.setImageResource(R.drawable.dress6btn);
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();
                    db.execSQL("update PROFILE_TABLE set choice = '" + "" + "'");

                }
                //CA.dressSave("dress1");
            }
        });
    }
}
