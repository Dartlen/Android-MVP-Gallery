package by.test.dartlen.gallery.gallery;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.data.remote.GetImagesCallback;
import by.test.dartlen.gallery.data.remote.Image;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 28.10.2017.
 */

public class GalleryPresenter implements GalleryContract.Presenter {

    private GalleryRepository mGalleryRepository;
    private GalleryContract.View mGalleryView;
    private FirebaseAuth mAuth;

    public GalleryPresenter(@NonNull GalleryRepository galleryRepository, @NonNull GalleryContract.View galleryView,
                            @NonNull FirebaseAuth firebaseAuth){
        mGalleryRepository = checkNotNull(galleryRepository, "gallery cannot be null");
        mGalleryView       = checkNotNull(galleryView, "galleryView cannot be null!");
        mAuth              = checkNotNull(firebaseAuth, "firebase cannot be null!");
        mGalleryView.setPresenter(this);
    }

    @Override
    public void start() {
        mGalleryView.showLogin(mAuth.getCurrentUser().getEmail());
    }

    @Override
    public void loadImages() {
        mGalleryRepository.getImages(new GetImagesCallback() {
            @Override
            public void onImagesDataLoaded(List<Image> dataImages) {
                mGalleryView.showImages(dataImages);
            }

            @Override
            public void onDataNotAvailable(String error) {
                //mGalleryView.showError();
            }
        }, mAuth.getUid());
    }

    @Override
    public void onBackPressed() {
        App.INSTANCE.getRouter().exit();
    }

    @Override
    public void onClickedFab() {
        App.INSTANCE.getRouter().navigateTo("camera");
    }
}
