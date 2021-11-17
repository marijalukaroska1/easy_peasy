package com.example.easypeasy.screens.recipesList.recipesByNutrientsList;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.BackPressListener;
import com.example.easypeasy.screens.common.BaseActivity;

public class SearchByNutrientsActivity extends BaseActivity {
    private static final String TAG = SearchByNutrientsActivity.class.getSimpleName();
    private BackPressListener mBackPressListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "in onCreate");
        setContentView(R.layout.layout_content_frame);

        SearchByNutrientsFragment fragment;
        if (savedInstanceState == null) {
            fragment = new SearchByNutrientsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.frame_content, fragment).commit();
        } else {
            fragment = (SearchByNutrientsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
        }

        mBackPressListener = fragment;
    }

    @Override
    public void onBackPressed() {
        if (!mBackPressListener.onBackPress()) {
            super.onBackPressed();
        }
    }
}
