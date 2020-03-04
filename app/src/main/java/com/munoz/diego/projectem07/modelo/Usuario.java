package com.munoz.diego.projectem07.modelo;

public class Usuario {
    private String m_nombre;
    private String m_usuario;
    private String m_email;
    private String m_pass;
    private String m_sexo;

    public Usuario(){
    }

    public Usuario(String nombre, String usuario, String email, String sexo){
        m_nombre = nombre;
        m_usuario = usuario;
        m_email = email;
        m_sexo = sexo;
    }

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
    public void setSexo(String p){ m_sexo = p; }

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
    public String getSexo(){
        return m_sexo;
    }
}
