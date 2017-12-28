package by.test.dartlen.gallery.map;

import android.support.annotation.NonNull;

import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.data.Mapper;
import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 31.10.2017.
 */

public class MapPresenter implements MapContract.Presenter{

    private GalleryRepository mGalleryRepository;
    private MapContract.View mMapView;
    private Mapper mMapper;

    @Override
    public void start() {
        loadPoints();
    }

    public MapPresenter(@NonNull GalleryRepository galleryRepository, @NonNull MapContract.View mapView){
        mGalleryRepository = checkNotNull(galleryRepository, "gallery cannot be null");
        mMapView = checkNotNull(mapView, "galleryView cannot be null!");

        mMapView.setPresenter(this);
    }

    private void loadPoints(){

    }
}
