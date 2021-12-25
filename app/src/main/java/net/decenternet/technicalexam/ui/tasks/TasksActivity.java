package net.decenternet.technicalexam.ui.tasks;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.database.TaskDatabaseHandler;
import net.decenternet.technicalexam.domain.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksActivity extends AppCompatActivity implements TasksContract.View {

    private TasksContract.Presenter presenter;
    private FloatingActionButton btnFab;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private TaskDatabaseHandler db;
    private ArrayList<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        /**
         * Finish this simple task recording app.
         * The following are the remaining todos for this project:
         * 1. Make sure all defects are fixed.
         * 2. Showing a dialog to add/edit the task.
         * 3. Allowing the user to toggle it as completed.
         * 4. Allowing the user to delete a task.
         *
         */

        btnFab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyMainTaskList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        db = new TaskDatabaseHandler(this);
        presenter = new Presenter(this, db);
        if (db.getTasksCount() > 0) {
            Log.i("DBCOunt", db.getTasksCount()+"");
            displayTasks(db.getAllTasks());
        }

        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onAddTaskClicked();
            }
        });

    }

    @Override
    public void displayTasks(List<Task> tasks) {
        if (taskList.size() > 0)
            taskList.clear();

        taskList.addAll(tasks);
        if (taskAdapter == null) {
            taskAdapter = new TaskAdapter(this, taskList, this, presenter);
            recyclerView.setAdapter(taskAdapter);
        }
        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void addTaskToList(Task task) {
        db.addTask(task);
        displayTasks(db.getAllTasks());
    }

    @Override
    public void removeTaskFromList(Task task) {
        db.deleteTask(task);
        displayTasks(db.getAllTasks());
    }

    @Override
    public void updateTaskInList(Task task) {
        db.updateTask(task);
        displayTasks(db.getAllTasks());
    }

    @Override
    public void popupTaskAddingDialog() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(TasksActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        final EditText txt_inputText = (EditText) mView.findViewById(R.id.txt_input);
        Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
        Button btn_okay = (Button) mView.findViewById(R.id.btn_save);
        TextView tvDialogTitle = (TextView) mView.findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText("Edit Your Task");
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Task task = new Task();
                if (!txt_inputText.getText().toString().isEmpty()) {
                    task.setDescription(txt_inputText.getText().toString());
                    task.setIsCompleted("0");
                    presenter.onSaveTaskClicked(task);
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Task Description Should not be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
    }

    @Override
    public void popupTaskEditorDialog(final Task task) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(TasksActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        final EditText txt_inputText = (EditText) mView.findViewById(R.id.txt_input);
        txt_inputText.setText(task.getDescription());
        Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
        Button btn_okay = (Button) mView.findViewById(R.id.btn_save);
        TextView tvDialogTitle = (TextView) mView.findViewById(R.id.tvDialogTitle);
        tvDialogTitle.setText("Edit Your Task");
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!txt_inputText.getText().toString().isEmpty()) {
                    task.setDescription(txt_inputText.getText().toString());
                    updateTaskInList(task);
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Task Description Should not be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.show();
    }
}
