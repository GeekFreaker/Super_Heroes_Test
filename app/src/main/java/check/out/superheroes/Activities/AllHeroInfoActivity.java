package check.out.superheroes.Activities;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.slidertypes.BaseSliderView;
import com.glide.slider.library.slidertypes.TextSliderView;

import check.out.superheroes.Models.SuperHero;
import check.out.superheroes.Network.GetDataService;
import check.out.superheroes.Network.RetrofitClientInstance;
import check.out.superheroes.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AllHeroInfoActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private TextView mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    private int catchInt;
    private SliderLayout HeroLook;
    private ProgressDialog pg;
    private TextView heroBiography;
    private TextView heroConnections;
    private TextView heroAppearence;
    private TextView heroPowerStats;
    private TextView heroWork;
    private RequestOptions requestOptions;
    private String TAG;
    private GetDataService getService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_hero_info);
        TAG = "AllHeroActivity -->";
        Intent fromMain = getIntent();
        catchInt = fromMain.getExtras().getInt("number");

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        heroAppearence = findViewById(R.id.hero_appearance);
        heroConnections = findViewById(R.id.hero_connection);
        heroBiography = findViewById(R.id.hero_bio);
        heroPowerStats = findViewById(R.id.hero_powerstats);
        heroWork = findViewById(R.id.hero_work);
        HeroLook = findViewById(R.id.img_hero);

        requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        pg = new ProgressDialog(this);
        pg.setMessage("loading super heroes...");
        pg.show();

        getService = RetrofitClientInstance.getRetrofit().create(GetDataService.class);

        callSuperHero(pg,getService,catchInt);
    }

    public void callSuperHero(ProgressDialog pg ,GetDataService getService, int catchInt){
        Call<SuperHero> call = getService.getId(catchInt);
        call.enqueue(new Callback<SuperHero>() {
            @Override
            public void onResponse(Call<SuperHero> call, Response<SuperHero> response) {
                pg.dismiss();
                Log.d(TAG,"Working"+response.message());
                if (response.body() != null) {
                    GenerateSingleItem(response.body());
                } else {
                    Log.d(TAG,"issues with the API"+response.message());
                    Toast.makeText(getApplicationContext(), "Her deosn't exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuperHero> call, Throwable t) {
                Log.d(TAG,"issues with the API"+ t.getMessage());
                pg.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    public void GenerateSingleItem(SuperHero item) {
        String hero = (int) item.getId() + ". " + item.getName();

        String bio = getString(R.string.bio) + "\n\n" +
                getString(R.string.alignment) + " : " + item.getBiography().getAlignment() + "\n" +
                getString(R.string.full_name) + " : " + item.getBiography().getFullName() + "\n" +
                getString(R.string.alter_ego) + " : " + item.getBiography().getAlterEgos() + "\n" +
                getString(R.string.appearance) + " : " + item.getBiography().getFirstAppearance() + "\n" +
                getString(R.string.birthplace) + " : " + item.getBiography().getPlaceOfBirth() + "\n" +
                getString(R.string.published) + " : " + item.getBiography().getPublisher();

        String appareance = getString(R.string.appearance) + "\n\n" +
                getString(R.string.eye_color) + " : " + item.getAppearance().getEyeColor() + "\n" +
                getString(R.string.gender) + " : " + item.getAppearance().getGender() + "\n" +
                getString(R.string.hair_color) + "  : " + item.getAppearance().getHairColor() + "\n" +
                getString(R.string.race) + " : " + item.getAppearance().getRace();

        String powerstats = getString(R.string.power_stats) + "\n\n" +
                getString(R.string.combat) + " : " + item.getPowerStats().getCombat() + "\n" +
                getString(R.string.durable) + " : " + item.getPowerStats().getDurability() + "\n" +
                getString(R.string.intel) + " : " + item.getPowerStats().getIntelligence() + "\n" +
                getString(R.string.power) + " : " + item.getPowerStats().getPower() + "\n" +
                getString(R.string.speed) + " : " + item.getPowerStats().getSpeed() + "\n" +
                getString(R.string.strength) + " : " + item.getPowerStats().getStrength();

        String work = getString(R.string.work) + "\n\n" +
                getString(R.string.base) + " :" + item.getWork().getBase() + "\n" +
                getString(R.string.job) + " : " + item.getWork().getOccupation();

        String connections = getString(R.string.connection) + "\n\n" +
                getString(R.string.affiliates) + " :" + item.getConnections().getGroupAffiliation() + "\n" +
                getString(R.string.relatives) + " : " + item.getConnections().getRelatives();

        mContentView.setText(hero);
        heroBiography.setText(bio);
        heroAppearence.setText(appareance);
        heroConnections.setText(connections);
        heroPowerStats.setText(powerstats);
        heroWork.setText(work);

        //Image slider logic

        for (int pic = 0; pic < 4; pic++) {
            TextSliderView View = new TextSliderView(this);
            switch (pic) {
                case 0:

                    View.image(item.getImages().getLg())
                            .description(Float.toString(item.getId()))
                            .setRequestOption(requestOptions)
                            .setProgressBarVisible(true)
                            .setOnSliderClickListener(this);
                    break;

                case 1:
                    View.image(item.getImages().getLg())
                            .description(item.getName())
                            .setRequestOption(requestOptions)
                            .setProgressBarVisible(true)
                            .setOnSliderClickListener(this);
                    break;

                case 2:
                    View.image(item.getImages().getLg())
                            .description(item.getSlug())
                            .setRequestOption(requestOptions)
                            .setProgressBarVisible(true)
                            .setOnSliderClickListener(this);
                    break;

                case 3:
                    View.image(item.getImages().getLg())
                            .description(item.getWork().getOccupation())
                            .setRequestOption(requestOptions)
                            .setProgressBarVisible(true)
                            .setOnSliderClickListener(this);
                    break;
            }
            HeroLook.addSlider(View);
        }

        HeroLook.setPresetTransformer(SliderLayout.Transformer.Fade);
        HeroLook.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        HeroLook.setDuration(2000);
        HeroLook.stopCyclingWhenTouch(true);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation== Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_all_hero_info);
        }
        if(newConfig.orientation== Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_all_hero_info);
        }
    }
}
