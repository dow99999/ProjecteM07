package com.munoz.diego.projectem07.modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PostProcessor;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.munoz.diego.projectem07.controlador.PostAdapter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
                try{

                String id = dataSnapshot.getChildren().iterator().next().getKey();
                if( id != null) {
                    m_nextId = Long.valueOf(id) + 1;
                    //Log.i("id", "no null " + m_nextId);
                }
                else {
                    m_nextId = 0;
                    //Log.i("id", "null " + m_nextId);
                }
                }catch(NoSuchElementException e){
                    m_nextId = 0;
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
                        //Log.i("id", "no null " + m_nextId);
                    }
                    else {
                        m_nextId = 0;
                        //Log.i("id", "null " + m_nextId);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        return m_nextId;
    }

    public static void populateMap(int markers, final GoogleMap map){

        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("posts");

        ref.orderByKey().limitToLast(markers).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                DataSnapshot aux;

                for (Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator(); it.hasNext();){
                    aux = it.next();

                    String titulo = aux.child("titulo").getValue(String.class);
                    Double lat = aux.child("lat").getValue(Double.class);
                    Double lon = aux.child("lon").getValue(Double.class);

                    if(lat!=null && lon!= null){
                        LatLng pos = new LatLng(lat, lon);
                        map.addMarker(new MarkerOptions().position(pos).title(titulo).visible(true)).setSnippet(aux.getKey());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private final static String acaba = "";
    private static boolean final_boolean;

    public static void getNPosts(final int i, final PostAdapter adaptador, final SwipeRefreshLayout swipe){
        final_boolean = false;
        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("posts");

        aun_no = true;

        ref.orderByKey().limitToLast(i).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                adaptador.clear();
                String id = null;
                DataSnapshot aux;

                for (Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator(); it.hasNext();){
                    aux = it.next();
                    final long aux_id = Long.valueOf(aux.getKey());

                    final String titulo = aux.child("titulo").getValue(String.class);
                    final String descripcion = aux.child("desc").getValue(String.class);
                    final Double lat = aux.child("lat").getValue(Double.class);
                    final Double lon = aux.child("lon").getValue(Double.class);

                    synchronized (acaba){
                        postList.add(new Post(aux_id, titulo, descripcion, null, lat, lon));
                        acaba.notifyAll();
                        Log.i("ll", "notifica normal");
                    }


                    DatabaseReference r = FirebaseDatabase.getInstance().getReference("imgs").child(getIdAsString(aux_id));
                    r.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            synchronized (acaba){
                                //adaptador.clear();

                                Log.i("ll", dataSnapshot.getKey());

                                DataSnapshot finalAux = dataSnapshot.getChildren().iterator().next();

                                Log.i("ll", "key: " + finalAux.getKey());
                                String img_strb64 = finalAux.getValue(String.class);
                                Log.i("ll", "img: " + img_strb64);

                                Log.i("ll", "foto");

                                for(int j = 0; j < postList.size(); j++){
                                    Post aaauuuxxx = postList.get(j);

                                    if(getIdAsString(aaauuuxxx.getId()).equals(dataSnapshot.getKey()))
                                        aaauuuxxx.setFotos(convertStringToBitmap(img_strb64));

                                }

                                for(int j = 0; j < postList.size(); j++){
                                    if(postList.get(j).getFotos()==null){
                                        break;
                                    } else {
                                        if(j == postList.size() - 1)
                                            final_boolean = true;
                                    }
                                }
                                //NICE SPAGHETTI CODE
                                if(final_boolean){
                                    List<Post> auxx = new ArrayList<>();
                                    adaptador.clear();
                                    for(int j = postList.size() - 1; j >= 0; j--){
                                        auxx.add(postList.get(j));
                                    }

                                    adaptador.addAll(auxx);
                                    acaba.notifyAll();
                                    Log.i("ll", "notifica foto");
                                    Log.i("postLoad", "la i. " + i);
                                    swipe.setRefreshing(false);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                swipe.setRefreshing(false);
            }
        });

    }

    public static void cachePosts(final PostAdapter pa, final SwipeRefreshLayout swipe){
        for(int j = postList.size() - 1; j >= 0; j--){
            pa.add(postList.get(j));
        }
        swipe.setRefreshing(false);
    }

    public static void getNPostsSearch(final int i, final String search, final PostAdapter adaptador, final SwipeRefreshLayout swipe){
        //TODO ni caso de esto de momento
        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("posts");

        aun_no = true;

        ref.orderByKey().limitToLast(i).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                adaptador.clear();
                String id = null;
                DataSnapshot aux;

                    for (Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator(); it.hasNext();){
                        aux = it.next();
                        long aux_id = Long.valueOf(aux.getKey());

                        String titulo = aux.child("titulo").getValue(String.class);
                        String descripcion = aux.child("desc").getValue(String.class);
                        String img_strb64 = aux.child("imageUrl").getValue(String.class);
                        Double lat = aux.child("lat").getValue(Double.class);
                        Double lon = aux.child("lon").getValue(Double.class);

                        postList.add(new Post(aux_id, titulo, descripcion, convertStringToBitmap(img_strb64), lat, lon));
                        adaptador.add(postList.get(postList.size() - 1));
                    }
                    aun_no = false;

                    //adaptador.addAll(postList);
                    Log.i("postLoad", "la i. " + i);
                    swipe.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                swipe.setRefreshing(false);
            }
        });
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

    private Double m_lat;
    private Double m_lon;

    public long getId(){ return m_id; }
    public String getTitulo(){ return m_titulo; }
    public String getDescripcion(){ return m_descripcion; }
    public Usuario getUsuario(){ return m_usuario; }
    public Bitmap getFotos(){ return m_foto; }
    public Double getLat(){ return m_lat; }
    public Double getLon(){ return m_lon; }

    public Post(){

    }

    public Post(long id, String titulo, String desc, Bitmap foto, Double lat, Double lon){
        m_id = id;
        m_titulo = titulo;
        m_descripcion = desc;
        m_foto = foto;
        m_lon = lon;
        m_lat = lat;
    }

    public Post setId(long id){ m_id = id; return this; }
    public Post setTitulo(String t){ m_titulo = t; return this; }
    public Post setDescripcion(String d){ m_descripcion = d; return this; }
    public Post setUsuario(Usuario u){ m_usuario = u; return this; }
    public Post setFotos(Bitmap foto){ m_foto = foto; return this; }


}
