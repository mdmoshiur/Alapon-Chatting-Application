package com.moshiur.alapon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moshiur.alapon.R;
import com.moshiur.alapon.models.UserDataModel;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    private UserDataModel currentUser = new UserDataModel();

    private ConstraintLayout profileLayout, logoutLayout;
    private TextView logoutButton;
    private TextView nameView, aboutView, emailorPhoneView;
    private ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //get intent data
        getIntentData();
        //set people_toolbar
        setToolbar();
        toolbarButtonHandler();

        initializeUI();

        initializeVAR();

        logOutUser();

        setProfile();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        currentUser = intent.getParcelableExtra("userDataModel");
    }


    private void setProfile() {
        nameView.setText(currentUser.getUserName());
        emailorPhoneView.setText(currentUser.getUserEmailOrPhoneNumber());
        if (currentUser.getUserProfilePhotoURL().equals("default")) {
            profileImageView.setImageResource(R.drawable.profile_icon);
        } else {
            Glide.with(ProfileActivity.this).load(currentUser.getUserProfilePhotoURL()).into(profileImageView);
        }
    }

    private void initializeVAR() {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    private void initializeUI() {
        profileLayout = findViewById(R.id.profile_layout);
        logoutLayout = findViewById(R.id.logout_layout);

        profileImageView = profileLayout.findViewById(R.id.profile_image);
        nameView = profileLayout.findViewById(R.id.name);
        aboutView = profileLayout.findViewById(R.id.about);
        emailorPhoneView = profileLayout.findViewById(R.id.email_or_phone);

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

    private void setGoogleSignInClient() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // call sign in function
        mGoogleSignInClient = GoogleSignIn.getClient(ProfileActivity.this, gso);
    }

    private void logOutUser() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //logout current user
                mAuth.signOut();
                //check facebook logged in or not
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }
                //check google logged in or not
                if (GoogleSignIn.getLastSignedInAccount(ProfileActivity.this) != null) {
                    //google sign in client
                    setGoogleSignInClient();
                    mGoogleSignInClient.signOut();
                }

                startActivity(new Intent(ProfileActivity.this, LandingActivity.class));
            }
        });
    }
}
