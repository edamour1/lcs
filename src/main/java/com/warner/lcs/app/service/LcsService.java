package com.warner.lcs.app.service;

import com.warner.lcs.app.domain.*;

import java.util.List;

public interface LcsService {


    /**
     * Updates business details.
     *
     * This method uses the repository layer to update the business's details in the database.
     *
     * @param business has dats needed for the update
     * @param admin is who performed the update.
     * @return Business obj containing business updated details.
     */
    public Business updateBusiness(Business business, Admin admin) throws Exception;

    /**
     * Gets business details.
     *
     * This method uses the repository layer to fetch a additionalCostService from the database for invoiceInformation.
     *
     * @return Business obj containing business details.
     */
    public Business getBusiness() throws Exception;

    /**
     * Gets specific additionalCostService for invoiceInformation.
     *
     * This method uses the repository layer to fetch a additionalCostService from the database for invoiceInformation.
     *
     * @param additionalCostService used to fetch additionalCostService details.
     * @param invoiceInformation used to fetch additionalCostService details.
     * @return retrievedAdditionalCostService has updated data.
     */
    public AdditionalCostService getAdditionalCostService(AdditionalCostService additionalCostService, InvoiceInformation invoiceInformation) throws Exception;

    /**
     * Updates qty of additional_service for invoiceInformation.
     *
     * This method uses the repository layer to update the amount of additional_services list for  invoiceInformation.
     *
     * @param additionalCostService used to update quantity.
     * @param invoiceInformation used to update quantity.
     * @return updatedAdditionalCostService has updated data.
     */
    public AdditionalCostService updateAdditionalCostServiceQty(AdditionalCostService additionalCostService, InvoiceInformation invoiceInformation) throws Exception;

    /**
     * Gets specific treatment for invoiceInformation.
     *
     * This method uses the repository layer to fetch a treatment from the database for invoiceInformation.
     *
     * @param treatment used to fetch treatment details.
     * @param invoiceInformation used to fetch treatment details.
     * @return retrievedTreatment has updated data.
     */
    public Treatment getTreatment(Treatment treatment, InvoiceInformation invoiceInformation) throws Exception;

    /**
     * Updates qty of treatment for invoiceInformation.
     *
     * This method uses the repository layer to update the amount of treatments list for  invoiceInformation.
     *
     * @param treatment used to update quantity.
     * @param information used to update quantity.
     * @return updatedTreatment has updated data.
     */
    public Treatment updateTreatmentQty(Treatment treatment, InvoiceInformation information) throws Exception;

    /**
     * Gets total price of treatments and additionalCostServices
     *
     * This method sums up the total price of the invoice's treatments and additionalCostServices.
     *
     * @param invoiceInformation has price details.
     * @return totalCost of treatment.
     */
    public double calculateTotalCost(InvoiceInformation invoiceInformation);

    /**
     * Gets total the price of treatment
     *
     * This method sums up the total price of the invoice's treatment.
     *
     * @param invoiceInformation has price details.
     * @return totalCost of treatment.
     */
    public double calculateTotalTreatmentsCost(InvoiceInformation invoiceInformation);

    /**
     * Gets total the price of additionalCostServices
     *
     * This method sums up the total price of the invoice's additionalCostServices.
     *
     * @param invoiceInformation has price details.
     * @return totalCost of additionalCostServices.
     */
    public double calculateTotalAdditionalCostServicesCost(InvoiceInformation invoiceInformation);


    /**
     * Gets invoiceInformation.
     *
     * This method uses the repository layer to retrieve invoiceInformation from the database.
     *
     * @param invoiceNo is the id for the target invoiceInformation.
     * @return invoiceInformation data.
     */
    public InvoiceInformation getInvoiceInformation(String invoiceNo) throws Exception;

