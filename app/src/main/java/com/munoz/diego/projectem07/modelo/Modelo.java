package com.munoz.diego.projectem07.modelo;

import java.util.HashMap;
import java.util.Map;

public class Modelo {

    public Modelo(){
        m_posts = new HashMap<>();
        m_usuarios = new HashMap<>();
    }

    public static void initModelo(){
        m_modelo = new Modelo();
    }

    public static Modelo getModelo(){
        return m_modelo;
    }

    public void setCurrentUser(Usuario u){
        m_current_user = u;
    }

    public Usuario getCurrentUser(){
        return m_current_user;
    }

    public void addUser(Usuario u){
        m_usuarios.put(u.getUsuario(), u);
    }

    private static Modelo m_modelo;

    private Map<Usuario, Post> m_posts;
    private Map<String, Usuario> m_usuarios;

    private Usuario m_current_user;



}
