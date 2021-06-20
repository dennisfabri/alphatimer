package org.lisasp.alphatimer.livetiming.components;

import com.vaadin.flow.component.HasValue;

public class TextLabelChanged implements HasValue.ValueChangeEvent<String> {
    @Override
    public HasValue<?, String> getHasValue() {
        return null;
    }

    @Override
    public boolean isFromClient() {
        return false;
    }

    @Override
    public String getOldValue() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }
}
