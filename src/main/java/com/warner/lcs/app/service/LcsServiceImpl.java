package com.warner.lcs.app.service;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.repository.LcsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.text.DecimalFormat;

@Service
public class LcsServiceImpl implements LcsService {

    @Autowired
    LcsRepository lcsRepository;


    @Override
    public AdditionalCostService getAdditionalCostService(AdditionalCostService additionalCostService, Client client) throws Exception {
        return this.lcsRepository.getAdditionalCostService(additionalCostService,client);
    }

    @Override
    public AdditionalCostService updateAdditionalCostServiceQty(AdditionalCostService additionalCostService, Client client) throws Exception {
        return this.lcsRepository.updateAdditionalCostServiceQty(additionalCostService,client);
    }

    @Override
    public Treatment getTreatment(Treatment treatment, Client client) throws Exception {
        return this.lcsRepository.getTreatment(treatment,client);
    }

    @Override
    public Treatment updateTreatmentQty(Treatment treatment, Client client) throws Exception {
        return this.lcsRepository.updateTreatmentQty(treatment,client);
    }

    @Override
    public double calculateTotalCost(InvoiceInformation invoiceInformation) {
        double totalCost = this.calculateTotalTreatmentsCost(invoiceInformation)
                         + this.calculateTotalAdditionalCostServicesCost(invoiceInformation);

        // Create a DecimalFormat object with two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // Format the double value
        String formattedValue = decimalFormat.format(totalCost);

        // Convert the formatted value back to a double
        double totalCostRoundedValue = Double.parseDouble(formattedValue);

        return totalCostRoundedValue;
    }

    @Override
    public double calculateTotalTreatmentsCost(InvoiceInformation invoiceInformation) {
        double totalCost = 0;
        for(Treatment treatment: invoiceInformation.getTreatments()){
            totalCost += treatment.getPrice();
        }
        return totalCost;
    }

    @Override
    public double calculateTotalAdditionalCostServicesCost(InvoiceInformation invoiceInformation) {
        double totalCost = 0;
        for(AdditionalCostService additionalCostService: invoiceInformation.getAdditionalCostServices()){
            totalCost += additionalCostService.getPrice();
        }
        return totalCost;
    }

    @Override
    public Address getAddressesByInvoiceInformation(InvoiceInformation invoiceInformation) throws Exception {
        return this.lcsRepository.getAddressesByInvoiceInformation(invoiceInformation);
    }

    @Override
    public InvoiceInformation getInvoiceInformationByAddress(Address address) throws Exception {
        return this.lcsRepository.getInvoiceInformationByAddress(address);
    }

    @Override
    public InvoiceInformation updateInvoiceInformation(InvoiceInformation invoiceInformation, Client client, Admin admin) throws Exception {
        return this.lcsRepository.updateInvoiceInformation(invoiceInformation,client,admin);
    }

    @Override
    public List<Treatment> removeTreatmentFromList(Treatment treatment, Client client) throws Exception {
        return this.lcsRepository.removeTreatmentFromList(treatment,client);
    }

    @Override
    public List<AdditionalCostService> removeAdditionalCostServiceFromList(AdditionalCostService additionalCostService, Client client) throws Exception {
        return this.lcsRepository.removeAdditionalCostServiceFromList(additionalCostService,client);
    }

    @Override
    public List<AdditionalCostService> saveAdditionalCostServiceForInvoiceInformation(AdditionalCostService additionalCostService, Client client) throws Exception {
        return this.lcsRepository.saveAdditionalCostServiceForInvoiceInformation(additionalCostService,client);
    }

    @Override
    public List<Treatment> saveTreatmentForInvoiceInformation(Treatment treatment, Client client) throws Exception {
        return this.lcsRepository.saveTreatmentForInvoiceInformation(treatment,client);
    }

    @Override
    public List<InvoiceInformation> saveInvoiceInformation(InvoiceInformation invoiceInformation, Client client, Address address, Admin admin) throws Exception {
        return this.lcsRepository.saveInvoiceInformation(invoiceInformation,client,address,admin);
    }

    @Override
    public List<InvoiceInformation> getInvoiceInformationByClientId(Client client) throws Exception {
        return this.lcsRepository.getInvoiceInformationByClientId(client);
    }

