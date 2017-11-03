package by.test.dartlen.gallery.gallery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.map.MapFragment;
import by.test.dartlen.gallery.picture.PictureActivity;
import by.test.dartlen.gallery.picture.PictureFragment;
import by.test.dartlen.gallery.picture.PicturePresenter;
import by.test.dartlen.gallery.util.ActivityUtils;
import by.test.dartlen.gallery.util.Injection;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GalleryRepository mGalleryRepository;

    private FragmentManager mFragmentManager;
    private GalleryFragment mGalleryFragment;
    private MapFragment mMapFragment;
    private PictureFragment mPictureFragment;


    private static MainPageActivity sInstance;

    private GalleryPresenter mGalleryPresenter;
    private PicturePresenter mPicturePresenter;

    private FragmentTransaction mFragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        sInstance = this;
        setLoginHeader();

        mFragmentManager = getAppContext().getSupportFragmentManager();

        mMapFragment = new MapFragment();

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(this))
                .build();
        Picasso.setSingletonInstance(picasso);

        mGalleryFragment = (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.fr);
        if(mGalleryFragment ==null){
            mGalleryFragment  = GalleryFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mGalleryFragment, R.id.fr);
        }

        mGalleryRepository = Injection.provideGalleryRepository(getApplicationContext());
        mGalleryPresenter = new GalleryPresenter(mGalleryRepository, mGalleryFragment);

        mPictureFragment = PictureFragment.newInstance();
        mPicturePresenter = new PicturePresenter(mGalleryRepository, mPictureFragment);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getAppContext(), PictureActivity.class);
                startActivity(intent);

                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            /*getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();*/
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fr, mGalleryFragment)
                    .addToBackStack("GalleryFragment")
                    .commit();
        } else if (id == R.id.nav_map) {
            mFragmentTransaction = mFragmentManager.beginTransaction();
            mFragmentTransaction.replace(R.id.fr,mMapFragment);
            mFragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setLoginHeader(){
        SharedPreferences prefs;
        prefs = this.getSharedPreferences("by.test.gallery", MODE_PRIVATE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navLogin = (TextView) headerView.findViewById(R.id.header_login);
        navLogin.setText(prefs.getString("login",null));
    }

    @NonNull
    public static MainPageActivity getAppContext() {
        return sInstance;
    }
}
