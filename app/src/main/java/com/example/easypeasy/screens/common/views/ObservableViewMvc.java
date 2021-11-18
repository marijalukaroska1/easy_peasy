package com.example.easypeasy.screens.common.views;

import com.example.easypeasy.screens.common.views.ViewMvc;

public interface ObservableViewMvc<ListenerType> extends ViewMvc {

    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);
}
