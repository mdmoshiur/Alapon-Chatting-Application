package com.moshiur.alapon.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.moshiur.alapon.R;
import com.moshiur.alapon.activities.ProfileActivity;

public class PeopleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View peopleFragment = inflater.inflate(R.layout.fragment_people, container, false);

        //set chats_toolbar
        Toolbar toolbar = peopleFragment.findViewById(R.id.people_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //remove appname
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        //add onclick listener to profile image
        ImageView profileImage = toolbar.findViewById(R.id.toolbar_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });

        return peopleFragment;
    }
}