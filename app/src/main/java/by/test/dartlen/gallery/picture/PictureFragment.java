package by.test.dartlen.gallery.picture;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import by.test.dartlen.gallery.login.LoginContract;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 01.11.2017.
 */

public class PictureFragment extends Fragment implements PictureContract.View {

    private PictureContract.Presenter mPresenter;
    private static final int CAPTURE_IMAGE_REQUEST_CODE=1000;
    private static final int  IMAGE=2000;


    public static PictureFragment newInstance() {
        return new PictureFragment();
    }

    @Override
    public void setPresenter(PictureContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        go();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void go(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file=getOutputMediaFile(IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, file);

        //intent to get Image intent
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
    }

    private static File getOutputMediaFile(int type) {
        // get path of pictures directory and name a folder in it as MyCameraApp
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyCameraApp");
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


}
