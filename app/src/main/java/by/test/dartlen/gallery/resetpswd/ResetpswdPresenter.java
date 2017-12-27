package by.test.dartlen.gallery.resetpswd;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.test.dartlen.gallery.App;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 27.12.2017.
 */

public class ResetpswdPresenter implements ResetpswdContract.Presenter{

    private ResetpswdContract.View mResetpswdView;

    @Override
    public void start() {

    }

    public ResetpswdPresenter(@NonNull ResetpswdContract.View resetpswdView, @NonNull FirebaseAuth auth){
        mResetpswdView = checkNotNull(resetpswdView, "resetpswd cannot be null");
        mResetpswdView.setPresenter(this);
        mResetpswdView.setAuth(auth);
    }

    @Override
    public void onClickedResetpswd(String email) {
        if(isValidEmail(email)){
            mResetpswdView.showProgress();
            mResetpswdView.resetpswd(email);
        }else {
            mResetpswdView.showDialog("Incorrect username or password.");
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
    public void resetCompleted(Task<Void> task) {
        mResetpswdView.hideProgress();
        if(task.isSuccessful()){
            mResetpswdView.showToast("We have sent you instructions to reset your password!", Toast.LENGTH_SHORT);
            App.INSTANCE.getRouter().navigateTo("firstpage");
        }else {
            mResetpswdView.showToast("Failed to send reset email!", Toast.LENGTH_SHORT);
        }
    }
}
