package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PassLock2Activity extends AppCompatActivity {
    // MemoOpenHelperクラスを定義
    dbData helper = null;
    //データ
    int pass;
    int passRegiNum2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_lock2);

        final EditText passRegiText2;
        //キーボード表示
        InputMethodManager manager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(1, InputMethodManager.SHOW_IMPLICIT);

        //登録するパスコード入力後Enterキー押したとき
        passRegiText2 = (EditText) findViewById(R.id.passcRegiText2);
        passRegiText2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(passRegiText2.length() == 4) {
                    //登録パスコード(確認)を取得
                    String passRegiStr2 = passRegiText2.getText().toString();
                    passRegiNum2 = Integer.parseInt(passRegiStr2);
                    //テキスト
                    Context context = getApplicationContext();

                    // データベースから値を取得する
                    if(helper == null){
                        helper = new dbData(PassLock2Activity.this);
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
                            pass = c.getInt(3); // パスコードを取得
                            // 次の行が存在するか確認
                            next = c.moveToNext();

                        }
                        if(pass == passRegiNum2){
                            try {
                                db.execSQL("update PROFILE_TABLE set lock = '1' where id = '1'");
                                Toast.makeText(context, "パスコードを設定しました。", Toast.LENGTH_LONG).show();
                            }catch (NullPointerException e){

                            }

                        }else {
                            try {
                                db.execSQL("update PROFILE_TABLE set lock = '0' where id = '1'");
                                db.execSQL("update PROFILE_TABLE set pass = '' where id = '1'");
                                Toast.makeText(context, "パスコードを設定出来ませんでした。", Toast.LENGTH_LONG).show();
                            }catch (NullPointerException e){

                            }

                        }
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                    //設定画面に戻る
                    Intent intent = new Intent(getApplication(), Privacy2Activity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

    }
}
