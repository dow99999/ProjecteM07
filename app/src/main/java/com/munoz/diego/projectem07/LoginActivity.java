package com.munoz.diego.projectem07;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.munoz.diego.projectem07.modelo.Modelo;
import com.yariksoffice.lingver.Lingver;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    private EditText m_user;
    private EditText m_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try{
            Lingver.init(LoginActivity.this.getApplication(), "ca");
        }catch(IllegalStateException e){}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Modelo.initModelo();


        m_user = findViewById(R.id.etUser);
        m_pass = findViewById(R.id.etPassword);

        final ImageButton catalan = findViewById(R.id.ib_catalan);
        ImageButton castellano = findViewById(R.id.ib_castellano);
        ImageButton ingles = findViewById(R.id.ib_ingles);

        catalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lingver.getInstance().setLocale(LoginActivity.this.getApplication(), "ca");
                finish();
                startActivity(getIntent());
            }
        });

        castellano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lingver.getInstance().setLocale(LoginActivity.this.getApplication(), "es");
                finish();
                startActivity(getIntent());
            }
        });

        ingles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lingver.getInstance().setLocale(LoginActivity.this.getApplication(), "en");
                finish();
                startActivity(getIntent());
            }
        });
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

        if(m_user.getText().toString().equals(user)) {
            if(m_pass.getText().toString().equals(password)){
           Intent intent = new Intent(this, MainActivity.class);
           intent.putExtra("user", m_user.getText().toString());
           startActivity(intent);
            } else{
                Toast.makeText(this, "Password inválida.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Usuario inválido.", Toast.LENGTH_SHORT).show();
        }
    }

    private void runFadeInAnimation() {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        a.reset();
        ConstraintLayout ll = findViewById(R.id.);
        ll.clearAnimation();
        ll.startAnimation(a);
    }
}
