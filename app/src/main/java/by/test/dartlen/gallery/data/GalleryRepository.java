package by.test.dartlen.gallery.data;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.firebase.storage.UploadTask;

import org.greenrobot.greendao.annotation.NotNull;
import static com.google.common.base.Preconditions.checkNotNull;

import by.test.dartlen.gallery.data.local.GalleryLocalDataSource;
import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;
import by.test.dartlen.gallery.data.remote.PostImageCallback;

/***
 * Created by Dartlen on 26.10.2017.
 */

public class GalleryRepository{

    private static GalleryRepository INSTANCE = null;

    private final GalleryRemoteDataSource mGalleryRemoteDataSource;

    private final GalleryLocalDataSource mGalleryLocalDataSource;

    private GalleryRepository(@NonNull GalleryRemoteDataSource galleryRemoteDataSource,
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

    public void postImage(@NotNull final PostImageCallback callback, Uri fileUri, String fileName, final Double lat,
                          final Double lng, final Long date, final String userid){
        mGalleryRemoteDataSource.postImage(new PostImageCallback() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                callback.onSuccess(taskSnapshot);
            }

            @Override
            public void onFailure(Exception exception) {
                callback.onFailure(exception);
            }

            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                callback.onProgress(taskSnapshot);
            }

            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                callback.onPaused(taskSnapshot);
            }
        },fileUri, fileName, lat, lng, date, userid);
    }
}



