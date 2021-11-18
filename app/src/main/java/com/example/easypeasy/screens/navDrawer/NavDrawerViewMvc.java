package com.example.easypeasy.screens.navDrawer;

import android.widget.FrameLayout;

import com.example.easypeasy.screens.common.ObservableListViewMvc;

public interface NavDrawerViewMvc extends ObservableListViewMvc<NavDrawerViewMvc.Listener> {

    interface Listener {
        void onSearchByIngredientsClicked();
        void onSearchByNutrientsClicked();
    }

    boolean isDrawerOpen();

    void closeDrawer();

    void openDrawer();

    FrameLayout getFragmentFrame();
}
