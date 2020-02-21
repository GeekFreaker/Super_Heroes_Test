package check.out.superheroes.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import check.out.superheroes.Adapters.SuperHeroAdapter;
import check.out.superheroes.Models.SuperHero;
import check.out.superheroes.Network.GetDataService;
import check.out.superheroes.Network.RetrofitClientInstance;
import check.out.superheroes.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    //--- Variable Declaration.
    private String TAG;
    private List<SuperHero> superHeroes;
    private ProgressDialog pg;
    private View.OnClickListener onItemCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            Intent intHolder = new Intent( getApplicationContext(), AllHeroInfoActivity.class);
            int position = viewHolder.getAdapterPosition();
            intHolder.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intHolder.putExtra("number",(int)superHeroes.get(position).getId());
            startActivity(intHolder);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TAG = "MainActivity -->";

//      A dialog to show when the loading of API content begins.
        pg = new ProgressDialog(this);
        pg.setMessage("loading super heroes...");
        pg.show();

//      Create the Retrofit instance to get data from the SuperHeroAPI
        GetDataService getService = RetrofitClientInstance.getRetrofit().create(GetDataService.class);

        Call<List<SuperHero>> call = getService.getAllSuperHeroes();
        call.enqueue(new Callback<List<SuperHero>>() {
            @Override
            public void onResponse(Call<List<SuperHero>> call, Response<List<SuperHero>> response) {
                Log.d(TAG,"Working"+response.message());
                if (response.body() != null) {
                    GenerateDataList(response.body());
                } else {
                    Log.d(TAG,"issues with the API"+response.message());
                    Toast.makeText(getApplicationContext(), "Her deosn't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SuperHero>> call, Throwable t) {
                pg.dismiss();
                Log.d(TAG,"Issues with API: "+t.getMessage());
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * A function that setups the SuperHer Adapter View.
     *
     * @param items
    */
    private void GenerateDataList(List<SuperHero> items){
        SuperHeroAdapter itemsAdapter = new SuperHeroAdapter(getApplicationContext(), items);
        superHeroes = items;
        RecyclerView View = findViewById(R.id.super_hero);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        View.setLayoutManager(llm);
        View.setAdapter(itemsAdapter);
        itemsAdapter.msetOnItemClickLister(onItemCLickListener);
    }

}
