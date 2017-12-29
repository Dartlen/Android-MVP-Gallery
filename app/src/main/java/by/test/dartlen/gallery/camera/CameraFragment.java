package by.test.dartlen.gallery.camera;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.LocationSettingsStates;

import by.test.dartlen.gallery.R;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 07.11.2017.
 */

public class CameraFragment extends Fragment implements CameraContract.View {

    private CameraContract.Presenter mPresenter;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private static final int CAPTURE_IMAGE_REQUEST_CODE = 1000;
    private ProgressDialog progressDialog;

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    public CameraFragment() {
    }

    @Override
    public void setPresenter(CameraContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getContext());
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void showProgressDialog() {
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showToast(String message, int timetoast) {
        Toast.makeText(getActivity(), message, timetoast).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showCameraActivityResult(Intent intent, int CAPTURE_IMAGE_REQUEST_CODE) {
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mPresenter.getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    default:
                        break;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
                mPresenter.onCaptureImageResult(data);
            }
        }
    }
}
