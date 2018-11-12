package com.bkassistant;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roundedimage.akshay.vishal.RoundedImage;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public RoundedImage.DownloadImageTask roundedImage;
    public Handler musicHandler;
    public  Runnable runnable;

    public MediaPlayerApi mediaPlayerApi;
    public MediaPlayerUi mediaPlayerUi;

    public Globals globalVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mediaPlayerUi = new MediaPlayerUi();
        mediaPlayerApi = new MediaPlayerApi(HomeActivity.this, this);
        final SongDetail songDetail = new SongDetail("Valllaha","http://www.largesound.com/ashborytour/sound/brobob.mp3");
                mediaPlayerApi.playlist.add(songDetail);
        final Handler handler = new Handler();
        final Runnable r = new Runnable()
        {
            public void run()
            {
                mediaPlayerApi._updateSeekBar();
                handler.postDelayed(this, 1000);
            }
        };



        mediaPlayerApi.setOnMusicLoadedListener(new MediaPlayerApi.OnMusicLoadedListener() {
            @Override
            public void onMusicLoaded(MediaPlayer mediaPlayer) {
                mediaPlayerUi.mediaProgressBar.setVisibility(View.GONE);
                mediaPlayerUi.playPauseBtn.setVisibility(View.VISIBLE);
                mediaPlayerUi.setPauseIcon();
                mediaPlayerApi.playerState = MediaPlayerApi.PlayerState.playing;
                mediaPlayer.start();
                mediaPlayerUi.seekBar.setProgress( 0 );
                mediaPlayerUi.seekBar.setMax( mediaPlayer.getDuration() );
                handler.postDelayed(r, 100);
            }
        });





       globalVariable = (Globals) getApplicationContext();

        globalVariable.setMediaPlayerApi(mediaPlayerApi);

        HomePageSlider homePageSlider = new HomePageSlider(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        _applyUserCredentials(navigationView);



        findViewById(R.id.resourceMusicSongs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<SongDetail> songDetailsList = new ArrayList<>();
                    songDetailsList.add(new SongDetail(" Chirag Tale Andhera - BK Dr Sachin - 07-11-18","http://www.bkdrluhar.com/x/Chirag%20Tale%20Andhera%20-%20BK%20Dr%20Sachin%20-%2007-11-18.mp3"));
                    songDetailsList.add(new SongDetail("Becoming an Authority of Experience in Every Attainment - BK Dr Sachin - 01-11-18","http://www.bkdrluhar.com/x/Becoming%20an%20Authority%20of%20Experience%20in%20Every%20Attainment%20-%20BK%20Dr%20Sachin%20-%2001-11-18.mp3"));

                startActivity(new Intent(HomeActivity.this,Music_List.class).putExtra("songList",songDetailsList));
            }
        });

    }

    private void _applyUserCredentials(final NavigationView navigationView) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth mAuth) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if(user != null) {
                        TextView emailView, userName;
                        ImageView imgAvatar;
                        View headerView = navigationView.getHeaderView(0);
                        emailView = headerView.findViewById(R.id.oAuthUserEmailId);
                        userName = headerView.findViewById(R.id.oAuthUserUserName);
                        imgAvatar = findViewById(R.id.imageAvatar);

                        emailView.setText(user.getEmail());
                        userName.setText(user.getDisplayName());
                        if(user.getPhotoUrl() != null){
                            System.out.println(user.getPhotoUrl().toString());
                            new RoundedImage().ImageRoundedAkshay(HomeActivity.this,imgAvatar,user.getPhotoUrl().toString());
                       //     Glide.with(HomeActivity.this).load(user.getPhotoUrl() ).into(imgAvatar);
                        }
                    }
            }
        });


    }

    private void _checkUserLoggedIn() {
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        ProgressDialog progressDialog = new ProgressDialog(this );
//        progressDialog.setMessage("Checking logged in status");
//        progressDialog.setCancelable(false);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();

//        if(currentUser != null){
//            // We got logged in
//            progressDialog.dismiss();
//            startActivity(new Intent(
//                    HomeActivity.this,
//                    HomeActivity.class)
//            );
//        }else{
//            progressDialog.dismiss();
//            startActivity(new Intent(
//                    MainActivity.this,
//                    LoginActivity.class)
//            );
//        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_gallery) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(
                 HomeActivity.this,
                 DailyMurli.class
            ));
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    class MediaPlayerUi{

        public Button playPauseBtn , skipNext;
        public SeekBar seekBar;
        public TextView songTextView;
        public ProgressBar mediaProgressBar;
        public CardView mediaCardHolder;
        public Drawable play;
        public Drawable pause;

        MediaPlayerUi() {
            playPauseBtn = findViewById(R.id.playerPlayPauseBtn);
            skipNext = findViewById(R.id.playerSkipNextBtn);
            mediaCardHolder = findViewById(R.id.mediaPlayerCardViewHolder);
            seekBar = findViewById(R.id.playerSeekBarBtn);
            songTextView = findViewById(R.id.songNamePlayerLabel);
            mediaProgressBar = findViewById(R.id.progressBarMusicLoading);
             play = getApplicationContext().getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp);
             pause = getApplicationContext().getResources().getDrawable(R.drawable.ic_pause_black_24dp);


            setPlayIcon();



            playPauseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("clicked play/pause");

                    if(mediaPlayerApi.playerState == MediaPlayerApi.PlayerState.playing){
                        setPlayIcon();
                        mediaPlayerApi.pause();
                    }else if (mediaPlayerApi.playerState == MediaPlayerApi.PlayerState.paused){
                        setPauseIcon();
                        mediaPlayerApi.resume();
                    }else{
                        setPauseIcon();
                        mediaPlayerApi.play();
                    }

                }
            });

            skipNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("clicked skip/next");
                    mediaPlayerApi.next();
                }
            });

        }

        public void setPlayIcon(){
            playPauseBtn.setBackground(play);
        }

        public void setPauseIcon(){
            playPauseBtn.setBackground(pause);
        }



    }
}
