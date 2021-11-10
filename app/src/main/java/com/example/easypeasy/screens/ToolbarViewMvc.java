package com.example.easypeasy.screens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.BaseViewMvc;

public class ToolbarViewMvc extends BaseViewMvc {

    public interface NavigateUpClickListener {
        void onNavigateUpClicked();
    }

    private final TextView mTextTitle;
    private final AppCompatImageButton mButtonNavigation;
    private NavigateUpClickListener mNavigateUpClickListener;

    public ToolbarViewMvc(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_toolbar, parent, false));
        mTextTitle = findViewById(R.id.toolbarTitle);
        mButtonNavigation = findViewById(R.id.toolbarBackButton);

        mButtonNavigation.setOnClickListener(v -> mNavigateUpClickListener.onNavigateUpClicked());

    }

    public void setTitle(String title) {
        mTextTitle.setText(title);
    }

    public void setmButtonNavigationVisibility(int visibility) {
        mButtonNavigation.setVisibility(visibility);
    }

    public void enableNavigationButtonAndListen(NavigateUpClickListener navigateUpClickListener) {
        mNavigateUpClickListener = navigateUpClickListener;
    }
}
