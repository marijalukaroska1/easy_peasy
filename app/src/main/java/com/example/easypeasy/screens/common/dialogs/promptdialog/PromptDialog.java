package com.example.easypeasy.screens.common.dialogs.promptdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.easypeasy.screens.common.dialogs.BaseDialog;
import com.example.easypeasy.screens.common.dialogs.DialogsEventBus;

public class PromptDialog extends BaseDialog implements PromptViewMvc.Listener {

    private static final String TAG = PromptDialog.class.getSimpleName();

    private static final String ARGS_TITLE = "ARGS_TITLE";
    private static final String ARGS_MESSAGE = "ARGS_MESSAGE";
    private static final String ARGS_POSITIVE_BTN_CAPTION = "ARGS_POSITIVE_BTN_CAPTION";
    private static final String ARGS_NEGATIVE_BTN_CAPTION = "ARGS_NEGATIVE_BTN_CAPTION";

    public static PromptDialog newInstance(String title, String msg, String positiveBtnCaption, String negativeBtnCaption) {
        Log.d(TAG, "in newInstance()");
        Bundle bundle = new Bundle();
        bundle.putString(ARGS_TITLE, title);
        bundle.putString(ARGS_MESSAGE, msg);
        bundle.putString(ARGS_POSITIVE_BTN_CAPTION, positiveBtnCaption);
        bundle.putString(ARGS_NEGATIVE_BTN_CAPTION, negativeBtnCaption);
        PromptDialog promptDialog = new PromptDialog();
        promptDialog.setArguments(bundle);
        return promptDialog;
    }

    private PromptViewMvc mViewMvc;
    private DialogsEventBus mDialogsEventBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogsEventBus = getCompositionRoot().getDialogEventBus();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Log.d(TAG, "onCreateDialog");

        if (getArguments() == null) {
            throw new RuntimeException("arguments must not be null");
        }

        mViewMvc = getCompositionRoot().getViewMvcFactory().getPromptViewMvc(null);

        mViewMvc.setTitle(getArguments().getString(ARGS_TITLE));
        mViewMvc.setMessage(getArguments().getString(ARGS_MESSAGE));
        mViewMvc.setPositiveButtonCaption(getArguments().getString(ARGS_POSITIVE_BTN_CAPTION));
        mViewMvc.setNegativeButtonCaption(getArguments().getString(ARGS_NEGATIVE_BTN_CAPTION));

        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(mViewMvc.getRootView());

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onNegativeButtonClicked() {
        dismiss();
        mDialogsEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.NEGATIVE));
    }

    @Override
    public void onPositiveButtonClicked() {
        dismiss();
        mDialogsEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.POSITIVE));
    }
}
