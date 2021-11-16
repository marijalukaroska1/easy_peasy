package com.example.easypeasy.screens.common;

import android.content.Context;
import android.widget.Toast;

public class MessagesDisplayer {

    private Context mContext;

    public MessagesDisplayer(Context context) {
        mContext = context;
    }

    public void showFetchRecipeDetailsFailureMsg() {
        Toast.makeText(mContext, "Error fetching recipe information", Toast.LENGTH_LONG).show();
    }
}
