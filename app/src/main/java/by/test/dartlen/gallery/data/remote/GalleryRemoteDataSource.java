package by.test.dartlen.gallery.data.remote;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.NotNull;

import by.test.dartlen.gallery.data.remote.mockremote.UserServiceMockAdapter;
import by.test.dartlen.gallery.data.remote.retrofit.ApiFactory;
import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImage;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImagePost;
import by.test.dartlen.gallery.data.remote.retrofit.user.DataResponse;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;
import by.test.dartlen.gallery.data.remote.retrofit.RetrofitResponse;
import by.test.dartlen.gallery.data.remote.retrofit.RetrofitResponseCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/***
 * Created by Dartlen on 27.10.2017.
 */

public class GalleryRemoteDataSource {

    private static GalleryRemoteDataSource INSTANCE;

    public static GalleryRemoteDataSource getInstance(){
        if(INSTANCE == null){
            INSTANCE = new GalleryRemoteDataSource();
        }
        return INSTANCE;
    }

    private GalleryRemoteDataSource(){}

    public void signin(final @NonNull LoadLoginCallback callback, final @NonNull LoginData loginData) {
        retrofit2.Call<DataResponse> call = new UserServiceMockAdapter()
                .swapretrofit(ApiFactory.buildRetrofit()).Signin(loginData);
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

    public void signup(final @NonNull LoadLoginCallback callback, final @NonNull LoginData loginData) {
        retrofit2.Call<DataResponse> call = ApiFactory.get().Signup(loginData);
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

    public void getImages(final @NotNull LoadImageCallback callback, final @NotNull int page,
                          final @NotNull String token) {
        final retrofit2.Call<ResponseDataImage> call = new UserServiceMockAdapter()
                .swapretrofit(ApiFactory.buildRetrofit()).getImages(page, token);
        call.enqueue(new RetrofitResponse<ResponseDataImage>(new RetrofitResponseCallback<ResponseDataImage>() {
            @Override
            public void onDataLoaded(ResponseDataImage dataResponse) {
                callback.onDataLoaded(dataResponse);
            }

            @Override
            public void onError(String error) {
                callback.onError(error);
            }
        }));

    }

    public void postImage(final ImagePostCallback callback, String token, ImageData data) {
        final retrofit2.Call<ResponseDataImagePost> call = new UserServiceMockAdapter()
                .swapretrofit(ApiFactory.buildRetrofit()).postImage(token, data);

        call.enqueue(new Callback<ResponseDataImagePost>() {
            @Override
            public void onResponse(Call<ResponseDataImagePost> call, Response<ResponseDataImagePost> response) {
                ResponseDataImagePost dataImage = response.body();
                callback.onDataLoaded(dataImage);
            }

            @Override
            public void onFailure(Call<ResponseDataImagePost> call, Throwable t) {
                callback.onError(t.toString());
            }
        });

    }

    public interface LoadLoginCallback{
        void onLoggined(DataResponse login);
        void onDataNotAvailable(String error);
    }

    public interface LoadImageCallback{
        void onDataLoaded(ResponseDataImage dataResponse);
        void onError(String error);
    }

    public interface ImagePostCallback{
        void onDataLoaded(ResponseDataImagePost dataResponse);
        void onError(String error);
    }

}

