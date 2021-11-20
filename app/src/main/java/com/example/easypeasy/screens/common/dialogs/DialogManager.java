package com.example.easypeasy.screens.common.dialogs;

import android.content.Context;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.dialogs.infodialog.InfoDialog;

public class DialogManager {

    private final Context mContext;
    private FragmentManager mFragmentManager;

    public DialogManager(Context context, FragmentManager fragmentManager) {
        mContext = context;
        mFragmentManager = fragmentManager;
    }

    public void showRecipesFetchErrorDialog(String tag) {
        DialogFragment dialogFragment = InfoDialog.newInstance(getString(R.string.error_fetching_recipes_title),
                getString(R.string.error_fetching_recipes_message), getString(R.string.error_fetching_recipes_button_txt));
        dialogFragment.show(mFragmentManager, tag);
    }

    private String getString(int stringId) {
        return mContext.getString(stringId);
    }
}
