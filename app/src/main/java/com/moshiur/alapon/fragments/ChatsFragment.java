package com.moshiur.alapon.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moshiur.alapon.R;
import com.moshiur.alapon.activities.ConversationActivity;
import com.moshiur.alapon.activities.ProfileActivity;
import com.moshiur.alapon.adapters.ChatsRecyclerViewAdapter;
import com.moshiur.alapon.interfaces.MyOnItemClickListener;
import com.moshiur.alapon.models.LastMessageDataModel;
import com.moshiur.alapon.models.UserDataModel;
import com.moshiur.alapon.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    private static final String TAG = "ChatsFragment";

    private View chatsFragment;
    private Toolbar toolbar;

    //views
    private ImageView profileImage;


    ArrayList<LastMessageDataModel> mLastMessageDataModels = new ArrayList<>();
    private RecyclerView chatsRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ChatsRecyclerViewAdapter chatsRecyclerViewAdapter;

    private FirebaseUser currentFirebaseUser;
    private DatabaseReference mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatsFragment = inflater.inflate(R.layout.fragment_chats, container, false);

        //set chats_toolbar
        setToolbar();

        setCurrentUser();

        initializeUI();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            setChatsToolbarProfileImage();
        }

        toolbarButtonHandler();

        setChatsRecyclerView();

        chatsRecyclerViewAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                Intent intent = new Intent(getContext(), ConversationActivity.class);
                intent.putExtra("userID", "id");
                intent.putExtra("userName", "fake");
                intent.putExtra("userProfileImageURL", "default");
                startActivity(intent);
            }

            @Override
            public void OnItemLongClickListener(int position) {
                Toast.makeText(getContext(), "position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return chatsFragment;
    }

    private void setChatsToolbarProfileImage() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentFirebaseUser.getUid());
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDataModel user = dataSnapshot.getValue(UserDataModel.class);

                if (user.getUserProfileImageURL().equals("default")) {
                    profileImage.setImageResource(R.drawable.image);
                } else {
                    Glide.with(ChatsFragment.this).load(user.getUserProfileImageURL()).into(profileImage);
                }

                Log.d(TAG, "onDataChange: " + user.getUserName() + user.getUserPhoneNumber() + user.getUserPassword());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setChatsRecyclerView() {
        //find recyclerView
        chatsRecyclerView = chatsFragment.findViewById(R.id.chats_fragment_recycler_view);
        chatsRecyclerView.setHasFixedSize(true); //it increases performance

        //use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        chatsRecyclerView.setLayoutManager(layoutManager);
        //add item decoration
        chatsRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(50));

        dataPreparation();

        //set adapter
        chatsRecyclerViewAdapter = new ChatsRecyclerViewAdapter(mLastMessageDataModels);
        chatsRecyclerView.setAdapter(chatsRecyclerViewAdapter);

    }

    private void dataPreparation() {
        mLastMessageDataModels.add(new LastMessageDataModel(R.drawable.image, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.mipmap.ic_launcher, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.drawable.applogo, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.drawable.image, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.mipmap.ic_launcher, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.drawable.applogo, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.drawable.image, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.mipmap.ic_launcher, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.drawable.applogo, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.drawable.image, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.mipmap.ic_launcher, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.drawable.applogo, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.drawable.image, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.mipmap.ic_launcher, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));
        mLastMessageDataModels.add(new LastMessageDataModel(R.drawable.applogo, "Md Moshiur Rahman", "hello hi bye bye", "25 Dec"));

    }

    private void initializeUI() {
        profileImage = toolbar.findViewById(R.id.profile_image_conversation_toolbar);
    }

    private void toolbarButtonHandler() {
        //add onclick listener to profile image
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });
    }

    private void setToolbar() {
        toolbar = chatsFragment.findViewById(R.id.chatsToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //remove appname
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
    }

    private void setCurrentUser() {
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

}