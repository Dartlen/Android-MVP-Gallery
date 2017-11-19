package by.test.dartlen.gallery.gallery;

import android.support.annotation.NonNull;

import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.App;
import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImage;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 28.10.2017.
 */

public class GalleryPresenter implements GalleryContract.Presenter {

    private GalleryRepository mGalleryRepository;
    private GalleryContract.View mGalleryView;

    public GalleryPresenter(@NonNull GalleryRepository galleryRepository, @NonNull GalleryContract.View galleryView){
        mGalleryRepository = checkNotNull(galleryRepository, "gallery cannot be null");
        mGalleryView = checkNotNull(galleryView, "galleryView cannot be null!");

        mGalleryView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadFirstPage(int page) {

        String token = mGalleryRepository.getUser().getToken();

        mGalleryRepository.getImages(new GalleryRemoteDataSource.LoadImageCallback() {
            @Override
            public void onDataLoaded(ResponseDataImage dataResponse) {
                mGalleryView.addItemToAdapter(dataResponse);
            }

            @Override
            public void onError(String error) {

            }
        }, page, token);
    }

    @Override
    public void onBackPressed() {
        App.INSTANCE.getRouter().exit();
    }
}
