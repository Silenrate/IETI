package co.edu.eci.ieti.android.repositories;

import android.content.Context;

import java.util.List;

import co.edu.eci.ieti.android.network.RetrofitNetwork;
import co.edu.eci.ieti.android.network.service.TaskService;
import co.edu.eci.ieti.android.persistence.AppDB;
import co.edu.eci.ieti.android.persistence.dao.TaskDao;
import co.edu.eci.ieti.android.persistence.entities.Task;

public class TaskRepository {

    private final TaskDao taskDao;
    private final TaskService taskService;

    public TaskRepository(String token, Context context) {
        taskDao = AppDB.getDatabase(context).taskDao();
        taskService = new RetrofitNetwork(token).getTaskService();
    }

    public List<Task> getTasks() {
        try {
            List<Task> tasks = taskService.getTasks().execute().body();
            if (tasks != null) {
                taskDao.deleteAll();
                taskDao.insertAll(tasks);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taskDao.getAll();
    }

    public void insertAll(List<Task> tasks) {
        AppDB.databaseWriteExecutor.execute(() -> {
            taskDao.insertAll(tasks);
        });
    }
}
