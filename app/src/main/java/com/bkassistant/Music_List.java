package com.bkassistant;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Music_List extends AppCompatActivity {

    Globals globals;
    RecyclerView recyclerView;
    MediaPlayerApi mediaPlayerApi;
    Dialog musicActionDialog;
    FloatingActionButton floatingActionButton;
    private SongDetail activeSongDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music__list);

        final ArrayList<SongDetail> inheritedSongs =(ArrayList<SongDetail>) getIntent().getSerializableExtra("songList");

        globals = (Globals) getApplicationContext();
        mediaPlayerApi = globals.getMediaPlayerApi();
        floatingActionButton = findViewById(R.id.fabAddPlaylistBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (SongDetail songDetail: inheritedSongs){
                    mediaPlayerApi.addMusic(songDetail);
                }
                mediaPlayerApi.play();
            }
        });



        recyclerView = findViewById(R.id.musicListRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    recyclerView.setLayoutManager(linearLayoutManager);

         musicActionDialog = new Dialog(this,R.style.CustomDialogTheme);
               musicActionDialog.setContentView(R.layout.music_dialog_detail);
               musicActionDialog.findViewById(R.id.playBtnMusicDialogPopup).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       mediaPlayerApi.playMusic(activeSongDetail);
                   }
               });
        musicActionDialog.findViewById(R.id.addBtnMusicDialogPopup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerApi.addMusic(activeSongDetail);
            }
        });

        MusicListAdapter musicListAdapter = new MusicListAdapter(this,inheritedSongs);
        musicListAdapter.setMusicItemClickListener(new MusicListAdapter.MusicItemClickListener() {
            @Override
            public void onMusicItemClicked(View view, int position, SongDetail songDetail) {
                activeSongDetail = songDetail;
                musicActionDialog.show();
            }
        });
        recyclerView.setAdapter(musicListAdapter);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
}
