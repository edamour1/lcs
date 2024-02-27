package com.warner.lcs;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.service.LcsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

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
    private Address address;//used for testing
    private List<Address> addresses;//used for testing
    private City city;//used for testing
    private List<City> cities;//used for testing
    private State state;//used for testing
    private List<State> states;//used for testing
    private Treatment treatment;//used for testing
    private List<Treatment> treatments;//used for testing
    private AdditionalCostService additionalCostService;////used for testing
    private List<AdditionalCostService> additionalCostServices;////used for testing
    private Zipcode zipcode;//used for testing
    private List<Zipcode> zipcodes;//used for testing
    private InvoiceInformation invoiceInformation;//used for testing
    private List<InvoiceInformation> invoiceInformations;//used for testing

    @BeforeEach//do execute this method before running any tests
    void setup() {
        client = new Client();//create dummy object
        client.setFirstName("Test");
        client.setMiddleName("Test");
        client.setLastName("Test");
        client.setEmail("test@gmail.com");
        client.setPhoneNumber("4514560991");
        admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("password");
        admin.setRole("master administrator");
        admin.setHint("p*##word");
        city = new City();
        city.setCity("Tybee Island");
        treatment = new Treatment();
        treatment.setTreatmentName("treatment name 10");
        treatment.setTreatmentDescription("treatment 10 description");
        treatment.setPrice(117.59);
        zipcode = new Zipcode();
        zipcode.setZipcode("30101");
        state = new State();
        state.setState("GA");
        state.setId(10);
        address = new Address();
        address.setIsActive(true);
        address.setStreet("1366 Fallsbrook Way NW");
        address.setCity(city);
        address.setState(state);
        address.setZipcode(zipcode);
        this.additionalCostService = new AdditionalCostService();
        additionalCostService.setTreatmentName("additonal treatment name 8");
        additionalCostService.setTreatmentDescription("additional treatment description 8");
        additionalCostService.setPrice(89.45);
        this.invoiceInformation = new InvoiceInformation();
    }

    @Test//means method is meant to be tested
    public void updateInvoiceInformationTest() throws Exception {
//        this.client.setId(2);
//        this.client.setFirstName("Manny");
//        this.client.setMiddleName("June");
//        this.client.setLastName("Damour");
//        this.client.setEmail("clownprince1961@gmail.com");
//        this.client.setPhoneNumber("4702259753");
//
//        this.zipcode.setId(77);
//        this.zipcode.setZipcode("30905");
//        this.city.setId(2);
//        this.city.setCity("Augusta");
//        address.setStreet("2926 Hampshire Dr");
//        address.setState(this.state);
//        address.setZipcode(this.zipcode);
        invoiceInformation.setId(2);
//        invoiceInformation.setClient(client);
//        invoiceInformation.setAddress(address);
        invoiceInformation.setNotes("testing the notes!");

        // Define the input date string
        String paymentDueDateString = "2024-04-03", startDateString = "2024-05-06", endDateString="2024-06-07";

        // Define the desired date pattern
        String pattern = "yyyy-MM-dd";
        try {
            // Create a SimpleDateFormat object
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);

            // Parse the input string to a Date object
            sdf.parse(paymentDueDateString);
            Date paymentDueDate  =  new Date(sdf.parse(paymentDueDateString).getTime());
            Date startDate  =  new Date(sdf.parse(startDateString).getTime());
            Date endDate  =  new Date(sdf.parse(endDateString).getTime());

            invoiceInformation.setPaymentDueDate((java.sql.Date) paymentDueDate);
            invoiceInformation.setStartDate((java.sql.Date) startDate);
            invoiceInformation.setEndDate((java.sql.Date) endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Treatment t1 = new Treatment(), t2 = new Treatment(), t3 = new Treatment(), t4 = new Treatment();
        t1.setId(4);
        t1.setRemoveFromList(true);
        t2.setId(6);
        t2.setRemoveFromList(false);
        List<Treatment> tl = new ArrayList<>();
        tl.add(t1);
        tl.add(t2);
        AdditionalCostService a1 = new AdditionalCostService(), a2 = new AdditionalCostService();
        a1.setId(7);
        a1.setRemoveFromList(false);
        a2.setId(3);
        a2.setRemoveFromList(true);
        List<AdditionalCostService> al = new ArrayList<>();
        al.add(a1);
        al.add(a2);
        invoiceInformation.setTreatments(tl);
        invoiceInformation.setAdditionalCostServices(al);

        InvoiceInformation updatedInvoiceInformation = this.lcsService.updateInvoiceInformation(this.invoiceInformation,this.admin);

        assertThat(updatedInvoiceInformation.getId()).isEqualTo(invoiceInformation.getId());
        assertThat(updatedInvoiceInformation.getPaymentDueDate()).isEqualTo(invoiceInformation.getPaymentDueDate());
        assertThat(updatedInvoiceInformation.getStartDate()).isEqualTo(invoiceInformation.getStartDate());
        assertThat(updatedInvoiceInformation.getEndDate()).isEqualTo(invoiceInformation.getEndDate());

        for(Treatment currentTreatment : this.invoiceInformation.getTreatments()) {
            if(currentTreatment.getRemoveFromList()) { assertThat(updatedInvoiceInformation.getTreatments().contains(currentTreatment)).isFalse();
            } else { assertThat(updatedInvoiceInformation.getTreatments().contains(currentTreatment)).isTrue(); }
        }

        for(AdditionalCostService currentAdditionalCostService : this.invoiceInformation.getAdditionalCostServices()) {
            if(currentAdditionalCostService.getRemoveFromList()) { assertThat(updatedInvoiceInformation.getTreatments().contains(currentAdditionalCostService)).isFalse();
            } else { assertThat(updatedInvoiceInformation.getTreatments().contains(currentAdditionalCostService)).isTrue(); }
        }
    }

    @Test//means method is meant to be tested
    public void doesAddressExistsTest() throws Exception {
      boolean  doesAddressExists = this.lcsService.doesAddressExists(address);
      assertThat(doesAddressExists).isTrue();
      address.setStreet("123 does not exist street");
      doesAddressExists = this.lcsService.doesAddressExists(address);
      assertThat(doesAddressExists).isFalse();
    }
    @Test//means method is meant to be tested
    public void removeTreatmentFromListTest() throws Exception {
        this.client.setId(1);
        treatment.setId(6);
        List<Treatment> retrievedAdditionalCostServices = this.lcsService.removeTreatmentFromList(treatment,client);
        int n = 4;
        assertTrue(retrievedAdditionalCostServices.size() < n, "Array length should be less than n: "+n);
    }

    @Test//means method is meant to be tested
    public void removeAdditionalCostServiceFromListTest() throws Exception {
        this.client.setId(1);
        additionalCostService.setId(4);
        List<AdditionalCostService> retrievedAdditionalCostServices = this.lcsService.removeAdditionalCostServiceFromList(additionalCostService,client);
        int n = 4;
        assertTrue(retrievedAdditionalCostServices.size() < n, "Array length should be less than n: "+n);
    }
    @Test//means method is meant to be tested
    public void saveInvoiceInformationTest() throws Exception {
        this.client.setId(4);

        this.zipcode.setId(93);
        this.city.setId(38);
        address.setStreet("209 Turtle Rock Pl, Acworth");
        address.setState(this.state);
        address.setZipcode(this.zipcode);

        invoiceInformation.setClient(client);
        invoiceInformation.setAddress(address);
        invoiceInformation.setNotes("testing");

        // Define the input date string
        String paymentDueDateString = "2024-06-01", startDateString = "2024-07-01", endDateString="2024-08-01";

        // Define the desired date pattern
        String pattern = "yyyy-MM-dd";
        try {
            // Create a SimpleDateFormat object
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);

            // Parse the input string to a Date object
            sdf.parse(paymentDueDateString);
            Date paymentDueDate  =  new Date(sdf.parse(paymentDueDateString).getTime());
            Date startDate  =  new Date(sdf.parse(startDateString).getTime());
            Date endDate  =  new Date(sdf.parse(endDateString).getTime());

            invoiceInformation.setPaymentDueDate((java.sql.Date) paymentDueDate);
            invoiceInformation.setStartDate((java.sql.Date) startDate);
            invoiceInformation.setEndDate((java.sql.Date) endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(int i = 1; i < 4; i++){
            Treatment t = new Treatment();
            t.setId(i);
         this.invoiceInformation.getTreatments().add(t);
        }//for(int i = 1; i < 4; i++)

        for(int j = 1; j < 3; j++){
            AdditionalCostService a = new AdditionalCostService();
            a.setId(j);
            this.invoiceInformation.getAdditionalCostServices().add(a);
        }//for(int j = 1; j < 3; j++)

        List<InvoiceInformation> invoiceInformations = this.lcsService.saveInvoiceInformation(this.invoiceInformation,this.admin);

        /*####################################################################
         *   Very important make sure to add 1 to x before running the tests!##
         *       x = n + 1                                                   ##
         *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
         *  if:                                                              ##
         *       int n = 26;                                                 ##
         *       before running tests:                                       ##
         *                               int n = 27;                         ##
         * ####################################################################*/
        int n = 4;//make sure to add 1 to x be for running tests!!!!
        assertThat(invoiceInformations.get(0).getId()).isGreaterThan(n);
    }

    @Test//means method is meant to be tested
    public void saveAdditionalCostServiceForInvoiceInformationTest() throws Exception {
        this.client.setId(1);
        additionalCostService.setId(4);

        additionalCostServices = this.lcsService.saveAdditionalCostServiceForInvoiceInformation(this.additionalCostService,this.client);

        /*####################################################################
         *   Very important make sure to add 1 to x before running the tests!##
         *       x = n + 1                                                   ##
         *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
         *  if:                                                              ##
         *       int n = 26;                                                 ##
         *       before running tests:                                       ##
         *                               int n = 27;                         ##
         * ####################################################################*/
        int n = 2;//make sure to add 1 to x be for running tests!!!!
        assertThat(additionalCostServices.size()).isGreaterThan(n);
    }

    @Test//means method is meant to be tested
    public void saveTreatmentForInvoiceInformationTest() throws Exception {
        this.client.setId(1);
        treatment.setId(6);

        this.treatments = this.lcsService.saveTreatmentForInvoiceInformation(this.treatment,this.client);

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
        assertThat(this.treatments.size()).isGreaterThan(n);
    }

    @Test//means method is meant to be tested
    public void getInvoiceInformationByClientIdTest() throws Exception {
        try {
            client.setId(3);
            invoiceInformations = this.lcsService.getInvoiceInformationByClientId(client);
            assertThat(invoiceInformations).isNotNull();
            assertTrue(invoiceInformations.size() > 0, "Array length should be more than 6");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getAllInvoiceInformationsTest() throws Exception {
        try {
            invoiceInformations = this.lcsService.getAllInvoiceInformations();
            assertThat(invoiceInformations).isNotNull();
            assertTrue(invoiceInformations.size() > 1, "Array length should be more than 6");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getAdditionalCostServicesByClientIdTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            this.client.setId(1);
            additionalCostServices = this.lcsService.getAdditionalCostServicesByClientId(client);
            assertThat(additionalCostServices).isNotNull();
            int n = 1;
            assertTrue(additionalCostServices.size() > n, "Array length should be more than n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getTreatmentsByClientIdTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            this.client.setId(2);
            treatments = this.lcsService.getTreatmentsByClientId(client);
            assertThat(treatments).isNotNull();
            assertTrue(treatments.size() > 0, "Array length should be more than 6");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getAllAdditionalCostServicesTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            additionalCostServices = this.lcsService.getAllAdditionalCostServices();
            assertThat(additionalCostServices).isNotNull();
            int n = 6;
            assertTrue(additionalCostServices.size() > n, "Array length should be more than n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void updateAdditionalCostServiceTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            this.additionalCostService.setId(1);
            this.additionalCostService.setTreatmentName("treatment name 1");
            this.additionalCostService.setTreatmentDescription("treatment 1 description");
            this.additionalCostService.setPrice(38.91);
            AdditionalCostService updatedAdditionalCostService = this.lcsService.updateAdditionalCostService(additionalCostService);
            assertThat(updatedAdditionalCostService).isNotNull();
            assertThat(additionalCostService.getId()).isEqualTo(updatedAdditionalCostService.getId());
            assertThat(additionalCostService.getTreatmentName()).isEqualTo(updatedAdditionalCostService.getTreatmentName());
            assertThat(additionalCostService.getTreatmentDescription()).isEqualTo(updatedAdditionalCostService.getTreatmentDescription());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void saveAdditionalCostServiceTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            try {
                try {

                    AdditionalCostService savedObj = this.lcsService.saveAdditionalCostService(this.additionalCostService);//save additionalCostService
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getAdditionalCostServicesByIdTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            additionalCostService = this.lcsService.getAdditionalCostServicesById(1);
            assertThat(additionalCostService).isNotNull();
            assertThat(additionalCostService.getId()).isEqualTo(1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getCityTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            city.setId(39);
            city.setCity("Cartersville");
            City retrievedObject = this.lcsService.getCity(city);
            assertThat(retrievedObject).isNotNull();
            assertThat(city.getId()).isEqualTo(retrievedObject.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void saveAddressTest() throws Exception {
        try {
            try {
                this.client.setId(3);
                this.address.getCity().setId(2);
                this.address.getZipcode().setId(79);
                Address savedObj = this.lcsService.saveAddress(this.address,this.client);//save treatment
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
    public void updateAddressTest() throws Exception {
        try {
            this.client.setId(3);
            this.city.setId(38);
            this.zipcode.setId(93);

            address.setId(2);
            address.setStreet("1366 Fallsbrook Way NW");
            address.setCity(this.city);
            address.setState(this.state);
            address.setZipcode(this.zipcode);

            Address updatedAddress = this.lcsService.updateAddress(this.address,this.client);
            assertThat(updatedAddress).isNotNull();
            assertThat(city).isEqualTo(updatedAddress.getCity());
            assertThat(state).isEqualTo(updatedAddress.getState());
            assertThat(zipcode).isEqualTo(updatedAddress.getZipcode());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getAddressesByClientIdTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            this.client.setId(1);
            addresses = this.lcsService.getAddressesByClientId(this.client.getId());
            assertThat(addresses).isNotNull();
            assertTrue(addresses.size() > 0, "Array length should be more than 49");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getAddressesTest() throws Exception {
        try {
            addresses = this.lcsService.getAddresses();
            assertThat(addresses).isNotNull();
            assertTrue(addresses.size() > 1, "Array length should be more than 1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void saveZipcodeTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            try {
                this.city.setId(38);
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
            zipcodes = this.lcsService.getZipcodesByCity("Acworth");
            assertThat(zipcodes).isNotNull();
            int n = 0;
            assertTrue(zipcodes.size() > n, "Array length should be more than n");
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

                Treatment savedObj = this.lcsService.saveTreatment(this.treatment);//save additionalCostService
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
            this.treatment.setId(19);
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
                int n = 53;//make sure to add 1 to x be for running tests!!!!
                assertThat(savedObj.getId()).isGreaterThan(n);

            } catch (Exception e) {
                throw new RuntimeException(e);
            } //catch
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test//means method is meant to be tested
    public void getCitiesTest() throws Exception {//becuase were talking to the database we must throw an exception
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

        //TODO create updateAdmin
    @Test//means method is meant to be tested
    public void updateClientTest() throws Exception {//becuase were talking to the database we must throw an exception
        try {
            this.client.setId(3);
            this.client.setFirstName("Emmanuel");
            this.client.setLastName("Damour");
            this.client.setEmail("clownprince1961@gmail.com");
            this.client.setPhoneNumber("4702256306");
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
            assertTrue(clients.size() > 2, "Array length should be more than 5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
