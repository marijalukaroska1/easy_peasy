package com.example.easypeasy.screens.common.dialogs;

import androidx.fragment.app.DialogFragment;

import com.example.easypeasy.common.RecipesApplication;
import com.example.easypeasy.common.dependancyInjection.ControllerCompositionRoot;

public class BaseDialog extends DialogFragment {

    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((RecipesApplication) requireActivity().getApplication()).getCompositionRoot(), requireActivity());
        }
        return mControllerCompositionRoot;
    }
}
