package com.moshiur.alapon.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.moshiur.alapon.R;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //set chats_toolbar
        Toolbar toolbar = findViewById(R.id.login_toolbar_id);
        setSupportActionBar(toolbar);
        //remove appname
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        //set chats_toolbar backImage listener
        ImageView backButton = toolbar.findViewById(R.id.backButtonOnProfileToolbar);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
