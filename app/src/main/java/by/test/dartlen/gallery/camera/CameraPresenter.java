package by.test.dartlen.gallery.camera;

import org.greenrobot.greendao.annotation.NotNull;

import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImagePost;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 01.11.2017.
 */

public class CameraPresenter implements CameraContract.Presenter {

    private final GalleryRepository mGalleryRepository;
    private final CameraContract.View mCameraView;

    public CameraPresenter(@NotNull GalleryRepository galleryRepository, @NotNull CameraContract.View CameraView){
        mGalleryRepository = checkNotNull(galleryRepository,"gallery cannot be null");
        mCameraView        = checkNotNull(CameraView, "camerafragment cannot be null");
        mCameraView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void postImage(ImageData image) {

        mGalleryRepository.postImage(new GalleryRemoteDataSource.ImagePostCallback() {
            @Override
            public void onDataLoaded(ResponseDataImagePost dataResponse) {
                //TODO: сообщение что изображение згружено
                mCameraView.showMessage("Изображение загружено");
            }

            @Override
            public void onError(String error) {

            }
        },mGalleryRepository.getUser().getToken(), image);
    }
}
