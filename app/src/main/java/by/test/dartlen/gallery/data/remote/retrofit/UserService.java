package by.test.dartlen.gallery.data.remote.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/***
 * Created by Dartlen on 26.10.2017.
 */

public interface UserService {

    @Headers("Content-Type: application/json")
    @POST("/api/account/signup")
    Call<DataResponse> Signup(@Body LoginData data);

    @Headers("Content-Type: application/json")
    @POST("/api/account/signin")
    Call<DataResponse> Signin(@Body LoginData data);
}
