package by.test.dartlen.gallery.data;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.firebase.storage.UploadTask;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;
import by.test.dartlen.gallery.data.remote.GetImagesCallback;
import by.test.dartlen.gallery.data.remote.Image;
import by.test.dartlen.gallery.data.remote.PostImageCallback;
import by.test.dartlen.gallery.data.remote.RemovePhotoCallback;

/***
 * Created by Dartlen on 26.10.2017.
 */

public class GalleryRepository{

    private static GalleryRepository INSTANCE = null;

    private final GalleryRemoteDataSource mGalleryRemoteDataSource;

    private GalleryRepository(@NonNull GalleryRemoteDataSource galleryRemoteDataSource){
        mGalleryRemoteDataSource = checkNotNull(galleryRemoteDataSource);
    }

    public static GalleryRepository getInstance(GalleryRemoteDataSource galleryRemoteDataSource){
        if(INSTANCE == null){
            INSTANCE = new GalleryRepository(galleryRemoteDataSource);
        }

        return INSTANCE;
    }

    public void postImage(final PostImageCallback callback, Uri fileUri, String fileName, final Double lat,
                          final Double lng, final Long date,@NonNull String userid){
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

    public void getImages(final GetImagesCallback callback, String userid){
       mGalleryRemoteDataSource.getImages(new GetImagesCallback() {
           @Override
           public void onImagesDataLoaded(List<Image> dataImages) {
               callback.onImagesDataLoaded(dataImages);
           }

           @Override
           public void onDataNotAvailable(String error) {
                callback.onDataNotAvailable(error);
           }
       }, userid);
    }

    public void removePhoto(final RemovePhotoCallback callback, Image image, String userid){
        mGalleryRemoteDataSource.removePhoto(new RemovePhotoCallback() {
            @Override
            public void onRemoved(List<Image> dataImages) {
                callback.onRemoved(dataImages);
            }

            @Override
            public void onDataNotAvailable(String error) {
                callback.onDataNotAvailable(error);
            }
        }, image, userid);
    }
}



