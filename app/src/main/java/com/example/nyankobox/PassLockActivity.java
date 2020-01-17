package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PassLockActivity extends AppCompatActivity {

    // MemoOpenHelperクラスを定義
    dbData helper = null;
    // Sound
    private SoundPlayer soundPlayer;
    //データ
    int pass;
    int passRegiNum2;
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

        EditText passcRegiText2 = (EditText)findViewById(R.id.passcRegiText2);
        passcRegiText2.setTypeface(customFont);


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

                    setContentView(R.layout.activity_pass_lock2);

                    //Toastインフレータ
                    LayoutInflater inflater  =getLayoutInflater();

                    //カスタムToast用のViewを取得する
                    final View layout = inflater.inflate(R.layout.costom_toast, null);

                    //カスタムフォント
                    final Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

                    TextView textView = findViewById(R.id.textView);
                    textView.setTypeface(customFont);

                    TextView textView2 = findViewById(R.id.textView2);
                    textView2.setTypeface(customFont);

                    EditText passcRegiText2 = (EditText)findViewById(R.id.passcRegiText2);
                    passcRegiText2.setTypeface(customFont);

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
                                    if(passRegiNum == passRegiNum2) {
                                        if (newFlag == true) {
                                            // 新規作成の場合
                                            // INSERT
                                            try {
                                                db.execSQL("insert into PROFILE_TABLE(pass,lock) VALUES('passRegiNum','1')");
                                                TextView toastText = layout.findViewById(R.id.toastText);
                                                toastText.setText("パスコードを設定しました。");
                                                toastText.setTypeface(customFont);
                                                Toast toast = new Toast(context);
                                                toast.setView(layout);
                                                toast.show();
                                            } catch (NullPointerException e) {

                                            }

                                        } else if (newFlag == false) {
                                            try {
                                                db.execSQL("update PROFILE_TABLE set pass = 'passRegiNum' where id = '1'");
                                                db.execSQL("update PROFILE_TABLE set lock = '1' where id = '1'");
                                                TextView toastText = layout.findViewById(R.id.toastText);
                                                toastText.setText("パスコードを設定しました。");
                                                toastText.setTypeface(customFont);
                                                Toast toast = new Toast(context);
                                                toast.setView(layout);
                                                toast.show();
                                                //Toast.makeText(context, "パスコードを設定しました。", Toast.LENGTH_LONG).show();
                                            } catch (NullPointerException e) {

                                            }
                                        }
                                    }else{
                                        try {
                                            db.execSQL("update PROFILE_TABLE set lock = '0' where id = '1'");
                                            db.execSQL("update PROFILE_TABLE set pass = '' where id = '1'");
                                            TextView toastText = layout.findViewById(R.id.toastText);
                                            toastText.setText("パスコードを設定出来ませんでした。");
                                            toastText.setTypeface(customFont);
                                            Toast toast = new Toast(context);
                                            toast.setView(layout);
                                            toast.show();
                                            //Toast.makeText(context, "パスコードを設定出来ませんでした。", Toast.LENGTH_LONG).show();
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

                    //パスコード確認画面に遷移
                    //Intent intent = new Intent(getApplication(), PassLock2Activity.class);
                    //startActivity(intent);
                }
                return false;
            }
        });


    }


}
