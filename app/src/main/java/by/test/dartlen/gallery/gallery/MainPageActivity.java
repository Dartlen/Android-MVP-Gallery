package by.test.dartlen.gallery.gallery;

import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

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
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.camera.CameraFragment;
import by.test.dartlen.gallery.camera.CameraPresenter;
import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.App;
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

    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;

    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseAuth mAuth;

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
        if (checkPlayServices()) {
            buildGoogleApiClient();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        mGalleryRepository = Injection.provideGalleryRepository();

        if (mGalleryFragment == null) {
            mGalleryFragment = GalleryFragment.newInstance();
        }
        mGalleryPresenter = new GalleryPresenter(mGalleryRepository, mGalleryFragment, mAuth);

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

        if(mPictureFragment == null) {
            mPictureFragment = PictureFragment.newInstance();
        }

        if(mMapFragment == null) {
            mMapFragment = MapFragment.newInstance();
        }
        mMapPresenter = new MapPresenter(mGalleryRepository, mMapFragment, mAuth);

        if(mResetpswdFragment == null){
            mResetpswdFragment = ResetpswdFragment.newInstance();
        }
        mResetpswdPresenter = new ResetpswdPresenter(mResetpswdFragment, mAuth);

        setSupportActionBar(mToolbar);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGalleryPresenter.onClickedFab();
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

    @Override
    protected void onResume() {
        super.onResume();
        App.INSTANCE.getNavigatorHolder().setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        App.INSTANCE.getNavigatorHolder().removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {

    }

    private Navigator navigator = new SupportFragmentNavigator(getSupportFragmentManager(),
            R.id.fr) {

        @Override
        protected Fragment createFragment(String screenKey, Object data) {
            switch(screenKey) {
                case "map":
                    return mMapFragment;
                case "gallery":
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
                case "camera":
                    if (mCameraFragment == null) {
                        mCameraFragment = CameraFragment.newInstance();
                    }

                    mCameraPresenter = new CameraPresenter(mGalleryRepository, mCameraFragment, mGoogleApiClient, mAuth);
                    return mCameraFragment;
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
        } else if (id == R.id.nav_map) {
            App.INSTANCE.getRouter().navigateTo("map");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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





