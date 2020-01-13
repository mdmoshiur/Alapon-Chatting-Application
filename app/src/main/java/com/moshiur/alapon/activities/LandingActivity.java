package com.moshiur.alapon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moshiur.alapon.R;
import com.moshiur.alapon.models.UserDataModel;
import com.moshiur.alapon.network.FirebaseHelper;

public class LandingActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityAlapon";
    private static final int RC_SIGN_IN = 1001;
    //layout
    private ConstraintLayout loginLayout;
    private ProgressBar progressBar;
    private Group group;

    private ImageView googleLoginButton, facebookLoginButton, phoneNumberLoginButton;
    private LoginButton mFacebookLogInButton;

    //firebase variable
    private FirebaseAuth mAuth;
    //for facebook login
    private CallbackManager mCallbackManager;

    //user data model
    private UserDataModel currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        mAuth = FirebaseAuth.getInstance();

        initializeUI();
        //login button handler
        logInButtonHandler();
    }

    private void initializeUI() {
        loginLayout = findViewById(R.id.login_button_layout);

        googleLoginButton = loginLayout.findViewById(R.id.google_login_button);
        facebookLoginButton = loginLayout.findViewById(R.id.facebook_login_button);
        phoneNumberLoginButton = loginLayout.findViewById(R.id.phone_number_login_button);

        progressBar = findViewById(R.id.landing_progressbar);
        group = findViewById(R.id.group);

    }

    private void logInButtonHandler() {
        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogIn();
            }
        });

        phoneNumberLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandingActivity.this, PhoneNumberLogInActivity.class));
            }
        });

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleLogIn();
            }
        });
    }

    private void googleLogIn() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // call sign in function
        GoogleSignInClient mGoogleSignInClient;
        mGoogleSignInClient = GoogleSignIn.getClient(LandingActivity.this, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    // for google sign in
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        } else { // for facebook login
            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    //  for google sign in
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            FirebaseHelper firebaseHelper = new FirebaseHelper();
                            firebaseHelper.checkUserExistance(user);

                            //refresh activity
                            finish();
                            startActivity(getIntent());

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void facebookLogIn() {
        mCallbackManager = CallbackManager.Factory.create();

        //create virtual login button
        mFacebookLogInButton = new LoginButton(LandingActivity.this);
        mFacebookLogInButton.performClick();

        mFacebookLogInButton.setPermissions("email", "public_profile");
        mFacebookLogInButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // check user previously  logged in or not

                            FirebaseHelper firebaseHelper = new FirebaseHelper();
                            firebaseHelper.checkUserExistance(user);

                            //updateUI(user);
                            //refresh activity
                            finish();
                            startActivity(getIntent());

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LandingActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onStart() {
        if (mAuth.getCurrentUser() == null) {
            group.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            group.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        currentUser = dataSnapshot.getValue(UserDataModel.class);

                        //start main activity
                        Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                        intent.putExtra("userDataModel", currentUser);
                        startActivity(intent);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        super.onStart();
    }
}
