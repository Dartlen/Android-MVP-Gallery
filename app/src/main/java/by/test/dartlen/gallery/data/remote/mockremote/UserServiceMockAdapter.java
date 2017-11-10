package by.test.dartlen.gallery.data.remote.mockremote;

import by.test.dartlen.gallery.data.remote.retrofit.UserService;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/***
 * Created by Dartlen on 02.11.2017.
 */

public class UserServiceMockAdapter {
    private MockRetrofit mockRetrofit;

    public UserService swapretrofit(Retrofit ret){
        NetworkBehavior behavior = NetworkBehavior.create();

        mockRetrofit = new MockRetrofit.Builder(ret)
                .networkBehavior(behavior)
                .build();

        BehaviorDelegate<UserService> delegate = mockRetrofit.create(UserService.class);
        UserService mockUserService = new MockUserServiceResponse(delegate);

        return mockUserService;
    }
}
