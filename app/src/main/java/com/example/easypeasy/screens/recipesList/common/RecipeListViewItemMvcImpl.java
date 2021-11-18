package com.example.easypeasy.screens.recipesList.common;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.easypeasy.R;
import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.screens.common.views.BaseObservableViewMvc;

public class RecipeListViewItemMvcImpl extends BaseObservableViewMvc<RecipeListViewItemMvc.Listener> implements RecipeListViewItemMvc {

    private static final String TAG = RecipeListViewItemMvcImpl.class.getSimpleName();
    private final TextView recipeTitleTextView;
    private final TextView noRecipesFoundTextView;
    private final ImageView imageView;

    public RecipeListViewItemMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_recipe_item, parent, false));

        recipeTitleTextView = findViewById(R.id.recipeText);
        imageView = findViewById(R.id.recipeImage);
        noRecipesFoundTextView = findViewById(R.id.noRecipesFoundId);
    }

    @Override
    public void bindRecipe(RecipeDetailsSchema recipe) {
        recipeTitleTextView.setText(recipe.getTitle());

        Glide.with(getContext())
                .load(recipe.getImageUrl())
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
                .into(imageView);

        getRootView().setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onRecipeClicked(getContext(), recipe);
            }
        });
    }

    @Override
    public void showNoRecipesFoundMsg() {
        noRecipesFoundTextView.setVisibility(View.VISIBLE);
    }
}
