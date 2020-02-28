package com.munoz.diego.projectem07.controlador;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.munoz.diego.projectem07.R;
import com.munoz.diego.projectem07.modelo.Post;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    private List<Post> m_postData;
    private Context m_context;

    public PostAdapter(Context context, List<Post> postData){
        m_context = context;
        m_postData = postData;
    }

    public void clear() {
        m_postData.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        m_postData.addAll(list);
        notifyDataSetChanged();
    }

    public void add(Post post){
        m_postData.add(post);
        notifyDataSetChanged();
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
        private ImageView mPostImage;


        ViewHolder(View itemView){
            super(itemView);

            mTitleText = itemView.findViewById(R.id.title);
            mPostImage = itemView.findViewById(R.id.postImage);

            itemView.setOnClickListener(this);
        }
        void bindTo(Post currentPost){
            // Populate the textviews with data.
            mTitleText.setText(currentPost.getTitulo());

            Log.d("viewholder",currentPost.getTitulo());

            // Load the images into the ImageView using the Glide library.
            Glide.with(m_context).load(currentPost.getFotos())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            // log exception
                            Log.e("TAG", "Error loading image", e);
                            return false; // important to return false so the error placeholder can be placed
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(mPostImage);
        }

        @Override
        public void onClick(View view) {
            //TODO: que ocurre si clicamos un post
        }
    }
}
