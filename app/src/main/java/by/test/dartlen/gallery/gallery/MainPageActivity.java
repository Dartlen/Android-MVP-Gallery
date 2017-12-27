package by.test.dartlen.gallery.gallery;


import android.app.Activity;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import butterknife.BindView;
import butterknife.ButterKnife;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.camera.CameraFragment;
import by.test.dartlen.gallery.camera.CameraPresenter;
import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.first.FirstContract;
import by.test.dartlen.gallery.first.FirstFragment;
import by.test.dartlen.gallery.first.FirstPresenter;
import by.test.dartlen.gallery.login.LoginFragment;
import by.test.dartlen.gallery.login.LoginPresenter;
import by.test.dartlen.gallery.map.MapFragment;
import by.test.dartlen.gallery.map.MapPresenter;
import by.test.dartlen.gallery.picture.PictureFragment;
import by.test.dartlen.gallery.picture.PicturePresenter;
import by.test.dartlen.gallery.register.RegisterFragment;
import by.test.dartlen.gallery.register.RegisterPresenter;
import by.test.dartlen.gallery.resetpswd.ResetpswdFragment;
import by.test.dartlen.gallery.resetpswd.ResetpswdPresenter;
import by.test.dartlen.gallery.util.ActivityUtils;
import by.test.dartlen.gallery.util.Injection;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public class MainPageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private GalleryRepository mGalleryRepository;

    private GalleryFragment mGalleryFragment;
    private GalleryPresenter mGalleryPresenter;

    private MapFragment mMapFragment;
    private MapPresenter mMapPresenter;

    private CameraFragment mCameraFragment;
    private CameraPresenter mCameraPresenter;

    private PictureFragment mPictureFragment;
    private PicturePresenter mPicturePresenter;

    private FirstFragment mFirstFragment;
    private FirstPresenter mFirstPresneter;


    private FirstContract.Presenter mFirstPresenter;

    private LoginFragment mLoginFragment;
    private LoginPresenter mLoginPresenter;

    private RegisterFragment mRegisterFragment;
    private RegisterPresenter mRegisterPresenter;

    private ResetpswdFragment mResetpswdFragment;
    private ResetpswdPresenter mResetpswdPresenter;

    private static final int CAPTURE_IMAGE_REQUEST_CODE=1000;

    private static final int  IMAGE=2000;
    private String imagestring;

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;

    private Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;

    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;

    double latitude;
    double longitude;

    boolean isPermissionGranted = true;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ActionBarDrawerToggle mDrawerToggle;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.bind(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        mGalleryRepository = Injection.provideGalleryRepository(getApplicationContext());

        if (mGalleryFragment == null) {
            mGalleryFragment = GalleryFragment.newInstance();
        }
        mGalleryPresenter = new GalleryPresenter(mGalleryRepository, mGalleryFragment);


        if (mLoginFragment == null) {
            mLoginFragment = mLoginFragment.newInstance();
        }

        if(mRegisterFragment ==null)
            mRegisterFragment = mRegisterFragment.newInstance();
        mRegisterPresenter = new RegisterPresenter(mRegisterFragment, mAuth);

        mLoginPresenter = new LoginPresenter(mLoginFragment, mAuth, mGoogleSignInClient);

        if(mFirstFragment == null)
            mFirstFragment = mFirstFragment.newInstance();
        mFirstPresneter = new FirstPresenter(mGalleryRepository, mFirstFragment, mLoginFragment, mRegisterFragment);


        if (mCameraFragment == null) {
            mCameraFragment = CameraFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mCameraFragment, R.id.fr);
        }
        mCameraPresenter = new CameraPresenter(mGalleryRepository, mCameraFragment);

        if(mPictureFragment == null) {
            mPictureFragment = PictureFragment.newInstance();
        }

        if(mMapFragment == null) {
            mMapFragment = MapFragment.newInstance();
        }
        mMapPresenter = new MapPresenter(mGalleryRepository, mMapFragment);

        if(mResetpswdFragment == null){
            mResetpswdFragment = ResetpswdFragment.newInstance();
        }
        mResetpswdPresenter = new ResetpswdPresenter(mResetpswdFragment, mAuth);

        setSupportActionBar(mToolbar);

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

        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener( mDrawerToggle);
        mDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        mToolbar.setVisibility(View.INVISIBLE);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        App.INSTANCE.getRouter().navigateTo("firstpage");

    }

    private void enableViews(boolean enable) {
        /*if(mFab.getVisibility()==View.INVISIBLE){
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
        }*/
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
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //enableViews(false);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mGalleryPresenter.onBackPressed();
        }*/

    }

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(),
            R.id.fr) {

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch(screenKey) {
                case "map":
                    //enableViews(true);
                    return mMapFragment;
                case "gallery":
                    //enableViews(true);
                    return mGalleryFragment;
                case "picture":
                    /*mPictureFragment = PictureFragment.newInstance();
                    mPicturePresenter = new PicturePresenter(mGalleryRepository, mPictureFragment, (Images)data);
                    enableViews(true);*/
                    return mPictureFragment;
                case "firstpage":

                    return mFirstFragment;
                case "resetpswd":
                    return mResetpswdFragment;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_gallery) {
            App.INSTANCE.getRouter().navigateTo("gallery");
            //enableViews(false);
        } else if (id == R.id.nav_map) {
            App.INSTANCE.getRouter().navigateTo("map");
            //enableViews(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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





