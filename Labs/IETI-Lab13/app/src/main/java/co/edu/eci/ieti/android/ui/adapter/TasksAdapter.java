package co.edu.eci.ieti.android.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.edu.eci.ieti.R;
import co.edu.eci.ieti.android.persistence.entities.Task;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    List<Task> taskList = null;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.priorityText.setText(String.valueOf(task.getPriority()));
        holder.dueDateText.setText(task.getDueDate().toString());
        holder.descriptionText.setText(task.getDescription());
    }

    @Override
    public int getItemCount() {
        return taskList != null ? taskList.size() : 0;
    }

    public void updateTasks(List<Task> tasks) {
        this.taskList = tasks;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionText, priorityText, dueDateText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            descriptionText = itemView.findViewById(R.id.taskDescription);
            dueDateText = itemView.findViewById(R.id.taskDueDate);
            priorityText = itemView.findViewById(R.id.taskPriority);
        }
    }

}
