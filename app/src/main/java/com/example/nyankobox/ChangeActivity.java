package com.example.nyankobox;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class ChangeActivity extends DressActivity {
    // MemoOpenHelperクラスを定義
    dbData helper = null;

    //データ
    String id="";
    String select="";
    String selectDress="";

    // 新規フラグ
    boolean newFlag = true;


    //衣装保存
    public void dressSave(String dressname) {

        // データベースから値を取得する
        if (helper == null) {
            helper = new dbData(ChangeActivity.this);
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
            //新規作成
            if (newFlag==true){
                // INSERT
                db.execSQL("insert into PROFILE_TABLE(id,choice) VALUES('1','"+ dressname +"')");
            }else{
                //UPDATE
                db.execSQL("update PROFILE_TABLE set choice = '"+ dressname +"'");
            }
        } finally {
            // finallyは、tryの中で例外が発生した時でも必ず実行される
            // dbを開いたら確実にclose
            db.close();
        }
    }

    //着せ替える
    public String dress(String dress) {

        // データベースから値を取得する
        if (helper == null) {
            helper = new dbData(ChangeActivity.this);
        }

        // データベースを取得する
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            // rawQueryというSELECT専用メソッドを使用してデータを取得する
            Cursor c = db.rawQuery("select * from DRESS_TABLE where id = dress ", null);

            // Cursorの先頭行があるかどうか確認
            boolean next = c.moveToFirst();


            // 取得した全ての行を取得
            while (next) {
                // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                id = String.valueOf(c.getInt(0)); // idを取得
                // 次の行が存在するか確認
                next = c.moveToNext();
            }

        } finally {
            // finallyは、tryの中で例外が発生した時でも必ず実行される
            // dbを開いたら確実にclose
            db.close();
        }
        return  id;
    }
}
