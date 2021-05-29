package com.example.tasks.service.repository;

import android.content.Context;

import com.example.tasks.R;
import com.example.tasks.service.constants.TaskConstants;
import com.example.tasks.service.listener.APIListener;
import com.example.tasks.service.model.TaskModel;
import com.example.tasks.service.repository.remote.RetrofitClient;
import com.example.tasks.service.repository.remote.TaskService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskRepository extends BaseRepository {

    private TaskService mTaskService;

    public TaskRepository(Context context) {
        super(context);
        this.mTaskService = RetrofitClient.createService(TaskService.class);
    }

    public void save(TaskModel taskModel, final APIListener<Boolean> listener) {
        Call<Boolean> call = mTaskService.create(
                taskModel.getPriorityId(),
                taskModel.getDescription(),
                taskModel.getDueDate(),
                taskModel.getComplete()
        );
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == TaskConstants.HTTP.SUCCESS) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(handleFailure(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                listener.onFailure(mContext.getString(R.string.ERROR_UNEXPECTED));
            }
        });
    }
}