    @Override
    public List<InvoiceInformation> getAllInvoiceInformations() throws Exception {
        return this.lcsRepository.getAllInvoiceInformations();
    }

    @Override
    public List<AdditionalCostService> getAllAdditionalCostServices() throws Exception {
        return this.lcsRepository.getAllAdditionalCostServices();
    }

    @Override
    public List<AdditionalCostService> getAdditionalCostServicesByClientId(Client client) throws Exception {
        return this.lcsRepository.getAdditionalCostServicesByClientId(client);
    }

    @Override
    public AdditionalCostService updateAdditionalCostService(AdditionalCostService additionalCostService) throws Exception {
        return this.lcsRepository.updateAdditionalCostService(additionalCostService);
    }

    @Override
    public AdditionalCostService saveAdditionalCostService(AdditionalCostService additionalCostService) throws Exception {
        return this.lcsRepository.saveAdditionalCostService(additionalCostService);
    }

    @Override
    public AdditionalCostService getAdditionalCostServicesById(int id) throws Exception {
        return this.lcsRepository.getAdditionalCostServicesById(id);
    }

    @Override
    public Address updateAddress(Address address, Client client) throws Exception {
        return this.lcsRepository.updateAddress(address,client);
    }

    @Override
    public Address saveAddress(Address address, Client client) throws Exception {
        return this.lcsRepository.saveAddress(address,client);
    }

    @Override
    public List<Address> getAddressesByClientId(int id) throws Exception {
        return this.lcsRepository.getAddressesByClientId(id);
    }

    @Override
    public List<Address> getAddresses() throws Exception {
        return this.lcsRepository.getAddresses();
    }

    @Override
    public boolean doesAddressExists(Address address) throws Exception {
        return this.lcsRepository.doesAddressExists(address);
    }

    @Override
    public Zipcode saveZipcode(Zipcode zipcode, City city) throws Exception { return this.lcsRepository.saveZipcode(zipcode,city); }

    @Override
    public Zipcode getZipcodesById(int id) throws Exception { return this.lcsRepository.getZipcodesById(id); }

    @Override
    public List<Zipcode> getZipcodesByCity(String city) throws Exception { return this.lcsRepository.getZipcodesByCity(city); }

    @Override
    public List<Zipcode> getAllZipcodes() throws Exception { return this.lcsRepository.getAllZipcodes(); }

    @Override
    public Treatment saveTreatment(Treatment treatment) throws Exception { return this.lcsRepository.saveTreatment(treatment); }

    @Override
    public Treatment getTreatmentById(int id) throws Exception { return this.lcsRepository.getTreatmentById(id); }

    @Override
    public List<Treatment> getTreatmentsByClientId(Client client) throws Exception {
        return this.lcsRepository.getTreatmentsByClientId(client);
    }

    @Override
    public Treatment updateTreatment(Treatment treatment) throws Exception { return this.lcsRepository.updateTreatment(treatment); }

    @Override
    public List<Treatment> getAllTreatments() throws Exception { return this.lcsRepository.getAllTreatments(); }

    @Override
    public List<State> getAllStates() throws Exception { return this.lcsRepository.getAllStates(); }

    @Override
    public City saveCity(City city) throws Exception { return this.lcsRepository.saveCity(city); }

    @Override
    public List<City> getCities() throws Exception { return this.lcsRepository.getCities(); }

    @Override
    public City getCity(City city) throws Exception { return this.lcsRepository.getCity(city); }

    @Override
    public Admin saveAdmin(Admin admin) throws Exception { return this.lcsRepository.saveAdmin(admin); }

    @Override
    public Admin getAdminById(int id) throws Exception { return this.lcsRepository.getAdminById(id); }

    @Override
    public List<Admin> getAllAdmin() throws Exception { return this.lcsRepository.getAllAdmin(); }

    @Override
    public Client saveClient(Client client) throws Exception { return this.lcsRepository.saveClient(client); }

    @Override
    public Client updateClient(Client client) throws Exception { return this.lcsRepository.updateClient(client); }

    @Override
    public Client getClientById(int id) throws Exception { return this.lcsRepository.getClientById(id); }

    @Override
    public List<Client> getClients() throws Exception { return this.lcsRepository.getClients(); }

}