    /**
     * Gets address based on the invoiceInformation database
     *
     * This method uses this repository layer to get a InvoiceInformation object based on invoiceInformation from the database.
     *
     * @param invoiceInformation used to fetch address.
     * @return address object containing data based on the invoiceInformation.
     * @throws Exception If error occurs in the repo layer.
     */
     public Address getAddressesByInvoiceInformation(InvoiceInformation invoiceInformation) throws Exception;

    /**
     * Gets invoiceInformation based on the address database
     *
     * This method uses this repository layer to get a InvoiceInformation object based on address from the database.
     *
     * @param address used to fetch invoiceInformation.
     * @return invoiceInformation object containing data based on the address.
     * @throws Exception If error occurs in the repo layer.
     */
    public InvoiceInformation getInvoiceInformationByAddress(Address address) throws Exception;

    /**
     * Updates an invoiceInformation to the database
     *
     * This method uses this repository layer to update an InvoiceInformation object in the database.
     *
     * @param invoiceInformation has the data that needs an update.
     * @param client will be used to remove a treatment from  list.
     * @param admin used to keep track of who updated the invoice.
     * @return InvoiceInformation object containing the id.
     * @throws Exception If error occurs in the repo layer.
     */
    public InvoiceInformation updateInvoiceInformation(InvoiceInformation invoiceInformation, Client client, Admin admin) throws Exception;

    /**
     * Deletes treatment from invoice in database
     *
     * This method uses the repository layer to delete treatment from InvoiceInformation's additionalCostServices list.
     *
     * @param treatment will be used to remove a treatment from  list.
     * @param invoiceInformation will be used to remove a treatment from  list.
     * @return Treatment objects remained in invoice list.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Treatment> removeTreatmentFromList(Treatment treatment,  InvoiceInformation invoiceInformation) throws Exception;

    /**
     * Deletes additionalCostService from invoice in database
     *
     * This method uses the repository layer to delete additionalCostService from InvoiceInformation's additionalCostServices list.
     *
     * @param additionalCostService will be used to remove an additionalCostService from  list.
     * @param invoiceInformation will be used to remove an additionalCostService from  list.
     * @return AdditionalCostService objects remained in invoice list.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<AdditionalCostService> removeAdditionalCostServiceFromList(AdditionalCostService additionalCostService, InvoiceInformation invoiceInformation) throws Exception;

    /**
     * Saves a additionalCostService to the database
     *
     * This method uses the repsitory layer to add a additionalCostService to  database to InvoiceInformation's additionalCostServices list.
     *
     * @param additionalCostService will be added to invoice additionalCostServices list.
     * @param client used to link additionalCostService to invoice.
     * @param invoiceInformation used to link additionalCostService to invoice.
     * @return List AdditionalCostService objects that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<AdditionalCostService> saveAdditionalCostServiceForInvoiceInformation(AdditionalCostService additionalCostService, Client client, InvoiceInformation invoiceInformation) throws Exception;

    /**
     * Saves a treatment to the database
     *
     * This method uses the repsitory layer to add a treatment to  database for InvoiceInformation's treatments list.
     *
     * @param treatment will be added to invoice treatments list.
     * @param client used to link treatment to invoice.
     * @param invoiceInformation used to link additionalCostService to invoice.
     * @return List Treatment objects that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Treatment> saveTreatmentForInvoiceInformation(Treatment treatment, Client client, InvoiceInformation invoiceInformation) throws Exception;

    /**
     * Saves an invoiceInformation to the database
     *
     * This method uses this repository layer to perisists (save) an InvoiceInformation object in the database.
     *
     * @param invoiceInformation Has the invoiceInformation data.
     * @param client is who the invoiceInformation data is for.
     * @param address of the client.
     * @param admin used to keep track of who saved the invoice.
     * @return InvoiceInformation object containing the id.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<InvoiceInformation> saveInvoiceInformation(InvoiceInformation invoiceInformation, Client client, Address address, Admin admin) throws Exception;

    /**
     * Returns a list of invoiceInformations from the database based on the client.
     *
     * This method uses the repository layer to fetch a list of invoiceInformations based on client from the database.
     * @param client is going to be used to fetch a filtered invoiceInformation list.
     * @return List InvoiceInformation objects that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<InvoiceInformation> getInvoiceInformationByClientId(Client client) throws Exception;

    /**
     * Get a List of all invoiceInformations from the database
     *
     * This method uses this repository layer to get of the all invoiceInformations data from the database.
     * @return gets a List of all InvoiceInformation objects.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<InvoiceInformation> getAllInvoiceInformations() throws Exception;

    /**
     * Get a List of all additionalCostServices from the database
     *
     * This method uses the repository layer to get of the all additionalCostServices data from the database.
     * @return gets a List of all AdditionalCostService objects.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<AdditionalCostService> getAllAdditionalCostServices() throws Exception;

    /**
     * Returns a list of additionalCostServices from the database based on the client.
     *
     * This method uses the repository layer to fetch a list of additionalCostServices based on client from the database.
     * @param invoiceInformation is going to be used to fetch a filtered additionalCostService list.
     * @return List AdditionalCostService objects that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<AdditionalCostService> getAdditionalCostServicesByClientId(InvoiceInformation invoiceInformation) throws  Exception;

    /**
     * Updates a additionalCostService in the database
     *
     * This method uses the repository layer to update the additionalCostService data in the database.
     * @param additionalCostService Has the additionalCostService data.
     * @param admin has data of whose performing the action.
     * @return AdditionalCostService object.
     * @throws Exception If error occurs in the repo layer.
     */
    public AdditionalCostService updateAdditionalCostService(AdditionalCostService additionalCostService, Admin admin) throws Exception;

