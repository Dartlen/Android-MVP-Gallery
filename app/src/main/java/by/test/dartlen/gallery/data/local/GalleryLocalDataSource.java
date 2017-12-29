package by.test.dartlen.gallery.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import by.test.dartlen.gallery.data.Mapper;
import by.test.dartlen.gallery.data.local.greendao.ImagesDao;
import by.test.dartlen.gallery.data.local.greendao.Users;
import by.test.dartlen.gallery.data.local.greendao.UsersDao;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/***
 * Created by Dartlen on 27.10.2017.
 */

public class GalleryLocalDataSource{

    private static GalleryLocalDataSource INSTANCE;
    private ImagesDao mImagesDao;
    private UsersDao mUsersDao;
    private Context mContext;
    private SharedPreferences prefs;
    private Mapper mapper;

    private GalleryLocalDataSource(@NonNull Context context){
        checkNotNull(context);
    }

    public static GalleryLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GalleryLocalDataSource(context);
        }
        return INSTANCE;
    }

    public Users getUser() {
        return null;
    }

}
