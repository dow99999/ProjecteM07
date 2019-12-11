package com.munoz.diego.projectem07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText m_user;
    private EditText m_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_user = findViewById(R.id.etUser);
        m_pass = findViewById(R.id.etPassword);

    }

    public void handleRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void handleLogin(View view){

        //TODO hacer la comprobacion del usuario/password

        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("user", m_user.getText().toString());
        startActivity(intent);
    }
}
