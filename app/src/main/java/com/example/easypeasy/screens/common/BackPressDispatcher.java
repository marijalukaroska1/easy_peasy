package com.example.easypeasy.screens.common;

public interface BackPressDispatcher {

    void registerListener(BackPressListener listener);

    void unregisterListener(BackPressListener listener);
}
