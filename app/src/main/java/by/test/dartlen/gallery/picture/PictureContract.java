package by.test.dartlen.gallery.picture;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;
import by.test.dartlen.gallery.login.LoginContract;

/**
 * Created by Dartlen on 01.11.2017.
 */

public interface PictureContract {
    interface Presenter extends BasePresenter {

    }
    interface View extends BaseView<PictureContract.Presenter> {

    }
}
