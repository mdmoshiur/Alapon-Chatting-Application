package com.moshiur.alapon.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moshiur.alapon.R;
import com.moshiur.alapon.fragments.ChatsFragment;
import com.moshiur.alapon.fragments.PeopleFragment;
import com.moshiur.alapon.models.UserDataModel;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivityAlapon";

    private BottomNavigationView bottomNavigationView;

    private UserDataModel currentUser;
    //connect to bottom nav menu
    private BottomNavigationView.OnNavigationItemSelectedListener bottom_navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_chats:
                            selectedFragment = ChatsFragment.newInstance(currentUser);
                            break;
                        case R.id.navigation_people:
                            selectedFragment = PeopleFragment.newInstance(currentUser);
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

        //receive intent data
        currentUser = getIntent().getParcelableExtra("userDataModel");

        Log.d("Parcelable", "onCreateView: " + currentUser.getUserName() + currentUser.getUserEmailOrPhoneNumber() + currentUser.getUserProfilePhotoURL());

        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        setBottomNav(savedInstanceState);

    }

    private void setBottomNav(Bundle savedInstanceState) {
        //bottom nav view listener
        bottomNavigationView.setOnNavigationItemSelectedListener(bottom_navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            ChatsFragment chatsFragment = ChatsFragment.newInstance(currentUser);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, chatsFragment).commit();
        }
    }



}
