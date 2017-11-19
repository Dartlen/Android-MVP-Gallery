package by.test.dartlen.gallery.camera;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.greenrobot.greendao.annotation.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import by.test.dartlen.gallery.data.GalleryDataSource;
import by.test.dartlen.gallery.data.local.greendao.App;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImagePost;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 01.11.2017.
 */

public class CameraPresenter implements CameraContract.Presenter {

    private final GalleryDataSource mGalleryRepository;
    private final CameraContract.View mCameraView;

    public CameraPresenter(@NotNull GalleryDataSource galleryRepository, @NotNull CameraContract.View CameraView){
        mGalleryRepository = checkNotNull(galleryRepository,"gallery cannot be null");
        mCameraView        = checkNotNull(CameraView, "camerafragment cannot be null");
        mCameraView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void postImage(ImageData image) {

        mGalleryRepository.postImage(new GalleryDataSource.ImagePostCallback() {
            @Override
            public void onDataLoaded(ResponseDataImagePost dataResponse) {
                //TODO: сообщение что изображение згружено

            }

            @Override
            public void onError(String error) {

            }
        },mGalleryRepository.getUser().getToken(),image);
    }



}
