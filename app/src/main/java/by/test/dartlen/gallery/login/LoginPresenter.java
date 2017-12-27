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
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;
import by.test.dartlen.gallery.data.remote.retrofit.user.DataResponse;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;
import by.test.dartlen.gallery.register.RegisterContract;
import by.test.dartlen.gallery.register.RegisterFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/*** Created by Dartlen on 26.10.2017.
 */

public class LoginPresenter implements LoginContract.Presenter{

    private final GalleryRepository mGalleryRepository;
    private final LoginContract.View mLoginView;
    private final RegisterFragment mRegisterView;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    public LoginPresenter(@NonNull GalleryRepository galleryRepository, @NonNull LoginContract.View loginView,
                          @NonNull RegisterFragment registerView, @NonNull FirebaseAuth firebaseAuth,
                          @NonNull GoogleSignInClient googleSignInClient){
        mGalleryRepository  = checkNotNull(galleryRepository, "gallery cannot be null");
        mLoginView          = checkNotNull(loginView, "loginView cannot be null!");
        mRegisterView       = checkNotNull(registerView, "registerView cannot be null!");
        mAuth               = checkNotNull(firebaseAuth, "firebaseAuth cannot be null!");
        mGoogleSignInClient = checkNotNull(googleSignInClient, "googleSignInClient cannot be null!");

        mLoginView.setPresenter(this);
        //mRegisterView.setPresenter(this);
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
            mLoginView.showDialog("Incorrect username or password. ");
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
            // Sign in success, update UI with the signed-in user's information
            //Log.d(TAG, "signInWithEmail:success");
            //FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
            mLoginView.hideProgress();
            App.INSTANCE.getRouter().navigateTo("gallery");
        } else {
            mLoginView.showDialog("Authentication failed.");
            // If sign in fails, display a message to the user.
            //Log.w(TAG, "signInWithEmail:failure", task.getException());
            /*Toast.makeText(getActivity(), "Authentication failed.",
                    Toast.LENGTH_SHORT).show();*/
            //updateUI(null);

        }
    }

    @Override
    public void googleAccountReturnedData(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = task.getResult(ApiException.class);
            mLoginView.showProgress();
            mLoginView.firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            mLoginView.showDialog("Google sign in failed");
        }
    }

    /* @Override
    public void register() {

        LoginData ld = mRegisterView.getLoginPassword();
        mRegisterView.showProgress();
        mGalleryRepository.signup(new GalleryRemoteDataSource.LoadLoginCallback() {
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
        /*mGalleryRepository.signin(new GalleryRemoteDataSource.LoadLoginCallback() {
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
        },ld);*/
    //}
}


