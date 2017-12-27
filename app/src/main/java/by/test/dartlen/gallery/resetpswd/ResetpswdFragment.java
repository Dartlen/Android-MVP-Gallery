package by.test.dartlen.gallery.resetpswd;

import android.app.AlertDialog;
import android.app.Dialog;
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
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.gallery.GalleryFragment;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 27.12.2017.
 */

public class ResetpswdFragment extends Fragment implements ResetpswdContract.View {

    private ResetpswdContract.Presenter mResetpswdPresenter;
    private FirebaseAuth mAuth;
    private static ProgressDialog mDialog;
    
    @BindView(R.id.email)
    EditText editTextEmail;

    @BindView(R.id.btn_reset_password)
    Button bnt_reset_password;

    public ResetpswdFragment(){}

    public static ResetpswdFragment newInstance() {
        return new ResetpswdFragment();
    }

    @Override
    public void setPresenter(ResetpswdContract.Presenter presenter) {
        mResetpswdPresenter = checkNotNull(presenter, "presenter cannot be null!");
    }

    @Override
    public void setAuth(FirebaseAuth auth) {
        mAuth = checkNotNull(auth, "auth cannot be null!");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_resetpswd, container, false);
        ButterKnife.bind(this,root);

        bnt_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResetpswdPresenter.onClickedResetpswd(editTextEmail.getText().toString());
            }
        });

        return root;
    }

    @Override
    public void resetpswd(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mResetpswdPresenter.resetCompleted(task);
                    }
                });
    }

    @Override
    public void showToast(String text, int toastTime) {
        Toast.makeText(getContext(),text, toastTime).show();
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
    public void showProgress() {
        mDialog = ProgressDialog.show(getContext(),"", "please wait sendind mail");
    }

    @Override
    public void hideProgress() {
        mDialog.dismiss();
    }

}
