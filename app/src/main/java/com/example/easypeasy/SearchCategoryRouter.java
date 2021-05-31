package com.example.easypeasy;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.easypeasy.activities.CategoryActivity;
import com.example.easypeasy.activities.SearchByIngredientsActivity;
import com.example.easypeasy.activities.SearchByCuisineActivity;
import com.example.easypeasy.activities.SearchByNutrientsActivity;
import com.example.easypeasy.events.CategoryItemClickListener;

import java.lang.ref.WeakReference;

interface SearchCategoryRouterInput {
    Intent determineNextScreen(int position);

    void passDataToNextScene(int position, Intent intent);
}

public class SearchCategoryRouter implements SearchCategoryRouterInput, View.OnClickListener, CategoryItemClickListener {

    private static final String TAG = SearchCategoryRouter.class.getSimpleName();
    public WeakReference<CategoryActivity> activity;
    private int chosenCategoryPosition;


    @Override
    public Intent determineNextScreen(int position) {
        //Based on the position or some other data decide what is the next scene
        String category = activity.get().getCategories().get(position).getName();
        Log.d(TAG, "marija category: " + category);
        if (category.equals("ingredients")) {
            return new Intent(activity.get(), SearchByIngredientsActivity.class);
        } else if (category.equals("nutrients")) {
            return new Intent(activity.get(), SearchByNutrientsActivity.class);
        } else {
            return new Intent(activity.get(), SearchByCuisineActivity.class);
        }
    }

    @Override
    public void passDataToNextScene(int position, Intent intent) {

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "marija in onClick");
        Intent intent = determineNextScreen(chosenCategoryPosition);
        activity.get().startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        chosenCategoryPosition = position;
    }
}
