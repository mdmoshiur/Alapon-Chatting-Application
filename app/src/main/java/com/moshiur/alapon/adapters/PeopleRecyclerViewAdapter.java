package com.moshiur.alapon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshiur.alapon.R;
import com.moshiur.alapon.interfaces.MyOnItemClickListener;
import com.moshiur.alapon.models.PeopleDataModel;

import java.util.ArrayList;

public class PeopleRecyclerViewAdapter extends RecyclerView.Adapter<PeopleRecyclerViewAdapter.PeopleRecyclerViewHolder> {

    ArrayList<PeopleDataModel> listData;
    //interface variable
    private MyOnItemClickListener myOnItemClickListener;

    public PeopleRecyclerViewAdapter(ArrayList<PeopleDataModel> peopleDataModels) {
        this.listData = peopleDataModels;
    }

    @NonNull
    @Override
    public PeopleRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_people_layout, parent, false);
        return new PeopleRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleRecyclerViewHolder holder, int position) {
        PeopleDataModel peopleDataModel = listData.get(position);

        holder.imageView.setImageResource(peopleDataModel.getImage());
        holder.nameView.setText(peopleDataModel.getName());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    //listener interface
    public void setOnItemClickListener(MyOnItemClickListener listener) {
        this.myOnItemClickListener = listener;
    }

    public class PeopleRecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameView;

        public PeopleRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            //find all the views
            imageView = itemView.findViewById(R.id.profile_image_id_single_people_layout);
            nameView = itemView.findViewById(R.id.name_id_single_people_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    myOnItemClickListener.OnItemClickListener(position);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myOnItemClickListener.OnItemLongClickListener(getAdapterPosition());
                    return false;
                }
            });
        }
    }

}
