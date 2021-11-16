package com.example.easypeasy.screens.categoriesList;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.BackPressListener;
import com.example.easypeasy.screens.common.BaseActivity;

public class CategoryActivity extends BaseActivity {
    private static final String TAG = CategoryActivity.class.getSimpleName();
    private BackPressListener mBackPressedListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "in onCreate");
        CategoryFragment categoryFragment;

        setContentView(R.layout.layout_content_frame);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            categoryFragment = new CategoryFragment();
            ft.add(R.id.frame_content, categoryFragment).commit();
        } else {
            //if this activity is recreated then fragment can be get from the system
            categoryFragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
        }

        mBackPressedListener = categoryFragment;
    }

    @Override
    public void onBackPressed() {
        if (!mBackPressedListener.onBackPress()) {
            super.onBackPressed();
        }
    }
}
