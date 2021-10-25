package com.example.easypeasy.screens.common;

import android.app.Activity;

import com.example.easypeasy.RecipesApplication;
import com.example.easypeasy.common.dependancyInjection.ControllerCompositionRoot;

public class BaseActivity extends Activity {

    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((RecipesApplication) getApplication()).getCompositionRoot(), this);
        }
        return mControllerCompositionRoot;
    }
}
