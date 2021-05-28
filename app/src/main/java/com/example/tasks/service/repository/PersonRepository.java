package com.example.tasks.service.repository;

import android.content.Context;

import com.example.tasks.R;
import com.example.tasks.service.constants.TaskConstants;
import com.example.tasks.service.listener.APIListener;
import com.example.tasks.service.model.PersonModel;
import com.example.tasks.service.repository.local.SecurityPreferences;
import com.example.tasks.service.repository.remote.PersonService;
import com.example.tasks.service.repository.remote.RetrofitClient;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonRepository {

    private PersonService mPersonService;
    private Context mContext;
    private SecurityPreferences mSecurityPreferences;

    public PersonRepository(Context context) {
        this.mPersonService = RetrofitClient.createService(PersonService.class);
        this.mContext = context;
        this.mSecurityPreferences = new SecurityPreferences(context);
    }

    public void create(String name, String email, String password) {
        Call<PersonModel> call = this.mPersonService.create(name, email, password, true);
        call.enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {
                PersonModel p = response.body();
                int code = response.code();
                String s = "";
            }

            @Override
            public void onFailure(Call<PersonModel> call, Throwable t) {
                String s = "";
            }
        });
    }

    public void login(String email, String password, final APIListener<PersonModel> listener) {
        Call<PersonModel> call = this.mPersonService.login(email, password);
        call.enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {
                if (response.code() == TaskConstants.HTTP.SUCCESS) {
                    listener.onSuccess(response.body());
                } else {
                    try {
                        String json = response.errorBody().string();
                        String str = new Gson().fromJson(json, String.class);
                        listener.onFailure(str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonModel> call, Throwable t) {
                listener.onFailure(mContext.getString(R.string.ERROR_UNEXPECTED));
            }
        });
    }

    public void saveUserData(PersonModel model) {
        this.mSecurityPreferences.storeString(TaskConstants.SHARED.TOKEN_KEY, model.getToken());
        this.mSecurityPreferences.storeString(TaskConstants.SHARED.PERSON_KEY, model.getPersonKey());
        this.mSecurityPreferences.storeString(TaskConstants.SHARED.PERSON_NAME, model.getName());
    }

    public PersonModel getUserData() {
        PersonModel model = new PersonModel();
        model.setName(this.mSecurityPreferences.getStoreString(TaskConstants.SHARED.PERSON_NAME));
        model.setToken(this.mSecurityPreferences.getStoreString(TaskConstants.SHARED.TOKEN_KEY));
        model.setPersonKey(this.mSecurityPreferences.getStoreString(TaskConstants.SHARED.PERSON_KEY));

        return model;
    }
}
