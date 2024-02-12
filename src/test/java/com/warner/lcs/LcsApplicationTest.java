package com.warner.lcs;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.service.LcsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

//don't load anything needed for the web becuase it's not needed
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LcsApplicationTest {

    @Autowired//get bean
    private LcsService lcsService;

    private Admin admin;//used for testing
    private List<Admin> adminList;//used for testing
    private Client client;//used for testing
    private List<Client> clients;//used for testing
    private City city;//used for testing
    private List<City> cities;//used for testing
    private List<State> states;//used for testing
    private Treatment treatment;//used for testing
    private List<Treatment> treatments;//used for testing
    private Zipcode zipcode;
    private List<Zipcode> zipcodes;

    @BeforeEach//do execute this method before running any tests
    void setup() {
        client = new Client();//create dummy object
        client.setFirstName("Test");
        client.setMiddleName("Test");
        client.setLastName("Test");
        client.setEmail("test@gmail.com");
        client.setPhoneNumber("451-456-0991");
        admin = new Admin();
        admin.setUsername("testAdmin");
        admin.setPassword("password");
        admin.setRole("master administrator");
        admin.setHint("p*##word");
        city = new City();
        city.setCity("Helen");
        treatment = new Treatment();
//        treatment.setId(2);
        treatment.setTreatmentName("treatment 8");
        treatment.setTreatmentDescription("treatment 8 description");
        treatment.setPrice(117.59);
        zipcode = new Zipcode();
        zipcode.setZipcode("30545");
    }

    @Test//means method is meant to be tested
    public void saveZipcodeTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            try {
                this.city.setId(53);
                Zipcode savedObj = this.lcsService.saveZipcode(this.zipcode,this.city);//save treatment
                assertThat(savedObj).isNotNull();//see if it's empty or not

                /*####################################################################
                 *   Very important make sure to add 1 to x before running the tests!##
                 *       x = n + 1                                                   ##
                 *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
                 *  if:                                                              ##
                 *       int n = 26;                                                 ##
                 *       before running tests:                                       ##
                 *                               int n = 27;                         ##
                 * ####################################################################*/
                int n = 60;//make sure to add 1 to x be for running tests!!!!
                assertThat(savedObj.getId()).isGreaterThan(n);

            } catch (Exception e) {
                throw new RuntimeException(e);
            } //catch
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getZipcodesByIdTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            zipcode = this.lcsService.getZipcodesById(13);
            assertThat(zipcode).isNotNull();
            assertThat(zipcode.getId()).isEqualTo(13);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void getZipcodesByCityTest() throws Exception {
        try {
            zipcodes = this.lcsService.getZipcodesByCity("Augusta");
            assertThat(zipcodes).isNotNull();
            assertTrue(zipcodes.size() > 10, "Array length should be more than 30");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getAllZipcodesTest() throws Exception {
        try {
            zipcodes = this.lcsService.getAllZipcodes();
            assertThat(zipcodes).isNotNull();
            assertTrue(zipcodes.size() > 30, "Array length should be more than 30");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void saveTreatment() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            try {

                Treatment savedObj = this.lcsService.saveTreatment(this.treatment);//save treatment
                assertThat(savedObj).isNotNull();//see if it's empty or not

                /*####################################################################
                *   Very important make sure to add 1 to x before running the tests!##
                *       x = n + 1                                                   ##
                *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
                *  if:                                                              ##
                *       int n = 26;                                                 ##
                *       before running tests:                                       ##
                *                               int n = 27;                         ##
                * ####################################################################*/
                int n = 7;//make sure to add 1 to x be for running tests!!!!
                assertThat(savedObj.getId()).isGreaterThan(n);

            } catch (Exception e) {
                throw new RuntimeException(e);
            } //catch
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void updateTreatmentTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            this.treatment.setId(8);
            Treatment updatedTreatment = this.lcsService.updateTreatment(treatment);
            assertThat(updatedTreatment).isNotNull();
            assertThat(treatment.getId()).isEqualTo(updatedTreatment.getId());
            assertThat(treatment.getTreatmentName()).isEqualTo(updatedTreatment.getTreatmentName());
            assertThat(treatment.getTreatmentDescription()).isEqualTo(updatedTreatment.getTreatmentDescription());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getTreatmentByIdTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            treatment = this.lcsService.getTreatmentById(1);
            assertThat(treatment).isNotNull();
            assertThat(treatment.getId()).isEqualTo(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getAllTreatmentsTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            treatments = this.lcsService.getAllTreatments();
            assertThat(treatments).isNotNull();
            assertTrue(treatments.size() > 6, "Array length should be more than 6");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getAllStatesTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            states = this.lcsService.getAllStates();
            assertThat(states).isNotNull();
            assertTrue(states.size() > 49, "Array length should be more than 49");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void saveCityTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            try {

                City savedObj = this.lcsService.saveCity(this.city);//save treatment
                assertThat(savedObj).isNotNull();//see if it's empty or not

                /*####################################################################
                 *   Very important make sure to add 1 to x before running the tests!##
                 *       x = n + 1                                                   ##
                 *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
                 *  if:                                                              ##
                 *       int n = 26;                                                 ##
                 *       before running tests:                                       ##
                 *                               int n = 27;                         ##
                 * ####################################################################*/
                int n = 54;//make sure to add 1 to x be for running tests!!!!
                assertThat(savedObj.getId()).isGreaterThan(n);

            } catch (Exception e) {
                throw new RuntimeException(e);
            } //catch
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getCities() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            cities = this.lcsService.getCities();
            assertThat(cities).isNotNull();
            assertTrue(cities.size() > 10, "Array length should be more than 10");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void saveAdminTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            try {

                Admin savedObj = this.lcsService.saveAdmin(this.admin);//save treatment
                assertThat(savedObj).isNotNull();//see if it's empty or not

                /*####################################################################
                 *   Very important make sure to add 1 to x before running the tests!##
                 *       x = n + 1                                                   ##
                 *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
                 *  if:                                                              ##
                 *       int n = 26;                                                 ##
                 *       before running tests:                                       ##
                 *                               int n = 27;                         ##
                 * ####################################################################*/
                int n = 1;//make sure to add 1 to x be for running tests!!!!
                assertThat(savedObj.getId()).isGreaterThan(n);

            } catch (Exception e) {
                throw new RuntimeException(e);
            } //catch
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getAdminById() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            admin = this.lcsService.getAdminById(2);
            assertThat(admin).isNotNull();
            assertThat(admin.getId()).isEqualTo(2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getAllAdminTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            adminList = this.lcsService.getAllAdmin();
            assertThat(adminList).isNotNull();
            assertTrue(adminList.size() > 1, "Array length should be more than 1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void saveClientTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            try {

                Client savedObj = this.lcsService.saveClient(this.client);//save treatment
                assertThat(savedObj).isNotNull();//see if it's empty or not

                /*####################################################################
                 *   Very important make sure to add 1 to x before running the tests!##
                 *       x = n + 1                                                   ##
                 *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
                 *  if:                                                              ##
                 *       int n = 26;                                                 ##
                 *       before running tests:                                       ##
                 *                               int n = 27;                         ##
                 * ####################################################################*/
                int n = 1;//make sure to add 1 to x be for running tests!!!!
                assertThat(savedObj.getId()).isGreaterThan(n);

            } catch (Exception e) {
                throw new RuntimeException(e);
            } //catch
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void updateClientTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            this.client.setId(1);
            Client updatedClient = this.lcsService.updateClient(client);
            assertThat(updatedClient).isNotNull();
            assertThat(client.getId()).isEqualTo(updatedClient.getId());
            assertThat(client.getFirstName()).isEqualTo(updatedClient.getFirstName());
            assertThat(client.getMiddleName()).isEqualTo(updatedClient.getMiddleName());
            assertThat(client.getLastName()).isEqualTo(updatedClient.getLastName());
            assertThat(client.getEmail()).isEqualTo(updatedClient.getEmail());
            assertThat(client.getPhoneNumber()).isEqualTo(updatedClient.getPhoneNumber());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getClientByIdTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            client = this.lcsService.getClientById(2);
            assertThat(client).isNotNull();
            assertThat(client.getId()).isEqualTo(2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getClientsTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            clients = this.lcsService.getClients();
            assertThat(clients).isNotNull();
            assertTrue(clients.size() > 5, "Array length should be more than 5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
