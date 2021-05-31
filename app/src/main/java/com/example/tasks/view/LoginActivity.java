package com.example.tasks.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasks.R;
import com.example.tasks.service.listener.Feedback;
import com.example.tasks.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private LoginViewModel mLoginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Incializa as variáveis
        this.mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        this.mViewHolder.edtEmail = findViewById(R.id.edt_email);
        this.mViewHolder.edtPassword = findViewById(R.id.edt_password);
        this.mViewHolder.btnLogin = findViewById(R.id.button_login);
        this.mViewHolder.textRegister = findViewById(R.id.text_register);

        this.setListerners();

        // Cria observadores
        this.loadObservers();

        this.verifyUserLogged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_login) {

            String email = this.mViewHolder.edtEmail.getText().toString();
            String password = this.mViewHolder.edtPassword.getText().toString();

            this.mLoginViewModel.login(email, password);

        } else if (id == R.id.text_register){
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        }
    }

    private void setListerners() {
        this.mViewHolder.btnLogin.setOnClickListener(this);
        this.mViewHolder.textRegister.setOnClickListener(this);
    }

    private void loadObservers() {
        this.mLoginViewModel.login.observe(this, new Observer<Feedback>() {
            @Override
            public void onChanged(Feedback feedback) {
                if (feedback.isSuccess()) {
                    startMain();
                } else {
                    Toast.makeText(getApplicationContext(), feedback.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.mLoginViewModel.userLogged.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean logged) {
                if (logged) {
                    startMain();
                }
            }
        });
    }

    private void verifyUserLogged() {
        this.mLoginViewModel.verifyUserLogged();
    }

    private void startMain(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * ViewHolder
     */
    private static class ViewHolder {

        EditText edtEmail;
        EditText edtPassword;
        Button btnLogin;
        TextView textRegister;
    }

}