package by.test.dartlen.gallery.data.remote;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.greendao.annotation.NotNull;

/***
 * Created by Dartlen on 27.10.2017.
 */

public class GalleryRemoteDataSource {

    private static GalleryRemoteDataSource INSTANCE;

    private DatabaseReference mDataReference;
    private StorageReference imageReference;
    private StorageReference fileRef;

    public static GalleryRemoteDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GalleryRemoteDataSource();
        }
        return INSTANCE;
    }

    public GalleryRemoteDataSource(){}

    public void postImage(@NotNull final PostImageCallback callback, final Uri fileUri, final String fileName, final Double lat,
                          final Double lng, final Long date, String userid){
        mDataReference = FirebaseDatabase.getInstance().getReference("images").child(userid);
        imageReference = FirebaseStorage.getInstance().getReference().child("images").child(userid);
        fileRef = imageReference.child(fileName);
        fileRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String name = taskSnapshot.getMetadata().getName();
                        String url = taskSnapshot.getDownloadUrl().toString();

                        writeNewImageInfoToDB(fileUri.toString(), lat, lng, date);
                        callback.onSuccess(taskSnapshot);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        callback.onFailure(exception);
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        callback.onProgress(taskSnapshot);
                    }
                })
                .addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                        callback.onPaused(taskSnapshot);
                    }
                });
    }

    private void writeNewImageInfoToDB(String url, Double lat, Double lng, Long date) {
        Image info = new Image(url, date, lat, lng);

        String key = mDataReference.push().getKey();
        mDataReference.child(key).setValue(info);
    }
}

