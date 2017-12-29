package by.test.dartlen.gallery.data.remote;

import java.util.List;

/***
 * Created by Dartlen on 29.12.2017.
 */

public interface GetImagesCallback {
    void onImagesDataLoaded(List<Image> dataImages);
    void onDataNotAvailable(String error);
}
