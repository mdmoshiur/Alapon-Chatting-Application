package com.moshiur.alapon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.moshiur.alapon.R;
import com.moshiur.alapon.interfaces.MyOnItemClickListener;
import com.moshiur.alapon.models.LastMessageDataModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatsRecyclerViewAdapter extends RecyclerView.Adapter<ChatsRecyclerViewAdapter.ChatsRecyclerViewHolder> {

    private MyOnItemClickListener myOnItemClickListener;

    private List<LastMessageDataModel> mList;
    private Context mContext;


    public ChatsRecyclerViewAdapter(Context context, List<LastMessageDataModel> mList) {
        this.mContext = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public ChatsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.last_message_layout, parent, false);
        return new ChatsRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsRecyclerViewHolder holder, int position) {

        LastMessageDataModel model = mList.get(position);

        if (model.getProfileImageURL().equals("default")) {
            holder.imageView.setImageResource(R.drawable.profile_icon);
        } else {
            Glide.with(mContext).load(model.getProfileImageURL()).into(holder.imageView);
        }

        holder.nameView.setText(model.getUserName());
        holder.lastMessageView.setText(model.getLastMessage());
        holder.lastMessageTimeView.setText(getMessageTime(model.getLastMessageTime()));

    }

    @Override
    public int getItemCount() {
        return mList.size();
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


    private String getMessageTime(String messageTime) {
        String messageDate = messageTime.substring(0, messageTime.indexOf("AT")).trim();
        String currentDate = currentDate();
        if (currentDate.equals(messageDate)) {
            messageDate = messageTime.substring(messageTime.indexOf("AT") + 3);
            return messageDate;
        } else {
            return messageDate;
        }
    }

    private String currentDate() {
        //SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        return sdf.format(new Date());
    }


}
