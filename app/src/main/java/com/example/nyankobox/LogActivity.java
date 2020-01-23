package com.example.nyankobox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Calendar;

public class LogActivity extends AppCompatActivity {
    // MemoOpenHelperクラスを定義
    dbData helper = null;
    // 新規フラグ
    boolean newFlag = true;
    //データ
    String choiceDate = "";
    String choiceDiary ="";
    String choiceGoal="";
    String choiceClear="";
    String choiceEmo="";
    androidx.constraintlayout.widget.ConstraintLayout mainLayout;
    InputMethodManager inputMethodManager;

    // Sound
    private SoundPlayer soundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        //サウンド
        soundPlayer = new SoundPlayer(this);

        //カスタムフォント
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");
        //目標
        EditText gt = findViewById(R.id.goalText);
        gt.setTypeface(customFont);
        //日記
        EditText dt = findViewById(R.id.editDiary);
        dt.setTypeface(customFont);

        mainLayout = (androidx.constraintlayout.widget.ConstraintLayout) findViewById(R.id.mainLayout);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //日付の変数
        final EditText dateText;

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
        final ImageButton diarysend = findViewById(R.id.diaryBtn);
        diarysend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), DiaryActivity.class);
                startActivity(intent);
            }
        });

        //着せ替え画面に遷移
        ImageButton dresssend = findViewById(R.id.dressBtn);
        dresssend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), DressActivity.class);
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

        //感情非表示
        findViewById(R.id.imageEmo).setVisibility(View.INVISIBLE);
        //達成非表示
        findViewById(R.id.imageClear).setVisibility(View.INVISIBLE);

        final ImageView emoView = findViewById(R.id.imageEmo);
        final ImageView clearView = findViewById(R.id.imageClear);

        /**
         * カレンダーボタンタップ時のカレンダー表示
         */
        dateText = (EditText) findViewById(R.id.dateText);
        dateText.setTypeface(customFont);
        ImageButton calendarBtn = findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                //Calendarインスタンスを取得
                final Calendar date = Calendar.getInstance();
                //DatePickerDialogインスタンスを取得
                DatePickerDialog datePickerDialog = new DatePickerDialog(LogActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //setした日付を取得して表示
                        dateText.setText(String.format("%d年 %02d月 %02d日", year,month+1, dayOfMonth));
                        choiceDate = String.format("%d/%02d/%02d ", year,month+1, dayOfMonth);

                        // データベースから値を取得する
                        if(helper == null){
                            helper = new dbData(LogActivity.this);
                        }

                        // データベースを取得する
                        SQLiteDatabase db = helper.getWritableDatabase();

                        //カスタムフォント
                        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

                        try {
                            // rawQueryというSELECT専用メソッドを使用してデータを取得する
                            Cursor c = db.rawQuery("select * from NYANKO_TABLE where date = '"+ choiceDate +"'", null);

                            // Cursorの先頭行があるかどうか確認
                            boolean next = c.moveToFirst();


                            // 取得した全ての行を取得
                            while (next) {
                                // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                                choiceDiary = c.getString(1); // 日記を取得
                                choiceEmo = c.getString(2); // 感情を取得
                                choiceGoal = c.getString(3); // 目標を取得
                                choiceClear = String.valueOf(c.getInt(4)); // 達成を取得
                                // 次の行が存在するか確認
                                next = c.moveToNext();
                                //フラグを変更
                                newFlag = false;
                            }
                            if(newFlag==false) {
                                //データがある場合
                                //感情表示
                                findViewById(R.id.imageEmo).setVisibility(View.VISIBLE);
                                if (choiceEmo.equals("わくわく")) {
                                    emoView.setImageResource(R.drawable.wkwk);
                                } else if (choiceEmo.equals("いらいら")) {
                                    emoView.setImageResource(R.drawable.irir);

                                } else if (choiceEmo.equals("しくしく")) {
                                    emoView.setImageResource(R.drawable.sksk);
                                } else if (choiceEmo.equals("うとうと")) {
                                    emoView.setImageResource(R.drawable.utut);
                                }else {
                                    findViewById(R.id.imageEmo).setVisibility(View.INVISIBLE);
                                }
                                //達成表示
                                if(choiceClear.equals("1")) {
                                    findViewById(R.id.imageClear).setVisibility(View.VISIBLE);
                                }else if(choiceClear.equals("0")){
                                    findViewById(R.id.imageClear).setVisibility(View.INVISIBLE);
                                }
                                //目標表示
                                //指定書式に変換して表示
                                EditText gt = findViewById(R.id.goalText);
                                gt.setTypeface(customFont);
                                //メッセージ表示
                                gt.setText(choiceGoal);
                                //日記内容表示
                                //指定書式に変換して表示
                                EditText dt = findViewById(R.id.editDiary);
                                dt.setTypeface(customFont);
                                //メッセージ表示
                                dt.setText(choiceDiary);
                                //フラグを変更
                                newFlag = true;
                            }else{
                                //データがない場合
                                //指定書式に変換して表示
                                EditText dt = new EditText(LogActivity.this);
                                dt.setTypeface(customFont);
                                //指定書式に変換して表示
                                EditText gt = findViewById(R.id.editDiary);
                                gt.setTypeface(customFont);
                                //メッセージ非表示
                                gt.getEditableText().clear();
                                //目標の非表示
                                EditText goalT = findViewById(R.id.goalText);
                                goalT.getEditableText().clear();
                                goalT.setTypeface(customFont);
                                //感情非表示
                                findViewById(R.id.imageEmo).setVisibility(View.INVISIBLE);
                                //達成非表示
                                findViewById(R.id.imageClear).setVisibility(View.INVISIBLE);
                            }
                        } finally {
                            // finallyは、tryの中で例外が発生した時でも必ず実行される
                            // dbを開いたら確実にclose
                            db.close();
                        }

                    }
                },
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DATE)
                );
                //dialogを表示
                datePickerDialog.show();
            }
        });

        //編集
        ImageButton editButton = findViewById(R.id.editBtn);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.send();
                //日付が選択されているか
                EditText date = findViewById(R.id.dateText);
                String dateStr = date.getText().toString();
                if(!dateStr.equals("")) {
                    // 入力内容を取得する
                    EditText diary = findViewById(R.id.editDiary);
                    String diaryStr = diary.getText().toString();
                    // DBに保存
                    SQLiteDatabase db = helper.getWritableDatabase();
                    try {
                        if (newFlag == false) {
                            // UPDATE
                            db.execSQL("update NYANKO_TABLE set diary = '" + diaryStr + "' where date = '" + choiceDate + "'");
                        } else {
                            // INSERT
                            db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('" + choiceDate + "','" + diaryStr + "','" + "" + "','" + "" + "',' '' ')");
                        }
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }

                   /* AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);// FragmentではActivityを取得して生成
                    builder.setTitle("にゃんこぼっくすより")
                            .setMessage("日記の内容を更新したにゃ～")
                            .setPositiveButton("OK", null)
                            .show();
                     */
                   //カスタムフォント
                    Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

                // カスタムレイアウトの用意
                LayoutInflater layoutInflater = getLayoutInflater();
                View customAlertView = layoutInflater.inflate(R.layout.diary_dialog, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);
                builder.setView(customAlertView);

                // タイトルの変更
                TextView title = customAlertView.findViewById(R.id.title);
                title.setText("にゃんこぼっくすより");
                title.setTypeface(customFont);

                // メッセージの変更
                TextView message = customAlertView.findViewById(R.id.message);
                message.setText("日記の内容を更新したにゃ～");
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
                }else{
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);// FragmentではActivityを取得して生成
                    builder.setTitle("にゃんこぼっくすより")
                            .setMessage("日付を選択してにゃ～")
                            .setPositiveButton("OK", null)
                            .show();*/
                    //カスタムフォント
                    Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

                    // カスタムレイアウトの用意
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View customAlertView = layoutInflater.inflate(R.layout.custom_alert_dialog, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);
                    builder.setView(customAlertView);

                    // タイトルの変更
                    TextView title = customAlertView.findViewById(R.id.title);
                    title.setText("にゃんこぼっくすより");
                    title.setTypeface(customFont);

                    // メッセージの変更
                    TextView message = customAlertView.findViewById(R.id.message);
                    message.setText("日付を選択してにゃ～");
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
            }
        });
        //削除
        ImageButton deleteButton = findViewById(R.id.deleteBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.send();
                //日付が選択されているか
                EditText date = findViewById(R.id.dateText);
                String dateStr = date.getText().toString();
                if(!dateStr.equals("")) {
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);// FragmentではActivityを取得して生成
                    builder.setTitle("にゃんこぼっくすより")
                            .setMessage("日記を削除してもよろしいですか？")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // OK button pressed
                                    String diaryStr = "";
                                    // DBに保存
                                    SQLiteDatabase db = helper.getWritableDatabase();
                                    try {
                                        // UPDATE
                                        db.execSQL("update NYANKO_TABLE set diary = '" + diaryStr + "' where date = '" + choiceDate + "'");

                                    } finally {
                                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                                        // dbを開いたら確実にclose
                                        db.close();
                                    }
                                    //指定書式に変換して表示
                                    EditText dt = findViewById(R.id.editDiary);
                                    //メッセージ表示
                                    dt.getEditableText().clear();
                                }
                            })
                            .setNegativeButton("キャンセル", null)
                            .show();*/
                    //カスタムフォント
                    Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

                    // カスタムレイアウトの用意
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View customAlertView = layoutInflater.inflate(R.layout.custom_alert_dialog2, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);
                    builder.setView(customAlertView);

                    // タイトルの変更
                    TextView title = customAlertView.findViewById(R.id.title);
                    title.setText("にゃんこぼっくすより");
                    title.setTypeface(customFont);

                    // メッセージの変更
                    TextView message = customAlertView.findViewById(R.id.message);
                    message.setText("日記を削除してもよろしいですか？");
                    message.setTypeface(customFont);

                    final AlertDialog alertDialog = builder.create();

                    // ボタンの設定
                    Button alertBtn = customAlertView.findViewById(R.id.btnPositive);
                    alertBtn.setTypeface(customFont);
                    Button alertBtn2 = customAlertView.findViewById(R.id.btnPositive2);
                    alertBtn2.setTypeface(customFont);
                    alertBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // ボタンを押した時の処理を書く
                            // OK button pressed
                            String diaryStr = "";
                            // DBに保存
                            SQLiteDatabase db = helper.getWritableDatabase();
                            try {
                                // UPDATE
                                db.execSQL("update NYANKO_TABLE set diary = '" + diaryStr + "' where date = '" + choiceDate + "'");

                            } finally {
                                // finallyは、tryの中で例外が発生した時でも必ず実行される
                                // dbを開いたら確実にclose
                                db.close();
                            }
                            //指定書式に変換して表示
                            EditText dt = findViewById(R.id.editDiary);
                            //メッセージ表示
                            dt.getEditableText().clear();
                            // ダイアログを閉じる
                            alertDialog.dismiss();
                        }
                    });
                    //キャンセルボタン押したとき
                    alertBtn2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // ダイアログを閉じる
                            alertDialog.dismiss();
                        }
                    });
                    // ダイアログ表示
                    alertDialog.show();
                }else {
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);// FragmentではActivityを取得して生成
                    builder.setTitle("にゃんこぼっくすより")
                            .setMessage("日付を選択してにゃ～")
                            .setPositiveButton("OK", null);*/

                    //カスタムフォント
                    Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

                    // カスタムレイアウトの用意
                    LayoutInflater layoutInflater = getLayoutInflater();
                    View customAlertView = layoutInflater.inflate(R.layout.custom_alert_dialog, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(LogActivity.this);
                    builder.setView(customAlertView);

                    // タイトルの変更
                    TextView title = customAlertView.findViewById(R.id.title);
                    title.setText("にゃんこぼっくすより");
                    title.setTypeface(customFont);


                    // メッセージの変更
                    TextView message = customAlertView.findViewById(R.id.message);
                    message.setText("日付を選択してにゃ～");
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
            }
        });
        //目標表示

    }
    /**
     * EditText編集時に背景をタップしたらキーボードを閉じるようにするタッチイベントの処理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //フォーカスを背景に移す
        mainLayout.requestFocus();

        return false;
    }


}
