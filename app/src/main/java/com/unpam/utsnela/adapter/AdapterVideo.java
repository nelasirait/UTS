package com.unpam.utsnela.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unpam.utsnela.R;
import com.unpam.utsnela.model.VideoModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.MyViewHolder> {
    private List<VideoModel> video;
    private Context context;

    private OnRecycleViewClickListener listener;

    public interface OnRecycleViewClickListener{
        void OnItemClick(int position);
    }

    public void OnRecycleViewClickListener (OnRecycleViewClickListener listener)
    {
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, time, detail;

        ImageView img;
        MyViewHolder(View view, OnRecycleViewClickListener listener) {
            super(view);
            title = view.findViewById(R.id.title);
            time = view.findViewById(R.id.time);
            img = view.findViewById(R.id.gambar_berita);
            detail = view.findViewById(R.id.detail);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                        listener.OnItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }

    public AdapterVideo(List<VideoModel> video) {
        this.video = video;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.desain_new_video, parent, false);
        return new AdapterVideo.MyViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        VideoModel berita1 = video.get(position);
        holder.title.setText(berita1.getTitle());
        holder.time.setText(berita1.getTime());
        holder.detail.setText(berita1.getTagline());
        Picasso.with(holder.img.getContext()).load(berita1.getImage()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return video.size();
    }
}
