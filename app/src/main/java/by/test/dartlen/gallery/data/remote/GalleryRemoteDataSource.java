package by.test.dartlen.gallery.data.remote;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

    public void postImage(final PostImageCallback callback, final Uri fileUri, final String fileName, final Double lat,
                          final Double lng, final Long date, @NonNull String userid){
        mDataReference = FirebaseDatabase.getInstance().getReference("images").child(userid);
        imageReference = FirebaseStorage.getInstance().getReference().child("images").child(userid);
        fileRef = imageReference.child(userid+date);
        fileRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String name = taskSnapshot.getMetadata().getName();
                        String url = taskSnapshot.getDownloadUrl().toString();

                        writeNewImageInfoToDB(url, lat, lng, new Date().getTime());
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

    public void getImages(final GetImagesCallback callback, String userid){
        mDataReference = FirebaseDatabase.getInstance().getReference("images").child(userid);
        mDataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String,Image>> dataImages = new GenericTypeIndicator<HashMap<String,Image>>(){};
                HashMap<String,Image> listResult = dataSnapshot.getValue(dataImages);
                if(listResult!=null)
                    callback.onImagesDataLoaded(new ArrayList<Image>(listResult.values()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onDataNotAvailable(databaseError.getMessage());
            }
        });
    }
}

