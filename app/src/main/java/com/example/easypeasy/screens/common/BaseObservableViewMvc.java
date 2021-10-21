package com.example.easypeasy.screens.common;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseObservableViewMvc<ListenerType> extends BaseViewMvc
        implements ObservableListViewMvc<ListenerType> {

    private final Set<ListenerType> listeners = new HashSet<>();

    public void registerListener(ListenerType listener) {
        listeners.add(listener);
    }

    public void unregisterListener(ListenerType listener) {
        listeners.remove(listener);
    }

    protected Set<ListenerType> getListeners() {
        return Collections.unmodifiableSet(listeners);
    }
}
