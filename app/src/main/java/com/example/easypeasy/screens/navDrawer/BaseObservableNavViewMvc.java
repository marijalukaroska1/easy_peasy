package com.example.easypeasy.screens.navDrawer;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.BaseObservableViewMvc;
import com.google.android.material.navigation.NavigationView;

import static com.example.easypeasy.screens.navDrawer.DrawerItem.SELECT_SEARCH_BY_INGREDIENTS;
import static com.example.easypeasy.screens.navDrawer.DrawerItem.SELECT_SEARCH_BY_NUTRIENTS;

public abstract class BaseObservableNavViewMvc<ListenerType> extends BaseObservableViewMvc<ListenerType> implements NavDrawerViewMvc {

    private static final String TAG = BaseObservableNavViewMvc.class.getSimpleName();
    private final DrawerLayout mDrawerLayout;
    private final NavigationView mNavigationView;
    private final FrameLayout mFrameLayout;


    public BaseObservableNavViewMvc(LayoutInflater inflater, ViewGroup parent) {

        super.setRootView(inflater.inflate(R.layout.layout_drawer, parent, false));

        mDrawerLayout = findViewById(R.id.drawerLayoutId);
        mNavigationView = findViewById(R.id.navigationViewId);
        mFrameLayout = findViewById(R.id.frameContentId);

        mNavigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.drawer_menu_search_by_ingredients) {
                Log.d(TAG, "onDrawerItemClick: " + SELECT_SEARCH_BY_INGREDIENTS);
                onDrawerItemClick(SELECT_SEARCH_BY_INGREDIENTS);
            } else if (item.getItemId() == R.id.drawer_menu_search_by_nutrients) {
                Log.d(TAG, "onDrawerItemClick: " + SELECT_SEARCH_BY_NUTRIENTS);
                onDrawerItemClick(SELECT_SEARCH_BY_NUTRIENTS);
            }
            mDrawerLayout.closeDrawers();
            return false;
        });
    }

    protected abstract void onDrawerItemClick(DrawerItem item);

    @Override
    protected void setRootView(View view) {
        mFrameLayout.addView(view);
    }


    @Override
    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
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
