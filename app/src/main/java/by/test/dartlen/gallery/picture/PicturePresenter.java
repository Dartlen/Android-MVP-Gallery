package by.test.dartlen.gallery.picture;

import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.data.remote.Image;


import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 10.11.2017.
 */

public class PicturePresenter implements PictureContract.Presenter{

    GalleryRepository mGalleryRepository;
    PictureContract.View mPictureView;
    Image mImageData;

    @Override
    public void start() {
        mPictureView.showPicture(mImageData);
    }

    public PicturePresenter(){}

    public PicturePresenter(GalleryRepository galleryRepository, PictureContract.View pictureView,
                            Image imageData){
        mGalleryRepository = checkNotNull(galleryRepository, "repository can't be null");
        mPictureView       = checkNotNull(pictureView, "viewfragment can't be null");
        mImageData         = checkNotNull(imageData);
        mPictureView.setPresenter(this);
    }

    @Override
    public void setPictureData(Image imageData) {
       // mImageData = checkNotNull(imageData);
    }
}
