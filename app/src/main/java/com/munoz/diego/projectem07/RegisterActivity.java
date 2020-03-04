package com.munoz.diego.projectem07;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.munoz.diego.projectem07.modelo.Modelo;
import com.munoz.diego.projectem07.modelo.Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RA:Registro";
    private EditText m_Nombre;
    private EditText m_Usuario;
    private EditText m_Email;
    private EditText m_Contrasena;
    private EditText m_Contrasena2;

    private RadioGroup m_sexo;
    private RadioButton m_hombre;
    private RadioButton m_mujer;
    private RadioButton m_otro;

    private Button b_registrar;


    private final Modelo m_modelo = Modelo.getModelo();//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        m_Nombre = findViewById(R.id.etNombre);
        m_Usuario = findViewById(R.id.etUsuario);
        m_Email = findViewById(R.id.etEmail);
        m_Contrasena = findViewById(R.id.etContrasena);
        m_Contrasena2 = findViewById(R.id.etContrasena2);

        m_sexo = findViewById(R.id.rg_grupo_de_sexo_uwu);
        m_hombre = findViewById(R.id.rbhombre);
        m_mujer = findViewById(R.id.rbmujer);
        m_otro = findViewById(R.id.rbotro);

        b_registrar = findViewById(R.id.btn_Usuario);

    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();
    }

    public void handleCrearUsuario(View view) {
        String nombre = m_Nombre.getText().toString();
        String usuario = m_Usuario.getText().toString();
        String email = m_Email.getText().toString();
        String paswd1 = m_Contrasena.getText().toString();
        String paswd2 = m_Contrasena2.getText().toString();


        //SharedPreferences user_info = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //SharedPreferences.Editor editor = user_info.edit();
        b_registrar.setEnabled(true);

        if (nombre.equals("") || usuario.equals("") || email.equals("") || paswd1.equals("")) {
            showToast("Debes rellenar todos los campos.");
        } else if(!paswd1.equals(paswd2)){
            showToast("Las contraseñas no coinciden");
        }else if (paswd1.length() <6) {
            showToast("La contraseña ha de tener más de 6 caracteres.");
        } else {
            //editor.putString("nombre", nombre);
            //editor.putString("user", usuario);
            //editor.putString("email", email);
            //editor.putString("pass", paswd1);
            //int checkedRadioButtonId = m_sexo.getCheckedRadioButtonId();
            //editor.putInt("checkedRadioButtonId", checkedRadioButtonId);
            //editor.apply();
            registerFirebase(nombre, usuario, email, "---", paswd1);

            //finish();
        }
    }
    
    public void registerFirebase(final String nombre, final String usuario, final String email, final String sexo, String password) {
        m_modelo.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            m_modelo.reloadCurrentUser();
                            showToast("Usuario/a Creado");
                            b_registrar.setEnabled(false);

                            DatabaseReference ref;

                            ref = FirebaseDatabase.getInstance().getReference("users").
                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            ref.setValue(new Usuario(nombre, usuario, email, sexo));

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "No se pudo crear el usuario",
                                    Toast.LENGTH_SHORT).show();
                            b_registrar.setEnabled(true);
                        }
                    }
                });
    }
}

