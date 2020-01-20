package com.moshiur.alapon.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moshiur.alapon.R;
import com.moshiur.alapon.activities.ConversationActivity;
import com.moshiur.alapon.activities.ProfileActivity;
import com.moshiur.alapon.adapters.PeopleRecyclerViewAdapter;
import com.moshiur.alapon.interfaces.MyOnItemClickListener;
import com.moshiur.alapon.models.PeopleDataModel;
import com.moshiur.alapon.models.UserDataModel;
import com.moshiur.alapon.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment {

    private static final String TAG = "PeopleFragment";

    private View peopleFragment;
    private Toolbar toolbar;
    private static final String USER_DATA_MODEL = "userDataModel";

    private Button allButton, activeButton;

    List<PeopleDataModel> mPeopleDataModel = new ArrayList<>();

    private RecyclerView peopleRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PeopleRecyclerViewAdapter peopleRecyclerViewAdapter;
    private ImageView profileImage;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private UserDataModel currentUser = new UserDataModel();

    public static PeopleFragment newInstance(UserDataModel userDataModel) {

        PeopleFragment mPeopleFragment = new PeopleFragment();

        Bundle args = new Bundle();
        args.putParcelable(USER_DATA_MODEL, userDataModel);
        mPeopleFragment.setArguments(args);

        return mPeopleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        peopleFragment = inflater.inflate(R.layout.fragment_people, container, false);

        //get fragment data
        if (getArguments() != null) {
            currentUser = getArguments().getParcelable(USER_DATA_MODEL);
        }
        //for menu
        setHasOptionsMenu(true);
        setToolbar(); //set peoples_toolbar

        handleToolbarMenu();
        handleButtons();
        //recyclerViewDataPreparation();
        setRecyclerView();
        setRecyclerViewOnItemClickListener();

        return peopleFragment;
    }


    private void handleButtons() {
        allButton = peopleFragment.findViewById(R.id.all_button);
        activeButton = peopleFragment.findViewById(R.id.active_button);
        allButton.setSelected(true);
        allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actions here

                //change button color
                activeButton.setSelected(false);
                allButton.setSelected(true);
            }
        });
        activeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //actions here

                //change button color
                activeButton.setSelected(true);
                allButton.setSelected(false);
            }
        });

    }

    private void setRecyclerViewOnItemClickListener() {
        peopleRecyclerViewAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                Intent intent = new Intent(getContext(), ConversationActivity.class);
                intent.putExtra("userID", mPeopleDataModel.get(position).getUserID());
                intent.putExtra("userName", mPeopleDataModel.get(position).getName());
                intent.putExtra("userProfileImageURL", mPeopleDataModel.get(position).getImageURL());
                startActivity(intent);
            }

            @Override
            public void OnItemLongClickListener(int position) {
                Toast.makeText(getContext(), "position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerView() {
        //find recyclerView
        peopleRecyclerView = peopleFragment.findViewById(R.id.people_recycler_view);
        peopleRecyclerView.setHasFixedSize(true);

        //use a linear layout manger
        layoutManager = new LinearLayoutManager(getContext());
        peopleRecyclerView.setLayoutManager(layoutManager);

        //set adapter
        peopleRecyclerViewAdapter = new PeopleRecyclerViewAdapter(getContext(), mPeopleDataModel);
        peopleRecyclerView.setAdapter(peopleRecyclerViewAdapter);
        //add item decoration
        peopleRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(45));
    }

    private void recyclerViewDataPreparation() {
        //data prep
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                if (dataSnapshot.exists()) {
                    UserDataModel user = dataSnapshot.getValue(UserDataModel.class);
                    assert user != null;
                    //Log.d(TAG, "onDataChange:  userName"+user.getUserName());
                    if (!currentUser.getUserID().equals(user.getUserID())) {
                        mPeopleDataModel.add(new PeopleDataModel(user.getUserID(), user.getUserName(), user.getUserProfilePhotoURL()));
                    }
                }

                peopleRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void handleToolbarMenu() {
        profileImage = toolbar.findViewById(R.id.toolbar_image);

        if (currentUser.getUserProfilePhotoURL().equals("default")) {
            profileImage.setImageResource(R.drawable.profile_icon);
        } else {
            Glide.with(PeopleFragment.this).load(currentUser.getUserProfilePhotoURL()).into(profileImage);
        }

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("userDataModel", currentUser);
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        toolbar = peopleFragment.findViewById(R.id.people_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //remove appname
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.people_fragment_options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPeopleDataModel.clear();
        recyclerViewDataPreparation();
    }
}