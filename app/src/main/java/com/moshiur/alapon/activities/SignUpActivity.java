package com.moshiur.alapon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.moshiur.alapon.R;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private Toolbar toolbar;
    private EditText name, phone, password;
    private Button signupButton;

    private String user_name, phone_no, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setToolbar();
        toolbarButtonHandler();

        initializeUI();


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processEditTextInput();
                Log.d(TAG, "onClick: " + user_name + phone_no + pass);
                if (user_name.isEmpty() || user_name.length() <= 3) {
                    name.setError("valid name is required!");
                    name.setFocusable(true);
                    name.requestFocus();
                    return;
                } else if (phone_no.isEmpty() || phone_no.length() < 11) {
                    phone.setError("phone number is not valid");
                    phone.setFocusable(true);
                    phone.requestFocus();
                    return;
                } else if (pass.isEmpty() || pass.length() < 6) {
                    password.setError("password 6 or more chars");
                    password.setFocusable(true);
                    password.requestFocus();
                    return;
                }
                createConfirmDialog();
            }
        });


    }

    private void processEditTextInput() {
        user_name = name.getText().toString().trim();
        phone_no = phone.getText().toString().trim();
        pass = password.getText().toString().trim();

    }

    private void createConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.verify_confirmation_alert_dialog, null);
        builder.setView(mView);
        final AlertDialog confirmDialog = builder.create();
        confirmDialog.show();

        //find text view
        TextView phone_no_text = mView.findViewById(R.id.verify_confirm_dialog_phone_number_id);
        phone_no_text.setText(phone_no);

        //find buttons
        TextView editButton = mView.findViewById(R.id.edit_button_confirm_dialog_id);
        TextView okButton = mView.findViewById(R.id.ok_button_confirm_dialog_id);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.cancel();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("user_name", user_name);
                intent.putExtra("phone_number", phone_no);
                intent.putExtra("password", pass);

                startActivity(intent);
            }
        });
    }

    private void initializeUI() {
        name = findViewById(R.id.singnup_name_editText);
        phone = findViewById(R.id.login_phone_editText);
        password = findViewById(R.id.signup_password_editText);

        signupButton = findViewById(R.id.signUpButton);
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
        //set sign up toolbar
        toolbar = findViewById(R.id.signup_toolbar_id);
        setSupportActionBar(toolbar);
        //remove appname
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
    }
}
