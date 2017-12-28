package by.test.dartlen.gallery.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import static com.google.common.base.Preconditions.checkNotNull;

import by.test.dartlen.gallery.data.local.GalleryLocalDataSource;
import by.test.dartlen.gallery.data.local.greendao.Images;
import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;

/***
 * Created by Dartlen on 26.10.2017.
 */

public class GalleryRepository{

    private static GalleryRepository INSTANCE = null;

    private static Integer SIZE_PAGE=5;

    private final GalleryRemoteDataSource mGalleryRemoteDataSource;

    private final GalleryLocalDataSource mGalleryLocalDataSource;

    private boolean isRemoteLoaded = false;

    private List<Images> mCachedImages = new ArrayList<Images>();

    private Integer pageremotecount = 1;

    boolean mCachIsDirty = false;

    private  GalleryRepository(@NonNull GalleryRemoteDataSource galleryRemoteDataSource,
                               @NonNull GalleryLocalDataSource galleryLocalDataSource){
        mGalleryRemoteDataSource = checkNotNull(galleryRemoteDataSource);
        mGalleryLocalDataSource = checkNotNull(galleryLocalDataSource);
    }

    public static GalleryRepository getInstance(GalleryRemoteDataSource galleryRemoteDataSource,
                                                GalleryLocalDataSource galleryLocalDataSource){
        if(INSTANCE == null){
            INSTANCE = new GalleryRepository(galleryRemoteDataSource, galleryLocalDataSource);
        }

        return INSTANCE;
    }

    public void appendnewitems(List<Images> images){
        boolean flag = false;
        for(Images im: images) {
            for (Images x : mCachedImages) {
                if (x.getUrl().equals(im.getUrl())) {
                    flag=true;
                }
            }
            if(!flag){
                mCachedImages.add(im);
            }
        }
    }

}



