package com.warner.lcs.app.domain.table_ui;

import com.warner.lcs.app.domain.Address;
import com.warner.lcs.app.domain.Treatment;
import javafx.util.StringConverter;

public class AddressStringConverter extends StringConverter<Address> {
    @Override
    public String toString(Address address) {
        return address == null ? null : address.getStreet();
    }

    @Override
    public Address fromString(String s) {
        return null;
    }
}
