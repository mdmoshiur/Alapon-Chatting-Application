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

import com.moshiur.alapon.R;
import com.moshiur.alapon.activities.ConversationActivity;
import com.moshiur.alapon.activities.ProfileActivity;
import com.moshiur.alapon.adapters.PeopleRecyclerViewAdapter;
import com.moshiur.alapon.interfaces.MyOnItemClickListener;
import com.moshiur.alapon.models.PeopleDataModel;
import com.moshiur.alapon.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

public class PeopleFragment extends Fragment {

    private View peopleFragment;
    private Toolbar toolbar;

    private Button allButton, activeButton;

    ArrayList<PeopleDataModel> mPeopleDataModel = new ArrayList<>();

    private RecyclerView peopleRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PeopleRecyclerViewAdapter peopleRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        peopleFragment = inflater.inflate(R.layout.fragment_people, container, false);
        //for menu
        setHasOptionsMenu(true);
        setToolbar(); //set peoples_toolbar
        handleToolbarMenu();
        handleButtons();
        recyclerViewDataPreparation();
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
                startActivity(new Intent(getContext(), ConversationActivity.class));
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
        peopleRecyclerViewAdapter = new PeopleRecyclerViewAdapter(mPeopleDataModel);
        peopleRecyclerView.setAdapter(peopleRecyclerViewAdapter);
        //add item decoration
        peopleRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(45));
    }

    private void recyclerViewDataPreparation() {
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
    }

    private void handleToolbarMenu() {
        ImageView profileImage = toolbar.findViewById(R.id.toolbar_image);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
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
}