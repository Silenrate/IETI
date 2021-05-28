package co.edu.eci.ieti.android.network.service;

import java.util.List;

import co.edu.eci.ieti.android.persistence.entities.Task;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TaskService {

    @GET("task")
    Call<List<Task>> getTasks();
}
