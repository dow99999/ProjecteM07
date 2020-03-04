package com.munoz.diego.projectem07.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.munoz.diego.projectem07.R;
import com.munoz.diego.projectem07.controlador.PostAdapter;
import com.munoz.diego.projectem07.modelo.Modelo;
import com.munoz.diego.projectem07.modelo.Post;
import com.munoz.diego.projectem07.modelo.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public static boolean first_run;

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private List<Post> m_posts = new ArrayList<>();
    private PostAdapter m_postAdapter;

    private SwipeRefreshLayout mSwipeContainer;

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = root.findViewById(R.id.rvPosts);
        mSwipeContainer = root.findViewById(R.id.swipeContainer);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //public Post(String titulo, String desc, LocalDateTime fecha, Usuario u, String[] foto){
        m_postAdapter = new PostAdapter(getActivity(), m_posts);
        mRecyclerView.setAdapter(m_postAdapter);
        mSwipeContainer.setRefreshing(true);

        SharedPreferences prefs = mContext.getSharedPreferences("com.munoz.diego.projectem07", Context.MODE_PRIVATE);

        final int posts = prefs.getInt("num_posts", 5);

        Post.getNPosts(posts, m_postAdapter, mSwipeContainer);


        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Post.getNPosts(posts, m_postAdapter, mSwipeContainer);
            }
        });
        Log.i("posts: ", String.valueOf(m_postAdapter.getItemCount()));

        return root;
    }
}