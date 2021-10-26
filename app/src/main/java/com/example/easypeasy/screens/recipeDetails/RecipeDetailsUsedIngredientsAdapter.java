package com.example.easypeasy.screens.recipeDetails;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.networking.ingredients.IngredientSchema;
import com.example.easypeasy.screens.common.ViewMvcFactory;

import java.util.List;

public class RecipeDetailsUsedIngredientsAdapter extends RecyclerView.Adapter<RecipeDetailsUsedIngredientsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RecipeDetailsUsedIngredientsItemViewMvc mViewItemMvc;

        public ViewHolder(@NonNull RecipeDetailsUsedIngredientsItemViewMvc viewMvc) {
            super(viewMvc.getRootView());
            mViewItemMvc = viewMvc;
        }
    }

    private static final String TAG = RecipeDetailsUsedIngredientsAdapter.class.getSimpleName();
    private final List<IngredientSchema> mUsedIngredientList;
    private final ViewMvcFactory mViewMvcFactory;

    public RecipeDetailsUsedIngredientsAdapter(List<IngredientSchema> usedIngredientList, ViewMvcFactory viewMvcFactory) {
        mUsedIngredientList = usedIngredientList;
        mViewMvcFactory = viewMvcFactory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeDetailsUsedIngredientsItemViewMvc viewItemMvc = mViewMvcFactory.getRecipeInformationUsedIngredientsItemViewMvc(parent);
        return new RecipeDetailsUsedIngredientsAdapter.ViewHolder(viewItemMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailsUsedIngredientsAdapter.ViewHolder holder, int position) {
        String usedIngredient = "\u2022" + " " + mUsedIngredientList.get(position).getNameWithAmount();
        Log.d(TAG, "mUsedIngredientList: " + mUsedIngredientList);
        holder.mViewItemMvc.bindUsedIngredientToView(usedIngredient);
    }

    @Override
    public int getItemCount() {
        return mUsedIngredientList.size();
    }
}
