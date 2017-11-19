package by.test.dartlen.gallery.gallery;


import android.app.Activity;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.camera.CameraFragment;
import by.test.dartlen.gallery.camera.CameraPresenter;
import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.map.MapFragment;
import by.test.dartlen.gallery.map.MapPresenter;
import by.test.dartlen.gallery.picture.PictureFragment;
import by.test.dartlen.gallery.picture.PicturePresenter;
import by.test.dartlen.gallery.util.ActivityUtils;
import by.test.dartlen.gallery.util.Injection;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private GalleryRepository mGalleryRepository;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private GalleryFragment mGalleryFragment;
    private MapFragment mMapFragment;
    private CameraFragment mCameraFragment;
    private PictureFragment mPictureFragment;

    private static MainPageActivity sInstance;

    private GalleryPresenter mGalleryPresenter;
    private CameraPresenter mCameraPresenter;
    private PicturePresenter mPicturePresenter;
    private MapPresenter mMapPresenter;

    private static final int CAPTURE_IMAGE_REQUEST_CODE=1000;

    private static final int  IMAGE=2000;
    private String imagestring;


    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;

    double latitude;
    double longitude;

    boolean isPermissionGranted=true;

    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private FloatingActionButton mFab;

    private boolean mToolBarNavigationListenerIsRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //GalleryApplication.INSTANCE.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        sInstance = this;
        setLoginHeader();

        mFragmentManager = this.getSupportFragmentManager();

        mGalleryFragment = (GalleryFragment) getSupportFragmentManager().findFragmentById(R.id.fr);
        if (mGalleryFragment == null) {
            mGalleryFragment = GalleryFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mGalleryFragment, R.id.fr);
        }

        mGalleryRepository = Injection.provideGalleryRepository(getApplicationContext());
        mGalleryPresenter = new GalleryPresenter(mGalleryRepository, mGalleryFragment);

        mCameraFragment = (CameraFragment) getSupportFragmentManager().findFragmentById(R.id.fr);
        if (mCameraFragment == null) {
            mCameraFragment = CameraFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mCameraFragment, R.id.fr);
        }
        mCameraPresenter = new CameraPresenter(mGalleryRepository, mCameraFragment);

        mMapFragment  = MapFragment.newInstance();
        mMapPresenter = new MapPresenter(mGalleryRepository, mMapFragment);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = getOutputMediaFile(IMAGE);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

                if (checkPlayServices()) {
                    buildGoogleApiClient();
                }

                getLocation();

                if (mLastLocation != null) {
                    latitude = mLastLocation.getLatitude();
                    longitude = mLastLocation.getLongitude();
                }

                startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener( mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void enableViews(boolean enable) {
        if(mFab.getVisibility()==View.INVISIBLE){
            mFab.setVisibility(View.VISIBLE);
        }
        if (enable) {
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (!mToolBarNavigationListenerIsRegistered) {
                mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerToggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.INSTANCE.getNavigatorHolder().setNavigator(navigator);
        checkPlayServices();
    }

    @Override
    protected void onPause() {
        App.INSTANCE.getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        enableViews(false);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mGalleryPresenter.onBackPressed();
        }


    }

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(),
            R.id.fr) {

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch(screenKey) {
                case "map":
                    enableViews(true);
                    return mMapFragment;
                case "gallery":
                    enableViews(true);
                    return mGalleryFragment;
                case "picture":
                    mPictureFragment = PictureFragment.newInstance();
                    mPicturePresenter = new PicturePresenter(mGalleryRepository, mPictureFragment, (Images)data);
                    enableViews(true);
                    return mPictureFragment;
                default:
                    throw new RuntimeException("Unknown screen key!");
            }
        }

        @Override
        protected void showSystemMessage(String message) {
            Toast.makeText(MainPageActivity.this, message, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void exit() {
            finish();
        }
    };

    /*private Navigator navigator = new Navigator() {
        @Override
        public void applyCommand(Command command) {
            if (command instanceof Forward) {
                forward((Forward) command);
            } else if (command instanceof Replace) {
                replace((Replace) command);
            } else if (command instanceof Back) {
                back();
            } else if (command instanceof SystemMessage) {
                Toast.makeText(StartActivity.this, ((SystemMessage) command).getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Log.e("Cicerone", "Illegal command for this screen: " + command.getClass().getSimpleName());
            }
        }

        private void forward(Forward command) {
            switch (command.getScreenKey()) {
                case Screens.START_ACTIVITY_SCREEN:
                    startActivity(new Intent(StartActivity.this, StartActivity.class));
                    break;
                case Screens.MAIN_ACTIVITY_SCREEN:
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    break;
                case Screens.BOTTOM_NAVIGATION_ACTIVITY_SCREEN:
                    startActivity(new Intent(StartActivity.this, BottomNavigationActivity.class));
                    break;
                case Screens.PROFILE_SCREEN:
                    startActivity(new Intent(StartActivity.this, ProfileActivity.class));
                    break;
                default:
                    Log.e("Cicerone", "Unknown screen: " + command.getScreenKey());
                    break;
            }
        }

        private void replace(Replace command) {
            switch (command.getScreenKey()) {
                case Screens.START_ACTIVITY_SCREEN:
                case Screens.MAIN_ACTIVITY_SCREEN:
                case Screens.BOTTOM_NAVIGATION_ACTIVITY_SCREEN:
                case Screens.PROFILE_SCREEN:
                    forward(new Forward(command.getScreenKey(), command.getTransitionData()));
                    finish();
                    break;
                default:
                    Log.e("Cicerone", "Unknown screen: " + command.getScreenKey());
                    break;
            }
        }

        private void back() {
            finish();
        }
    };*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            App.INSTANCE.getRouter().navigateTo("gallery");
            enableViews(false);
        } else if (id == R.id.nav_map) {
            App.INSTANCE.getRouter().navigateTo("map");
            enableViews(true);
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

    private static File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if(type==IMAGE)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        }
        else
        {
            return null;
        }
        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    default:
                        break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        imagestring = bitmapToBase64(bitmap);

        ImageData imageForPost = new ImageData(imagestring,
                mLastLocation.getTime(),
                mLastLocation.getLatitude(),
                mLastLocation.getLongitude());
        mCameraFragment.imageFromSave(imageForPost);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public interface CameraImage {
        void imageFromSave(ImageData image);
    }

    private void getLocation() {

        if (isPermissionGranted) {

            try
            {
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {

                            status.startResolutionForResult(MainPageActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this,resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(" ", "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }
}





