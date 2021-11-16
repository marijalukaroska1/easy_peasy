package com.example.easypeasy.screens.recipeDetails;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.BackPressListener;
import com.example.easypeasy.screens.common.BaseActivity;

public class RecipeDetailsActivity extends BaseActivity {

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();
    private BackPressListener mBackPressListener;
    private HandleIntentListener mHandleIntentListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "in onCreate");

        setContentView(R.layout.layout_content_frame);

        RecipeDetailsFragment fragment;

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragment = new RecipeDetailsFragment();
            ft.add(R.id.frame_content, fragment).commit();
        } else {
            fragment = (RecipeDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
        }

        mBackPressListener = fragment;
        mHandleIntentListener = fragment;

        if (mHandleIntentListener != null) {
            mHandleIntentListener.onHandleIntent(getIntent());
        }
    }


    @Override
    public void onBackPressed() {
        if (!mBackPressListener.onBackPress()) {
            super.onBackPressed();
        }
    }
}
