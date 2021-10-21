package com.example.easypeasy.screens.common;

public interface ObservableListViewMvc<ListenerType> extends ViewMvc {

    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);
}
