package com.moshiur.alapon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moshiur.alapon.R;
import com.moshiur.alapon.interfaces.MyOnItemClickListener;
import com.moshiur.alapon.models.MessageModel;
import com.moshiur.alapon.utils.VerticalSpaceItemDecoration;

import java.util.ArrayList;

public class MessageRecyclerViewAdapter extends RecyclerView.Adapter<MessageRecyclerViewAdapter.MessageRecyclerViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private FirebaseUser firebaseUser;

    private Context mContext;
    private String receiverImageURL;
    private ArrayList<MessageModel> messages;
    //interface variable
    private MyOnItemClickListener myOnItemClickListener;

    public MessageRecyclerViewAdapter(Context context, ArrayList<MessageModel> messageModels, String imageURL) {
        this.messages = messageModels;
        this.mContext = context;
        this.receiverImageURL = imageURL;
    }

    @NonNull
    @Override
    public MessageRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right_layout, parent, false);
            return new MessageRecyclerViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left_layout, parent, false);
            return new MessageRecyclerViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageRecyclerViewHolder holder, int position) {
        MessageModel messageModel = messages.get(position);

//        if (receiverImageURL.equals("default")){
//            holder.receiverImageView.setImageResource(R.mipmap.ic_launcher);
//        } else {
//            Glide.with(mContext).load(receiverImageURL).into(holder.receiverImageView);
//        }

        holder.message.setText(messageModel.getMessage());
        //holder.time.setText(messageModel.getTimestamp());
//        holder.seen.setText(messageModel.isSeen());
//        holder.messageSentIcon.setImageResource(R.drawable.ic_check_circle_black_24dp);

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    //listener interface
    public void setOnItemClickListener(MyOnItemClickListener listener) {
        this.myOnItemClickListener = listener;
    }

    public void addItemDecoration(VerticalSpaceItemDecoration verticalSpaceItemDecoration) {

    }

    @Override
    public int getItemViewType(int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (messages.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    public class MessageRecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView receiverImageView;
        public TextView message;
        public TextView time;
        public TextView seen;
        public ImageView messageSentIcon;

        public MessageRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            //find all the views
            receiverImageView = itemView.findViewById(R.id.chat_item_left_profile_image_id);
            message = itemView.findViewById(R.id.message_id);
            time = itemView.findViewById(R.id.message_time_id);
            seen = itemView.findViewById(R.id.message_seen_id);
            messageSentIcon = itemView.findViewById(R.id.message_sent_id);

            message.setOnClickListener(new View.OnClickListener() {
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
