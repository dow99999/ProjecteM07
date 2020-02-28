package com.munoz.diego.projectem07.modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Post {

    private static long m_nextId;
    private static List<Post> postList = new ArrayList<>();
    private static boolean aun_no;

    public static void initNextId(){
        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("posts");


        ref.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String id = dataSnapshot.getChildren().iterator().next().getKey();
                if( id != null) {
                    m_nextId = Long.valueOf(id) + 1;
                    Log.i("id", "no null " + m_nextId);
                }
                else {
                    m_nextId = 0;
                    Log.i("id", "null " + m_nextId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public static long getNextId(){
        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("posts");


            ref.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String id = dataSnapshot.getChildren().iterator().next().getKey();
                    if( id != null) {
                        m_nextId = Long.valueOf(id) + 1;
                        Log.i("id", "no null " + m_nextId);
                    }
                    else {
                        m_nextId = 0;
                        Log.i("id", "null " + m_nextId);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        return m_nextId;
    }

    public static List<Post> getNPosts(final int i, final SwipeRefreshLayout swipe){

        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("posts");

        aun_no = true;

        ref.orderByKey().limitToLast(i).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                String id = null;
                DataSnapshot aux;

                    for (Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator(); it.hasNext();){
                        aux = it.next();
                        long aux_id = Long.valueOf(aux.getKey());

                        String titulo = aux.child("titulo").getValue(String.class);
                        String descripcion = aux.child("desc").getValue(String.class);
                        String img_strb64 = aux.child("imageUrl").getValue(String.class);

                        postList.add(new Post(aux_id, titulo, descripcion, convertStringToBitmap(img_strb64)));
                    }
                    aun_no = false;

                    Log.i("postLoad", "la i. " + i);
                    swipe.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return postList;
    }

    public static Bitmap convertStringToBitmap(String b){
        Bitmap bitmap;
        byte[] foto = Base64.decode(b, Base64.DEFAULT);

        bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
        return bitmap;
    }

    //id de 10 de longitud
    public static String getIdAsString(long id){
        String idString = String.valueOf(id);
        int falta = 10 - idString.length();
        String aux = "";
        for(int i = 0; i < falta; i++){
            aux += "0";
        }

        return aux + idString;
    }


    private long m_id;
    private String m_titulo;
    private String m_descripcion;
    private Usuario m_usuario;
    private Bitmap m_foto;

    public long getId(){ return m_id; }
    public String getTitulo(){ return m_titulo; }
    public String getDescripcion(){ return m_descripcion; }
    public Usuario getUsuario(){ return m_usuario; }
    public Bitmap getFotos(){ return m_foto; }

    public Post(){

    }

    public Post(long id, String titulo, String desc, Bitmap foto){
        m_id = id;
        m_titulo = titulo;
        m_descripcion = desc;
        m_foto = foto;
    }

    public Post setId(long id){ m_id = id; return this; }
    public Post setTitulo(String t){ m_titulo = t; return this; }
    public Post setDescripcion(String d){ m_descripcion = d; return this; }
    public Post setUsuario(Usuario u){ m_usuario = u; return this; }
    public Post setFotos(Bitmap foto){ m_foto = foto; return this; }


}
