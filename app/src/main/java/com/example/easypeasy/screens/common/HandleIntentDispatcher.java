package com.example.easypeasy.screens.common;

public interface HandleIntentDispatcher {

    void registerListener(HandleIntentListener listener);

    void unregisterListener(HandleIntentListener listener);
}
