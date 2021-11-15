package com.example.easypeasy.screens.recipesList.common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.networking.recipes.RecipeDetailsSchema;
import com.example.easypeasy.screens.common.ViewMvcFactory;
import com.example.easypeasy.screens.recipeDetails.RecipeDetailsActivity;

import java.util.List;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.ViewHolder> implements RecipeListViewItemMvc.Listener {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecipeListViewItemMvc mViewItemMvc;

        public ViewHolder(@NonNull RecipeListViewItemMvc viewMvc) {
            super(viewMvc.getRootView());
            mViewItemMvc = viewMvc;
        }
    }

    private static final String TAG = RecipesListAdapter.class.getSimpleName();
    private final List<RecipeDetailsSchema> recipesList;

    private final ViewMvcFactory mViewMvcFactory;


    public RecipesListAdapter(List<RecipeDetailsSchema> recipesList, ViewMvcFactory viewMvcFactory) {
        Log.d(TAG, "recipesList: " + recipesList.size());
        this.recipesList = recipesList;
        mViewMvcFactory = viewMvcFactory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeListViewItemMvc viewItemMvc = mViewMvcFactory.getRecipeListViewItemMvc(parent);
        viewItemMvc.registerListener(this);
        return new RecipesListAdapter.ViewHolder(viewItemMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (recipesList.size() > 0) {
            holder.mViewItemMvc.bindRecipe(recipesList.get(position));
        } else {
            holder.mViewItemMvc.showNoRecipesFoundMsg();
        }
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    @Override
    public void onRecipeClicked(Context context, RecipeDetailsSchema recipe) {
        Log.d(TAG, "Clicked on recipe: " + recipe.getTitle() + " id: " + recipe.getId());
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra("recipeId", recipe.getId());
        context.startActivity(intent);
    }
}
