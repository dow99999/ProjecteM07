package com.munoz.diego.projectem07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        String user,
                password;

        SharedPreferences user_info= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        user = user_info.getString("user", "++--");
        password = user_info.getString("pass", "--++");

        if(m_user.getText().toString().equals(user) &&
                m_pass.getText().toString().equals(password)) {
           Intent intent = new Intent(this, MainActivity.class);
           intent.putExtra("user", m_user.getText().toString());
           startActivity(intent);
        } else {
            Toast.makeText(this, "Usuario invalido", Toast.LENGTH_SHORT).show();
        }
    }
}
