package com.bkassistant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.SongHolder> {

    private MusicItemClickListener musicItemClickListener;
    private LayoutInflater layoutInflater;
    private ArrayList<SongDetail> songDetailsList;


    MusicListAdapter(Context context, ArrayList<SongDetail> songDetails){
        this.songDetailsList = songDetails;
        this.layoutInflater = LayoutInflater.from(context);
        System.out.println(songDetails.size());
    }

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.song_row, viewGroup, false);
        return new SongHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull SongHolder songHolder, final int i) {
        songHolder.songName.setText(songDetailsList.get(i).songName);
        songHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(musicItemClickListener != null){
                    musicItemClickListener.onMusicItemClicked(v,i,songDetailsList.get(i));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return songDetailsList.size();
    }


    public interface MusicItemClickListener{
        void onMusicItemClicked(View view, int position , SongDetail songDetail);
    }

    public void setMusicItemClickListener(MusicItemClickListener musicItemClickListener) {
        this.musicItemClickListener = musicItemClickListener;
    }

    public class SongHolder extends RecyclerView.ViewHolder{
        TextView songName;
        ImageButton imageButton;
        CardView cardView;
        public SongHolder(@NonNull View itemView) {
            super(itemView);
            this.songName = itemView.findViewById(R.id.songNameTextLabel);
            this.cardView = itemView.findViewById(R.id.songCardViewHolder);
        }
    }
}
