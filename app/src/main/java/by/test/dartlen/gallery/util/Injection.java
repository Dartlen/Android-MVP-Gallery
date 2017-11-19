package by.test.dartlen.gallery.util;

import android.content.Context;
import android.support.annotation.NonNull;

import by.test.dartlen.gallery.data.local.GalleryLocalDataSource;
import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;
import by.test.dartlen.gallery.data.GalleryRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/***
 * Created by Dartlen on 27.10.2017.
 */

public class Injection {
    public static GalleryRepository provideGalleryRepository(@NonNull Context context){
        checkNotNull(context);

        return GalleryRepository.getInstance(GalleryRemoteDataSource.getInstance(),GalleryLocalDataSource.getInstance(context));
    }
}