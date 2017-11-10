package by.test.dartlen.gallery.camera;

import java.io.File;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;

/**
 * Created by Dartlen on 01.11.2017.
 */

public interface CameraContract {

    interface Presenter extends BasePresenter {
        void postImage(ImageData image);
    }
    interface View extends BaseView<CameraContract.Presenter> {

        void setPresenter(Presenter presenter);
    }
}
