package com.moshiur.alapon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshiur.alapon.R;
import com.moshiur.alapon.models.LastMessageNode;

import java.util.ArrayList;

public class ChatsRecyclerViewAdapter extends RecyclerView.Adapter<ChatsRecyclerViewAdapter.ChatsRecycerViewHolder> {

    ArrayList<LastMessageNode> listData;

    public ChatsRecyclerViewAdapter(ArrayList<LastMessageNode> lastMessageNodes) {
        this.listData = lastMessageNodes;
    }

    @NonNull
    @Override
    public ChatsRecyclerViewAdapter.ChatsRecycerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_message_layout, parent, false);
        ChatsRecycerViewHolder crvh = new ChatsRecycerViewHolder(v);
        return crvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsRecyclerViewAdapter.ChatsRecycerViewHolder holder, int position) {
        LastMessageNode lastMessageNode = listData.get(position);

        holder.imageView.setImageResource(lastMessageNode.getImage());
        holder.nameView.setText(lastMessageNode.getName());
        holder.lastMessageView.setText(lastMessageNode.getLastMessage());
        holder.lastMessageTimeView.setText(lastMessageNode.getLastMessageTime());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ChatsRecycerViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameView;
        public TextView lastMessageView;
        public TextView lastMessageTimeView;

        public ChatsRecycerViewHolder(@NonNull View itemView) {
            super(itemView);
            //find all the views
            imageView = itemView.findViewById(R.id.profile_image_id_last_message_layout);
            nameView = itemView.findViewById(R.id.name_id_last_message_layout);
            lastMessageView = itemView.findViewById(R.id.message_id_last_message_layout);
            lastMessageTimeView = itemView.findViewById(R.id.last_message_time_id_last_message_layout);
        }
    }

}
