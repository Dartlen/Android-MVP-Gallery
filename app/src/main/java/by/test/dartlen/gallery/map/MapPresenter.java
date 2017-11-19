package by.test.dartlen.gallery.map;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import by.test.dartlen.gallery.data.GalleryDataSource;
import by.test.dartlen.gallery.data.GalleryRepository;
import by.test.dartlen.gallery.data.Mapper;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImage;
import by.test.dartlen.gallery.gallery.GalleryContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
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

        mGalleryRepository.getLocalImages(new GalleryDataSource.LoadImageCallback() {
            @Override
            public void onDataLoaded(ResponseDataImage dataResponse) {
                mMapView.showPoints(dataResponse.getData());
            }

            @Override
            public void onError(String error) {

            }
        },1, mGalleryRepository.getUser().getToken());
    }



}
