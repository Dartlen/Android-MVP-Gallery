package by.test.dartlen.gallery.data.remote;

import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.webkit.MimeTypeMap;

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

import java.util.Date;

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

    public void postImage(Uri fileUri, String fileName){
        mDataReference = FirebaseDatabase.getInstance().getReference("images");
        imageReference = FirebaseStorage.getInstance().getReference().child("images");
        fileRef = imageReference.child(fileName);
        fileRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //progressDialog.dismiss();

                        String name = taskSnapshot.getMetadata().getName();
                        String url = taskSnapshot.getDownloadUrl().toString();

                        Log.e("dsadas", "Uri: " + url);
                        //Log.e(TAG, "Name: " + name);

                        writeNewImageInfoToDB(name, url);

                        //Toast.makeText(StorageActivity.this, "File Uploaded ", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //progressDialog.dismiss();
                        Log.e("dsad", "Name: " );
                        //Toast.makeText(StorageActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        // progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        // percentage in progress dialog
                        //progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                })
                .addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                        System.out.println("Upload is paused!");
                    }
                });
    }

    private void writeNewImageInfoToDB(String name, String url) {
        Image info = new Image(url, 123L, 41.24,31.32);

        String key = mDataReference.push().getKey();
        mDataReference.child(key).setValue(info);
    }



}

