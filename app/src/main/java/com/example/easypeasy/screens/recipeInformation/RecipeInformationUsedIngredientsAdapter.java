package com.example.easypeasy.screens.recipeInformation;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.screens.common.ViewMvcFactory;

import java.util.List;

public class RecipeInformationUsedIngredientsAdapter extends RecyclerView.Adapter<RecipeInformationUsedIngredientsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RecipeInformationUsedIngredientsItemViewMvc mViewItemMvc;

        public ViewHolder(@NonNull RecipeInformationUsedIngredientsItemViewMvc viewMvc) {
            super(viewMvc.getRootView());
            mViewItemMvc = viewMvc;
        }
    }

    private static final String TAG = RecipeInformationUsedIngredientsAdapter.class.getSimpleName();
    private final List<Ingredient> mUsedIngredientList;
    private final ViewMvcFactory mViewMvcFactory;

    public RecipeInformationUsedIngredientsAdapter(List<Ingredient> usedIngredientList, ViewMvcFactory viewMvcFactory) {
        mUsedIngredientList = usedIngredientList;
        mViewMvcFactory = viewMvcFactory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeInformationUsedIngredientsItemViewMvc viewItemMvc = mViewMvcFactory.getRecipeInformationUsedIngredientsItemViewMvc(parent);
        return new RecipeInformationUsedIngredientsAdapter.ViewHolder(viewItemMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeInformationUsedIngredientsAdapter.ViewHolder holder, int position) {
        String usedIngredient = "\u2022" + " " + mUsedIngredientList.get(position).getNameWithAmount();
        Log.d(TAG, "mUsedIngredientList: " + mUsedIngredientList);
        holder.mViewItemMvc.bindUsedIngredientToView(usedIngredient);
    }

    @Override
    public int getItemCount() {
        return mUsedIngredientList.size();
    }
}
