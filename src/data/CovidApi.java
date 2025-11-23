package data;

import com.google.gson.JsonObject;
import country.Country;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface CovidApi {

    @Headers("Content-Type: application/json")

    @GET("all")
    Call<JsonObject> getAllWorld();

    @GET("countries")
    Call<List<Country>> getAllCountries();

    @GET("countrie/{country}")
    Call<JsonObject> getByCountryName(@Path("country") String countryname);

}
