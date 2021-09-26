package com.example.easypeasy.activities;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.easypeasy.utils.Constants;
import com.example.easypeasy.R;
import com.example.easypeasy.RecipeInformationInteractorInput;
import com.example.easypeasy.adapters.RecipeInformationUsedIngredientsAdapter;
import com.example.easypeasy.configurators.Configurator;
import com.example.easypeasy.models.RecipeInformationResponse;
import com.example.easypeasy.spoonacular.RecipeInformationRequest;

public class RecipeInformationActivity extends Activity implements RecipeInformationInput {

    private static final String TAG = RecipeInformationActivity.class.getSimpleName();
    public RecipeInformationInteractorInput output;
    long recipeId = 0L;
    ImageView recipeImageView;
    TextView recipeTitleTextView, readyInMinutesTextView, servingsTextView;
    RecyclerView usedIngredientsRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configurator.INSTANCE.configure(this);
        handleIntent();
        setUpUserInterface();
    }

    private void handleIntent() {
        recipeId = getIntent().getLongExtra("recipeId", 0L);
        Log.d(TAG, "fetchRecipeInformationMetaData is called with recipeId: " + recipeId);
        fetchRecipeInformationMetaData();
    }

    private void setUpUserInterface() {
        setContentView(R.layout.activity_recipe_information);
        recipeImageView = findViewById(R.id.recipeImageId);
        recipeTitleTextView = findViewById(R.id.recipeTitleId);
        readyInMinutesTextView = findViewById(R.id.readyInMinutesId);
        servingsTextView = findViewById(R.id.servingsId);
        usedIngredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
    }

    void fetchRecipeInformationMetaData() {
        RecipeInformationRequest recipeInformationRequest = new RecipeInformationRequest(this, recipeId);
        output.fetchRecipeInformation(recipeInformationRequest);
    }

    @Override
    public void displayRecipeInformation(RecipeInformationResponse recipeInformationResponse, RecipeInformationUsedIngredientsAdapter adapter) {
        recipeImageView.setVisibility(View.VISIBLE);
        recipeTitleTextView.setVisibility(View.VISIBLE);
        readyInMinutesTextView.setVisibility(View.VISIBLE);
        servingsTextView.setVisibility(View.VISIBLE);
        usedIngredientsRecyclerView.setVisibility(View.VISIBLE);

        String readyInMinutesText = getString(R.string.ready_in_minutes);
        readyInMinutesText = readyInMinutesText.replace(Constants.REGEX_X, String.valueOf(recipeInformationResponse.getReadyInMinutes()));
        readyInMinutesTextView.setText(readyInMinutesText);

        String servingsText = getString(R.string.servings);
        servingsText = servingsText.replace(Constants.REGEX_X, String.valueOf(recipeInformationResponse.getServings()));
        servingsTextView.setText(servingsText);

        recipeTitleTextView.setText(recipeInformationResponse.getTitle());

        Glide.with(this)
                .load(recipeInformationResponse.getImageUrl())
                .error(R.mipmap.ic_launcher)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) // resize does not respect aspect ratio
                .fitCenter() // scale to fit entire image within ImageView
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // log exception
                        Log.e(TAG, "Error loading image", e);
                        return false; // important to return false so the error placeholder can be placed
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(recipeImageView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        usedIngredientsRecyclerView.setLayoutManager(linearLayoutManager);
        usedIngredientsRecyclerView.setAdapter(adapter);
    }
}
