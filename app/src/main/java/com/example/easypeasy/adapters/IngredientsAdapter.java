package com.example.easypeasy.adapters;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.activities.SearchByIngredientsActivity;
import com.example.easypeasy.events.InsertIngredientFieldListener;
import com.example.easypeasy.events.UnitsSpinnerClickListener;
import com.example.easypeasy.models.Ingredient;

import java.util.List;

import static com.example.easypeasy.Constants.PAYLOAD_INSERT_INGREDIENT_FIELD;
import static com.example.easypeasy.Constants.PAYLOAD_INSERT_INGREDIENT_FIELD_UNITS;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private static final String TAG = "IngredientsAdapter";
    List<Ingredient> ingredientList;
    InsertIngredientFieldListener insertIngredientFieldListener;
    UnitsSpinnerClickListener unitsSpinnerClickListener;
    Activity activity;
    String[] ingredientPossibleUnits = new String[0];

    public IngredientsAdapter(List<Ingredient> ingredientList, InsertIngredientFieldListener insertIngredientFieldListener, UnitsSpinnerClickListener unitsSpinnerClickListener, Activity activity) {
        this.ingredientList = ingredientList;
        this.insertIngredientFieldListener = insertIngredientFieldListener;
        this.unitsSpinnerClickListener = unitsSpinnerClickListener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_insert_ingredient, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            // compare each Object in the payloads to the PAYLOAD you provided to notifyItemChanged
            for (Object payload : payloads) {
                if (payload.equals(PAYLOAD_INSERT_INGREDIENT_FIELD)) {
                    ((ViewHolder) holder).insertIngredientField.setVisibility(View.INVISIBLE);
                } else if (payload.equals(PAYLOAD_INSERT_INGREDIENT_FIELD_UNITS)) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, ingredientPossibleUnits);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    holder.unitsSpinner.setAdapter(adapter);
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "in onBindViewHolder position: " + position + " ingredientList: " + ingredientList);

        SearchManager searchManager =
                (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        holder.insertIngredientName.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));

        Ingredient ingredient = ingredientList.get(position);

        if (ingredient != null) {
            holder.insertIngredientName.setQuery(ingredient.getName(), false);
            holder.insertIngredientQuantity.setText(String.valueOf(ingredient.getAmount()));
            holder.unitsSpinner.setPrompt(ingredient.getUnit());

            if (position == getItemCount() - 1) {
                holder.insertIngredientField.setVisibility(View.VISIBLE);
            } else {
                holder.insertIngredientField.setVisibility(View.INVISIBLE);
            }

            if (ingredient.getName() != null && !ingredient.getName().isEmpty()) {
                holder.insertIngredientQuantity.setEnabled(true);
            }

            holder.insertIngredientName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d(TAG, "in onQueryTextSubmit ingredientName: " + query);
                    ingredient.setName(query);
                    sendSearchQuery(query, position);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d(TAG, "in onQueryTextChange ingredientName: " + newText);
                    if (!newText.isEmpty()) {
                        ingredient.setName(newText);
                        holder.insertIngredientQuantity.setEnabled(true);
                    } else {
                        holder.insertIngredientQuantity.setEnabled(false);
                    }
                    return true;
                }
            });

            holder.insertIngredientName.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
                Log.d(TAG, "in onFocusChange: " + holder.insertIngredientName.getQuery().toString() + " ingredientFetchDataPosition: " + position + " hasFocus: " + hasFocus);
                if (!holder.insertIngredientName.getQuery().toString().isEmpty() && !hasFocus) {
                    holder.insertIngredientQuantity.setEnabled(true);
                    ingredient.setName(holder.insertIngredientName.getQuery().toString());
                    sendSearchQuery(holder.insertIngredientName.getQuery().toString(), position);
                }
            });

            holder.insertIngredientQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.d(TAG, "in onTextChanged ingredientQuantity: " + s);
                    if (s != null && !s.toString().equals("")) {
                        ingredientList.get(position).setAmount(Float.parseFloat(s.toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_item, ingredientPossibleUnits);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.unitsSpinner.setAdapter(adapter);

            holder.unitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ingredient.setUnit(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            holder.insertIngredientField.setOnClickListener(v -> {
                for (Ingredient in : ingredientList) {
                    Log.d(TAG, "ingredient: " + in.toString());
                }
                insertIngredientFieldListener.insertItemFieldAndNotify(ingredient);
            });
        }
    }


    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public void setIngredientPossibleUnits(String[] possibleUnits) {
        this.ingredientPossibleUnits = possibleUnits;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        SearchView insertIngredientName;
        EditText insertIngredientQuantity;
        ImageView insertIngredientField;
        Spinner unitsSpinner;
        ImageView searchViewCloseButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            insertIngredientName = itemView.findViewById(R.id.insertIngredientEditTextId);
            insertIngredientQuantity = itemView.findViewById(R.id.insertQuantityEditTextId);
            insertIngredientField = itemView.findViewById(R.id.addNewIngredientFieldId);
            unitsSpinner = itemView.findViewById(R.id.unitsSpinnerId);
            insertIngredientName.setInputType(InputType.TYPE_CLASS_TEXT);
            searchViewCloseButton = (ImageView) insertIngredientName.findViewById(androidx.appcompat.R.id.search_close_btn);
        }
    }

    private void sendSearchQuery(String query, int ingredientPositionInAdapter) {
        Log.d(TAG, "sendSearchQuery: " + query);
        Intent intent = new Intent(activity, SearchByIngredientsActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        intent.putExtra("query", query);
        intent.putExtra("ingredientPositionInAdapter", ingredientPositionInAdapter);
        activity.startActivity(intent);
    }
}
