package com.munoz.diego.projectem07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.munoz.diego.projectem07.modelo.Modelo;
import com.yariksoffice.lingver.Lingver;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LA:Login";
    private EditText m_user;
    private EditText m_pass;

    private Button m_login;

    private Modelo m_modelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Modelo.initModelo();
        m_modelo  = Modelo.getModelo();
        if(m_modelo.getCurrentUser() != null){
            finish();
            //overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        try {
            Lingver.init(LoginActivity.this.getApplication(), "ca");
        } catch (IllegalStateException e) {}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        m_login = findViewById(R.id.btnLogin);

        m_user = findViewById(R.id.etUser);
        m_pass = findViewById(R.id.etPassword);

        ImageButton catalan = findViewById(R.id.ib_catalan);
        ImageButton castellano = findViewById(R.id.ib_castellano);
        ImageButton ingles = findViewById(R.id.ib_ingles);

        catalan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lingver.getInstance().setLocale(LoginActivity.this.getApplication(), "ca");
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(getIntent());
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        castellano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lingver.getInstance().setLocale(LoginActivity.this.getApplication(), "es");
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(getIntent());
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        ingles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lingver.getInstance().setLocale(LoginActivity.this.getApplication(), "en");
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                startActivity(getIntent());
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    public void handleRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void handleLogin(View view) {
        String user, password;

        m_login.setEnabled(false);
        //SharedPreferences user_info = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        user = m_user.getText().toString();
        password = m_pass.getText().toString();

        if(!user.equals("") && !password.equals(""))
            firebaseLogin(user, password);
        else{
            m_login.setEnabled(true);
            Toast.makeText(LoginActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void firebaseLogin(String email, String password) {
        m_modelo.getFirebaseAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            m_modelo.reloadCurrentUser();
                            finish();
                            //overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            m_login.setEnabled(true);
                        }
                    }
                });
    }
}
