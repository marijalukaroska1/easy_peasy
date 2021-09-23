package com.example.easypeasy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.models.Ingredient;

import java.util.List;

public class RecipeInformationUsedIngredientsAdapter extends RecyclerView.Adapter<RecipeInformationUsedIngredientsAdapter.ViewHolder> {

    List<Ingredient> usedIngredientList;

    public RecipeInformationUsedIngredientsAdapter(List<Ingredient> usedIngredientList) {
        this.usedIngredientList = usedIngredientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recipe_information_used_ingredient_item, parent, false);
        return new RecipeInformationUsedIngredientsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeInformationUsedIngredientsAdapter.ViewHolder holder, int position) {
        String usedIngredient = "\u2022" + " " + usedIngredientList.get(position).getNameWithAmount();
        holder.textView.setText(usedIngredient);
    }

    @Override
    public int getItemCount() {
        return usedIngredientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recipeTitleId);
        }
    }
}
