package com.example.easypeasy.screens.searchByIngredientsList;

import android.app.SearchableInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.screens.common.ViewMvcFactory;

import java.util.ArrayList;
import java.util.List;

import static com.example.easypeasy.common.utils.Constants.PAYLOAD_INSERT_INGREDIENT_FIELD;
import static com.example.easypeasy.common.utils.Constants.PAYLOAD_INSERT_INGREDIENT_FIELD_UNITS;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> implements SearchByIngredientsViewItemMvc.Listener {

    interface Listener {
        void insertNewIngredientField(Ingredient ingredient);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        SearchByIngredientsViewItemMvc mViewItemMvc;

        public ViewHolder(@NonNull SearchByIngredientsViewItemMvc viewMvc) {
            super(viewMvc.getRootView());
            mViewItemMvc = viewMvc;
        }
    }

    private static final String TAG = IngredientsAdapter.class.getSimpleName();
    private final List<Ingredient> mIngredientList = new ArrayList<>();
    private final Listener mListener;
    private String[] ingredientPossibleUnits = new String[0];
    private final SearchableInfo mSearchableInfo;
    private final ViewMvcFactory mViewMvcFactory;

    public IngredientsAdapter(IngredientsAdapter.Listener listener, SearchableInfo searchableInfo, ViewMvcFactory viewMvcFactory) {
        mListener = listener;
        mSearchableInfo = searchableInfo;
        mViewMvcFactory = viewMvcFactory;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchByIngredientsViewItemMvc viewItemMvc = mViewMvcFactory.getSearchByIngredientsViewItemMvc(parent, mSearchableInfo);
        viewItemMvc.bindIngredients(mIngredientList);
        viewItemMvc.registerListener(this);
        return new IngredientsAdapter.ViewHolder(viewItemMvc);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            // compare each Object in the payloads to the PAYLOAD you provided to notifyItemChanged
            for (Object payload : payloads) {
                if (payload.equals(PAYLOAD_INSERT_INGREDIENT_FIELD)) {
                    holder.mViewItemMvc.setInsertIngredientFieldVisibility(View.INVISIBLE);
                } else if (payload.equals(PAYLOAD_INSERT_INGREDIENT_FIELD_UNITS)) {
                    holder.mViewItemMvc.bindPossibleUnits(ingredientPossibleUnits);
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "in onBindViewHolder position: " + position + " ingredientList: " + mIngredientList);
        holder.mViewItemMvc.bindIngredientToView(position);
    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }

    @Override
    public void insertNewIngredientButtonClicked(Ingredient ingredient) {
        mListener.insertNewIngredientField(ingredient);
    }

    public void setIngredientPossibleUnits(String[] possibleUnits) {
        this.ingredientPossibleUnits = possibleUnits;
    }

    public List<Ingredient> getIngredientList() {
        return mIngredientList;
    }

    public void bindIngredient(Ingredient ingredient) {
        mIngredientList.add(ingredient);
        notifyItemInserted(mIngredientList.size() - 1);
    }
}
