package by.test.dartlen.gallery.map;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.data.remote.GetImagesCallback;
import by.test.dartlen.gallery.data.remote.Image;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 31.10.2017.
 */

public class MapPresenter implements MapContract.Presenter{

    private GalleryRepository mGalleryRepository;
    private MapContract.View mMapView;
    private FirebaseAuth mAuth;
    @Override
    public void start() {
        loadPoints();
    }

    public MapPresenter(@NonNull GalleryRepository galleryRepository, @NonNull MapContract.View mapView,
    @NonNull FirebaseAuth firebaseauth){
        mGalleryRepository = checkNotNull(galleryRepository, "gallery cannot be null");
        mMapView           = checkNotNull(mapView, "galleryView cannot be null!");
        mAuth              = checkNotNull(firebaseauth, "firebaseauth cannot be null!");
        mMapView.setPresenter(this);
    }

    private void loadPoints(){

        mGalleryRepository.getImages(new GetImagesCallback() {
            @Override
            public void onImagesDataLoaded(List<Image> dataImages) {
                mMapView.showPoints(dataImages);
            }

            @Override
            public void onDataNotAvailable(String error) {
                //mMapView.showMessage(error);
            }
        }, mAuth.getUid());
    }
}