    /**
     * Saves an additionalCostService to the database
     *
     * This method uses this repository layer to perisists (save) an AdditionalCostService object in the database.
     *
     * @param additionalCostService Has the additionalCostService data.
     * @param admin has data of whose performing the action.
     * @return AdditionalCostService object containing the id.
     * @throws Exception If error occurs in the repo layer.
     */

    public AdditionalCostService saveAdditionalCostService(AdditionalCostService additionalCostService, Admin admin) throws Exception;

    /**
     * Returns a desired additionalCostService from the database based on provided id
     *
     * This method uses the repository layer to fetch the additionalCostService data by id from the database.
     * @param id is going to be used to get the specific additionalCostService.
     * @return AdditionalCostService object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public AdditionalCostService getAdditionalCostServicesById(int id) throws Exception;

    /**
     * Updates an address in the database
     *
     * This method uses repository layer to update an Address object in the database.
     *
     * @param client Has the client's personal information.
     * @param address Has the client's address information.
     * @param admin has the admin's data.
     * @return Address object containing the id of the updated object.
     * @throws Exception If error occurs in the repo layer.
     */
    public Address updateAddress(Address address, Client client,Admin admin) throws Exception;

    /**
     * Saves an address to the database
     *
     * This method uses this repository layer to perisists (save) an Address object in the database.
     *
     * @param client Has the client's personal information.
     * @param address Has the client's address information.
     * @param admin has admin data.
     * @return Address object containing the id of the inerted object.
     * @throws Exception If error occurs in the repo layer.
     */
    public Address saveAddress(Address address, Client client,Admin admin) throws Exception;

    /**
     * Returns a desired addresses from the database based on provided id
     *
     * This method uses the repository layer to fetch a lis of addresses information by id from the database.
     * @param id is going to be used to get the specific address.
     * @return List Address objects that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Address> getAddressesByClientId(int id) throws Exception;

    /**
     * Returns a list of addresses from the database.
     *
     * This method uses the repository layer to fetch a lis of addresses information by id from the database.
     * @return List Address objects that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Address> getAddresses() throws Exception;

    /**
     * Checks if address exists within database.
     *
     * This method uses the repository layer to verify if address exists within the database.
     *
     * @param address has the client's address data.
     * @return boolean doesAddressExists variable should be true or false.
     * @throws Exception If error occurs in the repo layer.
     */
    public boolean doesAddressExists(Address address) throws Exception;

