package by.test.dartlen.gallery.first;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;
import by.test.dartlen.gallery.login.LoginFragment;
import by.test.dartlen.gallery.register.RegisterFragment;

/***
 * Created by Dartlen on 26.12.2017.
 */

public interface FirstContract {
    interface View extends BaseView<FirstContract.Presenter>{
        void setViewFragments(LoginFragment loginFragment, RegisterFragment registerFragment);

    }

    interface Presenter extends BasePresenter{

    }
}
