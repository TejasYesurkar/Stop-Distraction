package com.project.stopdistraction.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.stopdistraction.R;
import com.project.stopdistraction.model.DailyTaskList;
import com.project.stopdistraction.roomdb.DailyTask;
import com.project.stopdistraction.ui.TodoTaskFragment;


import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<DailyTaskList> mContacts;
    private Context mContext;

    public CategoryAdapter(List<DailyTaskList> mContacts, Context mContext) {
        this.mContacts = mContacts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_category, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final DailyTaskList item = mContacts.get(position);
        holder.tvTaskName.setText(item.getTask());
        holder.tvTaskCategory.setText(item.getCategory());
        holder.tvDate.setText(item.getTime());
        holder.checkBoxStatus.setChecked(item.isStatus());

        holder.checkBoxStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("Checked",holder.checkBoxStatus.isChecked()+"--"+item.getId());
                TodoTaskFragment.updateTask(item.getId(),holder.checkBoxStatus.isChecked());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTaskName;
        TextView tvTaskCategory;
        TextView tvDate;
        CheckBox checkBoxStatus;


        public ViewHolder(@NonNull View rowView) {
            super(rowView);
            tvTaskName = (TextView) rowView.findViewById(R.id.tvTaskName);
            tvTaskCategory = (TextView) rowView.findViewById(R.id.tvTaskCategory);
            tvDate = (TextView) rowView.findViewById(R.id.tvDate);
            checkBoxStatus = (CheckBox) rowView.findViewById(R.id.checkStatus);


        }

    }
}
