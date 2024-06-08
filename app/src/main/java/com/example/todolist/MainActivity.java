package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Interface.OnDialogCloseListener;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.DataBaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseListener {
    private RecyclerView RecyclerView_for_list_item;
    private FloatingActionButton floating_Action_Button;
    private DataBaseHelper dataBaseHelper;
    private List<ToDoModel> modelList = new ArrayList<>();
    private ToDoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.parseColor("#0A3D62"));

        dataBaseHelper = new DataBaseHelper(this); // Ensure this is properly initialized

        RecyclerView_for_list_item = findViewById(R.id.RecyclerView_for_list_item);
        floating_Action_Button = findViewById(R.id.floating_Action_Button);

        adapter = new ToDoAdapter(this, dataBaseHelper); // Pass initialized dataBaseHelper
        RecyclerView_for_list_item.setHasFixedSize(true);
        RecyclerView_for_list_item.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView_for_list_item.setAdapter(adapter);

        floating_Action_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("TAG", "floating Action Button was Clicked Now" );
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new RecylerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(RecyclerView_for_list_item);

        loadTasks();
    }

    private void loadTasks() {
        modelList = dataBaseHelper.getAllTasks();
        Collections.reverse(modelList);
        adapter.setTask(modelList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        loadTasks();
    }
}
