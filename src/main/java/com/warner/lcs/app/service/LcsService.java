package com.warner.lcs.app.service;

import com.warner.lcs.app.domain.*;

import java.util.List;

public interface LcsService {


    /**
     * Updates an address in the database
     *
     * This method uses repository layer to update an Address object in the database.
     *
     * @param client Has the client's personal information.
     * @param address Has the client's address information.
     * @return Address object containing the id of the updated object.
     * @throws Exception If error occurs in the repo layer.
     */
    public Address updateAddress(Address address, Client client) throws Exception;

    /**
     * Saves an address to the database
     *
     * This method uses this repository layer to perisists (save) an Address object in the database.
     *
     * @param client Has the client's personal information.
     * @param address Has the client's address information.
     * @return Address object containing the id of the inerted object.
     * @throws Exception If error occurs in the repo layer.
     */
    public Address saveAddress(Address address, Client client) throws Exception;

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
     * This method uses this repository layer to perisists (save) a Treatment object in the database.
     *
     * @param treatment Has the treatment data.
     * @return Treatment object containing the id.
     * @throws Exception If error occurs in the repo layer.
     */
    public Treatment saveTreatment(Treatment treatment) throws Exception;

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
     * Updates a treatment in the database
     *
     * This method uses the repository layer to update the treatment data in the database.
     * @param treatment Has the treatment data.
     * @return Treatment object.
     * @throws Exception If error occurs in the repo layer.
     */
    public Treatment updateTreatment(Treatment treatment) throws Exception;


    /**
     * Get a List of all treatments from the database
     *
     * This method uses this repository layer to get of the all treatments data from the database.
     * @return gets a List of all Treatment objects.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Treatment> getAllTreatments() throws Exception;

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
     * Saves an admin to the database
     *
     * This method uses this repository layer to perisists (save) an Admin object to the database.
     *
     * @param admin Has the admin data.
     * @return Admin object containing the id.
     * @throws Exception If error occurs in the repo layer.
     */
    public Admin saveAdmin(Admin admin) throws Exception ;

    /**
     * Returns a desired admin from the database based on provided id
     *
     * This method uses the repository layer to fetch the admin data by id from the database.
     * @param id is going to be used to get the specific treatment.
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
     * Saves a client to the database
     *
     * This method uses this repository layer to perisists (save) an client object to the database.
     *
     * @param client Has the client data.
     * @return Client object containing the id.
     * @throws Exception If error occurs in the repo layer.
     */
    public Client saveClient(Client client) throws Exception;

    /**
     * Updates a client in the database
     *
     * This method uses the repository layer to update the client data in the database.
     * @param client Has the client data.
     * @return Client object.
     * @throws Exception If error occurs in the repo layer.
     */
    public Client updateClient(Client client) throws Exception;


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
