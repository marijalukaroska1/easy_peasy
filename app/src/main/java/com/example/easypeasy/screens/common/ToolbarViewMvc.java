package com.example.easypeasy.screens.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;

import com.example.easypeasy.R;

public class ToolbarViewMvc extends BaseViewMvc {

    public interface NavigateUpClickListener {
        void onNavigateUpClicked();
    }

    public interface HamburgerClickListener {
        void onHamburgerClick();
    }

    private final TextView mTextTitle;
    private final AppCompatImageButton mNavigationButton;
    private final AppCompatImageButton mHamburgerButton;
    private NavigateUpClickListener mNavigateUpClickListener;
    private HamburgerClickListener mHamburgerClickListener;

    public ToolbarViewMvc(LayoutInflater inflater, ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.layout_toolbar, parent, false));
        mTextTitle = findViewById(R.id.toolbarTitle);
        mNavigationButton = findViewById(R.id.toolbarBackButton);
        mHamburgerButton = findViewById(R.id.openNavDrawerButtonId);

        mNavigationButton.setOnClickListener(v -> mNavigateUpClickListener.onNavigateUpClicked());
        mHamburgerButton.setOnClickListener(v -> mHamburgerClickListener.onHamburgerClick());
    }

    public void setTitle(String title) {
        mTextTitle.setText(title);
    }

    public void enableNavigationBackButtonAndListen(NavigateUpClickListener navigateUpClickListener) {
        if (mHamburgerClickListener != null) {
            throw new RuntimeException("hamburger and up button shouldn't be shown together");
        }
        mNavigateUpClickListener = navigateUpClickListener;
        mNavigationButton.setVisibility(View.VISIBLE);
    }

    public void enableHamburgerButtonAndListen(HamburgerClickListener hamburgerClickListener) {
        if (mNavigateUpClickListener != null) {
            throw new RuntimeException("hamburger and up button shouldn't be shown together");
        }
        mHamburgerClickListener = hamburgerClickListener;
        mHamburgerButton.setVisibility(View.VISIBLE);
    }
}
