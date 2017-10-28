package by.test.dartlen.gallery.data.local.greendao;

import android.content.Context;
import android.support.annotation.NonNull;

import by.test.dartlen.gallery.data.GalleryDataSource;
import by.test.dartlen.gallery.data.remote.retrofit.LoginData;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 27.10.2017.
 */

public class GalleryLocalDataSource implements GalleryDataSource{

    private static GalleryLocalDataSource INSTANCE;

    private GalleryLocalDataSource(@NonNull Context context){
        checkNotNull(context);

    }
    public static GalleryLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GalleryLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void signup(@NonNull LoadLoginCallback callback, @NonNull LoginData loginData) {

    }

    @Override
    public void signin(@NonNull LoadLoginCallback callback, @NonNull LoginData loginData) {

    }

}
