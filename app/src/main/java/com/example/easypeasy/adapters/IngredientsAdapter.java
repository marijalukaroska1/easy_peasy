package com.example.easypeasy.adapters;

import android.content.Context;
import android.text.Editable;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.easypeasy.R;
import com.example.easypeasy.Units;
import com.example.easypeasy.events.InsertIngredientFieldListener;
import com.example.easypeasy.events.UnitsSpinnerClickListener;
import com.example.easypeasy.models.Ingredient;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static com.example.easypeasy.Constants.PAYLOAD_INSERT_INGREDIENT_FIELD;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private static final String TAG = "IngredientsAdapter";
    List<Ingredient> ingredientList;
    InsertIngredientFieldListener insertIngredientFieldListener;
    UnitsSpinnerClickListener unitsSpinnerClickListener;
    Context context;

    public IngredientsAdapter(List<Ingredient> ingredientList, InsertIngredientFieldListener insertIngredientFieldListener, UnitsSpinnerClickListener unitsSpinnerClickListener, Context context) {
        this.ingredientList = ingredientList;
        this.insertIngredientFieldListener = insertIngredientFieldListener;
        this.unitsSpinnerClickListener = unitsSpinnerClickListener;
        this.context = context;
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
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "in onBindViewHolder position: " + position);
        Ingredient ingredient = ingredientList.get(position);

        if (ingredient != null) {
            holder.insertIngredientName.setText(ingredient.getName());
            holder.insertIngredientQuantity.setText(String.valueOf(ingredient.getAmount()));

            if (position == getItemCount() - 1) {
                holder.insertIngredientField.setVisibility(View.VISIBLE);
            } else {
                holder.insertIngredientField.setVisibility(View.INVISIBLE);
            }

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

            holder.insertIngredientName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.d(TAG, "in onTextChanged ingredientName: " + s + " position: " + position);
                    ingredientList.get(position).setName(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.insertIngredientField.setOnClickListener(v -> {
                for (Ingredient in : ingredientList) {
                    Log.d(TAG, "ingredient: " + in.toString());
                }
                insertIngredientFieldListener.insertItemFieldAndNotify(ingredient);
            });

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.units_array, android.R.layout.simple_spinner_item);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.unitsSpinner.setAdapter(adapter);
            holder.unitsSpinner.setSelection(getSelectedUnitPosition(ingredient.getUnit()));

            holder.unitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ingredient.setUnit((String) parent.getItemAtPosition(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        EditText insertIngredientName;
        EditText insertIngredientQuantity;
        ImageView insertIngredientField;
        Spinner unitsSpinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            insertIngredientName = itemView.findViewById(R.id.insertIngredientEditTextId);
            insertIngredientQuantity = itemView.findViewById(R.id.insertQuantityEditTextId);
            insertIngredientField = itemView.findViewById(R.id.addNewIngredientFieldId);
            unitsSpinner = itemView.findViewById(R.id.unitsSpinnerId);
        }
    }

    public int getSelectedUnitPosition(String unit) {
        int unitIndex = 0;
        List<Units> unitList = new ArrayList<Units>(EnumSet.allOf(Units.class));
        for (Units unitName : unitList) {
            if (unitName.getUnit().equals(unit)) {
                unitIndex = unitList.indexOf(unitName);
            }
        }
        return unitIndex;
    }
}
