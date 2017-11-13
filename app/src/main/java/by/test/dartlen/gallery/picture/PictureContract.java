package by.test.dartlen.gallery.picture;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;
import by.test.dartlen.gallery.data.local.greendao.Images;

/***
 * Created by Dartlen on 10.11.2017.
 */

public interface PictureContract {

    interface Presenter extends BasePresenter{
        void setPictureData(Images imageData);
    }

    interface View extends BaseView<PictureContract.Presenter>{
        void showPicture(Images imageData);
    }
}
