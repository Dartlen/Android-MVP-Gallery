package by.test.dartlen.gallery.camera;

import android.content.Intent;

import java.io.File;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;


/***
 * Created by Dartlen on 01.11.2017.
 */

public interface CameraContract {

    interface Presenter extends BasePresenter {
        void getLocation();
        void startCamera();
        void onCaptureImageResult(Intent data);
    }
    interface View extends BaseView<CameraContract.Presenter> {
        void setPresenter(Presenter presenter);
        void showEror(Exception e, String image);
        void showMessage(String message);
        void showCameraActivityResult(Intent intent, int CAPTURE_IMAGE_REQUEST_CODE);
    }
}
