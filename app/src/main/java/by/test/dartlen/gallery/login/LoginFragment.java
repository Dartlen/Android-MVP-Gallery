package by.test.dartlen.gallery.login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/*** Created by Dartlen on 26.10.2017.
 */

public class LoginFragment extends Fragment implements LoginContract.View, View.OnClickListener {

    private LoginContract.Presenter mPresenter;

    @BindView(R.id.login)
    EditText mLogin;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.button)
    Button mButton;

    @BindView(R.id.sign_in_button)
    SignInButton mGoogleSignIn;

    @BindView(R.id.btn_reset_password)
    Button mButtonReset;

    private static final int RC_SIGN_IN = 9001;

    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;

    private static ProgressDialog mDialog;

    public LoginFragment() {}

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, root);
        mButton.setOnClickListener(this);

        mGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onClickedReset();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        signOut();
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onClick(View v) {
        mPresenter.onClickedLogin(mLogin.getText().toString(), mPassword.getText().toString());
    }

    @Override
    public void signin(String login, String password) {
        mAuth.signInWithEmailAndPassword(login, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mPresenter.signInCompleted(task);
                    }
                });
    }

    @Override
    public void showDialog(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setMessage(text)
                .setCancelable(false)
                .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void showProgress() {
        mDialog = ProgressDialog.show(getContext(),
                "",
                "Please wait authorization...");
    }

    @Override
    public void hideProgress() {
        mDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       mPresenter.signInCompleted(task);
                    }
                });
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mAuth.signOut();

        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void revokeAccess() {
        mAuth.signOut();

        mGoogleSignInClient.revokeAccess().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            mPresenter.googleAccountReturnedData(data);
        }
    }

    @Override
    public void setAuth(FirebaseAuth firebaseAuth, GoogleSignInClient googleSignInClient) {
        mAuth               = firebaseAuth;
        mGoogleSignInClient = googleSignInClient;
    }
}
