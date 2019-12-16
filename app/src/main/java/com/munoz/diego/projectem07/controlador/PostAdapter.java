package com.munoz.diego.projectem07.controlador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.munoz.diego.projectem07.R;
import com.munoz.diego.projectem07.modelo.Post;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private ArrayList<Post> m_postData;
    private Context m_context;

    public PostAdapter(Context context, ArrayList<Post> gameData){
        m_context = context;
        m_postData = gameData;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(m_context).inflate(R.layout.list_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post currentPost = m_postData.get(position);

        holder.bindTo(currentPost);
    }

    @Override
    public int getItemCount() {
        return m_postData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        private TextView mTitleText;
        private TextView mInfoText;
        private ImageView mPostImage;


        ViewHolder(View itemView){
            super(itemView);

            mTitleText = itemView.findViewById(R.id.title);
            mPostImage = itemView.findViewById(R.id.PostImage);

            itemView.setOnClickListener(this);
        }
        void bindTo(Post currentPost){
            // Populate the textviews with data.
            mTitleText.setText(currentPost.getTitulo());

            // Load the images into the ImageView using the Glide library.
            Glide.with(m_context).load(
                    currentPost.getFotos()[0]).into(mPostImage);
        }

        @Override
        public void onClick(View view) {
            //TODO: que ocurre si clicamos un post
        }
    }
}
