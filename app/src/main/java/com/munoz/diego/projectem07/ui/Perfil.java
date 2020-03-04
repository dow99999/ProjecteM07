package com.munoz.diego.projectem07.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.munoz.diego.projectem07.MainActivity;
import com.munoz.diego.projectem07.R;
import com.munoz.diego.projectem07.modelo.Usuario;

public class Perfil extends AppCompatActivity {

    private ImageView m_foto;
    private TextView m_usuari;
    private TextView m_email;
    private TextView m_nom;
    private TextView m_sexo;
    private TextView m_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        m_foto = findViewById(R.id.iv_perfil_foto);
        m_usuari = findViewById(R.id.tv_perfil_usuario);
        m_email = findViewById(R.id.tv_perfil_email);
        m_nom = findViewById(R.id.tv_perfil_nombre);
        m_sexo = findViewById(R.id.tv_perfil_sexo);
        m_uid = findViewById(R.id.tv_perfil_uid);

        if(MainActivity.currentUser != null){
            Usuario u = MainActivity.currentUser;
            m_usuari.setText(u.getUsuario());
            m_email.setText(u.getEmail());
            m_nom.setText(u.getNombre());
            m_sexo.setText(u.getSexo());
            m_uid.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());

        } else {
            m_usuari.setText("-");
            m_email.setText("-");
            m_nom.setText("-");
            m_sexo.setText("-");
            m_uid.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
        }
    }
}
