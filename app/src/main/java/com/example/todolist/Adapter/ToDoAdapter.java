package com.example.todolist.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.AddNewTask;
import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;
import com.example.todolist.Utils.DataBaseHelper;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> modelList;
    private MainActivity mainActivity;
    private DataBaseHelper dataBaseHelper;
    private static final String TAG = "ToDoAdapter";

    public ToDoAdapter(MainActivity mainActivity, DataBaseHelper dataBaseHelper) {
        this.modelList = new ArrayList<>(); // Initialize modelList to avoid null reference
        this.mainActivity = mainActivity;
        this.dataBaseHelper = dataBaseHelper;
    }

    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ToDoAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_lauout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ViewHolder holder, int position) {
        try {
            final ToDoModel item = modelList.get(position);
            if (item != null) { // Check if item is not null
                holder.idMaterialCheckBoxitemView.setText(item.getTask());
                holder.idMaterialCheckBoxitemView.setChecked(toBoolean(item.getStatus()));
                holder.idMaterialCheckBoxitemView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            dataBaseHelper.updateStatus(item.getId(), 1); // Use item.getId() to update status
                        } else {
                            dataBaseHelper.updateStatus(item.getId(), 0); // Use item.getId() to update status
                        }
                    }
                });
            } else {
                Log.e(TAG, "Item at position " + position + " is null.");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error binding view at position " + position, e);
        }
    }

    private boolean toBoolean(int status) {
        return status != 0;
    }

    public Context getContext() {
        return mainActivity;
    }

    public void setTask(List<ToDoModel> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position) {
        ToDoModel item = modelList.get(position);
        dataBaseHelper.deleteTask(item.getId());
        modelList.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position) {
        ToDoModel item = modelList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("Task", item.getTask());
        bundle.putString("Description", item.getDescription());

        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(mainActivity.getSupportFragmentManager(), task.getTag());
    }

    @Override
    public int getItemCount() {
        return modelList == null ? 0 : modelList.size(); // Add null check to avoid NullPointerException
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCheckBox idMaterialCheckBoxitemView;
        TextView todo_list_task_descriptionitemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idMaterialCheckBoxitemView = itemView.findViewById(R.id.idMaterialCheckBoxitemView);
            todo_list_task_descriptionitemView = itemView.findViewById(R.id.todo_list_task_description);
        }
    }
}