    /**
     * Deletes address from database.
     *
     * This method uses the repository layer to deactivate an address within database.
     *
     * @param address has the client's address data.
     * @param admin is the user doing the deleting.
     * @return Address object containing the updated information.
     * @throws Exception If error occurs in the repo layer.
     */
    public Address deleteAddress(Address address, Admin admin) throws Exception;


    /**
     * Gets  address by id from database.
     *
     * This method uses the repository layer to get the address by id from the database.
     *
     * @param address has the client's address data.
     * @return address data.
     * @throws Exception If error occurs in the repo layer.
     */
    public Address getAddressById(Address address) throws Exception;



    /**
     * Saves a zipcode to the database
     *
     * This method uses this repository layer to perisists (save) a Zipcode object in the database.
     *
     * @param zipcode Has the zipcode data.
     * @param city has the city id needed to save the zipcode.
     * @return Zipcode object containing the id.
     * @throws Exception If error occurs in the repo layer.
     */
    public Zipcode saveZipcode(Zipcode zipcode, City city) throws Exception;

    /**
     * Gets a Zipcode from the database based on zipcode value
     *
     * This method uses the jdbc template to fetch the zipcode data by id from the database.
     * @param zipcode is going to be used to get the specific zipcode.
     * @return Zipcode object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public Zipcode getZipcode(String zipcode) throws Exception;

    /**
     * Returns a desired zipcode from the database based on provided id
     *
     * This method uses the repository layer to fetch the zipcode data by id from the database.
     * @param id is going to be used to get the specific zipcode.
     * @return Zipcode object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public Zipcode getZipcodesById(int id) throws Exception;

    /**
     * Returns a list of zipcodes from the database based on provided id
     *
     * This method uses the repository layer to fetch the treatment data by id from the database.
     * @param city is going to be used to filter zipcodes.
     * @return List of Zipcode objects that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Zipcode> getZipcodesByCity(String city) throws Exception;

    /**
     * Get a List of all zipcodes from the database
     *
     * This method uses this repository layer to get of the all zipcodes data from the database.
     * @return gets a List of all zipcodes objects.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Zipcode> getAllZipcodes() throws Exception;

    /**
     * Saves a treatment to the database
     *
     * This method uses the repository saves a Treatment object to the database.
     * @param treatment has the city's name.
     * @param admin has data of whose performing the action.
     * @return Treatment object containing the information about the treatment that way it's saved to the database.
     * @throws Exception If error occurs during db operation layer.
     */
    public Treatment saveTreatment(Treatment treatment,Admin admin) throws Exception;

    /**
     * Returns a desired treatment from the database based on provided id
     *
     * This method uses the repository layer to fetch the treatment data by id from the database.
     * @param id is going to be used to get the specific treatment.
     * @return Treatment object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public Treatment getTreatmentById(int id) throws Exception;

    /**
     * Returns a list of treatments from the database based on the client.
     *
     * This method uses the repository layer to fetch a list of treatments based on client from the database.
     * @param invoiceInformation is going to be used to fetch a filtered treatment list.
     * @return List Treatment objects that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Treatment> getTreatmentsByClientId(InvoiceInformation invoiceInformation) throws  Exception;

    /**
     * Updates a treatment in the database
     *
     * This method uses the repository layer to update the treatment data in the database.
     * @param treatment Has the treatment data.
     * @param admin has data of whose performing the action.
     * @return Treatment object.
     * @throws Exception If error occurs in the repo layer.
     */
    public Treatment updateTreatment(Treatment treatment, Admin admin) throws Exception;


