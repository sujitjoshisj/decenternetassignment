package net.decenternet.technicalexam.ui.tasks;

import android.content.Context;

import net.decenternet.technicalexam.database.TaskDatabaseHandler;
import net.decenternet.technicalexam.domain.Task;

public class Presenter implements TasksContract.Presenter {

    private TasksContract.View mainView;
    private TaskDatabaseHandler taskDatabaseHandlerl;
    public Presenter(TasksContract.View mainView, TaskDatabaseHandler handler) {
        this.mainView = mainView;
        taskDatabaseHandlerl = handler;
    }


    @Override
    public void onAddTaskClicked() {
        if (mainView != null) {
            mainView.popupTaskAddingDialog();
        }
    }

    @Override
    public void onSaveTaskClicked(Task task) {
        if (mainView != null) {
            mainView.addTaskToList(task);
        }
    }

    @Override
    public void onTaskChecked(int taskId) {
        if (mainView != null) {
            Task task = taskDatabaseHandlerl.getTask(taskId);
            task.setIsCompleted("1");
            mainView.updateTaskInList(task);
        }

    }

    @Override
    public void onTaskUnchecked(int taskId) {
        if (mainView != null) {
            Task task = taskDatabaseHandlerl.getTask(taskId);
            task.setIsCompleted("0");
            mainView.updateTaskInList(task);
        }
    }

    @Override
    public void onDeleteTaskClicked(int taskId) {
        if (mainView != null) {
            Task task = taskDatabaseHandlerl.getTask(taskId);
            mainView.removeTaskFromList(task);
        }
    }
}
