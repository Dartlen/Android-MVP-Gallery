package by.test.dartlen.gallery.resetpswd;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;

/***
 * Created by Dartlen on 27.12.2017.
 */

public interface ResetpswdContract {
    interface View extends BaseView<ResetpswdContract.Presenter>{
        void resetpswd(String email);
        void setAuth(FirebaseAuth auth);
        void showToast(String text, int toastTime);
        void showDialog(String text);
        void showProgress();
        void hideProgress();
    }
    interface Presenter extends BasePresenter{
        void onClickedResetpswd(String email);
        void resetCompleted(Task<Void> task);
    }
}
