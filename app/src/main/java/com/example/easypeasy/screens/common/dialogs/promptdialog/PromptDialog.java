package com.example.easypeasy.screens.common.dialogs.promptdialog;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.dialogs.BaseDialog;
import com.example.easypeasy.screens.common.dialogs.DialogsEventBus;

public class PromptDialog extends BaseDialog {

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

    private static final String ARGS_TITLE = "ARGS_TITLE";
    private static final String ARGS_MESSAGE = "ARGS_MESSAGE";
    private static final String ARGS_POSITIVE_BTN_CAPTION = "ARGS_POSITIVE_BTN_CAPTION";
    private static final String ARGS_NEGATIVE_BTN_CAPTION = "ARGS_NEGATIVE_BTN_CAPTION";
    private static final String TAG = PromptDialog.class.getSimpleName();
    private TextView title;
    private TextView message;
    private AppCompatButton buttonPositive;
    private AppCompatButton buttonNegative;
    private DialogsEventBus mDialogsEventBus;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Log.d(TAG, "onCreateDialog");

        if (getArguments() == null) {
            throw new RuntimeException("arguments must not be null");
        }

        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog_prompt);
        title = dialog.findViewById(R.id.txt_title);
        message = dialog.findViewById(R.id.txt_message);
        buttonPositive = dialog.findViewById(R.id.btn_positive);
        buttonNegative = dialog.findViewById(R.id.btn_negative);

        title.setText(getArguments().getString(ARGS_TITLE));
        message.setText(getArguments().getString(ARGS_MESSAGE));
        buttonPositive.setText(getArguments().getString(ARGS_POSITIVE_BTN_CAPTION));
        buttonNegative.setText(getArguments().getString(ARGS_NEGATIVE_BTN_CAPTION));

        mDialogsEventBus = getCompositionRoot().getDialogEventBus();

        buttonNegative.setOnClickListener(v -> onNegativeButtonClicked());
        buttonPositive.setOnClickListener(v -> onPositiveButtonClicked());

        return dialog;
    }

    void onNegativeButtonClicked() {
        dismiss();
        mDialogsEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.NEGATIVE));
    }

    void onPositiveButtonClicked() {
        dismiss();
        mDialogsEventBus.postEvent(new PromptDialogEvent(PromptDialogEvent.Button.POSITIVE));
    }
}
