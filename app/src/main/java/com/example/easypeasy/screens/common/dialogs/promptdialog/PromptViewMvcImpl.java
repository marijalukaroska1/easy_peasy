package com.example.easypeasy.screens.common.dialogs.promptdialog;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.example.easypeasy.R;
import com.example.easypeasy.screens.common.views.BaseObservableViewMvc;

public class PromptViewMvcImpl extends BaseObservableViewMvc<PromptViewMvc.Listener> implements PromptViewMvc {

    private static final String TAG = PromptViewMvcImpl.class.getSimpleName();
    private final AppCompatButton mPositiveButton;
    private final AppCompatButton mNegativeButton;
    private final TextView mTitle;
    private final TextView mMessage;


    public PromptViewMvcImpl(LayoutInflater inflater,
                             ViewGroup parent) {
        setRootView(inflater.inflate(R.layout.dialog_prompt, parent, false));

        mPositiveButton = findViewById(R.id.btn_positive);
        mNegativeButton = findViewById(R.id.btn_negative);
        mTitle = findViewById(R.id.txt_title);
        mMessage = findViewById(R.id.txt_message);

        mPositiveButton.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onPositiveButtonClicked();
            }
        });

        mNegativeButton.setOnClickListener(v -> {
            for (Listener listener : getListeners()) {
                listener.onNegativeButtonClicked();
            }
        });
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setMessage(String message) {
        mMessage.setText(message);
    }

    @Override
    public void setPositiveButtonCaption(String caption) {
        mPositiveButton.setText(caption);
    }

    @Override
    public void setNegativeButtonCaption(String caption) {
        mNegativeButton.setText(caption);
    }
}
