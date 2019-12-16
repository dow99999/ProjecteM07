package com.munoz.diego.projectem07.modelo;

import java.time.LocalDateTime;

public class Post {
    private String m_titulo;
    private String m_descripcion;
    private LocalDateTime m_fecha;
    private Usuario m_usuario;
    private String[] m_foto;

    public String getTitulo(){ return m_titulo; }
    public String getDescripcion(){ return m_descripcion; }
    public LocalDateTime getFechaRaw(){ return m_fecha; }
    public String getFecha() { return m_fecha.toString(); }
    public Usuario getUsuario(){ return m_usuario; }
    public String[] getFotos(){ return m_foto; }

    public void setTitulo(String t){ m_titulo = t; }
    public void setDescripcion(String d){ m_descripcion = d; }
    public void setFechaRaw(LocalDateTime e){ m_fecha = e; }
    public void setUsuario(Usuario u){ m_usuario = u; }
    public void setFotos(String[] foto){ m_foto = foto; }


}
