package by.test.dartlen.gallery.register;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;

/***
 * Created by Dartlen on 27.12.2017.
 */

public interface RegisterContract {
    interface Presenter extends BasePresenter {
        void onClickedRegister(String login, String password);
        void signUpCompleted(Task<AuthResult> task);
    }
    interface View extends BaseView<RegisterContract.Presenter> {
        void setAuth(FirebaseAuth firebaseAuth);
        void signUp(String login, String password);
        void showDialog(String text);
        void showProgress();
        void hideProgress();
        void showToast(String text, int toastTime);

    }
}
