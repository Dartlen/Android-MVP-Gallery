package by.test.dartlen.gallery.login;

import android.support.annotation.NonNull;
import android.util.Log;

import by.test.dartlen.gallery.data.GalleryDataSource;
import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.data.remote.retrofit.user.DataResponse;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;

import static com.google.common.base.Preconditions.checkNotNull;

/*** Created by Dartlen on 26.10.2017.
 */

public class LoginPresenter implements LoginContract.Presenter{

    private final GalleryDataSource mGalleryRepository;
    private final LoginContract.View mLoginView;
    private final LoginContract.View mRegisterView;

    public LoginPresenter(@NonNull GalleryRepository galleryRepository, @NonNull LoginContract.View loginView,
                          @NonNull LoginContract.View registerView){
        mGalleryRepository = checkNotNull(galleryRepository, "gallery cannot be null");
        mLoginView = checkNotNull(loginView, "loginView cannot be null!");
        mRegisterView = checkNotNull(registerView, "registerView cannot be null!");
        mLoginView.setPresenter(this);
        mRegisterView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void register() {

        LoginData ld = mRegisterView.getLoginPassword();
        mRegisterView.showProgress();
        mGalleryRepository.signup(new GalleryDataSource.LoadLoginCallback() {
            @Override
            public void onLoggined(DataResponse login) {
                mRegisterView.hideProgress();
                mRegisterView.showMain();
            }

            @Override
            public void onDataNotAvailable(String error) {
                mRegisterView.hideProgress();
            }
        },ld);
    }

    @Override
    public void login() {
        LoginData ld = mLoginView.getLoginPassword();
        mLoginView.showProgress();
        mGalleryRepository.signin(new GalleryDataSource.LoadLoginCallback() {
            @Override
            public void onLoggined(DataResponse login) {
                mLoginView.hideProgress();
                mLoginView.showMain();
            }

            @Override
            public void onDataNotAvailable(String error) {
                mLoginView.hideProgress();
                mLoginView.showDialog();
            }
        },ld);
    }
}


