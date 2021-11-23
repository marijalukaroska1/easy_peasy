package com.example.easypeasy.screens.navDrawer;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.views.BaseObservableViewMvc;
import com.google.android.material.navigation.NavigationView;

public class NavDrawerViewMvcImpl extends BaseObservableViewMvc<NavDrawerViewMvc.Listener> implements NavDrawerViewMvc {

    private static final String TAG = NavDrawerViewMvcImpl.class.getSimpleName();
    private final DrawerLayout mDrawerLayout;
    private final NavigationView mNavigationView;
    private final FrameLayout mFrameLayout;

    public NavDrawerViewMvcImpl(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_drawer, parent, false));

        mDrawerLayout = findViewById(R.id.drawerLayoutId);
        mNavigationView = findViewById(R.id.navigationViewId);
        mFrameLayout = findViewById(R.id.frame_content);

        mNavigationView.setNavigationItemSelectedListener(item -> {
            mDrawerLayout.closeDrawers();
            if (item.getItemId() == R.id.drawer_menu_search_by_ingredients) {
                for (Listener listener : getListeners()) {
                    listener.onSearchByIngredientsClicked();
                }
            } else if (item.getItemId() == R.id.drawer_menu_search_by_nutrients) {
                for (Listener listener : getListeners()) {
                    listener.onSearchByNutrientsClicked();
                }
            }
            return false;
        });
    }

    @Override
    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return mFrameLayout;
    }

    @Override
    public boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(Gravity.LEFT);
    }

    @Override
    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }
}
