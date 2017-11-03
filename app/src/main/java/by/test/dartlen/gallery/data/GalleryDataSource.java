package by.test.dartlen.gallery.data;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

import by.test.dartlen.gallery.data.local.greendao.Users;
import by.test.dartlen.gallery.data.remote.retrofit.RetrofitResponse;
import by.test.dartlen.gallery.data.remote.retrofit.RetrofitResponseCallback;
import by.test.dartlen.gallery.data.remote.retrofit.image.DataImage;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImage;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImagePost;
import by.test.dartlen.gallery.data.remote.retrofit.user.Data;
import by.test.dartlen.gallery.data.remote.retrofit.user.DataResponse;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;

/***
 * Created by Dartlen on 26.10.2017.
 */

public interface GalleryDataSource {

    interface LoadLoginCallback{
        void onLoggined(DataResponse login);
        void onDataNotAvailable(String error);
    }

    interface ImagePostCallback{
        void onDataLoaded(ResponseDataImagePost dataResponse);
        void onError(String error);
    }

    interface LoadImageCallback{
        void onDataLoaded(ResponseDataImage dataResponse);
        void onError(String error);
    }

    void signup(@NonNull LoadLoginCallback callback, @NonNull LoginData loginData);
    void signin(@NonNull LoadLoginCallback callback, @NonNull LoginData loginData);

    void getImages(@NotNull LoadImageCallback callback, @NotNull int page, @NotNull String token);
    void postImage(@NotNull ImagePostCallback callback, @NotNull String token, @NotNull ImageData data);

    void setImages(List<DataImage> data, String token);
    void setUser(Data user);
    Users getUser();

}

