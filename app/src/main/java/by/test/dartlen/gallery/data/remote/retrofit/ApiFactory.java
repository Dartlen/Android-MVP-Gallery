package by.test.dartlen.gallery.data.remote.retrofit;

/***
 * Created by Dartlen on 26.10.2017.
 */

import android.support.annotation.NonNull;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public final class ApiFactory {

    private static String ROOT_URL = "http://213.184.248.43:9099";

    private static OkHttpClient sClient;

    private static Retrofit mRetrofit;

    private static volatile UserService sService;

    private ApiFactory() {}



    @NonNull
    public static UserService get() {
        UserService service = sService;
        if (service == null) {
            synchronized (ApiFactory.class) {
                service = sService;
                if (service == null) {
                    service = sService = buildRetrofit().create(UserService.class);
                }
            }
        }
        return service;
    }

    @NonNull
    public static Retrofit buildRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return mRetrofit;
    }

    @NonNull
    private static OkHttpClient getClient() {
        OkHttpClient client = sClient;
        if (client == null) {
            synchronized (ApiFactory.class) {
                client = sClient;
                if (client == null) {
                    client = sClient = buildClient();
                }
            }
        }
        return client;
    }

    @NonNull
    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor())
                .build();
    }

}
