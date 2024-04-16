package com.warner.lcs.app.domain.table_ui;

import com.warner.lcs.app.domain.AdditionalCostService;
import com.warner.lcs.app.domain.Treatment;
import javafx.util.StringConverter;

public class AdditionalCostServiceStringConverter extends StringConverter<AdditionalCostService> {
    @Override
    public String toString(AdditionalCostService additionalCostService) {
        return additionalCostService == null ? null : additionalCostService.getTreatmentName();
    }

    @Override
    public AdditionalCostService fromString(String s) {
        return null;
    }
}
