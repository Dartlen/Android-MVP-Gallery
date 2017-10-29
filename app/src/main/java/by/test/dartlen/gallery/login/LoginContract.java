package by.test.dartlen.gallery.login;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;

/**
 * Created by Dartlen on 26.10.2017.
 */

public interface LoginContract {
    interface Presenter extends BasePresenter {

        void login();
        void register();
    }
    interface View extends BaseView<LoginContract.Presenter>{
        void showLogin();
        void showRegister();
        void showMain();
        void showLoginError();
        LoginData getLoginPassword();
    }
}
