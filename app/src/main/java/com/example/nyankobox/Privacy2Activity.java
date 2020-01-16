package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Privacy2Activity extends AppCompatActivity {
    // Sound
    private SoundPlayer soundPlayer;
    // MemoOpenHelperクラスを定義
    dbData helper = null;
    //データ
    int lock;

    //新規フラグ
    boolean newFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy2);

        //Toastインフレータ
        LayoutInflater inflater  =getLayoutInflater();

        //カスタムToast用のViewを取得する
        final View layout = inflater.inflate(R.layout.costom_toast, null);

        //サウンド
        soundPlayer = new SoundPlayer(this);
        //カスタムフォント
        final Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/nikumaru.ttf");

        TextView set = findViewById(R.id.textView);
        set.setTypeface(customFont);

        Button passBtn = (Button)findViewById(R.id.passBtn);
        passBtn.setTypeface(customFont);

        Button passDeleteBtn = (Button)findViewById(R.id.passDeleteBtn);
        passDeleteBtn.setTypeface(customFont);


        //パスコード登録画面に遷移
        Button passlocksend = findViewById(R.id.passBtn);
        passlocksend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), PassLockActivity.class);
                startActivity(intent);
            }
        });

        //パスコード変更画面に遷移
/*        Button passchengsend = findViewById(R.id.passChangeBtn);
        passchengsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                Intent intent = new Intent(getApplication(), PassChangeActivity.class);
                startActivity(intent);
            }
        });
*/
        //パスコード削除画面に遷移
        Button passdeletesend = findViewById(R.id.passDeleteBtn);
        passdeletesend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Sound
                soundPlayer.pompom();
                // データベースから値を取得する
                if(helper == null){
                    helper = new dbData(Privacy2Activity.this);
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
                        lock = c.getInt(4); // パスロックの有無を取得
                        // 次の行が存在するか確認
                        next = c.moveToNext();
                        //新規フラグ
                        newFlag = false;
                    }
                    //テキスト
                    Context context = getApplicationContext();

                    if(newFlag==true){
                        // 新規作成の場合
                        TextView toastText = layout.findViewById(R.id.toastText);
                        toastText.setText("パスコードが設定されていません。");
                        toastText.setTypeface(customFont);
                        Toast toast = new Toast(context);
                        toast.setView(layout);
                        toast.show();
                        //Toast.makeText(context, "パスコードが設定されていません。", Toast.LENGTH_LONG).show();
                    }else if(newFlag==false) {
                        try {
                            if(lock==1) {
                                db.execSQL("update PROFILE_TABLE set lock = '0' where id = '1'");
                                db.execSQL("update PROFILE_TABLE set pass = '' where id = '1'");
                                TextView toastText = layout.findViewById(R.id.toastText);
                                toastText.setText("パスコードが削除されました。");
                                toastText.setTypeface(customFont);
                                Toast toast = new Toast(context);
                                toast.setView(layout);
                                toast.show();
                               // Toast.makeText(context, "パスコードが削除されました。", Toast.LENGTH_LONG).show();
                            }else if(lock == 0){
                                db.execSQL("update PROFILE_TABLE set lock = '0' where id = '1'");
                                db.execSQL("update PROFILE_TABLE set pass = '' where id = '1'");
                                TextView toastText = layout.findViewById(R.id.toastText);
                                toastText.setText("パスコードが設定されていません。");
                                toastText.setTypeface(customFont);
                                Toast toast = new Toast(context);
                                toast.setView(layout);
                                toast.show();
                                //Toast.makeText(context, "パスコードが設定されていません。", Toast.LENGTH_LONG).show();
                            }
                        }catch (NullPointerException e){

                        }
                    }

                } finally {
                    // finallyは、tryの中で例外が発生した時でも必ず実行される
                    // dbを開いたら確実にclose
                    db.close();
                }
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
