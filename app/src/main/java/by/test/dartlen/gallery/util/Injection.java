package by.test.dartlen.gallery.util;

import by.test.dartlen.gallery.data.remote.GalleryRemoteDataSource;
import by.test.dartlen.gallery.data.GalleryRepository;

/***
 * Created by Dartlen on 27.10.2017.
 */

public class Injection {
    public static GalleryRepository provideGalleryRepository(){
        return GalleryRepository.getInstance(GalleryRemoteDataSource.getInstance());
    }
}