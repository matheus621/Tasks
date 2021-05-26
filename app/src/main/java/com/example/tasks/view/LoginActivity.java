package com.example.tasks.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tasks.R;
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

        this.setListerners();

        // Cria observadores
        this.loadObservers();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_login) {

            String email = this.mViewHolder.edtEmail.getText().toString();
            String password = this.mViewHolder.edtPassword.getText().toString();

            this.mLoginViewModel.login(email, password);

        }
    }

    private void setListerners() {
        this.mViewHolder.btnLogin.setOnClickListener(this);
    }

    private void loadObservers() {
    }


    /**
     * ViewHolder
     */
    private static class ViewHolder {

        EditText edtEmail;
        EditText edtPassword;
        Button btnLogin;
    }

}