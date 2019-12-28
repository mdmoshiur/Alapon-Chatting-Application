package com.moshiur.alapon.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.moshiur.alapon.R;

public class ConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        //set people_toolbar
        Toolbar toolbar = findViewById(R.id.conversation_toolbar);
        setSupportActionBar(toolbar);
        //remove app name
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");


    }
}
