package com.moshiur.alapon.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

import com.moshiur.alapon.R;
import com.moshiur.alapon.activities.ConversationActivity;
import com.moshiur.alapon.activities.ProfileActivity;
import com.moshiur.alapon.adapters.PeopleRecyclerViewAdapter;
import com.moshiur.alapon.interfaces.MyOnItemClickListener;
import com.moshiur.alapon.models.PeopleDataModel;
import com.moshiur.alapon.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

public class PeopleFragment extends Fragment {

    ArrayList<PeopleDataModel> mPeopleDataModel = new ArrayList<>();

    private RecyclerView peopleRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PeopleRecyclerViewAdapter peopleRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View peopleFragment = inflater.inflate(R.layout.fragment_people, container, false);
        //for menu
        setHasOptionsMenu(true);

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

        //find recyclerView
        peopleRecyclerView = peopleFragment.findViewById(R.id.people_recycler_view);
        peopleRecyclerView.setHasFixedSize(true);

        //use a linear layout manger
        layoutManager = new LinearLayoutManager(getContext());
        peopleRecyclerView.setLayoutManager(layoutManager);

        //add item decoration
        peopleRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(45));

        //data prep
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.image, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.applogo, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.mipmap.ic_launcher, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.ic_people_black_24dp, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.image, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.applogo, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.mipmap.ic_launcher, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.ic_people_black_24dp, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.image, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.applogo, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.mipmap.ic_launcher, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.ic_people_black_24dp, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.image, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.applogo, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.mipmap.ic_launcher, "Md Moshiur Rahman"));
        mPeopleDataModel.add(new PeopleDataModel(R.drawable.ic_people_black_24dp, "Md Moshiur Rahman"));

        //set adapter
        peopleRecyclerViewAdapter = new PeopleRecyclerViewAdapter(mPeopleDataModel);
        peopleRecyclerView.setAdapter(peopleRecyclerViewAdapter);

        peopleRecyclerViewAdapter.setOnItemClickListener(new MyOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                Toast.makeText(getContext(), "position: " + position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), ConversationActivity.class));
            }

            @Override
            public void OnItemLongClickListener(int position) {
                Toast.makeText(getContext(), "position: " + position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), ConversationActivity.class));
            }
        });

        return peopleFragment;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.people_fragment_options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}