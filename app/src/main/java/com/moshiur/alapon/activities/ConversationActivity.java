package com.moshiur.alapon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moshiur.alapon.R;
import com.moshiur.alapon.adapters.MessageRecyclerViewAdapter;
import com.moshiur.alapon.models.MessageModel;
import com.moshiur.alapon.utils.VerticalSpaceItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ConversationActivity extends AppCompatActivity {

    private Toolbar toolbar;

    ArrayList<MessageModel> messageModels = new ArrayList<>();
    private String recieverID, senderID, imageURL, message;
    private String reciverName;
    private String isSeen = "not seen";
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

    private DatabaseReference rootReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        getIntentData();
        setToolbar();
        initializeUI();
        senderID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        rootReference = FirebaseDatabase.getInstance().getReference();
        handleToolbarMenuItem();

        //display messages
        setConversationRecyclerView();

        //send message :)
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
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

        readMessages();

        //set adapter
        messageRecyclerViewAdapter = new MessageRecyclerViewAdapter(this, messageModels, imageURL);
        conversationMessageRecyclerView.setAdapter(messageRecyclerViewAdapter);

    }

    private void readMessages() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageModels.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        MessageModel messageModel = snapshot.getValue(MessageModel.class);
                        assert messageModel != null;
                        if (messageModel.getSender().equals(senderID) && messageModel.getReceiver().equals(recieverID)
                                || messageModel.getReceiver().equals(senderID) && messageModel.getSender().equals(recieverID)) {
                            messageModels.add(messageModel);
                        }
                    }
                    messageRecyclerViewAdapter.notifyDataSetChanged();
                }
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
            writeMessage(message);
        }
    }

    private void writeMessage(String message) {
        MessageModel newMessage = new MessageModel(senderID, recieverID, getTimestamp(), message, isSeen);
        rootReference.child("chats").push().setValue(newMessage)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ConversationActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
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

        recieverID = intent.getStringExtra("userID");
        reciverName = intent.getStringExtra("userName");
        imageURL = intent.getStringExtra("userProfileImageURL");

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
        if (imageURL.equals("default")) {
            profileImageToolbar.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(this).load(imageURL).into(profileImageToolbar);
        }

        //load name
        toolbarName.setText(reciverName);

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
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        return sdf.format(new Date());
    }
}
