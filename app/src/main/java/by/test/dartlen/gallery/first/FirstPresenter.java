package by.test.dartlen.gallery.first;

import android.support.annotation.NonNull;

import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.login.LoginFragment;
import by.test.dartlen.gallery.login.LoginPresenter;
import by.test.dartlen.gallery.register.RegisterFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 26.12.2017.
 */

public class FirstPresenter implements FirstContract.Presenter{

    private LoginFragment mLoginFragment;
    private LoginPresenter mLoginPresenter;

    private RegisterFragment mRegisterFragment;
    //private RegisterPresenter mRegisterPresenter;

    private FirstContract.View mFirstFragment;

    private GalleryRepository mGalleryRepository;

    public FirstPresenter(@NonNull GalleryRepository galleryRepository, @NonNull FirstContract.View firstView,
    @NonNull LoginFragment loginFragment, @NonNull RegisterFragment registerFragment){
        mGalleryRepository = checkNotNull(galleryRepository, "gallery cannot be null");
        mFirstFragment     = checkNotNull(firstView, "firstView cannont be null!");
        mLoginFragment     = checkNotNull(loginFragment, "loginfragment cannot be null");
        mRegisterFragment  = checkNotNull(registerFragment, "registerfragment cannot be  null");

        mFirstFragment.setPresenter(this);
        mFirstFragment.setViewFragments(loginFragment, registerFragment);
    }

    @Override
    public void start() {

    }

}
