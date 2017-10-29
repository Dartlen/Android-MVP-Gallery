package by.test.dartlen.gallery.gallery;


import android.content.Context;

import java.util.List;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.login.LoginContract;

/**
 * Created by Dartlen on 28.10.2017.
 */

public interface GalleryContract {

    interface Presenter extends BasePresenter {
        void loadImages();
        void postImage();
    }

    interface View extends BaseView<GalleryContract.Presenter>{
        void showImages(List<Images> data);
        void showLogin(Context context);
    }



}
