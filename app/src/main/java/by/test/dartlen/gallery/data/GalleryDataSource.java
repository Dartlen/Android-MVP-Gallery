package by.test.dartlen.gallery.data;

import android.support.annotation.NonNull;

import by.test.dartlen.gallery.data.remote.retrofit.DataResponse;
import by.test.dartlen.gallery.data.remote.retrofit.LoginData;

/**
 * Created by Dartlen on 26.10.2017.
 */

public interface GalleryDataSource {

    interface LoadLoginCallback{
        void onLoggined(DataResponse login);
        void onDataNotAvailable(String error);
    }

    void signup(@NonNull LoadLoginCallback callback, @NonNull LoginData loginData);
    void signin(@NonNull LoadLoginCallback callback, @NonNull LoginData loginData);
}

