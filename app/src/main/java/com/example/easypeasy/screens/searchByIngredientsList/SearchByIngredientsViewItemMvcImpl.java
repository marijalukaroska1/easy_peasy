package com.example.easypeasy.screens.searchByIngredientsList;

import android.app.SearchableInfo;
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

import androidx.appcompat.widget.SearchView;

import com.example.easypeasy.R;
import com.example.easypeasy.models.Ingredient;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;

import java.util.ArrayList;
import java.util.List;

public class SearchByIngredientsViewItemMvcImpl extends BaseObservableViewMvc<SearchByIngredientsViewItemMvc.Listener> implements SearchByIngredientsViewItemMvc {

    private static final String TAG = SearchByIngredientsViewItemMvcImpl.class.getSimpleName();

    SearchView insertIngredientName;
    EditText insertIngredientQuantity;
    ImageView insertIngredientField;
    Spinner unitsSpinner;
    ImageView searchViewCloseButton;
    List<Ingredient> mIngredientList;
    SearchableInfo mSearchableInfo;

    public SearchByIngredientsViewItemMvcImpl(LayoutInflater inflater, ViewGroup parent, SearchableInfo searchableInfo, List<Ingredient> ingredients) {
        setRootView(inflater.inflate(R.layout.layout_insert_ingredient, parent, false));
        insertIngredientName = findViewById(R.id.insertIngredientEditTextId);
        insertIngredientQuantity = findViewById(R.id.insertQuantityEditTextId);
        insertIngredientField = findViewById(R.id.addNewIngredientFieldId);
        unitsSpinner = findViewById(R.id.unitsSpinnerId);
        insertIngredientName.setInputType(InputType.TYPE_CLASS_TEXT);
        searchViewCloseButton = (ImageView) insertIngredientName.findViewById(androidx.appcompat.R.id.search_close_btn);
        mSearchableInfo = searchableInfo;
        mIngredientList = new ArrayList<>(ingredients);
    }

    @Override
    public void bindIngredientToView(int position) {
        insertIngredientName.setSearchableInfo(mSearchableInfo);

        Ingredient ingredient = mIngredientList.get(position);

        if (ingredient != null) {
            insertIngredientName.setQuery(ingredient.getName(), false);
            insertIngredientQuantity.setText(String.valueOf(ingredient.getAmount()));
            unitsSpinner.setPrompt(ingredient.getUnit());

            if (position == mIngredientList.size() - 1) {
                insertIngredientField.setVisibility(View.VISIBLE);
            } else {
                insertIngredientField.setVisibility(View.INVISIBLE);
            }

            if (ingredient.getName() != null && !ingredient.getName().isEmpty()) {
                insertIngredientQuantity.setEnabled(true);
            }

            insertIngredientName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                        insertIngredientQuantity.setEnabled(true);
                    } else {
                        insertIngredientQuantity.setEnabled(false);
                    }
                    return true;
                }
            });

            insertIngredientName.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
                Log.d(TAG, "in onFocusChange: " + insertIngredientName.getQuery().toString() + " ingredientFetchDataPosition: " + position + " hasFocus: " + hasFocus);
                if (!insertIngredientName.getQuery().toString().isEmpty() && !hasFocus) {
                    insertIngredientQuantity.setEnabled(true);
                    ingredient.setName(insertIngredientName.getQuery().toString());
                    sendSearchQuery(insertIngredientName.getQuery().toString(), position);
                }
            });

            insertIngredientQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.d(TAG, "in onTextChanged ingredientQuantity: " + s);
                    if (s != null && !s.toString().equals("")) {
                        mIngredientList.get(position).setAmount(Float.parseFloat(s.toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            unitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ingredient.setUnit(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            insertIngredientField.setOnClickListener(v -> {
                for (Listener listener : getListeners()) {
                    listener.insertNewIngredientButtonClicked(ingredient);
                }
            });
        }
    }

    @Override
    public List<Ingredient> getIngredients() {
        return mIngredientList;
    }

    @Override
    public void bindPossibleUnits(String[] unitsList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, unitsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitsSpinner.setAdapter(adapter);
    }

    @Override
    public void setInsertIngredientFieldVisibility(int visibility) {
        insertIngredientField.setVisibility(visibility);
    }

    private void sendSearchQuery(String query, int ingredientPositionInAdapter) {
        Log.d(TAG, "sendSearchQuery: " + query);
        Intent intent = new Intent(getContext(), SearchByIngredientsActivity.class);
        intent.setAction(Intent.ACTION_SEARCH);
        intent.putExtra("query", query);
        intent.putExtra("ingredientPositionInAdapter", ingredientPositionInAdapter);
        getContext().startActivity(intent);
    }
}
