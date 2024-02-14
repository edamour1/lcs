package com.warner.lcs.app.repository;

import com.warner.lcs.app.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public interface LcsRepository {

    /**
     * Updates an address in the database
     *
     * This method uses jdbc template to update an Address object in the database.
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
     * This method uses the jdbc template to perisists (save) an Address object in the database.
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
     * This method uses the jsbc template to fetch a list of addresses information by id from the database.
     * @param id is going to be used to get the specific address.
     * @return List Address objects that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Address> getAddressesByClientId(int id) throws Exception;

    /**
     * Returns a list of addresses from the database.
     *
     * This method uses the jdbc template to fetch a lis of addresses information by id from the database.
     * @return List Address objects that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public List<Address> getAddresses() throws Exception;


    /**
     * Saves a zipcode to the database using the jdbc template.
     *
     * This method uses saves a Zipcode object to the database.
     * @param zipcode has the data that will be saved.
     * @param city has the city id needed to save the zipcode.
     * @return Zipcode object containing the information about the zipcode that way it's saved to the database.
     * @throws Exception If error occurs during db operation layer.
     */
    public Zipcode saveZipcode(Zipcode zipcode, City city) throws Exception;

    /**
     * Gets a Zipcode from the database based on id
     *
     * This method uses the jdbc template to fetch the zipcode data by id from the database.
     * @param id is going to be used to get the specific treatment.
     * @return Zipcode object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public Zipcode getZipcodesById(int id) throws Exception;

    /**
     * Saves a client to the data
     *
     * This method uses saves a City object to the database.
     * The id of the new city inserted will be returned in a City object.
     * @param city has the city's name.
     * @return City object containing the id.
     * @throws Exception If error occurs during db operation layer.
     */
    public List<Zipcode> getZipcodesByCity(String city) throws Exception;

    /**
     * Get a List of all zipcodes from the database
     *
     * This method uses jdbc template to retrive all zipcodes data and return a list of Zipcode objects.
     * @return gets a List of all Zipcodes objects.
     * @throws Exception If error something sql related goes wrong.
     */
    public List<Zipcode> getAllZipcodes() throws Exception;

    /**
     * Saves a treatment to the database
     *
     * This method uses saves a Treatment object to the database.
     * @param treatment has the city's name.
     * @return Treatment object containing the information about the treatment that way it's saved to the database.
     * @throws Exception If error occurs during db operation layer.
     */
    public Treatment saveTreatment(Treatment treatment) throws Exception;

    /**
     * Gets a treatment from the database based on id
     *
     * This method uses the jdbc template to fetch the treatment data by id from the database.
     * @param id is going to be used to get the specific treatment.
     * @return Treatment object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public Treatment getTreatmentById(int id) throws Exception;

    /**
     * Updates a treatment in the database
     *
     * This method uses the jdbc template to update the treatment data in the database.
     * @param treatment Has the treatment data.
     * @return Treatment object which will contain the updated information in it.
     * @throws Exception If error occurs in the repo layer.
     */
    public Treatment updateTreatment(Treatment treatment) throws Exception;

    /**
     * Get a List of all treatments from the database
     *
     * This method uses jdbc template to retrive all teatment's data and return a list of Treatment objects containing the data.
     * @return gets a List of all Treatmeant objects.
     * @throws Exception If error something sql related goes wrong.
     */
    public List<Treatment> getAllTreatments() throws Exception;

    /**
     * Get a List of all states from the database
     *
     * This method uses jdbc template to retrive all states data and return a list of State objects containing the data.
     * @return gets a List of all State objects.
     * @throws Exception If error something sql related goes wrong.
     */
    public List<State> getAllStates() throws Exception;

    /**
     * Saves a client to the data
     *
     * This method uses saves a City object to the database.
     * The id of the new city inserted will be returned in a City object.
     * @param city has the city's name.
     * @return City object containing the id.
     * @throws Exception If error occurs during db operation layer.
     */
    public City saveCity(City city) throws Exception;

    /**
     * Get a List of all cities from the database
     *
     * This method uses jdbc template to retrive all cities data and return a list of City objects containing the data.
     * @return gets a List of all City objects.
     * @throws Exception If error something sql related goes wrong.
     */
    public List<City> getCities() throws Exception;

    /**
     * Returns a desired city from the database
     *
     * This method uses the jdbc template to fetch the city data by name from the database.
     * @param city is going to be used to get the specific treatment.
     * @return City object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public City getCity(City city) throws Exception;

    /**
     * Saves a client to the data
     *
     * This method uses saves a Client object to the database.
     * The id of the new admin inserted will be returned in an Admin object.
     * @param admin Has the administration info registry data.
     * @return Admin object containing the id.
     * @throws Exception If error occurs during db operation layer.
     */
    public Admin saveAdmin(Admin admin) throws Exception ;

    /**
     * Gets a Admin from the database based on id
     *
     * This method uses the jdbc template to fetch the admin data by id from the database.
     * @param id is going to be used to get the specific treatment.
     * @return Admin object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public Admin getAdminById(int id) throws Exception;

    /**
     * Get a List of all administrators from the database
     *
     * This method uses jdbc template to retrive all administrators data and return a list of Admin objects containing the data.
     * @return gets a List of all Admin objects.
     * @throws Exception If error something sql related goes wrong.
     */
    public List<Admin> getAllAdmin() throws Exception;

    /**
     * Saves a client to the data
     *
     * This method uses saves a Client object to the database.
     *The id of the new client inserted will be returned in a Client object.
     * @param client Has the client data.
     * @return Client object containing the id.
     * @throws Exception If error occurs during db operation layer.
     */
    public Client saveClient(Client client) throws Exception;

    /**
     * Updates a client in the database
     *
     * This method uses the jdbc template to update the client data in the database.
     * @param client Has the client data.
     * @return Client object which will contain the updated information in it.
     * @throws Exception If error occurs in the repo layer.
     */
    public Client updateClient(Client client) throws Exception;

    /**
     * Gets a Client from the database based on id
     *
     * This method uses the jdbc template to fetch the client data by id from the database.
     * @param id is going to be used to get the specific treatment.
     * @return Client object that contains data that was fetched.
     * @throws Exception If error occurs in the repo layer.
     */
    public Client getClientById(int id) throws Exception;

    /**
     * Saves a client to the data
     *
     * This method uses the JDBC template to perisists (save) a client object to the database.
     *
     * @return All Client objects from the database.
     * @throws Exception If error occurs during query.
     */
    public List<Client> getClients() throws Exception;
}
