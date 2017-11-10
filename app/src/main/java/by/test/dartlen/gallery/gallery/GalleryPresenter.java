package by.test.dartlen.gallery.gallery;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;

import by.test.dartlen.gallery.data.GalleryDataSource;
import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.data.Mapper;
import by.test.dartlen.gallery.data.remote.retrofit.RetrofitResponseCallback;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImage;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImagePost;
import by.test.dartlen.gallery.login.LoginContract;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 28.10.2017.
 */

public class GalleryPresenter implements GalleryContract.Presenter {

    private GalleryDataSource mGalleryRepository;
    private GalleryContract.View mGalleryView;

    public GalleryPresenter(@NonNull GalleryRepository galleryRepository, @NonNull GalleryContract.View galleryView){
        mGalleryRepository = checkNotNull(galleryRepository, "gallery cannot be null");
        mGalleryView = checkNotNull(galleryView, "galleryView cannot be null!");

        mGalleryView.setPresenter(this);
    }

    @Override
    public void start() {
        loadFirstPage();
    }

    @Override
    public void loadFirstPage() {

        String token = mGalleryRepository.getUser().getToken();

        mGalleryRepository.getImages(new GalleryDataSource.LoadImageCallback() {
            @Override
            public void onDataLoaded(ResponseDataImage dataResponse) {
                mGalleryView.showFirstPage(dataResponse.getData());
            }

            @Override
            public void onError(String error) {

            }
        },1, token);
    }


    @Override
    public void loadNextPage(int currentPage, final GalleryContract.CallbackImages callbackImages) {
        String token = mGalleryRepository.getUser().getToken();

        mGalleryRepository.getImages(new GalleryDataSource.LoadImageCallback() {
            @Override
            public void onDataLoaded(ResponseDataImage dataResponse) {
               callbackImages.load(dataResponse.getData());
            }

            @Override
            public void onError(String error) {

            }
        },currentPage ,token);
    }



}
