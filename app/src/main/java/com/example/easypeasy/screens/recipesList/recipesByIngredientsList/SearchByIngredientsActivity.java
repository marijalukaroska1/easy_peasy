package com.example.easypeasy.screens.recipesList.recipesByIngredientsList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentTransaction;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.BackPressListener;
import com.example.easypeasy.screens.common.BaseActivity;
import com.example.easypeasy.screens.recipeDetails.HandleIntentListener;

public class SearchByIngredientsActivity extends BaseActivity {

    private static final String TAG = SearchByIngredientsActivity.class.getSimpleName();
    private HandleIntentListener mHandleIntentListener;
    private BackPressListener mBackPressListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(): " + getIntent());
        setContentView(R.layout.layout_content_frame);

        SearchByIngredientsFragment fragment;
        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragment = new SearchByIngredientsFragment();
            ft.add(R.id.frame_content, fragment).commit();
        } else {
            fragment = (SearchByIngredientsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
        }

        mHandleIntentListener = fragment;
        mBackPressListener = fragment;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mHandleIntentListener.onHandleIntent(intent);
    }

    @Override
    public void onBackPressed() {
        if (!mBackPressListener.onBackPress()) {
            super.onBackPressed();
        }
    }
}