package com.munoz.diego.projectem07.modelo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class Modelo {


    private Modelo(){
        m_auth = FirebaseAuth.getInstance();
        m_current_user = m_auth.getCurrentUser();
    }

    public static void initModelo(){
        m_modelo = new Modelo();
    }

    public static Modelo getModelo(){

        return m_modelo;
    }

    public void setCurrentUser(FirebaseUser u){
        m_current_user = u;
    }

    public FirebaseUser getCurrentUser(){
        return m_current_user;
    }

    public FirebaseAuth getFirebaseAuth(){
        return m_auth;
    }

    private static Modelo m_modelo;

    FirebaseUser m_current_user;
    private FirebaseAuth m_auth;

    public void reloadCurrentUser() {
        m_current_user = m_auth.getCurrentUser();
    }
}
