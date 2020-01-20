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
import com.google.firebase.database.FirebaseDatabase;
import com.moshiur.alapon.R;
import com.moshiur.alapon.activities.ConversationActivity;
import com.moshiur.alapon.activities.ProfileActivity;
import com.moshiur.alapon.adapters.ChatsRecyclerViewAdapter;
import com.moshiur.alapon.interfaces.MyOnItemClickListener;
import com.moshiur.alapon.models.LastMessageDataModel;
import com.moshiur.alapon.models.UserDataModel;
import com.moshiur.alapon.utils.VerticalSpaceItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ChatsFragment extends Fragment {

    private static final String TAG = "ChatsFragment";

    private View chatsFragment;
    private Toolbar toolbar;

    //views
    private ImageView profileImage;


    List<LastMessageDataModel> mLastMessageDataModels = new ArrayList<>();
    private RecyclerView chatsRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ChatsRecyclerViewAdapter chatsRecyclerViewAdapter;

    private DatabaseReference mDatabaseReference;

    private static final String USER_DATA_MODEL = "userDataModel";
    private UserDataModel currentUser;

    private String userID, key, imageURL, lastMessage, userName, lastMessageTime;

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
                LastMessageDataModel lastMessageDataModel = mLastMessageDataModels.get(position);
                Intent intent = new Intent(getContext(), ConversationActivity.class);
                intent.putExtra("userID", lastMessageDataModel.getUserID());
                intent.putExtra("userName", lastMessageDataModel.getUserName());
                intent.putExtra("userProfileImageURL", lastMessageDataModel.getProfileImageURL());
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
        chatsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //add item decoration
        chatsRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(50));
        // dataPreparation();
        //set adapter
        chatsRecyclerViewAdapter = new ChatsRecyclerViewAdapter(getContext(), mLastMessageDataModels);
        chatsRecyclerView.setAdapter(chatsRecyclerViewAdapter);

    }

    private void dataPreparation() {
        final String currentUserID = currentUser.getUserID();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chats");

//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                mLastMessageDataModels.clear();
//                if (dataSnapshot.exists()){
//                    key = dataSnapshot.getKey();
//                    if (key.contains(currentUserID)){
//
//                        //retrieve user who has chat with me
//                        userID = key.replace(currentUserID, "");
//                        DatabaseReference db = FirebaseDatabase.getInstance().getReference("users").child(userID);
//                        db.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()){
//                                    UserDataModel model = dataSnapshot.getValue(UserDataModel.class);
//                                    assert model != null;
//                                    userName = model.getUserName();
//                                    imageURL = model.getUserProfilePhotoURL();
//                                    mLastMessageDataModels.add(new LastMessageDataModel(userID, imageURL, userName, lastMessage, lastMessageTime));
//                                }
//                                chatsRecyclerViewAdapter.notifyDataSetChanged();
//
////                                FirebaseDatabase.getInstance().getReference("chats").child(key).orderByKey().limitToLast(1)
////                                        .addChildEventListener(new ChildEventListener() {
////                                            @Override
////                                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
////                                                if (dataSnapshot.exists()){
////                                                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
////                                                    assert messageModel != null;
////                                                    lastMessage = messageModel.getMessage();
////                                                    lastMessageTime = messageModel.getTimestamp();
////                                                    lastMessageTime = lastMessageTime.substring(0,lastMessageTime.indexOf("AT")).trim();
////                                                    String date = currentDate();
////                                                    if (date.equals(lastMessageTime)){
////                                                        lastMessageTime = messageModel.getTimestamp();
////                                                        lastMessageTime = lastMessageTime.substring(lastMessageTime.indexOf("AT")+3);
////                                                    }
////
////                                                    mLastMessageDataModels.add(new LastMessageDataModel(userID, imageURL, userName, lastMessage, lastMessageTime));
////
////                                                    Log.d(TAG, "onChildAdded: "+ lastMessage+lastMessageTime);
////
////                                                }
////
////                                                chatsRecyclerViewAdapter.notifyDataSetChanged();
////
////
////                                            }
////
////                                            @Override
////                                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
////
////                                            }
////
////                                            @Override
////                                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
////
////                                            }
////
////                                            @Override
////                                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
////
////                                            }
////
////                                            @Override
////                                            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                                            }
////                                        });
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }

    private void initializeUI() {
        profileImage = toolbar.findViewById(R.id.profile_image_conversation_toolbar);
    }

    private void setToolbarProfileImage() {
        if (currentUser.getUserProfilePhotoURL().equals("default")) {
            profileImage.setImageResource(R.drawable.profile_icon);
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

    private String currentDate() {
        //SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        return sdf.format(new Date());
    }

    @Override
    public void onStart() {
        super.onStart();

        dataPreparation();
    }
}