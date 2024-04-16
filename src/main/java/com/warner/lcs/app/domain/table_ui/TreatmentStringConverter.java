package com.warner.lcs.app.domain.table_ui;

import com.warner.lcs.app.domain.State;
import com.warner.lcs.app.domain.Treatment;
import javafx.util.StringConverter;

public class TreatmentStringConverter extends StringConverter<Treatment> {
    @Override
    public String toString(Treatment treatment) {

        return treatment == null  ?  null : treatment.getTreatmentName();

    }

    @Override
    public Treatment fromString(String s) {
        return null;
    }
}
