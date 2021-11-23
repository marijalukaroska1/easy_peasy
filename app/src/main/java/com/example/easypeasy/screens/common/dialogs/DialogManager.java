package com.example.easypeasy.screens.common.dialogs;

import android.content.Context;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.dialogs.promptdialog.PromptDialog;

public class DialogManager {

    private final Context mContext;
    private FragmentManager mFragmentManager;

    public DialogManager(Context context, FragmentManager fragmentManager) {
        mContext = context;
        mFragmentManager = fragmentManager;
    }

    public void showRecipesFetchErrorDialog(String tag) {
//        DialogFragment dialogFragment = InfoDialog.newInstance(getString(R.string.error_fetching_recipes_title),
//                getString(R.string.error_fetching_recipes_message), getString(R.string.error_fetching_recipes_button_txt));
//        dialogFragment.show(mFragmentManager, tag);

        DialogFragment dialogFragment = PromptDialog.newInstance(
                getString(R.string.error_network_call_failed_title),
                getString(R.string.error_network_call_failed_message),
                getString(R.string.error_network_call_failed_negative_button_caption),
                getString(R.string.error_network_call_failed_positive_button_caption));

        dialogFragment.show(mFragmentManager, tag);
    }

    private String getString(int stringId) {
        return mContext.getString(stringId);
    }
}
