package com.example.dellc.notes;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dellc on 2017/5/11.
 */

public class WriteActivity extends AppCompatActivity {
    private EditText editTitle,editContent;
    private FloatingActionButton fabSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        initViews();
        click();
    }

    private void initViews() {
        editTitle= (EditText) findViewById(R.id.editTitle);
        editContent= (EditText) findViewById(R.id.editContent);
        fabSave= (FloatingActionButton) findViewById(R.id.fabSave);
    }

    private void click() {
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
                Date curDate = new Date(System.currentTimeMillis());
                String str = formatter.format(curDate);

                Note note=new Note();
                note.setTitle(editTitle.getText().toString().trim());
                note.setContent(editContent.getText().toString().trim());
                note.setDate(str);
                note.save();

                finish();
            }
        });
    }

}
