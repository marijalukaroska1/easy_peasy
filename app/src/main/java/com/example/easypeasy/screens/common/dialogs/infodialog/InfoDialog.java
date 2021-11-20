package com.example.easypeasy.screens.common.dialogs.infodialog;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.dialogs.BaseDialog;

public class InfoDialog extends BaseDialog {

    public static InfoDialog newInstance(String title, String message, String btnCaption) {
        Bundle bundle = new Bundle(3);
        bundle.putString(ARGS_TITLE, title);
        bundle.putString(ARGS_MESSAGE, message);
        bundle.putString(ARGS_BTN_CAPTION, btnCaption);
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.setArguments(bundle);
        return infoDialog;
    }

    private static final String ARGS_TITLE = "ARGS_TITLE";
    private static final String ARGS_MESSAGE = "ARGS_MESSAGE";
    private static final String ARGS_BTN_CAPTION = "ARGS_BTN_CAPTION";
    private TextView mTitle;
    private TextView mMessage;
    private AppCompatButton mButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        if (getArguments() == null) {
            throw new RuntimeException("arguments must not be null");
        }

        Dialog dialog = new Dialog(requireActivity());
        dialog.setContentView(R.layout.dialog_info);

        mTitle = dialog.findViewById(R.id.txt_title);
        mMessage = dialog.findViewById(R.id.txt_message);
        mButton = dialog.findViewById(R.id.btn_positive);

        mTitle.setText(getArguments().getString(ARGS_TITLE));
        mMessage.setText(getArguments().getString(ARGS_MESSAGE));
        mButton.setText(getArguments().getString(ARGS_BTN_CAPTION));

        mButton.setOnClickListener(v -> dialog.dismiss());

        return dialog;
    }
}
