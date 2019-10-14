import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface API {
    /*登陆*/
    @POST("auth/login")
    Call<login_return> getCall(@Body login k);

    @POST("game/open")
    Call<open_return> getCall(@Header("X-Auth-Token") String token);

    @POST("game/submit")
    Call<Submit_return> getCall(@Header("X-Auth-Token") String token,@Body Puke k);
}
