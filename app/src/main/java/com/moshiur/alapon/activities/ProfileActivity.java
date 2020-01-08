package com.moshiur.alapon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.moshiur.alapon.R;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ConstraintLayout profileLayout, logoutLayout;
    private TextView logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //set people_toolbar
        setToolbar();
        toolbarButtonHandler();

        initializeUI();

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logout current user
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

    }

    private void initializeUI() {
        profileLayout = findViewById(R.id.profile_layout);
        logoutLayout = findViewById(R.id.logout_layout);

        logoutButton = logoutLayout.findViewById(R.id.logoutButton);

    }

    private void toolbarButtonHandler() {
        //set chats_toolbar backImage listener
        ImageView backButton = toolbar.findViewById(R.id.backButtonOnProfileToolbar);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        //remove app name
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
    }
}
