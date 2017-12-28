package by.test.dartlen.gallery.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsStates;

import org.greenrobot.greendao.annotation.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 01.11.2017.
 */

public class CameraPresenter implements CameraContract.Presenter {

    private final GalleryRepository mGalleryRepository;
    private final CameraContract.View mCameraView;
    private static final int CAPTURE_IMAGE_REQUEST_CODE=1000;
    private static final int  IMAGE=2000;
    private boolean isPermissionGranted = true;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private double latitude;
    private double longitude;
    private String imagestring;

    public CameraPresenter(@NotNull GalleryRepository galleryRepository, @NotNull CameraContract.View CameraView,
                           @NotNull GoogleApiClient googleApiClient){
        mGalleryRepository = checkNotNull(galleryRepository,"gallery cannot be null");
        mCameraView        = checkNotNull(CameraView, "camerafragment cannot be null");
        mGoogleApiClient   = checkNotNull(googleApiClient, "googleapiclient cannot be null!");
        mCameraView.setPresenter(this);
    }

    @Override
    public void start() {
        if(imagestring==null)
            startCamera();
        else{



            App.INSTANCE.getRouter().navigateTo("gallery");
        }
    }

    @Override
    public void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getOutputMediaFile(IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        getLocation();

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
        }

        mCameraView.showCameraActivityResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
    }

    public void getLocation() {

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



    public void onCaptureImageResult(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        imagestring = bitmapToBase64(bitmap);

        /*ImageData imageForPost = new ImageData(imagestring,
                mLastLocation.getTime(),
                mLastLocation.getLatitude(),
                mLastLocation.getLongitude());
        mCameraFragment.imageFromSave(imageForPost);*/

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


}
