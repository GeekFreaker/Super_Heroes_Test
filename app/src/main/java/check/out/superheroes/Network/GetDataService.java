package check.out.superheroes.Network;

import java.util.List;

import check.out.superheroes.Models.SuperHero;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("all.json")
    Call<List<SuperHero>> getAllSuperHeroes();

    @GET("id/{id}.json")
    Call<SuperHero> getId(@Path(value = "id",encoded = true)int id);


}
