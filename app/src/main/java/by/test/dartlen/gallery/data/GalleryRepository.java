package by.test.dartlen.gallery.data;

import android.support.annotation.NonNull;
import android.util.Log;

import static com.google.common.base.Preconditions.checkNotNull;
import by.test.dartlen.gallery.data.GalleryDataSource;
import by.test.dartlen.gallery.data.local.greendao.GalleryLocalDataSource;
import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;
import by.test.dartlen.gallery.data.remote.retrofit.DataResponse;
import by.test.dartlen.gallery.data.remote.retrofit.LoginData;

/***
 * Created by Dartlen on 26.10.2017.
 */

public class GalleryRepository implements GalleryDataSource{

    private static GalleryRepository INSTANCE = null;

    private final GalleryDataSource mGalleryRemoteDataSource;

    private final GalleryDataSource mGalleryLocalDataSource;

    private  GalleryRepository(@NonNull GalleryDataSource galleryRemoteDataSource,
                               @NonNull GalleryDataSource galleryLocalDataSource){
        mGalleryRemoteDataSource = checkNotNull(galleryRemoteDataSource);
        mGalleryLocalDataSource = checkNotNull(galleryLocalDataSource);
    }

    public static GalleryRepository getInstance(GalleryDataSource galleryRemoteDataSource,
                                                GalleryDataSource galleryLocalDataSource){
        if(INSTANCE == null){
            INSTANCE = new GalleryRepository(galleryRemoteDataSource, galleryLocalDataSource);
        }

        return INSTANCE;
    }

    @Override
    public void signup(@NonNull final LoadLoginCallback callback, final @NonNull LoginData loginData) {
        mGalleryRemoteDataSource.signup(new LoadLoginCallback() {
            @Override
            public void onLoggined(DataResponse login) {
                callback.onLoggined(login);
            }

            @Override
            public void onDataNotAvailable(String error) {
                callback.onDataNotAvailable(error);
            }
        }, loginData);

    }

    @Override
    public void signin(final @NonNull LoadLoginCallback callback, final @NonNull LoginData loginData) {
        mGalleryRemoteDataSource.signin(new LoadLoginCallback() {
            @Override
            public void onLoggined(DataResponse login) {
                callback.onLoggined(login);
            }

            @Override
            public void onDataNotAvailable(String error) {
                callback.onDataNotAvailable(error);
            }
        }, loginData);
    }
}
