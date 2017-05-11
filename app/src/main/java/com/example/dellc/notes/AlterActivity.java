package com.example.dellc.notes;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dellc on 2017/5/11.
 */

public class AlterActivity extends AppCompatActivity {
    private EditText editTitleAlter,editContentAlter;
    private FloatingActionButton fabSaveAlter;
    private String str3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter);
        initViews();
        click();
        initData();
    }

    private void initData() {
    }
    /*把主页面的数据传过来*/
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventBackground(String[] a) {
        editTitleAlter.setText(a[0]);
        editContentAlter.setText(a[1]);
        str3=a[2];
    }

    private void initViews() {
        editTitleAlter= (EditText) findViewById(R.id.editTitleAlter);
        editContentAlter= (EditText) findViewById(R.id.editContentAlter);
        fabSaveAlter= (FloatingActionButton) findViewById(R.id.fabSaveAlter);
        EventBus.getDefault().register(this);
    }

    private void click() {

        fabSaveAlter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);
                Note note=new Note();
                note.setDate(str);
                note.setTitle(editTitleAlter.getText().toString().trim());
                note.setContent(editContentAlter.getText().toString().trim());
                note.updateAll("date = ? ", str3);
                finish();

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解注册
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(AlterActivity.this);
    }
}
