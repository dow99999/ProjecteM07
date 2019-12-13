package com.munoz.diego.projectem07;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText m_Nombre;
    private EditText m_Usuario;
    private EditText m_Email;
    private EditText m_Contrasena;
    private EditText m_Contrasena2;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        m_Nombre = findViewById(R.id.etNombre);
        m_Usuario = findViewById(R.id.etUsuario);
        m_Email = findViewById(R.id.etEmail);
        m_Contrasena = findViewById(R.id.etContrasena);
        m_Contrasena2 = findViewById(R.id.etContrasena2);



        Context context = this.getApplicationContext();

        sharedPref = context.getSharedPreferences(
                getString(R.string.app_name), Context.MODE_PRIVATE);


    }

    public void handleCrearUsuario (View view){
        String nombre = m_Nombre.getText().toString();
        String usuario = m_Usuario.getText().toString();
        String email = m_Email.getText().toString();
        String paswd1 = m_Contrasena.getText().toString();
        String paswd2 = m_Contrasena2.getText().toString();

        SharedPreferences user_info= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("nombre", nombre);
        editor.putString("user", usuario);
        editor.putString("email", email);
        if(paswd1 == paswd2){
            editor.putString("pass", paswd1);
            editor.putString("pass2", paswd2);
        } else{
            Toast.makeText(this, "Las contraseñs no coinciden", Toast.LENGTH_SHORT).show();
        }
        }

    }

