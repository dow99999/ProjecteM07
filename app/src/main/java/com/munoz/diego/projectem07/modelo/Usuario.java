package com.munoz.diego.projectem07.modelo;

public class Usuario {
    private String m_usuario;
    private String m_nombre;
    private String m_email;
    private String m_pass;

    public void setUsuario(String u){
        m_usuario = u;
    }
    public void setNombre(String n){
        m_nombre = n;
    }
    public void setEmail(String e){
        m_email = e;
    }
    public void setPass(String p){
        m_pass = p;
    }

    public String getUsuario(){
        return m_usuario;
    }
    public String getNombre(){
        return m_nombre;
    }
    public String getEmail(){
        return m_email;
    }
    public String getPass(){
        return m_pass;
    }
}
