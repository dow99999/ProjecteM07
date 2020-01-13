package com.munoz.diego.projectem07.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.munoz.diego.projectem07.R;
import com.munoz.diego.projectem07.controlador.PostAdapter;
import com.munoz.diego.projectem07.modelo.Modelo;
import com.munoz.diego.projectem07.modelo.Post;
import com.munoz.diego.projectem07.modelo.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private ArrayList<Post> m_posts;
    private PostAdapter m_postAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = root.findViewById(R.id.rvPosts);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        m_posts = new ArrayList<>();

        //public Post(String titulo, String desc, LocalDateTime fecha, Usuario u, String[] foto){
        m_posts.add(new Post("Png hosteado en una web", "Arnau dia uno no esta tan mal", LocalDateTime.now(), new Usuario(), new String[]{"https://dam.ngenespanol.com/wp-content/uploads/2019/01/ardilla-roja-adoptan-770x395.png"}));
        m_posts.add(new Post("Foto google drive", "Arnau dia uno no esta tan mal", LocalDateTime.now(), new Usuario(), new String[]{"https://drive.google.com/uc?id=1YGK8EwywQEuqgIOTepI8N0ZPr_YlIvVX"}));

        m_postAdapter = new PostAdapter(getActivity(), m_posts);

        mRecyclerView.setAdapter(m_postAdapter);


        return root;
    }
}