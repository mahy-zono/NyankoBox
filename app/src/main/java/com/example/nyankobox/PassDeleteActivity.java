package com.example.nyankobox;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PassDeleteActivity extends AppCompatActivity {
    // MemoOpenHelperクラスを定義
    dbData helper = null;
    // Sound
    private SoundPlayer soundPlayer;
    //データ
    int pass;

    //新規フラグ
    boolean newFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // データベースから値を取得する
        if(helper == null){
            helper = new dbData(PassDeleteActivity.this);
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
                pass = c.getInt(1); // パスコードを取得
                // 次の行が存在するか確認
                next = c.moveToNext();
                //新規フラグ
                newFlag = false;
            }
            //テキスト
            Context context = getApplicationContext();

            if(newFlag==true){
                // 新規作成の場合
                Toast.makeText(context, "パスコードが設定されていません。", Toast.LENGTH_LONG).show();
            }else if(newFlag==false) {
                try {
                    db.execSQL("update PROFILE_TABLE set lock = '0' where id = '1'");
                    db.execSQL("update PROFILE_TABLE set pass = '' where id = '1'");
                    Toast.makeText(context, "パスコードが削除されました。", Toast.LENGTH_LONG).show();
                }catch (NullPointerException e){

                }
            }

        } finally {
            // finallyは、tryの中で例外が発生した時でも必ず実行される
            // dbを開いたら確実にclose
            db.close();
        }
        //パスコード確認画面に遷移
        Intent intent = new Intent(getApplication(), Privacy2Activity.class);
        startActivity(intent);
    }

}
