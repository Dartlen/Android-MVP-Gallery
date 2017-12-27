package by.test.dartlen.gallery.login;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.register.RegisterFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/*** Created by Dartlen on 26.10.2017.
 */

public class LoginPresenter implements LoginContract.Presenter{

    private final LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LoginContract.View loginView,
                          @NonNull FirebaseAuth firebaseAuth,
                          @NonNull GoogleSignInClient googleSignInClient){
        mLoginView          = checkNotNull(loginView, "loginView cannot be null!");

        mLoginView.setPresenter(this);
        mLoginView.setAuth(firebaseAuth, googleSignInClient);
    }

    @Override
    public void start() {

    }

    @Override
    public void onClickedLogin(String login, String password) {
        if(isValidEmail(login) && password.length()>=6) {
            mLoginView.showProgress();
            mLoginView.signin(login, password);
        }
        else {
            mLoginView.showDialog("Incorrect username or password.");
        }
    }

    public static boolean isValidEmail(String email)
    {
        String expression = "^[\\w\\.]+@([\\w]+\\.)+[A-Z]{2,7}$";
        CharSequence inputString = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);
        if (matcher.matches())
        {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public void signInCompleted(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            mLoginView.hideProgress();
            App.INSTANCE.getRouter().navigateTo("gallery");
        } else {
            mLoginView.hideProgress();
            mLoginView.showDialog("Authentication failed.");
        }
    }

    @Override
    public void googleAccountReturnedData(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            mLoginView.showProgress();
            mLoginView.firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            mLoginView.hideProgress();
            mLoginView.showDialog("Google sign in failed");
        }
    }

    @Override
    public void onClickedReset() {
        App.INSTANCE.getRouter().navigateTo("resetpswd");
    }
}


