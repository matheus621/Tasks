package com.example.tasks.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tasks.R;
import com.example.tasks.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private RegisterViewModel mRegisterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Botão de voltar nativo
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        this.mViewHolder.edtName = findViewById(R.id.edt_name);
        this.mViewHolder.edtEmail = findViewById(R.id.edt_email);
        this.mViewHolder.edtPassword = findViewById(R.id.edt_password);
        this.mViewHolder.btnCreate = findViewById(R.id.button_create);

        this.mViewHolder.btnCreate.setOnClickListener(this);

        // Incializa variáveis
        this.mRegisterViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Cria observadores
        this.loadObservers();
    }

    private void loadObservers() {}

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_create){

            String name = this.mViewHolder.edtName.getText().toString();
            String email = this.mViewHolder.edtEmail.getText().toString();
            String password = this.mViewHolder.edtPassword.getText().toString();

            this.mRegisterViewModel.create(name, email, password);

        }
    }

    /**
     * ViewHolder
     */
    private static class ViewHolder {
        EditText edtName;
        EditText edtEmail;
        EditText edtPassword;
        Button btnCreate;
    }

}