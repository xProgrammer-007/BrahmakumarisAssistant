package com.bkassistant;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roundedimage.akshay.vishal.RoundedImage;
import ss.com.bannerslider.Slider;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public RoundedImage.DownloadImageTask roundedImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        HomePageSlider homePageSlider = new HomePageSlider(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseAuth mAuth = FirebaseAuth.getInstance();
//                mAuth.signOut();
                Intent musicDetailIntent = new Intent(HomeActivity.this,MusicPlayerDetailActivity.class);
                 startActivity(musicDetailIntent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        _applyUserCredentials(navigationView);

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
                        imgAvatar = headerView.findViewById(R.id.imageAvatar);

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
}
