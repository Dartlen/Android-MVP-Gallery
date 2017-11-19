package by.test.dartlen.gallery.picture;

import org.greenrobot.greendao.annotation.NotNull;

import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.data.local.greendao.Images;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 10.11.2017.
 */

public class PicturePresenter implements PictureContract.Presenter{

    GalleryRepository mGalleryRepository;
    PictureContract.View mPictureView;
    Images mImageData;

    @Override
    public void start() {
        mPictureView.showPicture(mImageData);
    }

    public PicturePresenter(){}

    public PicturePresenter(@NotNull GalleryRepository galleryRepository, @NotNull PictureContract.View pictureView,
                            @NotNull Images imageData){
        mGalleryRepository = checkNotNull(galleryRepository, "repository can't be null");
        mPictureView       = checkNotNull(pictureView, "viewfragment can't be null");
        mImageData         = checkNotNull(imageData);
        mPictureView.setPresenter(this);
    }

    @Override
    public void setPictureData(Images imageData) {
       // mImageData = checkNotNull(imageData);
    }
}
