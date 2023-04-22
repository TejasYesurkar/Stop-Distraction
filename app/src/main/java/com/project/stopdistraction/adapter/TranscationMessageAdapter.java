package com.project.stopdistraction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.stopdistraction.R;
import com.project.stopdistraction.roomdb.DailyTask;
import com.project.stopdistraction.roomdb.Message;

import java.util.List;


public class TranscationMessageAdapter extends RecyclerView.Adapter<TranscationMessageAdapter.ViewHolder> {

    private List<Message> mContacts;
    private Context mContext;

    public TranscationMessageAdapter(List<Message> mContacts, Context mContext) {
        this.mContacts = mContacts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_msg, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Message item = mContacts.get(position);
        holder.tvMessage.setText(item.getMessage());
        holder.tvMessageType.setText(item.getMessageType());
        holder.tvDate.setText(item.getDate());

    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMessageType;
        TextView tvMessage;
        TextView tvDate;


        public ViewHolder(@NonNull View rowView) {
            super(rowView);
            tvMessage = (TextView) rowView.findViewById(R.id.tvMessage);
            tvMessageType = (TextView) rowView.findViewById(R.id.tvMessageType);
            tvDate = (TextView) rowView.findViewById(R.id.tvDate);
        }

    }
}
