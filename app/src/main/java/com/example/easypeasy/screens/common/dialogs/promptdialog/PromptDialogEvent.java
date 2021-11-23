package com.example.easypeasy.screens.common.dialogs.promptdialog;

public class PromptDialogEvent {

    public enum Button {
        POSITIVE,
        NEGATIVE
    }

    private final Button mClickedButton;

    public PromptDialogEvent(Button mClickedButton) {
        this.mClickedButton = mClickedButton;
    }

    public Button getClickedButton() {
        return mClickedButton;
    }
}
