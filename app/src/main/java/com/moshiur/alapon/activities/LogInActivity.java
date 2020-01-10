package com.moshiur.alapon.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moshiur.alapon.R;
import com.moshiur.alapon.models.UserDataModel;

public class LogInActivity extends AppCompatActivity {

    private static final String TAG = "LogInActivity";

    private EditText phone_number_edittext, password_edit;
    private TextView fotgotPassword;
    private Button loginButton;

    private String phone_number, password;
    ValueEventListener mValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            long count = dataSnapshot.getChildrenCount();
            Log.d(TAG, "onDataChange: count=" + count);
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserDataModel user = snapshot.getValue(UserDataModel.class);
                    Log.d(TAG, "onDataChange: pass" + user.getUserPassword() + user.getUserPhoneNumber());
                    Log.d(TAG, "onDataChange: input pass phone " + password + phone_number);
                    if (password.equals(user.getUserPassword())) {
                        //login successful
                        Log.d(TAG, "onDataChange: login successful");

                    } else {
                        Log.d(TAG, "onDataChange: password not match");
                    }
                }

            } else {
                Log.d(TAG, "onDataChange: this number is not signed up");
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.d(TAG, "onCancelled: login failed");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        setToolbar();

        initializeUI();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        if (validateInput()) {
            runQuery();
        }
    }

    private void runQuery() {
        Query targetUser = FirebaseDatabase.getInstance().getReference("users")
                .orderByChild("userPhoneNumber")
                .equalTo(phone_number);
        targetUser.addListenerForSingleValueEvent(mValueEventListener);
    }

    private boolean validateInput() {
        phone_number = phone_number_edittext.getText().toString().trim();
        password = password_edit.getText().toString().trim();

        if (phone_number.isEmpty() || phone_number.length() < 14) {
            phone_number_edittext.setError("valid phone number is required!");
            phone_number_edittext.setFocusable(true);
            phone_number_edittext.requestFocus();
            return false;
        } else if (password.isEmpty() || password.length() < 6) {
            password_edit.setError("valid password is required");
            password_edit.setFocusable(true);
            password_edit.requestFocus();
            return false;
        } else
            return true;

    }

    private void initializeUI() {
        phone_number_edittext = findViewById(R.id.login_phone_editText);
        password_edit = findViewById(R.id.login_password_editText);
        fotgotPassword = findViewById(R.id.forgot_password);
        loginButton = findViewById(R.id.logInButton);

    }

    private void setToolbar() {
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
