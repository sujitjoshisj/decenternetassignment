package net.decenternet.technicalexam.ui.tasks;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import net.decenternet.technicalexam.R;
import net.decenternet.technicalexam.domain.Task;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.Viewholder> {

    private Context context;
    private ArrayList<Task> taskList;
    private TasksContract.View taskListner;
    private TasksContract.Presenter presenter;

    // Constructor
    public TaskAdapter(Context context, ArrayList<Task> taskList, TasksContract.View taskListner,TasksContract.Presenter presenter) {
        this.context = context;
        this.taskList = taskList;
        this.taskListner = taskListner;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public TaskAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TaskAdapter.Viewholder holder, final int position) {

        final Task task = taskList.get(position);

        holder.tvDesc.setText(task.getDescription());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskListner.popupTaskEditorDialog(task);
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDeleteTaskClicked(task.getId());
            }
        });

        if (task.getIsCompleted().equals("1")){
            holder.toggleButton.setChecked(true);
            holder.toggleButton.setTextColor(context.getResources().getColor(R.color.white));
            holder.toggleButton.setBackground(context.getResources().getDrawable(R.drawable.btn_solid_bg));
        }else{
            holder.toggleButton.setChecked(false);
            holder.toggleButton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.toggleButton.setBackground(context.getResources().getDrawable(R.drawable.edit_text_border));
        }

        holder.toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.toggleButton.isChecked()) {
                    presenter.onTaskChecked(task.getId());
                    holder.toggleButton.setTextColor(context.getResources().getColor(R.color.white));
                    holder.toggleButton.setBackground(context.getResources().getDrawable(R.drawable.btn_solid_bg));
                }
                else {
                    presenter.onTaskUnchecked(task.getId());
                    holder.toggleButton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    holder.toggleButton.setBackground(context.getResources().getDrawable(R.drawable.edit_text_border));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return taskList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView tvDesc;
        private ImageView ivEdit, ivDelete;
        private ToggleButton toggleButton;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tvDescription);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            toggleButton = itemView.findViewById(R.id.taskToggleButton);
        }
    }
}
