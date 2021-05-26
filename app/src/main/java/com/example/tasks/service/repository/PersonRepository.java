package com.example.tasks.service.repository;

import com.example.tasks.service.model.PersonModel;
import com.example.tasks.service.repository.remote.PersonService;
import com.example.tasks.service.repository.remote.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonRepository {

    private PersonService mPersonService;

    public PersonRepository(){
        this.mPersonService = RetrofitClient.createService(PersonService.class);

    }

    public void create(String name, String email, String password){
        Call<PersonModel> call = this.mPersonService.create(name, email, password, true);
        call.enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {
                PersonModel p = response.body();
                int code = response.code();
            }

            @Override
            public void onFailure(Call<PersonModel> call, Throwable t) {

            }
        });
    }
}
