package by.test.dartlen.gallery.data.remote;

import java.util.List;

public interface RemovePhotoCallback {
    void onRemoved(List<Image> dataImages);
    void onDataNotAvailable(String error);
}
