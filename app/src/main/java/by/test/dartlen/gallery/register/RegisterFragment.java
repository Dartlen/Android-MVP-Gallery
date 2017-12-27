package by.test.dartlen.gallery.register;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;
import by.test.dartlen.gallery.login.LoginContract;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/*** Created by Dartlen on 26.10.2017.
 */

public class RegisterFragment extends Fragment implements RegisterContract.View, View.OnClickListener {

    private RegisterContract.Presenter mPresenterRegister;

    @BindView(R.id.login)
    EditText mLogin;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.passwordconfirm)
    EditText mPasswordConfirm;

    @BindView(R.id.button)
    Button mButton;

    private ProgressDialog mDialog;

    public static RegisterFragment newInstance(){
        return new RegisterFragment();
    }

    public RegisterFragment(){}

    @Override
    public void setPresenter(@NonNull RegisterContract.Presenter presenter) {
        mPresenterRegister = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.register_fragment, container, false);
        ButterKnife.bind(this, root);
        mButton.setOnClickListener(this);
        return root;
    }

    /*@Override
    public void showLogin() {

    }

    @Override
    public void showLoginError() {

    }

    @Override
    public void showRegister() {

    }*/

   /* @Override
    public LoginData getLoginPassword() {
        if(mPasswordConfirm.getText().toString().equals(mPassword.getText().toString()))
            return new LoginData(mLogin.getText().toString(), mPassword.getText().toString());
        else
            return null;
    }

    @Override
    public void showMain() {
        /*Intent intent = new Intent(getContext(), MainPageActivity.class);
        startActivity(intent);*/

    //}

    @Override
    public void onClick(View v) {
        //mPresenter.register();
    }

    /*@Override
    public void showProgress() {
        mDialog.show(getContext(),
                "ProgressDialog",
                "Wait!");
    }

    @Override
    public void hideProgress() {
        mDialog.dismiss();
    }

    @Override
    public void setAuth(FirebaseAuth firebaseAuth, GoogleSignInClient googleSignInClient) {

    }

    @Override
    public void signin(String login, String password) {

    }

    @Override
    public void showDialog(String text) {

    }

    @Override
    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

    }*/
}
