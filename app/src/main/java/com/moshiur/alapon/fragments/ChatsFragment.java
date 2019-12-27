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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshiur.alapon.R;
import com.moshiur.alapon.activities.ProfileActivity;
import com.moshiur.alapon.adapters.ChatsRecyclerViewAdapter;
import com.moshiur.alapon.models.LastMessageNode;
import com.moshiur.alapon.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {

    ArrayList<LastMessageNode> mLastMessageNodes = new ArrayList<>();
    private RecyclerView chatsRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ChatsRecyclerViewAdapter chatsRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View chatsFragment = inflater.inflate(R.layout.fragment_chats, container, false);

        //set chats_toolbar
        Toolbar toolbar = chatsFragment.findViewById(R.id.chatsToolbar);
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

        //find recyclerView
        chatsRecyclerView = chatsFragment.findViewById(R.id.chats_fragment_recycler_view);
        chatsRecyclerView.setHasFixedSize(true); //it increases performance

        //use a linear layout manager
        layoutManager = new LinearLayoutManager(getContext());
        chatsRecyclerView.setLayoutManager(layoutManager);
        //add item decoration
        chatsRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(50));

        mLastMessageNodes.add(new LastMessageNode(R.drawable.image, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.mipmap.ic_launcher, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.drawable.applogo, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.drawable.image, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.mipmap.ic_launcher, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.drawable.applogo, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.drawable.image, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.mipmap.ic_launcher, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.drawable.applogo, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.drawable.image, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.mipmap.ic_launcher, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.drawable.applogo, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.drawable.image, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.mipmap.ic_launcher, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));
        mLastMessageNodes.add(new LastMessageNode(R.drawable.applogo, "Md Moshiur Rahman", "hello hi bye bye", "11/11/11"));

        chatsRecyclerViewAdapter = new ChatsRecyclerViewAdapter(mLastMessageNodes);
        chatsRecyclerView.setAdapter(chatsRecyclerViewAdapter);

        return chatsFragment;
    }
}