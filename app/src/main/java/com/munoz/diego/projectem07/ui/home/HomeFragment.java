package com.munoz.diego.projectem07.ui.home;

import android.os.Bundle;
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

import com.munoz.diego.projectem07.R;
import com.munoz.diego.projectem07.controlador.PostAdapter;
import com.munoz.diego.projectem07.modelo.Post;

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

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //m_posts = new ArrayList<>();

        //m_postAdapter = new PostAdapter(this, m_posts);


        return root;
    }
}