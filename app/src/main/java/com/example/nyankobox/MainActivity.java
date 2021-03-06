package com.example.nyankobox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {
    EditText goalText;
    androidx.constraintlayout.widget.ConstraintLayout mainLayout;
    InputMethodManager inputMethodManager;

    //表情差分
    ImageView mel;

    // MemoOpenHelperクラスを定義
    dbData helper = null;
    // 新規フラグ
    boolean newFlag = true;
    // データ
    String select="";
    String nowdate = "";
    String emo = "";
    String dispGoal ="";
    String dispClear = "";
    String dressNo="";
    int cl=0;
    int sk=0;
    int ir=0;
    int wk=0;
    int ut=0;
    int dk=0;
    // 最終的に表示する文字列
    String dispEmo ="";

    // 再生の準備
    MediaPlayer p;
    // Sound
    private SoundPlayer soundPlayer;

    /*@Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //サウンド
        soundPlayer = new SoundPlayer(this);

        //カスタムフォント
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

        //現在日時の取得
        Date d = new Date();

        //書式の作成
        SimpleDateFormat sdf = new SimpleDateFormat("MM月 dd日 (E) ");

        //指定書式に変換して表示
        TextView dt = (TextView) findViewById(R.id.dateText);

        dt.setText(sdf.format(d));
        dt.setTypeface(customFont);

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

        //目標記入欄
        goalText = (EditText)findViewById(R.id.goalText);
        goalText.setTypeface(customFont);
        mainLayout = (ConstraintLayout)findViewById(R.id.mainLayout);
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        /*goalText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            //Enterキーが押された時の処理
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // goalTextViewに目標を表示
                String text = goalText.getText().toString();  //目標取得
                if(!text.equals("")) {
                    goalText.setText(text); //目標をセット
                }
                    // データベースを取得する
                    SQLiteDatabase db = helper.getWritableDatabase();

                    try {
                        // rawQueryというSELECT専用メソッドを使用してデータを取得する
                        Cursor c = db.rawQuery("select * from NYANKO_TABLE where date = '"+nowdate+"'", null);

                        // Cursorの先頭行があるかどうか確認
                        boolean next = c.moveToFirst();

                        // 取得した全ての行を取得
                        while (next) {
                            // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                            dispGoal = c.getString(3); // 目標を取得
                            // 次の行が存在するか確認
                            next = c.moveToNext();
                            //フラグを変更
                            newFlag = false;
                        }
                        if(newFlag==false){
                            //編集の場合
                            try {
                                //目標がリセット
                                if(text!="") {
                                    // UPDATE
                                    db.execSQL("update NYANKO_TABLE set goal = '" + text + "' where date = '" + nowdate + "'");
                                    db.execSQL("update NYANKO_TABLE set clear = '0' where date = '"+nowdate+"'");
                                    cl = 0;
                                    final ImageButton clearButton = findViewById(R.id.clearBtn);
                                    clearButton.setImageResource(R.drawable.clearbtn);
                                }else{
                                    //UPDATE
                                    db.execSQL("update NYANKO_TABLE set goal = '" + "" + "' where date = '" + nowdate + "'");
                                    db.execSQL("update NYANKO_TABLE set clear = '" + 0 + "' where date = '" + nowdate + "'");
                                }
                            }catch(NullPointerException e){

                            }
                        }else{
                            // 新規作成の場合
                            // INSERT
                            db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('"+ nowdate +"','"+ "" +"','" + "" + "','"+ text +"','')");
                        }
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                return false;
            }
        }); */

        //目標達成
        final ImageButton clearButton = findViewById(R.id.clearBtn);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // データベースを取得する
                SQLiteDatabase db = helper.getWritableDatabase();

                //ボタンの画像変更
                if(cl == 1){
                    //選択→未選択
                    cl = 0;
                    clearButton.setImageResource(R.drawable.clearbtn);

                    try {
                        // 空白
                        db.execSQL("update NYANKO_TABLE set clear = '0' where date = '"+nowdate+"'");
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }

                }else {
                    //未選択→選択
                    //Sound
                    soundPlayer.send();
                    try {
                        // rawQueryというSELECT専用メソッドを使用してデータを取得する
                        Cursor c = db.rawQuery("select * from NYANKO_TABLE where date = '"+nowdate+"'", null);

                        // Cursorの先頭行があるかどうか確認
                        boolean next = c.moveToFirst();

                        // 取得した全ての行を取得
                        while (next) {
                            // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                            dispGoal = c.getString(3); // 目標を取得
                            // 次の行が存在するか確認
                            next = c.moveToNext();
                            //フラグを変更
                            newFlag = false;
                        }

                        //カスタムフォント
                        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");
                        if (newFlag == false) {
                            try {
                                if (!dispGoal.equals("")) {
                                    //編集の場合
                                    cl = 1;
                                    clearButton.setImageResource(R.drawable.clearbtn_push);
                                    // UPDATE
                                    db.execSQL("update NYANKO_TABLE set clear = ' 1 ' where date = '" + nowdate + "'");

                                    // カスタムレイアウトの用意
                                    LayoutInflater layoutInflater = getLayoutInflater();
                                    View customAlertView = layoutInflater.inflate(R.layout.clear_dialog, null);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setView(customAlertView);

                                    // タイトルの変更
                                    TextView title = customAlertView.findViewById(R.id.title);
                                    title.setText("にゃんこぼっくすより");
                                    title.setTypeface(customFont);

                                    // メッセージの変更
                                    TextView message = customAlertView.findViewById(R.id.message);
                                    message.setText("目標達成おめでとにゃ～");
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
                                } else {
                                    // カスタムレイアウトの用意
                                    LayoutInflater layoutInflater = getLayoutInflater();
                                    View customAlertView = layoutInflater.inflate(R.layout.clear2_dialog, null);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setView(customAlertView);

                                    // タイトルの変更
                                    TextView title = customAlertView.findViewById(R.id.title);
                                    title.setText("にゃんこぼっくすより");
                                    title.setTypeface(customFont);

                                    // メッセージの変更
                                    TextView message = customAlertView.findViewById(R.id.message);
                                    message.setText("目標が入力されてないにゃ...");
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
                            } catch (NullPointerException e) {
                                // カスタムレイアウトの用意
                                LayoutInflater layoutInflater = getLayoutInflater();
                                View customAlertView = layoutInflater.inflate(R.layout.custom_alert_dialog, null);

                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setView(customAlertView);

                                // タイトルの変更
                                TextView title = customAlertView.findViewById(R.id.title);
                                title.setText("にゃんこぼっくすより");
                                title.setTypeface(customFont);

                                // メッセージの変更
                                TextView message = customAlertView.findViewById(R.id.message);
                                message.setText("目標を入力してにゃ～あ");
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
                        }else{
                            // カスタムレイアウトの用意
                            LayoutInflater layoutInflater = getLayoutInflater();
                            View customAlertView = layoutInflater.inflate(R.layout.custom_alert_dialog, null);

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setView(customAlertView);

                            // タイトルの変更
                            TextView title = customAlertView.findViewById(R.id.title);
                            title.setText("にゃんこぼっくすより");
                            title.setTypeface(customFont);

                            // メッセージの変更
                            TextView message = customAlertView.findViewById(R.id.message);
                            message.setText("目標を入力してにゃ～あ");
                            message.setTypeface(customFont);

                            final AlertDialog alertDialog = builder.create();

                            // ボタンの設定
                            Button alertBtn = customAlertView.findViewById(R.id.btnPositive);
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
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }
            }
        });

        //表情差分
        //mel.setImageResource(R.drawable.);
        //衣装非表示
        findViewById(R.id.dressView).setVisibility(View.INVISIBLE);
        final ImageView dressView = findViewById(R.id.dressView);

        //指定書式に変換して表示
        TextView mt = (TextView) findViewById(R.id.message);
        //メッセージ表示
        mt.setText("えへへ、まってたよ～！今日もお話きかせてほしいにゃ～！");
        mt.setTypeface(customFont);


        //感情ボタン設定
        ImageButton sendButton = findViewById(R.id.sendBtn);
        final ImageButton wkwkButton = findViewById(R.id.wkwkBtn);
        final ImageButton irirButton = findViewById(R.id.irirBtn);
        final ImageButton skskButton = findViewById(R.id.skskBtn);
        final ImageButton ututButton = findViewById(R.id.ututBtn);
        final ImageButton dkdkButton = findViewById(R.id.dkdkBtn);

        //日付の取得
        SimpleDateFormat dDate = new SimpleDateFormat("YYYY/MM/dd ");
        nowdate = dDate.format(d);



        // データベースから値を取得する
        if(helper == null){
            helper = new dbData(MainActivity.this);
        }

        // データベースを取得する
        SQLiteDatabase dressdb = helper.getWritableDatabase();
        try {
            // rawQueryというSELECT専用メソッドを使用してデータを取得する
            Cursor cdb = dressdb.rawQuery("select * from PROFILE_TABLE where id = '1'", null);

            // Cursorの先頭行があるかどうか確認
            boolean next = cdb.moveToFirst();

            // 取得した全ての行を取得
            while (next) {
                // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                select = cdb.getString(5); // 衣装を取得
                // 次の行が存在するか確認
                next = cdb.moveToNext();
            }

        } finally {
            // finallyは、tryの中で例外が発生した時でも必ず実行される
            // dbを開いたら確実にclose
            dressdb.close();
        }

        switch(select){
            case "dress1":
                findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                dressView.setImageResource(R.drawable.dress1);
                break;
            case "dress2":
                findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                dressView.setImageResource(R.drawable.dress2);
                break;
            case "dress3":
                findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                dressView.setImageResource(R.drawable.dress3);
                break;
            case "dress4":
                findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                dressView.setImageResource(R.drawable.dress4);
                break;
            case "dress5":
                findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                dressView.setImageResource(R.drawable.dress5);
                break;
            case "dress6":
                findViewById(R.id.dressView).setVisibility(View.VISIBLE);
                dressView.setImageResource(R.drawable.dress6);
                break;
        }

         mel= findViewById(R.id.imageView);

        // データベースを取得する
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            // rawQueryというSELECT専用メソッドを使用してデータを取得する
            Cursor c = db.rawQuery("select * from NYANKO_TABLE where date = '"+nowdate+"'", null);

            // Cursorの先頭行があるかどうか確認
            boolean next = c.moveToFirst();


            // 取得した全ての行を取得
            while (next) {
                // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                dispEmo = c.getString(2); // 感情を取得
                dispGoal = c.getString(3); // 目標を取得
                dispClear = String.valueOf(c.getInt(4)); // 目標達成を取得
                // 次の行が存在するか確認
                next = c.moveToNext();
                //フラグを変更
                newFlag = false;
            }
            try {
                //感情ボタン表示
                if (dispEmo.equals("わくわく")) {
                    wk=1;
                    mt.setText("わくわく！君がわくわくだとめるもうれしいにゃあ");
                    //表情差分
                    mel.setImageResource(R.drawable.wkcat);
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wk);
                    irirButton.setImageResource(R.drawable.irir);
                    ututButton.setImageResource(R.drawable.utut);
                    dkdkButton.setImageResource(R.drawable.dkdk);

                } else if (dispEmo.equals("いらいら")) {
                    ir=1;
                    mt.setText("ぷんぷん！\nめるも少しおこなのにゃ！！");
                    //表情差分
                    mel.setImageResource(R.drawable.ircat);
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.ir);
                    ututButton.setImageResource(R.drawable.utut);
                    dkdkButton.setImageResource(R.drawable.dkdk);

                } else if (dispEmo.equals("しくしく")) {
                    sk=1;
                    mt.setText("そっかぁ...。めるがよしよししてあげるにゃ～！いいこいいこ～♪");
                    //表情差分
                    mel.setImageResource(R.drawable.skcat);
                    skskButton.setImageResource(R.drawable.sk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.irir);
                    ututButton.setImageResource(R.drawable.utut);
                    dkdkButton.setImageResource(R.drawable.dkdk);

                } else if (dispEmo.equals("うとうと")) {
                    ut=1;
                    mt.setText("お疲れさまにゃ～\nめると一緒におやすみするにゃ～むにゃあ.｡o〇");
                    //表情差分
                    mel.setImageResource(R.drawable.utcat);
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.irir);
                    ututButton.setImageResource(R.drawable.ut);
                    dkdkButton.setImageResource(R.drawable.dkdk);
                } else if (dispEmo.equals("どきどき")) {
                    dk=1;
                    mt.setText("はにゃにゃにゃっっ！！！");
                    //表情差分
                    mel.setImageResource(R.drawable.dkcat);
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.irir);
                    ututButton.setImageResource(R.drawable.utut);
                    dkdkButton.setImageResource(R.drawable.dk);
                }
                //目標
                if(!dispGoal.equals("")){
                    goalText.setText(dispGoal);
                }
                //目標達成
                if(dispClear.equals("1")){
                    //クリアしているとき
                    clearButton.setImageResource(R.drawable.clearbtn_push);
                    cl=1;
                }else if(dispClear.equals("0")){
                    //未達成のとき
                    clearButton.setImageResource(R.drawable.clearbtn);
                    cl=0;
                }
            }catch(NullPointerException e){

            }


        } finally {
            // finallyは、tryの中で例外が発生した時でも必ず実行される
            // dbを開いたら確実にclose
            db.close();
        }


        // わくわくclick
        wkwkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                //指定書式に変換して表示
                TextView mt = (TextView) findViewById(R.id.message);
                // 入力内容を取得する
                emo = "わくわく";
                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();

                //ボタンの画像変更
                if(wk == 1){
                    //選択→未選択
                    wk = 0;
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    //表情差分
                    mel.setImageResource(R.drawable.sample);
                    //メッセージ表示
                    mt.setText("わくわくおわちゃったにゃ...？\n");

                    try {
                        // 空白
                        db.execSQL("update NYANKO_TABLE set emo = '' where date = '"+nowdate+"'");
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }

                }else{
                    //未選択→選択
                    sk = 0;
                    wk = 1;
                    ir = 0;
                    ut = 0;
                    dk = 0;
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wk);
                    irirButton.setImageResource(R.drawable.irir);
                    ututButton.setImageResource(R.drawable.utut);
                    dkdkButton.setImageResource(R.drawable.dkdk);
                    //表情差分
                    mel.setImageResource(R.drawable.wkcat);
                    //メッセージ表示
                    mt.setText("わくわく！君がわくわくだとめるもうれしいにゃあ");
                    try {
                        if(newFlag==false){
                            //編集の場合
                            // UPDATE
                            db.execSQL("update NYANKO_TABLE set emo = '"+ emo +"' where date = '"+nowdate+"'");
                        }else {
                            // 新規作成の場合

                            // INSERT
                            db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('"+ nowdate +"','"+ "" +"','" + emo + "','"+ "" +"','')");
                        }

                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }



            }
        });

        // いらいらclick
        irirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                //指定書式に変換して表示
                TextView mt = (TextView) findViewById(R.id.message);
                // 入力内容を取得する
                emo = "いらいら";
                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();

                //ボタンの画像変更
                if(ir == 1){
                    //選択→未選択
                    ir = 0;
                    irirButton.setImageResource(R.drawable.irir);
                    //メッセージ表示
                    mt.setText("いらいらはもう大丈夫にゃ？\n甘いものを食べて一緒にしあわせになるにゃ！！");
                    //表情差分
                    mel.setImageResource(R.drawable.sample);
                    try {
                        // 空白
                        db.execSQL("update NYANKO_TABLE set emo = '' where date = '"+nowdate+"'");
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }else{
                    //未選択→選択
                    sk = 0;
                    wk = 0;
                    ir = 1;
                    ut = 0;
                    dk = 0;
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.ir);
                    ututButton.setImageResource(R.drawable.utut);
                    dkdkButton.setImageResource(R.drawable.dkdk);
                    //メッセージ表示
                    mt.setText("ぷんぷん！\nめるも少しおこなのにゃ！！");
                    //表情差分
                    mel.setImageResource(R.drawable.ircat);
                    try {
                        if(newFlag==false){
                            //編集の場合
                            // UPDATE
                            db.execSQL("update NYANKO_TABLE set emo = '"+ emo +"' where date = '"+nowdate+"'");
                        }else {
                            // 新規作成の場合

                            // INSERT
                            db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('"+ nowdate +"','"+ "" +"','" + emo + "','"+ "" +"','')");
                        }
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }



            }
        });

        // しくしくclick
        skskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                //指定書式に変換して表示
                TextView mt = (TextView) findViewById(R.id.message);
                // 入力内容を取得する
                emo = "しくしく";
                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();

                //ボタンの画像変更
                if(sk == 1){
                    //選択→未選択
                    sk = 0;
                    skskButton.setImageResource(R.drawable.sksk);
                    //メッセージ表示
                    mt.setText("少しでもお役に立ててたら\nうれしいにゃ！\nまたいつでもめるを頼ってにゃあ");
                    //表情差分
                    mel.setImageResource(R.drawable.sample);
                    try {
                        // 空白
                        db.execSQL("update NYANKO_TABLE set emo = '' where date = '"+nowdate+"'");
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }else{
                    //未選択→選択
                    sk = 1;
                    wk = 0;
                    ir = 0;
                    ut = 0;
                    dk = 0;
                    skskButton.setImageResource(R.drawable.sk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.irir);
                    ututButton.setImageResource(R.drawable.utut);
                    dkdkButton.setImageResource(R.drawable.dkdk);
                    //メッセージ表示
                    mt.setText("そっかぁ...。めるがよしよししてあげるにゃ～！いいこいいこ～♪");
                    //表情差分
                    mel.setImageResource(R.drawable.skcat);
                    try {
                        if(newFlag==false){
                            //編集の場合
                            // UPDATE
                            db.execSQL("update NYANKO_TABLE set emo = '"+ emo +"' where date = '"+nowdate+"'");
                        }else {
                            // 新規作成の場合

                            // INSERT
                            db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('"+ nowdate +"','"+ "" +"','" + emo + "','"+ "" +"','')");
                        }


                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }
            }
        });

        // うとうとclick
        ututButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                //指定書式に変換して表示
                TextView mt = (TextView) findViewById(R.id.message);
                // 入力内容を取得する
                emo = "うとうと";
                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();

                //ボタンの画像変更
                if(ut == 1){
                    //選択→未選択
                    ut = 0;
                    ututButton.setImageResource(R.drawable.utut);
                    //メッセージ表示
                    mt.setText("おはようにゃ～！\nもう目は覚めたかにゃ？");
                    //表情差分
                    mel.setImageResource(R.drawable.sample);
                    try {
                        // 空白
                        db.execSQL("update NYANKO_TABLE set emo = '' where date = '"+nowdate+"'");
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }else{
                    //未選択→選択
                    sk = 0;
                    wk = 0;
                    ir = 0;
                    ut = 1;
                    dk = 0;
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.irir);
                    ututButton.setImageResource(R.drawable.ut);
                    dkdkButton.setImageResource(R.drawable.dkdk);
                    //メッセージ表示
                    mt.setText("お疲れさまにゃ～\nめると一緒におやすみするにゃ～むにゃあ.｡o〇");
                    //表情差分
                    mel.setImageResource(R.drawable.utcat);
                    try {
                        if(newFlag==false){
                            //編集の場合
                            // UPDATE
                            db.execSQL("update NYANKO_TABLE set emo = '"+ emo +"' where date = '"+nowdate+"'");
                        }else {
                            // 新規作成の場合

                            // INSERT
                            db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('"+ nowdate +"','"+ "" +"','" + emo + "','"+ "" +"','')");
                        }


                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }
            }
        });

        // どきどきclick
        dkdkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                //指定書式に変換して表示
                TextView mt = (TextView) findViewById(R.id.message);
                // 入力内容を取得する
                emo = "どきどき";
                // DBに保存
                SQLiteDatabase db = helper.getWritableDatabase();

                //ボタンの画像変更
                if(dk == 1){
                    //選択→未選択
                    dk = 0;
                    dkdkButton.setImageResource(R.drawable.dkdk);
                    //メッセージ表示
                    mt.setText("もう心は落ち着いたかにゃ？？");
                    //表情差分
                    mel.setImageResource(R.drawable.sample);
                    try {
                        // 空白
                        db.execSQL("update NYANKO_TABLE set emo = '' where date = '"+nowdate+"'");
                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }else{
                    //未選択→選択
                    sk = 0;
                    wk = 0;
                    ir = 0;
                    ut = 0;
                    dk = 1;
                    skskButton.setImageResource(R.drawable.sksk);
                    wkwkButton.setImageResource(R.drawable.wkwk);
                    irirButton.setImageResource(R.drawable.irir);
                    ututButton.setImageResource(R.drawable.utut);
                    dkdkButton.setImageResource(R.drawable.dk);
                    //メッセージ表示
                    mt.setText("はにゃにゃにゃっっ！！！");
                    //表情差分
                    mel.setImageResource(R.drawable.dkcat);
                    try {
                        if(newFlag==false){
                            //編集の場合
                            // UPDATE
                            db.execSQL("update NYANKO_TABLE set emo = '"+ emo +"' where date = '"+nowdate+"'");
                        }else {
                            // 新規作成の場合

                            // INSERT
                            db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('"+ nowdate +"','"+ "" +"','" + emo + "','"+ "" +"','')");
                        }


                    } finally {
                        // finallyは、tryの中で例外が発生した時でも必ず実行される
                        // dbを開いたら確実にclose
                        db.close();
                    }
                }
            }
        });




      /*  try {
            // rawQueryというSELECT専用メソッドを使用してデータを取得する
            Cursor c = dressdb.rawQuery("select * from DRESS_TABLE where date = '"+nowdate+"'", null);

            // Cursorの先頭行があるかどうか確認
            boolean next = c.moveToFirst();


            // 取得した全ての行を取得
            while (next) {
                // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                dispEmo = c.getString(2); // 感情を取得
                dispGoal = c.getString(3); // 目標を取得
                dispClear = String.valueOf(c.getInt(4)); // 目標達成を取得
                // 次の行が存在するか確認
                next = c.moveToNext();
                //フラグを変更
                newFlag = false;
            }
            try {
                //衣装変更

            }catch(NullPointerException e){

            }


        } finally {
            // finallyは、tryの中で例外が発生した時でも必ず実行される
            // dbを開いたら確実にclose
            dressdb.close();
        }*/

    }



    /**
     * EditText編集時に背景をタップしたらキーボードを閉じる,目標を保存するタッチイベントの処理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //フォーカスを背景に移す
        mainLayout.requestFocus();

                // goalTextViewに目標を表示
                String text = goalText.getText().toString();  //目標取得
                if(!text.equals("")) {
                    goalText.setText(text); //目標をセット
                }
                // データベースを取得する
                SQLiteDatabase db = helper.getWritableDatabase();

                try {
                    // rawQueryというSELECT専用メソッドを使用してデータを取得する
                    Cursor c = db.rawQuery("select * from NYANKO_TABLE where date = '"+nowdate+"'", null);

                    // Cursorの先頭行があるかどうか確認
                    boolean next = c.moveToFirst();

                    // 取得した全ての行を取得
                    while (next) {
                        // 取得したカラムの順番(0から始まる)と型を指定してデータを取得する
                        dispGoal = c.getString(3); // 目標を取得
                        // 次の行が存在するか確認
                        next = c.moveToNext();
                        //フラグを変更
                        newFlag = false;
                    }
                    if(newFlag==false){
                        //編集の場合
                        try {
                            if(dispGoal.equals(text)){

                            }else {
                                //目標がリセット
                                if (text != "") {
                                    // UPDATE
                                    db.execSQL("update NYANKO_TABLE set goal = '" + text + "' where date = '" + nowdate + "'");
                                    db.execSQL("update NYANKO_TABLE set clear = '0' where date = '" + nowdate + "'");
                                    cl = 0;
                                    final ImageButton clearButton = findViewById(R.id.clearBtn);
                                    clearButton.setImageResource(R.drawable.clearbtn);
                                } else {
                                    //UPDATE
                                    db.execSQL("update NYANKO_TABLE set goal = '" + "" + "' where date = '" + nowdate + "'");
                                    db.execSQL("update NYANKO_TABLE set clear = '" + 0 + "' where date = '" + nowdate + "'");
                                }
                            }
                        }catch(NullPointerException e){

                        }
                    }else{
                        // 新規作成の場合
                        // INSERT
                        db.execSQL("insert into NYANKO_TABLE(date,diary,emo,goal,clear) VALUES('"+ nowdate +"','"+ "" +"','" + "" + "','"+ text +"','')");
                    }
                } finally {
                    // finallyは、tryの中で例外が発生した時でも必ず実行される
                    // dbを開いたら確実にclose
                    db.close();
                }
                return false;


    }

}
