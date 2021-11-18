package com.example.easypeasy.screens.common.controllers;

import androidx.fragment.app.Fragment;

import com.example.easypeasy.common.RecipesApplication;
import com.example.easypeasy.common.dependancyInjection.ControllerCompositionRoot;

public class BaseFragment extends Fragment {
    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((RecipesApplication) requireActivity().getApplication()).getCompositionRoot(), requireActivity());
        }
        return mControllerCompositionRoot;
    }
}
