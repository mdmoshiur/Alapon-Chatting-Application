package com.moshiur.alapon.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
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

    private DatabaseReference mDatabaseReference;

    private static final String USER_DATA_MODEL = "userDataModel";
    private UserDataModel currentUser;

    public static ChatsFragment newInstance(UserDataModel userDataModel) {

        ChatsFragment mChatFragment = new ChatsFragment();

        Bundle args = new Bundle();
        args.putParcelable(USER_DATA_MODEL, userDataModel);
        mChatFragment.setArguments(args);

        return mChatFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatsFragment = inflater.inflate(R.layout.fragment_chats, container, false);

        //get fragment data
        if (getArguments() != null) {
            currentUser = getArguments().getParcelable(USER_DATA_MODEL);
        }

        //set chats_toolbar
        setToolbar();

        initializeUI();

        setToolbarProfileImage();

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

    private void setToolbarProfileImage() {
        if (currentUser.getUserProfilePhotoURL().equals("default")) {
            profileImage.setImageResource(R.drawable.image);
        } else {
            Glide.with(ChatsFragment.this).load(currentUser.getUserProfilePhotoURL()).into(profileImage);
        }
    }

    private void toolbarButtonHandler() {
        //add onclick listener to profile image
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
        toolbar = chatsFragment.findViewById(R.id.chatsToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //remove appname
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
    }

}