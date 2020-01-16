package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class PassLockActivity extends AppCompatActivity {

    // MemoOpenHelperクラスを定義
    dbData helper = null;
    // Sound
    private SoundPlayer soundPlayer;
    //データ
    int pass;
    int passRegiNum;
    //新規フラグ
    boolean newFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_lock);
        final EditText passRegiText;
        final TextView testText;
        final TextView textView;
        final TextView textView2;

        //カスタムフォント
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

        textView = (TextView)findViewById(R.id.textView);
        textView.setTypeface(customFont);

        textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setTypeface(customFont);


        //キーボード表示
        InputMethodManager manager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(1, InputMethodManager.SHOW_IMPLICIT);

        //パスワード仮表示する用
        //testText = (TextView)findViewById(R.id.testText);

        //登録するパスコード入力後Enterキー押したとき
        passRegiText = (EditText) findViewById(R.id.passcRegiText2);
        passRegiText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(passRegiText.length() == 4) {
                    //登録パスコードを取得
                    String passRegiStr = passRegiText.getText().toString();
                    passRegiNum = Integer.parseInt(passRegiStr);
                    //testTextに表示
                    //testText.setText(String.valueOf(String.format("%1$04d",passRegiNum)));

                    // データベースから値を取得する
                    if(helper == null){
                        helper = new dbData(PassLockActivity.this);
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
                            //新規フラグ
                            newFlag = false;
                        }

                        if(newFlag==true){
                            // 新規作成の場合
                            // INSERT
                            db.execSQL("insert into PROFILE_TABLE(pass) VALUES('passRegiNum')");

                        }else if(newFlag==false) {
                            try {
                                db.execSQL("update PROFILE_TABLE set pass = 'passRegiNum' where id = '1'");

                            }catch (NullPointerException e){

                            }
                        }

                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                    //パスコード確認画面に遷移
                    Intent intent = new Intent(getApplication(), PassLock2Activity.class);
                    startActivity(intent);
                }
                return false;
            }
        });


    }


}
