package com.example.easypeasy.screens.navDrawer;

import android.widget.FrameLayout;

import com.example.easypeasy.screens.common.views.ObservableViewMvc;

public interface NavDrawerViewMvc extends ObservableViewMvc<NavDrawerViewMvc.Listener> {

    interface Listener {
        void onSearchByIngredientsClicked();
        void onSearchByNutrientsClicked();
    }

    boolean isDrawerOpen();

    void closeDrawer();

    void openDrawer();

    FrameLayout getFragmentFrame();
}
