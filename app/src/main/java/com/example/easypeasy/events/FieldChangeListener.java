package com.example.easypeasy.events;

public interface FieldChangeListener {
    void insertItemFieldAndNotify(Object field);
    void removeItemFieldAndNotify(Object field);
}
