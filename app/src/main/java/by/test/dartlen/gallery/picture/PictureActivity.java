package by.test.dartlen.gallery.picture;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import by.test.dartlen.gallery.R;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 02.11.2017.
 */

public class PictureActivity  extends AppCompatActivity implements PictureContract.View {

    private PictureContract.Presenter mPresenter;
    private static final int CAPTURE_IMAGE_REQUEST_CODE=1000;
    private static final int  IMAGE=2000;

    public void setPresenter(PictureContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file=getOutputMediaFile(IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        //intent to get Image intent
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void go(){


    }

    private static File getOutputMediaFile(int type) {
        // get path of pictures directory and name a folder in it as MyCameraApp
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        //check if directory not exists and if not then create directory
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // get current date and time
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        //if type is image then create path to store image and name it as currentdateandtime.jpg
        if(type==IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        }
        else
        {
            return null;
        }
        return mediaFile;
    }

    //check if camera is available in device
    public boolean checkIfCameraAvailable()
    {
        if(PictureActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            //camera is available
            return true;
        }
        else
        {
            //camera is not available
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //if capturing image
        if(requestCode==CAPTURE_IMAGE_REQUEST_CODE)
        {
            // successfully captured
            if(resultCode==RESULT_OK)
            {
                Toast.makeText(PictureActivity.this," Image Captured Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(PictureActivity.this,"Error Capturing Image! Please Retry",Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
