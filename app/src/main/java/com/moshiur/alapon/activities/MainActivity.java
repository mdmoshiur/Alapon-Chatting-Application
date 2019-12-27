package com.moshiur.alapon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moshiur.alapon.R;
import com.moshiur.alapon.fragments.ChatsFragment;
import com.moshiur.alapon.fragments.NotificationsFragment;
import com.moshiur.alapon.fragments.PeopleFragment;


public class MainActivity extends AppCompatActivity {

    private ImageView profileImage;
    private Button loginButton, signupButton;
    //connect to bottom nav menu
    private BottomNavigationView.OnNavigationItemSelectedListener bottom_navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_chats:
                            selectedFragment = new ChatsFragment();
                            break;
                        case R.id.navigation_people:
                            selectedFragment = new PeopleFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new NotificationsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //login button handler
        ConstraintLayout veryFastLayout = findViewById(R.id.very_first_layout);
        loginButton = veryFastLayout.findViewById(R.id.login_button_very_first_layout);
        signupButton = veryFastLayout.findViewById(R.id.signup_button_very_first_layout);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        //bottom nav view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottom_navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                    new ChatsFragment()).commit();
        }


    }


}
