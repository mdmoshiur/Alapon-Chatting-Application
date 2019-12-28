package com.moshiur.alapon.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.moshiur.alapon.R;

public class ConversationActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        setToolbar();
        handleToolbarMenuItem();


    }

    private void handleToolbarMenuItem() {
        ImageView backArrow = toolbar.findViewById(R.id.back_arrow_conversation_toolbar);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setToolbar() {
        //set people_toolbar
        toolbar = findViewById(R.id.conversation_toolbar);
        setSupportActionBar(toolbar);
        //remove app name
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.conversation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
