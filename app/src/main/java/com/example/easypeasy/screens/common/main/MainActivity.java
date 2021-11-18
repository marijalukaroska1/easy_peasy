package com.example.easypeasy.screens.common.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easypeasy.screens.common.controllers.BackPressDispatcher;
import com.example.easypeasy.screens.common.controllers.BackPressListener;
import com.example.easypeasy.screens.common.controllers.BaseActivity;
import com.example.easypeasy.screens.common.controllers.HandleIntentDispatcher;
import com.example.easypeasy.screens.common.controllers.HandleIntentListener;
import com.example.easypeasy.screens.common.screennavigator.ScreenNavigator;
import com.example.easypeasy.screens.common.fragmentframehelper.FragmentContainerWrapper;
import com.example.easypeasy.screens.navDrawer.NavDrawerHelper;
import com.example.easypeasy.screens.navDrawer.NavDrawerViewMvc;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BaseActivity implements
        BackPressDispatcher, FragmentContainerWrapper,
        HandleIntentDispatcher, NavDrawerViewMvc.Listener, NavDrawerHelper {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final Set<BackPressListener> backPressListeners = new HashSet<>();
    private final Set<HandleIntentListener> handleIntentListeners = new HashSet<>();
    private NavDrawerViewMvc mViewMvc;
    private ScreenNavigator mScreenNavigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "savedInstanceState: " + savedInstanceState);

        mViewMvc = getCompositionRoot().getViewMvcFactory().getNavDrawerViewMvc(null);
        mScreenNavigator = getCompositionRoot().getScreenNavigator();
        setContentView(mViewMvc.getRootView());

        if (savedInstanceState == null) {
            mScreenNavigator.toCategoryList();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void registerListener(BackPressListener listener) {
        backPressListeners.add(listener);
    }

    @Override
    public void unregisterListener(BackPressListener listener) {
        backPressListeners.remove(listener);
    }

    @Override
    public void onBackPressed() {
        boolean isBackPressConsumedByAnyListener = false;
        for (BackPressListener listener : backPressListeners) {
            if (listener.onBackPressed()) {
                isBackPressConsumedByAnyListener = true;
            }
        }
        if (isBackPressConsumedByAnyListener) {
            return;
        }

        if (mViewMvc.isDrawerOpen()) {
            mViewMvc.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent() intent action: " + intent.getAction());
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            for (HandleIntentListener listener : handleIntentListeners) {
                listener.onHandleIntent(intent);
            }
        }
    }

    @Override
    public void registerListener(HandleIntentListener listener) {
        handleIntentListeners.add(listener);
    }

    @Override
    public void unregisterListener(HandleIntentListener listener) {
        handleIntentListeners.remove(listener);
    }

    @Override
    public void onSearchByIngredientsClicked() {
        mScreenNavigator.toSearchByIngredients();
    }

    @Override
    public void onSearchByNutrientsClicked() {
        mScreenNavigator.toSearchByNutrients();
    }

    @Override
    public void openDrawer() {
        mViewMvc.openDrawer();
    }

    @Override
    public void closeDrawer() {
        mViewMvc.closeDrawer();
    }

    @Override
    public boolean isDrawerOpen() {
        return mViewMvc.isDrawerOpen();
    }

    @NonNull
    @Override
    public ViewGroup getFragmentContainer() {
        return mViewMvc.getFragmentFrame();
    }
}
