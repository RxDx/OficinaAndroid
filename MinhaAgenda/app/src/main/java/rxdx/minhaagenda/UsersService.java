package rxdx.minhaagenda;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by rodrigo on 11/19/15.
 */
public interface UsersService {

    @GET("users.json")
    Call<List<User>> getUsers();

    @DELETE("users/{id}.json")
    Call<User> deleteUser(@Path("id") Integer userId);

    @POST("users.json")
    Call<User> createUser(@Body User user);
}
