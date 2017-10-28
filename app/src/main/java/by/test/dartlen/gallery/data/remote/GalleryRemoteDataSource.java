package by.test.dartlen.gallery.data.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import by.test.dartlen.gallery.data.GalleryDataSource;
import by.test.dartlen.gallery.data.remote.retrofit.ApiFactory;
import by.test.dartlen.gallery.data.remote.retrofit.DataResponse;
import by.test.dartlen.gallery.data.remote.retrofit.LoginData;
import by.test.dartlen.gallery.data.remote.retrofit.RetrofitResponse;
import by.test.dartlen.gallery.data.remote.retrofit.RetrofitResponseCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * Created by Dartlen on 27.10.2017.
 */

public class GalleryRemoteDataSource implements GalleryDataSource{

    private static GalleryRemoteDataSource INSTANCE;

    public static GalleryRemoteDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GalleryRemoteDataSource();
        }
        return INSTANCE;
    }

    private GalleryRemoteDataSource(){}

    @Override
    public void signin(final @NonNull LoadLoginCallback callback, final @NonNull LoginData loginData) {
        retrofit2.Call<DataResponse> call = ApiFactory.login().Signin(loginData);

        call.enqueue(new RetrofitResponse<DataResponse>(new RetrofitResponseCallback<DataResponse>() {
            @Override
            public void onDataLoaded(DataResponse dataResponse) {
                callback.onLoggined(dataResponse);
            }

            @Override
            public void onError(String error) {
                callback.onDataNotAvailable(error);
            }
        }));
    }

    @Override
    public void signup(final @NonNull LoadLoginCallback callback, final @NonNull LoginData loginData) {
        retrofit2.Call<DataResponse> call = ApiFactory.login().Signup(loginData);

        call.enqueue(new RetrofitResponse<DataResponse>(new RetrofitResponseCallback<DataResponse>() {
            @Override
            public void onDataLoaded(DataResponse dataResponse) {
                callback.onLoggined(dataResponse);
            }

            @Override
            public void onError(String error) {
                callback.onDataNotAvailable(error);
            }
        }));
    }
}
