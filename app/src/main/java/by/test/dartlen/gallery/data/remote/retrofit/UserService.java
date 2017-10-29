package by.test.dartlen.gallery.data.remote.retrofit;

import by.test.dartlen.gallery.data.remote.retrofit.image.ImageData;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImagePost;
import by.test.dartlen.gallery.data.remote.retrofit.user.DataResponse;
import by.test.dartlen.gallery.data.remote.retrofit.image.ResponseDataImage;
import by.test.dartlen.gallery.data.remote.retrofit.user.LoginData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @Headers("Content-Type: application/json")
    @GET("/api/image")
    Call<ResponseDataImage> getImages(@Query("page") int p, @Header("Access-Token") String token);

    @Headers("Content-Type: application/json")
    @POST("/api/image")
    Call<ResponseDataImagePost> postImage(@Header("Access-Token") String token, @Body ImageData data);

}
