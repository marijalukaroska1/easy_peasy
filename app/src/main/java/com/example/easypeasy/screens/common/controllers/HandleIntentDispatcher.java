package com.example.easypeasy.screens.common.controllers;

public interface HandleIntentDispatcher {

    void registerListener(HandleIntentListener listener);

    void unregisterListener(HandleIntentListener listener);
}
