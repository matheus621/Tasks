package com.example.tasks.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tasks.service.listener.APIListener;
import com.example.tasks.service.listener.Feedback;
import com.example.tasks.service.model.TaskModel;
import com.example.tasks.service.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class TaskListViewModel extends AndroidViewModel {

    private TaskRepository mTaskRepository;

    private MutableLiveData<List<TaskModel>> mList = new MutableLiveData<>();
    public LiveData<List<TaskModel>> list = this.mList;

    private MutableLiveData<Feedback> mFeedback = new MutableLiveData<>();
    public LiveData<Feedback> feedback = this.mFeedback;

    public TaskListViewModel(@NonNull Application application) {
        super(application);
        this.mTaskRepository = new TaskRepository(application);
    }

    public void list() {
        this.mTaskRepository.all(new APIListener<List<TaskModel>>() {
            @Override
            public void onSuccess(List<TaskModel> result) {
                mList.setValue(result);
            }

            @Override
            public void onFailure(String message) {
                mList.setValue(new ArrayList<TaskModel>());
                mFeedback.setValue(new Feedback(message));
            }
        });
    }

}