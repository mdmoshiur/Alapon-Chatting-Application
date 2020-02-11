package com.moshiur.alapon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.moshiur.alapon.R;
import com.moshiur.alapon.adapters.MessageRecyclerViewAdapter;
import com.moshiur.alapon.interfaces.MyOnItemClickListener;
import com.moshiur.alapon.models.LastMessageDataModel;
import com.moshiur.alapon.models.MessageModel;
import com.moshiur.alapon.utils.VerticalSpaceItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConversationActivity extends AppCompatActivity {

    private static final String TAG = "ConversationActivity";

    private Toolbar toolbar;

    List<MessageModel> messageModels = new ArrayList<>();
    Map<String, MessageModel> mappedMessage = new HashMap<>();

    private String receiverID, senderID, senderImageURL, receiverImageURL, message;
    private String senderName, receiverName;

    private ImageView profileImageToolbar;
    private TextView toolbarName;
    //type message layout views & buttons
    private ConstraintLayout typeMessageLayout;
    private ImageView sendMessageButton;
    private ImageView cameraButton;
    private ImageView emojiButton;
    private EditText typeMessageEditText;
    private RecyclerView conversationMessageRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MessageRecyclerViewAdapter messageRecyclerViewAdapter;

    private int notSentMessageIcon = R.drawable.ic_empty_circle_24dp;

    private DatabaseReference rootReference;
    private String conversationID;
    private ValueEventListener seenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        getIntentData();
        setToolbar();
        initializeUI();
        senderID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootReference = FirebaseDatabase.getInstance().getReference();

        setConversationID();

        isMessageSeen();

        handleToolbarMenuItem();

        //display messages
        setConversationRecyclerView();

        //set message item onclick listener
        setOnItemClickListener();

        //send message :)
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


    }

    private void setConversationID() {
        if (getStringValue(senderID) > getStringValue(receiverID)) {
            conversationID = senderID + receiverID;
        } else {
            conversationID = receiverID + senderID;
        }
    }

    private int getStringValue(String string) {
        int length = string.length();
        int value = 0;
        for (int i = 0; i < length; i++) {
            value += (int) string.charAt(i);
        }

        return value;
    }

    private void isMessageSeen() {
        DatabaseReference databaseReference = rootReference.child("chats").child(conversationID);
        seenListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageModel messageModel = snapshot.getValue(MessageModel.class);
                    assert messageModel != null;
                    if (messageModel.getReceiver().equals(senderID) && messageModel.getSender().equals(receiverID)) {

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("seenStatus", "seen");
                        snapshot.getRef().updateChildren(hashMap);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setOnItemClickListener() {
        messageRecyclerViewAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                //retrieve message data
            }

            @Override
            public void OnItemLongClickListener(int position) {

            }
        });
    }

    private void setConversationRecyclerView() {
        //find recyclerView
        conversationMessageRecyclerView = findViewById(R.id.conversation_message_recycler_view);
        conversationMessageRecyclerView.setHasFixedSize(true); //it increases performance

        //use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        conversationMessageRecyclerView.setLayoutManager(linearLayoutManager);
        //add item decoration
        conversationMessageRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(20));

        //readMessages();

        Query query = FirebaseDatabase.getInstance().getReference("chats").child(conversationID);

        FirebaseRecyclerOptions<MessageModel> options = new FirebaseRecyclerOptions.Builder<MessageModel>()
                .setQuery(query, MessageModel.class)
                //.setLifecycleOwner(this)
                .build();


        //set adapter
        messageRecyclerViewAdapter = new MessageRecyclerViewAdapter(options, this, receiverImageURL);

        // Scroll to bottom on new messages
        messageRecyclerViewAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                conversationMessageRecyclerView.smoothScrollToPosition(messageRecyclerViewAdapter.getItemCount());
            }
        });

        conversationMessageRecyclerView.setAdapter(messageRecyclerViewAdapter);

    }

    private void readMessages() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chats").child(conversationID);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    assert messageModel != null;
                    messageModels.add(messageModel);
                }
                conversationMessageRecyclerView.smoothScrollToPosition(messageModels.size());
                messageRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.exists()) {
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    assert messageModel != null;
                    messageModels.remove(messageModels.size() - 1);
                    messageModels.add(messageModel);
                }
                conversationMessageRecyclerView.smoothScrollToPosition(messageModels.size());
                messageRecyclerViewAdapter.notifyDataSetChanged();
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

    private void sendMessage() {
        message = typeMessageEditText.getText().toString().trim();

        if (!message.isEmpty()) {
            //clear editText
            typeMessageEditText.getText().clear();
            //check is it first message or not
            writeMessage(message);
            updateContactList(message);
        }
    }

    private void updateContactList(String message) {
        DatabaseReference ref = rootReference.child("contacts").child(senderID).child(receiverID);
        //for sender list
        LastMessageDataModel model = new LastMessageDataModel(receiverID, receiverImageURL, receiverName, message, getTimestamp());
        ref.setValue(model);

        ref = rootReference.child("contacts").child(receiverID).child(senderID);
        model = new LastMessageDataModel(senderID, senderImageURL, senderName, message, getTimestamp());
        ref.setValue(model);
    }

    private void writeMessage(String message) {
        final DatabaseReference ref = rootReference.child("chats").child(conversationID).push();
        MessageModel newMessage = new MessageModel(ref.toString(), senderID, receiverID, getTimestamp(), message, "not seen", notSentMessageIcon);
        //update map
        mappedMessage.put(ref.toString(), newMessage);
        ref.setValue(newMessage)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Toast.makeText(ConversationActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("messageSentImageResourceID", R.drawable.ic_check_circle_black_24dp);
                        ref.updateChildren(hashMap);
                    }
                });
    }

    private void initializeUI() {
        profileImageToolbar = toolbar.findViewById(R.id.profile_image_conversation_toolbar);
        toolbarName = toolbar.findViewById(R.id.name_id_conversation_toolbar);


        //type message layout
        typeMessageLayout = findViewById(R.id.conversation_type_message_layout_id);
        sendMessageButton = typeMessageLayout.findViewById(R.id.type_message_send_message_id);
        cameraButton = typeMessageLayout.findViewById(R.id.type_message_camera_icon_id);
        emojiButton = typeMessageLayout.findViewById(R.id.type_message_emoji_icon_id);
        typeMessageEditText = typeMessageLayout.findViewById(R.id.type_message_edit_text_id);

    }

    private void getIntentData() {
        Intent intent = getIntent();

        receiverID = intent.getStringExtra("userID");
        receiverName = intent.getStringExtra("userName");
        receiverImageURL = intent.getStringExtra("userProfileImageURL");
        senderImageURL = intent.getStringExtra("currentUserProfileImageURL");
        senderName = intent.getStringExtra("senderName");
    }

    private void handleToolbarMenuItem() {
        ImageView backArrow = toolbar.findViewById(R.id.back_arrow_conversation_toolbar);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //load image
        if (receiverImageURL.equals("default")) {
            profileImageToolbar.setImageResource(R.drawable.profile_icon);
        } else {
            Glide.with(ConversationActivity.this).load(receiverImageURL).into(profileImageToolbar);
        }
        //load name
        toolbarName.setText(receiverName);

    }

    private void setToolbar() {
        //set people_toolbar
        toolbar = findViewById(R.id.conversation_toolbar);
        setSupportActionBar(toolbar);
        //remove app name
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.conversation_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private String getTimestamp() {
        //SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd 'AT' h:mm a");
        return sdf.format(new Date());
    }

    private void updateUserActiveStatus(String activeStatus) {

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd 'AT' h:mm a");
        String timestamp = sdf.format(new Date());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userActiveStatus", activeStatus);
        hashMap.put("userLastActiveTimestamp", timestamp);

        FirebaseDatabase.getInstance().getReference("users").child(senderID).updateChildren(hashMap);
    }


    @Override
    protected void onStart() {
        super.onStart();
        messageRecyclerViewAdapter.startListening();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //remove seen listener
        rootReference.child("chats").child(conversationID).removeEventListener(seenListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        messageRecyclerViewAdapter.stopListening();
    }
}
