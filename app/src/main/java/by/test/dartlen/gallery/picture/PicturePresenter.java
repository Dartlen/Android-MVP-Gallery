package by.test.dartlen.gallery.picture;

import org.greenrobot.greendao.annotation.NotNull;

import by.test.dartlen.gallery.data.GalleryDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 01.11.2017.
 */

public class PicturePresenter implements PictureContract.Presenter {

    private final GalleryDataSource mGalleryRepository;
    private final PictureContract.View mPictureView;
    public PicturePresenter(@NotNull GalleryDataSource galleryRepository, @NotNull PictureContract.View pictureView){
        mGalleryRepository = checkNotNull(galleryRepository,"gallery cannot be null");
        mPictureView       = checkNotNull(pictureView, "pictureView cannot be null");
        mPictureView.setPresenter(this);
    }

    @Override
    public void start() {

    }


}