    /**
     * Get a List of all treatments from the database
     *
     * This method uses this repository layer to get of the all treatments data from the database.
     * @return gets a List of all Treatment objects.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Treatment> getAllTreatments() throws Exception;

    /**
     * Returns a desired state from the database
     *
     * This method uses the repository layer to fetch the state data by name from the database.
     * @param state is going to be used to get the specific State.
     * @return State object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public State getState(String state) throws Exception;

    /**
     * Get a List of all states from the database
     *
     * This method uses this repository layer to get of the all states data from the database.
     * @return gets a List of all State objects.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<State> getAllStates() throws Exception;

    /**
     * Saves a city to the database
     *
     * This method uses this repository layer to perisists (save) a City object in the database.
     *
     * @param city Has the city data.
     * @return City object containing the id.
     * @throws Exception If error occurs in the repo layer.
     */
    public City saveCity(City city) throws Exception;

    /**
     * Get a List of all cities from the database
     *
     * This method uses this repository layer to get of the all cities data from the database.
     * @return gets a List of all City objects.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<City> getCities() throws Exception;

    /**
     * Returns a desired city from the database
     *
     * This method uses the repository layer to fetch the city data by name from the database.
     * @param city is going to be used to get the specific treatment.
     * @return City object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public City getCity(City city) throws Exception;

    /**
     * Returns an admin object from the database
     *
     * This method uses the repository layer to fetch the admin data based on login crdentials from the database.
     * @param username used to login admin.
     * @param password used to login admin.
     * @return Admin object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public Admin adminLogin(String username, String password) throws Exception;

    /**
     * Saves an admin to the database
     *
     * This method uses this repository layer to perisists (save) an Admin object to the database.
     *
     * @param admin Has the admin data.
     * @return Admin object containing the id.
     * @throws Exception If error occurs in the repo layer.
     */
    public Admin saveAdmin(Admin admin) throws Exception;

    /**
     * Returns a desired admin from the database based on provided id
     *
     * This method uses the repository layer to fetch the admin data by id from the database.
     * @param id is going to be used to get the specific admin.
     * @return Admin object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public Admin getAdminById(int id) throws Exception;

    /**
     * Get a List of all administrators from the database
     *
     * This method uses this repository layer to get all admin data to from the database.
     * @return gets a List of all Admin objects.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Admin> getAllAdmin() throws Exception;

    /**
     * Returns a desired admin from the database based on provided username & password
     *
     * This method uses the repository layer to fetch the admin data by id from the database.
     * @param admin is going to be used to get the specific admin.
     * @return Admin object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public Admin adminLogin(Admin admin) throws Exception;

    /**
     * Updates an admin from the database
     *
     * This method uses the repository layer to update the given admin data.
     * @param admin is going to be used to update a specific admin.
     * @return Admin object that contains updated data.
     * @throws Exception If error occurs in the repo layer.
     */
    public Admin updateAdmin(Admin admin) throws Exception;


    /**
     * Saves a client to the database
     *
     * This method uses this repository layer to perisists (save) an client object to the database.
     *
     * @param client Has the client data.
     * @param admin has admin data.
     * @return Client object containing the id.
     * @throws Exception If error occurs in the repo layer.
     */
    public Client saveClient(Client client,Admin admin) throws Exception;

    /**
     * Updates a client in the database
     *
     * This method uses the repository layer to update the client data in the database.
     * @param client Has the client data.
     * @param admin has admin data.
     * @return Client object.
     * @throws Exception If error occurs in the repo layer.
     */
    public Client updateClient(Client client, Admin admin) throws Exception;


    /**
     * Deletes a client
     *
     * This method uses the repository layer delete a client.
     * @param client Has the client data.
     * @param admin has admin data.
     * @return Client object.
     * @throws Exception If error occurs in the repo layer.
     */
    public Client deleteClient(Client client, Admin admin) throws  Exception;


    /**
     * Returns a desired client from the database based on provided id
     *
     * This method uses the repository layer to fetch the client data by id from the database.
     * @param id is going to be used to get the specific treatment.
     * @return Client object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public Client getClientById(int id) throws Exception;

    /**
     * Gets all clients from the database
     *
     * This method uses this repository layer to get all clients data from the database.
     *
     * @return All Client objects from th database.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Client> getClients() throws Exception;

}
