package com.example.easypeasy.screens.recipeInformation;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.easypeasy.R;
import com.example.easypeasy.common.utils.Constants;
import com.example.easypeasy.models.RecipeData;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;
import com.example.easypeasy.screens.common.ViewMvcFactory;

public class RecipeInformationViewMvcImpl extends BaseObservableViewMvc<RecipeInformationViewMvc> implements RecipeInformationViewMvc {

    private static final String TAG = RecipeInformationViewMvcImpl.class.getSimpleName();
    private final ImageView recipeImageView;
    private final TextView recipeTitleTextView;
    private final TextView readyInMinutesTextView;
    private final TextView servingsTextView;
    private final TextView ingredientSourceUrlTextView;
    private final RecyclerView usedIngredientsRecyclerView;
    private RecipeInformationUsedIngredientsAdapter mAdapter;
    private final ViewMvcFactory mViewMvcFactory;

    public RecipeInformationViewMvcImpl(LayoutInflater inflater, ViewGroup parent, ViewMvcFactory viewMvcFactory) {
        setRootView(inflater.inflate(R.layout.activity_recipe_information, parent, false));
        recipeImageView = findViewById(R.id.recipeImageId);
        recipeTitleTextView = findViewById(R.id.recipeTitleId);
        readyInMinutesTextView = findViewById(R.id.readyInMinutesId);
        servingsTextView = findViewById(R.id.servingsId);
        usedIngredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        ingredientSourceUrlTextView = findViewById(R.id.sourceUrlTextView);
        mViewMvcFactory = viewMvcFactory;
    }

    @Override
    public void bindRecipe(RecipeData recipeData) {
        mAdapter = new RecipeInformationUsedIngredientsAdapter(recipeData.getUsedIngredients(), mViewMvcFactory);
        recipeImageView.setVisibility(View.VISIBLE);
        recipeTitleTextView.setVisibility(View.VISIBLE);
        readyInMinutesTextView.setVisibility(View.VISIBLE);
        servingsTextView.setVisibility(View.VISIBLE);
        usedIngredientsRecyclerView.setVisibility(View.VISIBLE);
        ingredientSourceUrlTextView.setVisibility(View.VISIBLE);

        String readyInMinutesText = getContext().getString(R.string.ready_in_minutes);
        readyInMinutesText = readyInMinutesText.replace(Constants.REGEX_X, String.valueOf(recipeData.getReadyInMinutes()));
        readyInMinutesTextView.setText(readyInMinutesText);

        String servingsText = getContext().getString(R.string.servings);
        servingsText = servingsText.replace(Constants.REGEX_X, String.valueOf(recipeData.getServings()));
        servingsTextView.setText(servingsText);

        recipeTitleTextView.setText(recipeData.getTitle());
        Log.d(TAG, "recipe source url: " + recipeData.getSourceUrl());

        ingredientSourceUrlTextView.setText(Html.fromHtml("<a href=\"" + recipeData.getSourceUrl() + "\">Click for Recipe Source</a> "));

        ingredientSourceUrlTextView.setMovementMethod(LinkMovementMethod.getInstance());
        ingredientSourceUrlTextView.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(recipeData.getSourceUrl()));
            getContext().startActivity(browserIntent);
        });

        Glide.with(getContext())
                .load(recipeData.getImageUrl())
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        usedIngredientsRecyclerView.setLayoutManager(linearLayoutManager);
        usedIngredientsRecyclerView.setAdapter(mAdapter);
    }
}
