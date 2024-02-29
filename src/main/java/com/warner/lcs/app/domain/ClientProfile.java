package com.warner.lcs.app.domain;

import java.util.List;

public class ClientProfile {

    private Client client;

    private List<Address> addresses;

    ClientProfile(){}

    ClientProfile(Client client, List<Address> addresses) {
        this.client = client;
        this.addresses = addresses;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}
