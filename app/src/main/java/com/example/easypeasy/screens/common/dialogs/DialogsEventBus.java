package com.example.easypeasy.screens.common.dialogs;

import com.example.easypeasy.screens.common.views.BaseObservableViewMvc;

public class DialogsEventBus extends BaseObservableViewMvc<DialogsEventBus.Listener> {

    public interface Listener {
        void onDialogEvent(Object event);
    }

    public void postEvent(Object event) {
        for (Listener listener : getListeners()) {
            listener.onDialogEvent(event);
        }
    }

}
