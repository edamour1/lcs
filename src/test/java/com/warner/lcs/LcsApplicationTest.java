package com.warner.lcs;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.InvoiceNumberGenerator;
import com.warner.lcs.common.util.PdfGenerator;
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

    private InvoiceNumberGenerator invoiceNumberGenerator;

    @BeforeEach//do execute this method before running any tests
    void setup() {
        this.invoiceNumberGenerator = new InvoiceNumberGenerator();
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
    public void updateAdminTest() throws Exception {

        this.admin.setId(1);
        this.admin.setUsername("ocean");
        this.admin.setPassword("skiprocks88");
        this.admin.setRole("employee");
        this.admin.setHint("sediment");

        Admin retrievedObj = this.lcsService.updateAdmin(this.admin);

        assertThat(this.admin.getId()).isEqualTo(retrievedObj.getId());
        assertThat(this.admin.getUsername()).isEqualTo(retrievedObj.getUsername());
        assertThat(this.admin.getPassword()).isEqualTo(retrievedObj.getPassword());
        assertThat(this.admin.getHint()).isEqualTo(retrievedObj.getHint());
    }

    @Test//means method is meant to be tested
    public void adminLoginTest() throws Exception {
        this.admin.setId(2);
        Admin retrievedObj = this.lcsService.adminLogin(admin);
        assertThat(retrievedObj.getId()).isEqualTo(this.admin.getId());
    }

    @Test//means method is meant to be tested
    public void updateAdditionalCostServiceQtyTest() throws Exception{
        this.additionalCostService.setId(2);
        this.additionalCostService.setQty(80);
        this.client.setId(4);
        AdditionalCostService updatedObj = this.lcsService.updateAdditionalCostServiceQty(additionalCostService,client);

        assertThat(updatedObj.getQty()).isEqualTo(this.additionalCostService.getQty());
    }

    @Test//means method is meant to be tested
    public void getAdditionalCostServiceTest() throws Exception {
        this.additionalCostService.setId(1);
        this.client.setId(4);
        AdditionalCostService retrievedObj = this.lcsService.getAdditionalCostService(this.additionalCostService,this.client);
        assertThat(retrievedObj.getId()).isEqualTo(this.additionalCostService.getId());
    }

    @Test//means method is meant to be tested
    public void updateTreatmentQtyTest() throws Exception {
        this.treatment.setId(1);
        this.treatment.setQty(3);
        this.client.setId(4);
        Treatment updatedObj = this.lcsService.updateTreatmentQty(treatment,client);

        assertThat(updatedObj.getQty()).isEqualTo(this.treatment.getQty());
    }

    @Test//means method is meant to be tested
    public void getTreatmentTest() throws Exception {
        this.treatment.setId(1);
        this.client.setId(4);
        Treatment retrievedObj = this.lcsService.getTreatment(this.treatment,this.client);
        assertThat(retrievedObj.getId()).isEqualTo(this.treatment.getId());

    }

    @Test//means method is meant to be tested
    public void pdfGeneratorTest() throws Exception {
        InvoiceNumberGenerator invoiceNumberGenerator = new InvoiceNumberGenerator();
        System.out.println(invoiceNumberGenerator.generateInvoiceNo());
        PdfGenerator pdfGenerator = new PdfGenerator("title.pdf");
    }

    @Test//means method is meant to be tested
    public void calculateTotalCostTest() throws Exception {
        this.client.setId(4);
        this.invoiceInformations = this.lcsService.getInvoiceInformationByClientId(this.client);
        double total = this.lcsService.calculateTotalCost(this.invoiceInformations.get(0));
        assertThat(total).isEqualTo(438.91);
    }

    @Test//means method is meant to be tested
    public void calculateTotalTreatmentsCost() throws Exception {
        this.client.setId(4);
        this.invoiceInformations = this.lcsService.getInvoiceInformationByClientId(this.client);
        double total = this.lcsService.calculateTotalTreatmentsCost(this.invoiceInformations.get(0));
        assertThat(total).isEqualTo(295.00);
    }
    @Test//means method is meant to be tested
    public void calculateTotalAdditionalCostServicesCostTest() throws Exception  {
        this.client.setId(4);
        this.invoiceInformations = this.lcsService.getInvoiceInformationByClientId(this.client);
        double total = this.lcsService.calculateTotalAdditionalCostServicesCost(this.invoiceInformations.get(0));
        assertThat(total).isEqualTo(143.91);
    }

    @Test//means method is meant to be tested
    public void getAddressesByInvoiceInformationTest() throws Exception {
        this.invoiceInformation.setAddressId(7);
        Address retrievedObj = this.lcsService.getAddressesByInvoiceInformation(invoiceInformation);

        assertThat(retrievedObj.getId()).isEqualTo(invoiceInformation.getAddressId());
    }

    @Test//means method is meant to be tested
    public void getInvoiceInformationByAddressTest() throws Exception {
        invoiceInformation.setId(2);
        this.address.setId(1);
        InvoiceInformation retrievedInvoiceInformation = this.lcsService.getInvoiceInformationByAddress(address);
        assertThat(invoiceInformation.getId()).isEqualTo(retrievedInvoiceInformation.getId());
    }

    @Test//means method is meant to be tested
    public void updateInvoiceInformationTest() throws Exception {
        this.client.setId(4);
        invoiceInformation.setId(11);
        Treatment t1 = this.lcsService.getTreatmentById(4), t2 = this.lcsService.getTreatmentById(2), t3 =  this.lcsService.getTreatmentById(3);
        AdditionalCostService a1 = this.lcsService.getAdditionalCostServicesById(4), a2 = this.lcsService.getAdditionalCostServicesById(2), a3 = this.lcsService.getAdditionalCostServicesById(4);

        boolean trueOrFalse = false;

        t1.setQty(4);
        t2.setQty(1);
        t3.setQty(2);
        a1.setQty(4);
        a2.setQty(7);
        a3.setQty(9);
//        invoiceInformation.setNotes("test notes");
//        // Define the input date string
//        String paymentDueDateString = "2024-07-03", startDateString = "2024-08-02", endDateString="2024-10-01";
//        this.admin.setUsername("fwner726");

//        a1.setQty(2);
//        a2.setQty(4);
//        t1.setQty(2);
//        t2.setQty(4);
//        t3.setQty(1);
        invoiceInformation.setNotes("test notes!!!");

        // Define the input date string
        String paymentDueDateString = "2024-06-04", startDateString = "2024-07-05", endDateString="2024-09-06";
        this.admin.setUsername("lnell26");

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


        t1.setRemoveFromList(!trueOrFalse);
        t2.setRemoveFromList(trueOrFalse);
        t3.setRemoveFromList(!trueOrFalse);
        t1.setUpdateQty(trueOrFalse);
        t2.setUpdateQty(!trueOrFalse);
        t3.setUpdateQty(trueOrFalse);

        List<Treatment> tl = new ArrayList<>();
        tl.add(t1);
        tl.add(t2);
        tl.add(t3);


        a1.setRemoveFromList(!trueOrFalse);
        a2.setRemoveFromList(trueOrFalse);
        a3.setRemoveFromList(trueOrFalse);
        a1.setUpdateQty(trueOrFalse);
        a2.setUpdateQty(!trueOrFalse);
        a3.setUpdateQty(trueOrFalse);

        List<AdditionalCostService> al = new ArrayList<>();
        al.add(a1);
        al.add(a2);
        al.add(a3);
        invoiceInformation.setTreatments(tl);
        invoiceInformation.setAdditionalCostServices(al);


        InvoiceInformation updatedInvoiceInformation = this.lcsService.updateInvoiceInformation(this.invoiceInformation, this.client,this.admin);

        assertThat(updatedInvoiceInformation.getId()).isEqualTo(invoiceInformation.getId());
        assertThat(updatedInvoiceInformation.getPaymentDueDate()).isEqualTo(invoiceInformation.getPaymentDueDate());
        assertThat(updatedInvoiceInformation.getStartDate()).isEqualTo(invoiceInformation.getStartDate());
        assertThat(updatedInvoiceInformation.getEndDate()).isEqualTo(invoiceInformation.getEndDate());
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
        this.client.setId(2);
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
        address.setStreet("612 Matheny Cut, Martinez");
        address.setState(this.state);
        address.setZipcode(this.zipcode);
        address.setId(7);
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
            t.setQty(i+1);
         this.invoiceInformation.getTreatments().add(t);
        }//for(int i = 1; i < 4; i++)

        for(int j = 1; j < 3; j++){
            AdditionalCostService a = new AdditionalCostService();
            a.setId(j);
            a.setQty(j+1);
            this.invoiceInformation.getAdditionalCostServices().add(a);
        }//for(int j = 1; j < 3; j++)
        invoiceInformation.setNo(this.invoiceNumberGenerator.generateInvoiceNo());
        List<InvoiceInformation> invoiceInformations = this.lcsService.saveInvoiceInformation(this.invoiceInformation,this.client,this.address,this.admin);

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
        this.client.setId(2);
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
        int n = 0;//make sure to add 1 to x be for running tests!!!!
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
            client.setId(4);
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
            this.client.setId(4);
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
                this.client.setId(4);
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
