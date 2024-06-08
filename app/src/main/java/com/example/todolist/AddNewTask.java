package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolist.Interface.OnDialogCloseListener;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.DataBaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.skydoves.elasticviews.ElasticButton;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTask";

    private EditText editTextTask;
    private EditText editTextDescription;
    private ElasticButton btnSaveEdit;
    private DataBaseHelper dataBaseHelper;

    public static AddNewTask newInstance() {
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextTask = view.findViewById(R.id.Edit_textTask);
        editTextDescription = view.findViewById(R.id.Edit_text_disc);
        btnSaveEdit = view.findViewById(R.id.BTnSavedEdit);
        dataBaseHelper = new DataBaseHelper(getActivity());

        Bundle bundle = getArguments();
        boolean isUpdate = bundle != null;

        if (isUpdate) {
            String task = bundle.getString("Task", "");
            String description = bundle.getString("Description", "");

            editTextTask.setText(task);
            editTextDescription.setText(description);
            btnSaveEdit.setEnabled(!task.isEmpty() || !description.isEmpty());
        }

        editTextTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().isEmpty()) {
                    btnSaveEdit.setEnabled(false);
                    btnSaveEdit.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                } else {
                    btnSaveEdit.setEnabled(true);
                    btnSaveEdit.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTextTask.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();

                if (isUpdate) {
                    try {
                        // Ensure that id, text (task), and description are extracted correctly from the bundle
                        int id = bundle.getInt("id");
                        // Extract description if available

                        // Call the updateTask method with the extracted id, text, and description
                        dataBaseHelper.updateTask(id, text, description);

                        Log.d(TAG, "Task updated successfully");
                    } catch (Exception e) {
                        Log.e(TAG, "Error updating task", e);
                    }

                } else {
                    ToDoModel item = new ToDoModel();
                    item.setTask(text);
                    item.setDescription(description);
                    item.setStatus(0);

                    try {
                        dataBaseHelper.insertTask(item);
                        Log.d(TAG, "Task inserted successfully");
                    } catch (Exception e) {
                        Log.e(TAG, "Error inserting task", e);
                    }
                }

                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() instanceof OnDialogCloseListener) {
            ((OnDialogCloseListener) getActivity()).onDialogClose(dialog);
        }
    }
}
