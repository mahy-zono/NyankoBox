package com.example.nyankobox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PassUnlockActivity extends AppCompatActivity {
    // Sound
    private SoundPlayer soundPlayer;

    // 再生の準備
    private MediaPlayer p;

    // MemoOpenHelperクラスを定義
    dbData helper = null;

    //データ
    String pass="";
    String name = "";
    String bd = "";
    int passRegiNum;
    String dispBd = "";
    String checkBd = "";
    String nowDate = "";
    String passcodeStr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_unlock);
        //サウンド
        soundPlayer = new SoundPlayer(this);

        //Toastインフレータ
        LayoutInflater inflater  =getLayoutInflater();

        //カスタムToast用のViewを取得する
        final View layout = inflater.inflate(R.layout.costom_toast, null);

        //カスタムフォント
        final Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

        final EditText passRegiText;
        final TextView testText;
        final TextView textView;
        final TextView textView2;

        //現在日時の取得
        Date d = new Date();

        //書式の作成
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY 年 MM 月 dd 日");
        nowDate = sdf.format(d);

        SimpleDateFormat dbd = new SimpleDateFormat("MM月dd日");
        dispBd = dbd.format(d);

        SimpleDateFormat cbd = new SimpleDateFormat("MM 月 dd 日");
        checkBd = cbd.format(d);



        textView = (TextView)findViewById(R.id.textView);
        textView.setTypeface(customFont);

        textView2 = (TextView)findViewById(R.id.textView2);
        textView2.setTypeface(customFont);

        final EditText passcode = (EditText)findViewById(R.id.passcode);
        passcode.setTypeface(customFont);

        //キーボード表示
        InputMethodManager manager =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.toggleSoftInput(1, InputMethodManager.SHOW_IMPLICIT);

        //登録するパスコード入力後Enterキー押したとき
        passRegiText = (EditText) findViewById(R.id.passcode);
        passRegiText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(passRegiText.length() == 4) {
                    //登録パスコードを取得
                    String passRegiStr = passRegiText.getText().toString();
                    passRegiNum = Integer.parseInt(passRegiStr);
                    //testTextに表示
                    //testText.setText(String.valueOf(String.format("%1$04d",passRegiNum)));
                    //テキスト
                    Context context = getApplicationContext();

                    // データベースから値を取得する
                    if(helper == null){
                        helper = new dbData(PassUnlockActivity.this);
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
                            pass = c.getString(3);//パスワード取得
                            // 次の行が存在するか確認
                            next = c.moveToNext();

                        }

                        passcodeStr = String.valueOf(passRegiNum);

                        if(pass.equals(passcodeStr)){
                            try {
                                //誕生日チェック
                                if (bd.substring(7).equals(checkBd)) {
                                    //Sound
                                    soundPlayer.hbd();
                                    //カスタムフォント
                                    Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

                                    // カスタムレイアウトの用意
                                    LayoutInflater layoutInflater = getLayoutInflater();
                                    View customAlertView = layoutInflater.inflate(R.layout.happybirthday_dialog, null);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(PassUnlockActivity.this);
                                    builder.setView(customAlertView);

                                    // タイトルの変更
                                    TextView title = customAlertView.findViewById(R.id.title);
                                    title.setText("にゃんこぼっくすより");
                                    title.setTypeface(customFont);

                                    //ImageView imageView = findViewById(R.id.ImageView);
                                    //imageView.setImageResource(R.drawable.hbd);

                                    // メッセージの変更
                                    TextView message = customAlertView.findViewById(R.id.message);
                                    if (name.equals("")) {
                                        message.setText("今日は" + dispBd + "！！お誕生日にゃ！！！めるがたくさんお祝いするにゃ～！");
                                    } else {
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
                                } else if (bd.substring(7).equals("")) {
                                    Intent intent = new Intent(getApplication(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(getApplication(), MainActivity.class);
                                    startActivity(intent);
                                }
                            } catch (NullPointerException e) {
                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                startActivity(intent);
                            }
                        }else {
                            //タイトル画面に遷移
                            TextView toastText = layout.findViewById(R.id.toastText);
                            toastText.setText("パスコードが間違っています。");
                            toastText.setTypeface(customFont);
                            Toast toast = new Toast(context);
                            toast.setView(layout);
                            toast.show();
                           // Toast.makeText(context, "パスコードが間違っています。", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplication(), TitleActivity.class);
                            startActivity(intent);
                        }
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }
                return false;
            }
        });

    }
}
