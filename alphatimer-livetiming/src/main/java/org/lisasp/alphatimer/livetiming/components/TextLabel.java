package org.lisasp.alphatimer.livetiming.components;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.shared.Registration;

public class TextLabel extends Label implements HasValue<HasValue.ValueChangeEvent<String>, String> {

    public TextLabel() {
    }

    public TextLabel(String text) {
        super(text);
    }

    @Override
    public void setValue(String s) {
        setText(s);
    }

    @Override
    public String getValue() {
        return getText();
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        if (!readOnly) {
            throw new IllegalArgumentException("Not Writable");
        }
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public void setRequiredIndicatorVisible(boolean requiredIndicatorVisible) {
        if (requiredIndicatorVisible) {
            throw new IllegalArgumentException("Not Writable");
        }
    }

    @Override
    public boolean isRequiredIndicatorVisible() {
        return false;
    }

    @Override
    public Registration addValueChangeListener(ValueChangeListener valueChangeListener) {
        return () -> {
        };
    }
}
