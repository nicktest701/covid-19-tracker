package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import country.Country;
import java.util.concurrent.CompletableFuture;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CovidService {

    Gson gson = new GsonBuilder().setLenient().create();

    String baseUrl = "https://disease.sh/v3/covid-19/";
    String baseUrl_Local = "http://localhost:4000/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    CovidApi getWorld = retrofit.create(CovidApi.class);

    public JsonObject getAll() {

        Call<JsonObject> call = getWorld.getAllWorld();
        CompletableFuture<JsonObject> completableFuture = new CompletableFuture<>();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                completableFuture.complete(response.body());
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable thrwbl) {
                completableFuture.completeExceptionally(thrwbl);
            }
        });

        JsonObject object = completableFuture.join();
        return object;
    }

    public List getAllCountry() {

        Call<List<Country>> call = getWorld.getAllCountries();
        CompletableFuture<List<Country>> completableFuture = new CompletableFuture<>();

        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                completableFuture.complete(response.body());
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable thrwbl) {
                completableFuture.completeExceptionally(thrwbl);
            }
        });

        List<Country> list = completableFuture.join();
        return list;
    }

}
