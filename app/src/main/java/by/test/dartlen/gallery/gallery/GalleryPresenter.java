package by.test.dartlen.gallery.gallery;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.data.remote.GetImagesCallback;
import by.test.dartlen.gallery.data.remote.Image;
import by.test.dartlen.gallery.data.remote.RemovePhotoCallback;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 28.10.2017.
 */

public class GalleryPresenter implements GalleryContract.Presenter {

    private GalleryRepository mGalleryRepository;
    private GalleryContract.View mGalleryView;
    private FirebaseAuth mAuth;
    private List<Image> dataImg;
    private Image foremove;

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
                dataImg=dataImages;
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

    @Override
    public void onItemLongClicked(int position) {
        foremove = dataImg.get(position);
        mGalleryView.showDialogQuestion();
    }

    @Override
    public void onClickedQuestionOk() {
        mGalleryRepository.removePhoto(new RemovePhotoCallback() {
            @Override
            public void onRemoved(List<Image> dataImages) {
                mGalleryView.hideQuestionDialog();
            }

            @Override
            public void onDataNotAvailable(String error) {

            }
        },foremove, mAuth.getUid() );

    }

    @Override
    public void onClickedQuestionCancel() {
        mGalleryView.hideQuestionDialog();
    }
}
