package com.moshiur.alapon.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moshiur.alapon.R;
import com.moshiur.alapon.interfaces.MyOnItemClickListener;
import com.moshiur.alapon.models.MessageModel;
import com.moshiur.alapon.utils.VerticalSpaceItemDecoration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MessageRecyclerViewAdapter extends FirebaseRecyclerAdapter<MessageModel, MessageRecyclerViewAdapter.MessageRecyclerViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private FirebaseUser firebaseUser;

    private ConstraintLayout chatItemLeft;
    private ImageView profileImage;
    private Context mContext;
    private String receiverImageURL;
    private List<MessageModel> messages;
    //interface variable
    private MyOnItemClickListener myOnItemClickListener;


    public MessageRecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<MessageModel> options, Context context, String receiverImageURL) {
        super(options);
        this.mContext = context;
        this.receiverImageURL = receiverImageURL;
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
    protected void onBindViewHolder(@NonNull MessageRecyclerViewHolder holder, int position, @NonNull MessageModel messageModel) {
        switch (holder.getItemViewType()) {
            case MSG_TYPE_LEFT:
                if (receiverImageURL.equals("default")) {
                    holder.receiverImageView.setImageResource(R.drawable.profile_icon);
                } else {
                    Glide.with(mContext).load(receiverImageURL).into(holder.receiverImageView);
                }
                holder.message.setText(messageModel.getMessage());
                holder.time.setText(getMessageTime(messageModel.getTimestamp()));
                break;

            case MSG_TYPE_RIGHT:
                holder.message.setText(messageModel.getMessage());
                holder.time.setText(getMessageTime(messageModel.getTimestamp()));
                holder.seen.setText(messageModel.getSeenStatus());
                holder.messageSentIcon.setImageResource(messageModel.getMessageSentImageResourceID());

                //set visibility of last message seen status and sent icon
                if (getItemCount() - 1 == position) {
                    //holder.seen.setVisibility(View.VISIBLE);
                    holder.messageSentIcon.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

//    @Override
//    public int getItemCount() {
//        return messages.size();
//    }


    //listener interface
    public void setOnItemClickListener(MyOnItemClickListener listener) {
        this.myOnItemClickListener = listener;
    }

    public void addItemDecoration(VerticalSpaceItemDecoration verticalSpaceItemDecoration) {

    }

    @Override
    public int getItemViewType(int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (getItem(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }

    private String currentDate() {
        //SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
        return sdf.format(new Date());
    }

    private String getMessageTime(String messageTime) {
        String messageDate = messageTime.substring(0, messageTime.indexOf("AT")).trim();
        String currentDate = currentDate();
        if (currentDate.equals(messageDate)) {
            messageDate = messageTime.substring(messageTime.indexOf("AT") + 3);
            return messageDate;
        } else {
            return messageTime;
        }
    }

    public class MessageRecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView receiverImageView;
        public TextView message;
        public TextView time;
        public TextView seen;
        public ImageView messageSentIcon;

        public MessageRecyclerViewHolder(@NonNull final View itemView) {
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
                    //get previous visibility
                    if (time.getVisibility() == View.GONE) {
                        time.setVisibility(View.VISIBLE);
                    } else {
                        time.setVisibility(View.GONE);
                    }

                    //for seen views
                    if (getItem(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        if (seen.getVisibility() == View.GONE) {
                            seen.setVisibility(View.VISIBLE);
                            messageSentIcon.setVisibility(View.VISIBLE);
                        } else {
                            seen.setVisibility(View.GONE);
                            messageSentIcon.setVisibility(View.INVISIBLE);
                        }
                    }
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
