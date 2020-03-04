package com.munoz.diego.projectem07.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.munoz.diego.projectem07.R;
import com.munoz.diego.projectem07.controlador.PostAdapter;
import com.munoz.diego.projectem07.libs.ZoomableImageView;
import com.munoz.diego.projectem07.modelo.Post;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ElPost extends AppCompatActivity {

    ZoomableImageView iv_foto;
    TextView v_titulo;
    TextView v_animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_el_post);

        iv_foto = findViewById(R.id.iv_foto_ver_post);
        v_titulo = findViewById(R.id.tv_titulo_ver_post);
        v_animal = findViewById(R.id.tv_animal_ver_post);

        getPost(getIntent().getLongExtra("id", 0));

    }

    private final static String acaba = "";
    private static boolean final_boolean;

    public void getPost(final long i){
        final Post auxPost = new Post();
        final_boolean = false;
        DatabaseReference ref =
                FirebaseDatabase.getInstance().getReference("posts").child(Post.getIdAsString(i));
        //Log.i("ye", "id: " + Post.getIdAsString(i));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {//TODO
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    final String titulo = dataSnapshot.child("titulo").getValue(String.class);
                    final String descripcion = dataSnapshot.child("desc").getValue(String.class);


                    //final Double lat = aux.child("lat").getValue(Double.class);
                    //final Double lon = aux.child("lon").getValue(Double.class);

                    synchronized (acaba){
                        auxPost.setTitulo(titulo);
                        auxPost.setId(i);
                        auxPost.setDescripcion(descripcion);
                        acaba.notifyAll();
                    }


                    DatabaseReference r = FirebaseDatabase.getInstance().getReference("imgs").child(Post.getIdAsString(i));
                    r.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            synchronized (acaba){

                                DataSnapshot finalAux = dataSnapshot.getChildren().iterator().next();
                                String img_strb64 = finalAux.getValue(String.class);
                                auxPost.setFotos(Post.convertStringToBitmap(img_strb64));


                                iv_foto.setImageBitmap(auxPost.getFotos());
                                v_titulo.setText(auxPost.getTitulo());
                                v_animal.setText(auxPost.getDescripcion());
                            }

                            //Log.i("ye", auxPost.getTitulo()==null?"nulo":"meh");

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
