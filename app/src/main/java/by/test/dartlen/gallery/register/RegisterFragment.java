package by.test.dartlen.gallery.register;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.test.dartlen.gallery.R;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 26.10.2017.
 */

public class RegisterFragment extends Fragment implements RegisterContract.View{

    private RegisterContract.Presenter mPresenterRegister;

    @BindView(R.id.login)
    EditText mLogin;

    @BindView(R.id.password)
    EditText mPassword;

    @BindView(R.id.button)
    Button mButton;

    private FirebaseAuth mAuth;
    private static ProgressDialog mDialog;

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
        View root = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, root);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenterRegister.onClickedRegister(mLogin.getText().toString(), mPassword.getText().toString());
            }
        });
        return root;
    }

    @Override
    public void setAuth(FirebaseAuth firebaseAuth) {
        mAuth = firebaseAuth;
    }

    @Override
    public void showProgress() {
        mDialog = ProgressDialog.show(getContext(),"", "please wait registration");
    }

    @Override
    public void hideProgress() {
        mDialog.dismiss();
    }

    @Override
    public void signUp(String login, String password) {
        mAuth.createUserWithEmailAndPassword(mLogin.getText().toString(), mPassword.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mPresenterRegister.signUpCompleted(task);
                    }
                });
    }

    @Override
    public void showDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(text)
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void showToast(String text, int toastTime) {
        Toast.makeText(getActivity(), text, toastTime).show();
    }
}
