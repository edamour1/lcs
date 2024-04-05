package com.warner.lcs.app.domain.table_ui;

import com.warner.lcs.app.domain.State;
import javafx.util.StringConverter;


public class StateStringConverter extends StringConverter<State> {
    @Override
    public String toString(State state) {

        return state == null ? null : state.getState();
    }

    @Override
    public State fromString(String s) {
        return null;
    }

}
