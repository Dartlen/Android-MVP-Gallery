package by.test.dartlen.gallery.data.remote;

import com.google.firebase.storage.UploadTask;

/***
 * Created by Dartlen on 29.12.2017.
 */

public interface PostImageCallback {
    void onSuccess(UploadTask.TaskSnapshot taskSnapshot);
    void onFailure(Exception exception);
    void onProgress(UploadTask.TaskSnapshot taskSnapshot);
    void onPaused(UploadTask.TaskSnapshot taskSnapshot);
}
