package com.example.sundari.accidentinfo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context mContext;
    private ArrayList<String> mUpload;

    public VideoAdapter() {
    }

    public VideoAdapter(Context mContext, ArrayList<String> mUpload) {
        this.mContext = mContext;
        this.mUpload = mUpload;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_video_list , viewGroup ,false);
        return new VideoViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder videoViewHolder, int i) {

        String string = mUpload.get(i);
        Uri uri = Uri.parse(string);

        videoViewHolder.videoView.setVideoURI(uri);
        videoViewHolder.videoView.requestFocus();
        videoViewHolder.videoView.start();

    }

    @Override
    public int getItemCount() {
        return mUpload.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public VideoView videoView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            videoView = itemView.findViewById(R.id.list_video_view);

        }
    }
}