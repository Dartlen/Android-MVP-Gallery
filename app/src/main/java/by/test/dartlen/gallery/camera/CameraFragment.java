package by.test.dartlen.gallery.camera;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.test.dartlen.gallery.R;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.gallery.MainPageActivity;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 07.11.2017.
 */

public class CameraFragment extends Fragment implements CameraContract.View, MainPageActivity.CameraImage {

    private CameraContract.Presenter mPresenter;

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    public CameraFragment(){}

    @Override
    public void setPresenter(CameraContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void imageFromSave(ImageData image) {
        mPresenter.postImage(image);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_camera, container, false);

        return root;
    }

    @Override
    public void showEror(Exception e, String image) {

    }
}
