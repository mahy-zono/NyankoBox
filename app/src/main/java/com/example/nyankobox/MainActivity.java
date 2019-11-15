package com.example.nyankobox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //現在日時の取得
        Date d = new Date();

        //書式の作成
        SimpleDateFormat sdf = new SimpleDateFormat("MM月 dd日 (E) ");

        //指定書式に変換して表示
        TextView dt = (TextView)findViewById(R.id.dateText);

        dt.setText(sdf.format(d));

        //日記記入画面に遷移
        ImageButton diarysend = findViewById(R.id.diaryBtn);
        diarysend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DiaryActivity.class);
                startActivity(intent);
            }
        });

        //ログ画面に遷移
        ImageButton logsend = findViewById(R.id.logBtn);
        logsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LogActivity.class);
                startActivity(intent);
            }
        });

        //着せ替え画面に遷移
        ImageButton dresssend = findViewById(R.id.dressBtn);
        dresssend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DressActivity.class);
                startActivity(intent);
            }
        });




/*        Calendar cal = Calendar.getInstance();       //カレンダーを取得

        int iMonth = cal.get(Calendar.MONTH) + 1;       //月を取得
        int iDate = cal.get(Calendar.DATE);         //日を取得
        int week = cal.get(Calendar.DAY_OF_WEEK);      //曜日を取得

        String weekString = "";
        switch(week) {
            case Calendar.MONDAY:
                weekString = "月";
                break;
            case Calendar.TUESDAY:
                weekString = "火";
                break;
            case Calendar.WEDNESDAY:
                weekString = "水";
                break;
            case Calendar.THURSDAY:
                weekString = "木";
                break;
            case Calendar.FRIDAY:
                weekString = "金";
                break;
            case Calendar.SATURDAY:
                weekString = "土";
                break;
            case Calendar.SUNDAY:
                weekString = "日";
                break;
        }


        String strDay = iMonth + "月" + iDate + "日" + "(" + weekString + ")";  //日付を表示形式で設定

        TextView dt = (TextView)findViewById(R.id.dateText);

        dt.setText(strDay);
*/
    }

}
