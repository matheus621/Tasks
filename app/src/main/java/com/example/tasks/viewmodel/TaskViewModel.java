package com.example.tasks.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tasks.service.listener.APIListener;
import com.example.tasks.service.listener.Feedback;
import com.example.tasks.service.model.PriorityModel;
import com.example.tasks.service.model.TaskModel;
import com.example.tasks.service.repository.PriorityRepository;
import com.example.tasks.service.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private PriorityRepository mPriorityRepository;
    private TaskRepository mTaskRepository;

    private MutableLiveData<List<PriorityModel>> mListPriority = new MutableLiveData<>();
    public LiveData<List<PriorityModel>> listPriority = this.mListPriority;

    private MutableLiveData<Feedback> mTaskSave = new MutableLiveData<>();
    public LiveData<Feedback> taskSave = this.mTaskSave;

    public TaskViewModel(@NonNull Application application) {
        super(application);

        this.mPriorityRepository = new PriorityRepository(application);
        this.mTaskRepository = new TaskRepository(application);
    }

    public void getList(){
        List<PriorityModel> lst = this.mPriorityRepository.getList();
        this.mListPriority.setValue(lst);
    }

    public void save(TaskModel task){
        this.mTaskRepository.save(task, new APIListener<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                mTaskSave.setValue(new Feedback());
            }

            @Override
            public void onFailure(String message) {
                mTaskSave.setValue(new Feedback(message));
            }
        });
    }

}
