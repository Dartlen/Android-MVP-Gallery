package by.test.dartlen.gallery.login;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import by.test.dartlen.gallery.BasePresenter;
import by.test.dartlen.gallery.BaseView;

/***
 * Created by Dartlen on 26.10.2017.
 */

public interface LoginContract {
    interface Presenter extends BasePresenter {
        void onClickedLogin(String login, String password);
        void signInCompleted(@NonNull Task<AuthResult> task);
        void googleAccountReturnedData(Intent data);
        void onClickedReset();
    }
    interface View extends BaseView<LoginContract.Presenter>{
        void showDialog(String text);
        void showProgress();
        void hideProgress();
        void signin(String login, String password);
        void setAuth(FirebaseAuth firebaseAuth, GoogleSignInClient googleSignInClient);
        void firebaseAuthWithGoogle(GoogleSignInAccount acct);
    }
}
