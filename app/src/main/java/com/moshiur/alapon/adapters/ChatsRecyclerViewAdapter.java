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
import com.moshiur.alapon.models.LastMessageDataModel;

import java.util.ArrayList;

public class ChatsRecyclerViewAdapter extends RecyclerView.Adapter<ChatsRecyclerViewAdapter.ChatsRecyclerViewHolder> {

    private MyOnItemClickListener myOnItemClickListener;

    ArrayList<LastMessageDataModel> listData;

    public ChatsRecyclerViewAdapter(ArrayList<LastMessageDataModel> lastMessageDataModels) {
        this.listData = lastMessageDataModels;
    }

    @NonNull
    @Override
    public ChatsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_message_layout, parent, false);
        ChatsRecyclerViewHolder crvh = new ChatsRecyclerViewHolder(v);
        return crvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsRecyclerViewHolder holder, int position) {
        LastMessageDataModel lastMessageDataModel = listData.get(position);

        holder.imageView.setImageResource(lastMessageDataModel.getImage());
        holder.nameView.setText(lastMessageDataModel.getName());
        holder.lastMessageView.setText(lastMessageDataModel.getLastMessage());
        holder.lastMessageTimeView.setText(lastMessageDataModel.getLastMessageTime());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    //listener interface
    public void setOnItemClickListener(MyOnItemClickListener listener) {
        this.myOnItemClickListener = listener;
    }

    public class ChatsRecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameView;
        public TextView lastMessageView;
        public TextView lastMessageTimeView;

        public ChatsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            //find all the views
            imageView = itemView.findViewById(R.id.profile_image_id_last_message_layout);
            nameView = itemView.findViewById(R.id.name_id_last_message_layout);
            lastMessageView = itemView.findViewById(R.id.message_id_last_message_layout);
            lastMessageTimeView = itemView.findViewById(R.id.last_message_time_id_last_message_layout);

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
