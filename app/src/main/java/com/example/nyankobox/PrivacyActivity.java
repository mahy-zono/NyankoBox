package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class PrivacyActivity extends AppCompatActivity {
    String passsw = ""; //0:OFF 1:ON
    CompoundButton sw;
    Button passChangeBtn;
    View line4;
    // MemoOpenHelperクラスを定義
    dbData helper = null;
    // 新規フラグ
    boolean newFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        //スイッチのインスタンス生成
        sw = (CompoundButton) findViewById(R.id.passSw);
        passChangeBtn = (Button) findViewById(R.id.passChangeBtn);
        line4 =  (View) findViewById(R.id.line4);

        // データベースから値を取得する
        if(helper == null){
            helper = new dbData(PrivacyActivity.this);
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
                passsw = String.valueOf(c.getInt(4)); // パスワード情報を取得
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
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // データベースを取得する
                SQLiteDatabase db = helper.getWritableDatabase();

                    if(isChecked == true) {
                        if (newFlag == false) {
                            try {
                                if(passsw.equals("1")) {
                                    //指定書式に変換して表示
                                    TextView mt = (TextView) findViewById(R.id.textView);
                                    //メッセージ表示
                                    mt.setText(passsw);
                                    //　パスワード未設定→設定
                                    passsw = "1";
                                    db.execSQL("update PROFILE_TABLE set lock = ' 1 ' where id = '1'");
                                    //パスコード設定画面表示
                                    Intent intent = new Intent(getApplication(), PassLockActivity.class);
                                    startActivity(intent);
                                    //パスコード変更ボタン表示
                                    sw.setChecked(true);
                                    passChangeBtn.setVisibility(View.VISIBLE);
                                    line4.setVisibility(View.VISIBLE);
                                }
                            } catch (NullPointerException e) {
                            }
                        } else {
                            // 未設定→設定
                            passsw="1";
                            // 新規作成の場合
                            db.execSQL("insert into PROFILE_TABLE(lock) VALUES(' 1 ')");
                            //パスコード設定画面表示
                            Intent intent = new Intent(getApplication(), PassLockActivity.class);
                            startActivity(intent);
                            //パスコード変更ボタン表示
                            sw.setChecked(true);
                            passChangeBtn.setVisibility(View.VISIBLE);
                            line4.setVisibility(View.VISIBLE);
                        }
                    }else if(!isChecked){
                        if(passsw.equals("1")) {
                            //設定→未設定
                            //switchがOFFのとき
                            passsw = "0";
                            db.execSQL("update PROFILE_TABLE set lock = ' 1 ' where id = '1'");
                            //パスコード変更のボタン非表示
                            passChangeBtn.setVisibility(View.INVISIBLE);
                            line4.setVisibility(View.INVISIBLE);
                        }
                    }

            }

        });
        //指定書式に変換して表示
        //TextView mt = (TextView) findViewById(R.id.textView);
        //メッセージ表示
        //mt.setText(passsw);

        //スイッチをオンにする
        /*if(passsw.equals("1")){
            //指定書式に変換して表示
            //TextView mt = (TextView) findViewById(R.id.textView);
            //メッセージ表示
            //mt.setText(passsw);
            sw.setChecked(true);
            passChangeBtn.setVisibility(View.VISIBLE);
            line4.setVisibility(View.VISIBLE);
        }*/



        //パスコードの変更
        passChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), PassChangeActivity.class);
                startActivity(intent);
            }
        });

        //戻るボタン押下で設定画面に戻る
        ImageButton backBtn  = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
