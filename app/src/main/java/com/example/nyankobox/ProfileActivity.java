package com.example.nyankobox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {
    EditText nameText;
    EditText birthdayDate;
    androidx.constraintlayout.widget.ConstraintLayout mainLayout;
    InputMethodManager inputMethodManager;

    // MemoOpenHelperクラスを定義
    dbData helper = null;
    // Sound
    private SoundPlayer soundPlayer;

    //データ
    String userName="";
    String userBD = "";
    String name ="";
    String bd = "";

    //新規フラグ
    boolean newFlag = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //サウンド
        soundPlayer = new SoundPlayer(this);

        //カスタムフォント
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

        TextView text = findViewById(R.id.textView);
        text.setTypeface(customFont);

        nameText = (EditText)findViewById(R.id.nameText);
        nameText.setTypeface(customFont);
        birthdayDate = findViewById(R.id.birthdayDate);
        birthdayDate.setTypeface(customFont);
        mainLayout = (androidx.constraintlayout.widget.ConstraintLayout)findViewById(R.id.mainLayout);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        TextView nt = findViewById(R.id.textView7);
        nt.setTypeface(customFont);

        TextView bdt = findViewById(R.id.textView6);
        bdt.setTypeface(customFont);

        // データベースから値を取得する
        if(helper == null){
            helper = new dbData(ProfileActivity.this);
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
                // 次の行が存在するか確認
                next = c.moveToNext();
                //新規フラグ
                newFlag = false;
            }

            if(newFlag==true){
                nameText.setText(""); //名前をセット
                birthdayDate.setText("");
            }else if(newFlag==false) {
                try {
                    if (name.equals("")) {
                        if (bd.equals("")) {
                            //名前なし、誕生日なし
                            nameText.setText(""); //名前をセット
                            birthdayDate.setText("");
                        } else {
                            //名前なし、誕生日あり
                            nameText.setText(""); //名前をセット
                            birthdayDate.setText(bd);
                            userBD=bd;
                        }
                    } else {
                        if (bd.equals("")) {
                            //名前あり、誕生日なし
                            nameText.setText(name); //名前をセット
                            userName=name;
                            birthdayDate.setText("");
                        } else {
                            //名前あり、誕生日あり
                            nameText.setText(name); //名前をセット
                            userName=name;
                            birthdayDate.setText(bd);
                            userBD=bd;
                        }
                    }
                }catch (NullPointerException e){

                }
            }
        } finally {
            // finallyは、tryの中で例外が発生した時でも必ず実行される
            // dbを開いたら確実にclose
            db.close();
        }

        nameText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            //Enterキーが押された時の処理
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // nameTextに名前を表示
                String text = nameText.getText().toString();  //名前取得
                userName = text;
                if(!text.equals("")){
                    nameText.setText(text); //名前をセット
                }
                return false;
            }
        });

        //誕生日選択
        birthdayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Calendarインスタンスを取得
                final Calendar date = Calendar.getInstance();
                //DataPickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ProfileActivity.this, android.R.style.Theme_Holo_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthdayDate.setText(String.format("%d 年 %02d 月 %02d 日", year, month+1, dayOfMonth));
                    }
                },
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DATE)
                );
                datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        birthdayDate.setText("");
                    }
                });
                //dialogを表示
                datePickerDialog.show();
            }
        });

        //保存ボタン
        Button saveButton  = findViewById(R.id.saveBtn);
        saveButton.setTypeface(customFont);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.send();
                userBD=birthdayDate.getText().toString();

                // データベースを取得する
                SQLiteDatabase db = helper.getWritableDatabase();


                if(newFlag==true){
                    // 新規作成の場合
                    // INSERT
                    db.execSQL("insert into PROFILE_TABLE(name,birthday) VALUES('"+ userName +"','"+ userBD +"')");
                }else if(newFlag==false) {
                    db.execSQL("update PROFILE_TABLE set name = '" + userName + "' where id = '1'");
                    db.execSQL("update PROFILE_TABLE set birthday = '" + userBD + "' where id = '1'");
                }

                //カスタムフォント
                Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

                // カスタムレイアウトの用意
                LayoutInflater layoutInflater = getLayoutInflater();
                View customAlertView = layoutInflater.inflate(R.layout.custom_alert_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setView(customAlertView);

                // タイトルの変更
                TextView title = customAlertView.findViewById(R.id.title);
                title.setText("にゃんこぼっくすより");
                title.setTypeface(customFont);

                // メッセージの変更
                TextView message = customAlertView.findViewById(R.id.message);
                message.setText("プロフィールを新しくしたにゃ～");
                message.setTypeface(customFont);

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

        //戻るボタン押下で前画面に戻る
        ImageButton backBtn  = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.back();
                finish();
            }
        });
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
