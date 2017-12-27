package by.test.dartlen.gallery.register;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.test.dartlen.gallery.App;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 27.12.2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter{

    private final RegisterContract.View mRegisterView;

    public RegisterPresenter(@NotNull RegisterContract.View registerView, @NonNull FirebaseAuth firebaseAuth){
        mRegisterView = checkNotNull(registerView,"registerView cannot be null");

        mRegisterView.setPresenter(this);
        mRegisterView.setAuth(firebaseAuth);
    }

    @Override
    public void start() {

    }

    public void onClickedRegister(String login, String password){
        if(isValidEmail(login) && password.length()>=6){
            mRegisterView.showProgress();
            mRegisterView.signUp(login,password);
        }else {
            mRegisterView.showDialog("Incorrect username or password.");
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
    public void signUpCompleted(Task<AuthResult> task) {
        if (!task.isSuccessful()) {
            mRegisterView.hideProgress();
            mRegisterView.showToast("Authentication failed." + task.getException(), Toast.LENGTH_SHORT);
        } else {
            mRegisterView.hideProgress();
            App.INSTANCE.getRouter().navigateTo("gallery");
        }
    }
}
