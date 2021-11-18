package com.example.easypeasy.screens.common.controllers;

import androidx.fragment.app.FragmentActivity;

import com.example.easypeasy.common.RecipesApplication;
import com.example.easypeasy.common.dependancyInjection.ControllerCompositionRoot;

public class BaseActivity extends FragmentActivity {

    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((RecipesApplication) getApplication()).getCompositionRoot(), this);
        }
        return mControllerCompositionRoot;
    }
}
