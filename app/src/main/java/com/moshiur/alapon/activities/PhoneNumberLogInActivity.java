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

public class PhoneNumberLogInActivity extends AppCompatActivity {

    private static final String TAG = "PhoneNumberLogIn";

    private Toolbar toolbar;
    private EditText name, phone;
    private Button logInButton;

    private String user_name, phone_no, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_login);

        setToolbar();
        toolbarButtonHandler();

        initializeUI();


        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processEditTextInput();
                Log.d(TAG, "onClick: " + user_name + phone_no + pass);
                if (user_name.isEmpty()) {
                    errorDialog("Name field is empty");
                    return;
                } else if (user_name.length() < 3) {
                    errorDialog("Name contains minimum 3 letters");
                    return;
                } else if (phone_no.isEmpty()) {
                    errorDialog("Phone number is empty");
                    return;
                } else if (phone_no.length() < 14) {
                    errorDialog("Phone number is not valid!!\nplease try with country code");
                    return;
                }

                createConfirmDialog();
            }
        });


    }


    private void processEditTextInput() {
        user_name = name.getText().toString().trim();
        phone_no = phone.getText().toString().trim();
    }

    private void createConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PhoneNumberLogInActivity.this);
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
                Intent intent = new Intent(PhoneNumberLogInActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("user_name", user_name);
                intent.putExtra("phone_number", phone_no);
                startActivity(intent);
            }
        });
    }

    private void errorDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PhoneNumberLogInActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.error_message_dialog_layout, null);
        TextView errorMessageTextView = mView.findViewById(R.id.error_message);
        errorMessageTextView.setText(errorMessage);
        Button okButton = mView.findViewById(R.id.error_dialog_ok_button);

        builder.setView(mView);
        final AlertDialog myErrorDialog = builder.create();
        myErrorDialog.show();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myErrorDialog.cancel();
            }
        });

    }

    private void initializeUI() {
        name = findViewById(R.id.singnup_name_editText);
        phone = findViewById(R.id.login_phone_editText);

        logInButton = findViewById(R.id.signUpButton);
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
