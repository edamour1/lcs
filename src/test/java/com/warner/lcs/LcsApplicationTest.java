package com.warner.lcs;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import static com.warner.lcs.common.util.SQL.get;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;

//don't load anything needed for the web becuase it's not needed
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class LcsApplicationTest {

    @Autowired//get bean
    private LcsService lcsService;

    private Admin admin;//used for testing
    private Business business;//used for testing
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
    private AdditionalCostService additionalCostService;//used for testing
    private List<AdditionalCostService> additionalCostServices;//used for testing
    private Zipcode zipcode;//used for testing
    private List<Zipcode> zipcodes;//used for testing
    private InvoiceInformation invoiceInformation;//used for testing
    private List<InvoiceInformation> invoiceInformations;//used for testing

    private InvoiceNumberGenerator invoiceNumberGenerator;//used for testing

    @BeforeEach//do execute this method before running any tests
    void setup() {
        this.invoiceNumberGenerator = new InvoiceNumberGenerator();
        client = new Client();//create dummy object
        client.setFirstName("first name 1");
        client.setMiddleName("middle name 1");
        client.setLastName("last name 1");
        client.setEmail("test@gmail.com");
        client.setPhoneNumber("4514560991");
        admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("password");
        admin.setRole("master administrator");
        admin.setHint("p*##word");
        city = new City();
        city.setCity("Atlanta");
        treatment = new Treatment();
        treatment.setTreatmentName("treatment name 10");
        treatment.setTreatmentDescription("treatment 10 description");
        treatment.setPrice(117.59);
        zipcode = new Zipcode();
        zipcode.setId(58);
        zipcode.setZipcode("30303");
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
        business = new Business();
        this.business.setName("Warner lawncare service");
        this.business.setEmail("edamourjr@gmail.com");
        this.business.setPhoneNo("678-410-9876");
        this.business.setFaxPhoneNo("770-223-0988");
    }


//    @Test//means method is meant to be tested
//    public void getZipcodeTest() throws Exception {
//        this.zipcode.setId(1);
//        this.zipcode.setZipcode("30301");
//        Zipcode obj = this.lcsService.getZipcode(this.zipcode.getZipcode());
//        assertThat(obj.getId()).isEqualTo(this.zipcode.getId());
//        assertThat(obj.getZipcode()).isEqualTo(this.zipcode.getZipcode());
//    }

//    @Test//means method is meant to be tested
//    public void getStateTest() throws Exception {
//            this.state.setId(38);
//            this.state.setState("PA");
//            State obj = this.lcsService.getState(this.state.getState());
//            assertThat(obj.getId()).isEqualTo(state.getId());
//            assertThat(obj.getState()).isEqualTo(state.getState());
//        }

//    @Test//means method is meant to be tested
//    public void emailUtilTest() throws Exception {
////       String emailSubject, String emailBody, String[] emailReceipients, String pdfFilePath
//        this.business = lcsService.getBusiness();
//        this.client = this.lcsService.getClientById(1);
//        String emailSubject = "Invoice", emailString = SQL.get("lcsSql","getEmailText"),pdfFilePath = "C:\\Users\\User\\Documents\\lcs\\lcs\\invoice.pdf",companyAddress;
//        String[] emailReceipients = {client.getEmail()};
//        companyAddress = "\n\t   "+business.getAddress().getStreet()+",\n\t    "+business.getAddress().getCity().getCity()+" "+business.getAddress().getState().getState()+" "+business.getAddress().getZipcode().getZipcode();
//        String emailBody =  String.format(emailString, this.client.getFirstName(), business.getName(), business.getEmail(), business.getPhoneNo(), business.getFaxPhoneNo(),companyAddress);
//        System.out.println(emailBody);
//        Email email = new Email(emailSubject,emailBody,emailReceipients,pdfFilePath);
//        email.sendEmail();
//    }
//    @Test//means method is meant to be tested
//    public void getInvoiceInformationTest() throws Exception {
//        String invoiceNo = "JB788715";
//        InvoiceInformation retrivedObj = this.lcsService.getInvoiceInformation(invoiceNo);
//    }
//
//    @Test//means method is meant to be tested
//    public void updateBusinessTest() throws Exception {
//        this.business.setId(3);
//        this.business.setName("Warner Lawncare Service inc.");
//        this.business.setEmail("edamourjr@gmail.comm");
//        this.business.setFaxPhoneNo("344-444-4444");
//        this.business.setPhoneNo("470-225-9753");
//
//        Business updatedObj = this.lcsService.updateBusiness(this.business,this.admin);
//        assertThat(updatedObj.getId()).isEqualTo(3);
//    }
//
//    @Test//means method is meant to be tested
//    public void getBusinessTest() throws Exception {
//        this.business = this.lcsService.getBusiness();
//        assertThat(business.getId()).isEqualTo(3);
//        assertThat(business.getAddress().getId()).isEqualTo(3);
//    }
//
//    @Test//means method is meant to be tested
//    public void getAddressByIdTest() throws Exception {
//        this.address.setId(1);
//        this.address.getState().setId(10);
//        this.address.getCity().setId(1);
//        this.address.getZipcode().setId(3);
//        Address retrievedObj = this.lcsService.getAddressById(this.address);
//        assertThat(this.address.getId()).isEqualTo(retrievedObj.getId());
//        assertThat(this.address.getStreet()).isEqualTo(retrievedObj.getStreet());
//        assertThat(this.address.getCity().getCity()).isEqualTo(retrievedObj.getCity().getCity());
//        assertThat(this.address.getState().getState()).isEqualTo(retrievedObj.getState().getState());
//        assertThat(this.address.getZipcode().getZipcode()).isEqualTo(retrievedObj.getZipcode().getZipcode());
//    }
//
//    @Test//means method is meant to be tested
//    public void updateAdminTest() throws Exception {
//
//        this.admin.setId(1);
//        this.admin.setUsername("ocean");
//        this.admin.setPassword("skiprocks88");
//        this.admin.setRole("employee");
//        this.admin.setHint("sediment");
//
//        Admin retrievedObj = this.lcsService.updateAdmin(this.admin);
//
//        assertThat(this.admin.getId()).isEqualTo(retrievedObj.getId());
//        assertThat(this.admin.getUsername()).isEqualTo(retrievedObj.getUsername());
//        assertThat(this.admin.getPassword()).isEqualTo(retrievedObj.getPassword());
//        assertThat(this.admin.getHint()).isEqualTo(retrievedObj.getHint());
//    }
//
//    @Test//means method is meant to be tested
//    public void adminLoginTest() throws Exception {
//        this.admin.setId(2);
//        Admin retrievedObj = this.lcsService.adminLogin("admin","password");
//        assertThat(retrievedObj.getId()).isEqualTo(this.admin.getId());
//    }
//
//    @Test//means method is meant to be tested
//    public void updateAdditionalCostServiceQtyTest() throws Exception{
//        this.additionalCostService.setId(3);
//        this.additionalCostService.setQty(80);
//        this.additionalCostService.setUnit("Kilolitre");
//        invoiceInformation.setNo("JB788715");
//        this.client.setId(4);
//        AdditionalCostService updatedObj = this.lcsService.updateAdditionalCostServiceQty(additionalCostService,invoiceInformation);
//
//        assertThat(updatedObj.getQty()).isEqualTo(this.additionalCostService.getQty());
//    }
//
//    @Test//means method is meant to be tested
//    public void getAdditionalCostServiceTest() throws Exception {
//        this.additionalCostService.setId(4);
//        this.client.setId(4);
//        invoiceInformation.setNo("TE287026");
//        AdditionalCostService retrievedObj = this.lcsService.getAdditionalCostService(this.additionalCostService,this.invoiceInformation);
//        assertThat(retrievedObj.getId()).isEqualTo(this.additionalCostService.getId());
//    }
//
//    @Test//means method is meant to be tested
//    public void updateTreatmentQtyTest() throws Exception {
//        this.treatment.setId(2);
//        this.treatment.setQty(0.75);
//        this.treatment.setUnit("Milligram");
//        this.client.setId(4);
//        this.invoiceInformation.setNo("TE287026");
//
//        Treatment updatedObj = this.lcsService.updateTreatmentQty(treatment,invoiceInformation);
//
//        assertThat(updatedObj.getQty()).isEqualTo(this.treatment.getQty());
//    }
//
//    @Test//means method is meant to be tested
//    public void getTreatmentTest() throws Exception {
//        this.treatment.setId(1);
//        this.client.setId(4);
//        Treatment retrievedObj = this.lcsService.getTreatment(this.treatment,this.invoiceInformation);
//        assertThat(retrievedObj.getId()).isEqualTo(this.treatment.getId());
//
//    }
//
//    @Test//means method is meant to be tested
//    public void pdfGeneratorTest() throws Exception {
//        this.client = this.lcsService.getClientById(1);
//        this.invoiceInformation = this.lcsService.getInvoiceInformation("JB788715");
//        this.address.setId(this.invoiceInformation.getAddressId());
//        Address billingAddress = this.lcsService.getAddressById(this.address);
//        this.business = this.lcsService.getBusiness();
//        System.out.println(invoiceNumberGenerator.generateInvoiceNo());
//        PdfGenerator pdfGenerator = new PdfGenerator("invoice.pdf", this.client, this.client, billingAddress, billingAddress, this.invoiceInformation, this.business);
//    }
//
//    @Test//means method is meant to be tested
//    public void calculateTotalCostTest() throws Exception {
//        this.client.setId(4);
//        this.invoiceInformations = this.lcsService.getInvoiceInformationByClientId(this.client);
//        double total = this.lcsService.calculateTotalCost(this.invoiceInformations.get(0));
//        assertThat(total).isEqualTo(438.91);
//    }
//
//    @Test//means method is meant to be tested
//    public void calculateTotalTreatmentsCost() throws Exception {
//        this.client.setId(4);
//        this.invoiceInformations = this.lcsService.getInvoiceInformationByClientId(this.client);
//        double total = this.lcsService.calculateTotalTreatmentsCost(this.invoiceInformations.get(0));
//        assertThat(total).isEqualTo(295.00);
//    }
//    @Test//means method is meant to be tested
//    public void calculateTotalAdditionalCostServicesCostTest() throws Exception  {
//        this.client.setId(4);
//        this.invoiceInformations = this.lcsService.getInvoiceInformationByClientId(this.client);
//        double total = this.lcsService.calculateTotalAdditionalCostServicesCost(this.invoiceInformations.get(0));
//        assertThat(total).isEqualTo(143.91);
//    }
//
//    @Test//means method is meant to be tested
//    public void getAddressesByInvoiceInformationTest() throws Exception {
//        this.invoiceInformation.setAddressId(1);
//        this.invoiceInformation.setNo("JB788715");
//        Address retrievedObj = this.lcsService.getAddressesByInvoiceInformation(invoiceInformation);
//
//        assertThat(retrievedObj.getId()).isEqualTo(this.invoiceInformation.getAddressId());
//    }
//
//    @Test//means method is meant to be tested
//    public void getInvoiceInformationByAddressTest() throws Exception {
//
//        this.address.setId(1);
//        this.invoiceInformation.setNo("JB788715");
//        InvoiceInformation retrievedInvoiceInformation = this.lcsService.getInvoiceInformationByAddress(address);
//        assertThat(invoiceInformation.getNo()).isEqualTo(retrievedInvoiceInformation.getNo());
//    }
//
//    @Test//means method is meant to be tested
//    public void updateInvoiceInformationTest() throws Exception {
//        this.client.setId(1);
//        this.invoiceInformation.setNo("JB788715");
//        Treatment t2 = this.lcsService.getTreatmentById(2), t4 = this.lcsService.getTreatmentById(4), t3 =  this.lcsService.getTreatmentById(3);
//        AdditionalCostService a3 = this.lcsService.getAdditionalCostServicesById(3), a2 = this.lcsService.getAdditionalCostServicesById(2), a4 = this.lcsService.getAdditionalCostServicesById(4);
//
//        boolean trueOrFalse = false;
//
//        t2.setQty(4.786);
//        t2.setUnit("Centimeter");
//        t4.setQty(0.991);
//        t4.setUnit("Millimeter");
//        t3.setQty(2.09);
//        t3.setUnit("Kilogram");
//        a3.setQty(4.567);
//        a3.setUnit("Kilolitre");
//        a2.setQty(7.543);
//        a2.setUnit("Square Feet");
//        a3.setQty(9.12);
//        a3.setUnit("Kilogram");
////        invoiceInformation.setNotes("test notes");
////        // Define the input date string
////        String paymentDueDateString = "2024-07-03", startDateString = "2024-08-02", endDateString="2024-10-01";
////        this.admin.setUsername("fwner726");
//
////        a1.setQty(2);
////        a2.setQty(4);
////        t1.setQty(2);
////        t2.setQty(4);
////        t3.setQty(1);
//        invoiceInformation.setNotes("test notes!!!");
//
//        // Define the input date string
//        String paymentDueDateString = "2024-06-04", startDateString = "2024-07-05", endDateString="2024-09-06";
//        this.admin.setUsername("lnell26");
//
//        // Define the desired date pattern
//        String pattern = "yyyy-MM-dd";
//        try {
//            // Create a SimpleDateFormat object
//            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//
//            // Parse the input string to a Date object
//            sdf.parse(paymentDueDateString);
//            Date paymentDueDate  =  new Date(sdf.parse(paymentDueDateString).getTime());
//            Date startDate  =  new Date(sdf.parse(startDateString).getTime());
//            Date endDate  =  new Date(sdf.parse(endDateString).getTime());
//
//            invoiceInformation.setPaymentDueDate((java.sql.Date) paymentDueDate);
//            invoiceInformation.setStartDate((java.sql.Date) startDate);
//            invoiceInformation.setEndDate((java.sql.Date) endDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//
//        t4.setRemoveFromList(!trueOrFalse);
//        t2.setRemoveFromList(trueOrFalse);
//        t3.setRemoveFromList(trueOrFalse);
//        t4.setUpdateQty(trueOrFalse);
//        t2.setUpdateQty(!trueOrFalse);
//        t3.setUpdateQty(!trueOrFalse);
//
//        List<Treatment> tl = new ArrayList<>();
//        tl.add(t4);
//        tl.add(t2);
//        tl.add(t3);
//
//
//        a4.setRemoveFromList(!trueOrFalse);
//        a2.setRemoveFromList(!trueOrFalse);
//        a3.setRemoveFromList(trueOrFalse);
//        a4.setUpdateQty(trueOrFalse);
//        a2.setUpdateQty(trueOrFalse);
//        a3.setUpdateQty(!trueOrFalse);
//
//        List<AdditionalCostService> al = new ArrayList<>();
//        al.add(a4);
//        al.add(a2);
//        al.add(a3);
//        invoiceInformation.setTreatments(tl);
//        invoiceInformation.setAdditionalCostServices(al);
//
//
//        InvoiceInformation updatedInvoiceInformation = this.lcsService.updateInvoiceInformation(this.invoiceInformation, this.client,this.admin);
//
//        assertThat(updatedInvoiceInformation.getNo()).isEqualTo(invoiceInformation.getNo());
//        assertThat(updatedInvoiceInformation.getPaymentDueDate()).isEqualTo(invoiceInformation.getPaymentDueDate());
//        assertThat(updatedInvoiceInformation.getStartDate()).isEqualTo(invoiceInformation.getStartDate());
//        assertThat(updatedInvoiceInformation.getEndDate()).isEqualTo(invoiceInformation.getEndDate());
//    }
//
//    @Test//means method is meant to be tested
//    public void doesAddressExistsTest() throws Exception {
//        this.address.setStreet("123 street");
//      boolean  doesAddressExists = this.lcsService.doesAddressExists(address);
//      assertThat(doesAddressExists).isTrue();
//        this.address.setStreet("1366 does not exists");
//      doesAddressExists = this.lcsService.doesAddressExists(address);
//      assertThat(doesAddressExists).isFalse();
//    }
//    @Test//means method is meant to be tested
//    public void removeTreatmentFromListTest() throws Exception {
//        treatment.setId(3);
//        this.invoiceInformation.setNo("TE287026");
//        List<Treatment> retrievedAdditionalCostServices = this.lcsService.removeTreatmentFromList(treatment,this.invoiceInformation);
//        int n = 4;
//        assertTrue(retrievedAdditionalCostServices.size() < n, "Array length should be less than n: "+n);
//    }
//
//    @Test//means method is meant to be tested
//    public void removeAdditionalCostServiceFromListTest() throws Exception {
//        this.client.setId(2);
//        additionalCostService.setId(3);
//        this.invoiceInformation.setNo("TE287026");
//        List<AdditionalCostService> retrievedAdditionalCostServices = this.lcsService.removeAdditionalCostServiceFromList(additionalCostService,this.invoiceInformation);
//        int n = 5;
//        assertTrue(retrievedAdditionalCostServices.size() < n, "Array length should be less than n: "+n);
//    }
//
//    @Test//means method is meant to be tested
//    public void saveInvoiceInformationTest() throws Exception {
//        this.client.setId(1);
//
//        this.zipcode = this.lcsService.getZipcodesById(27);
//        this.city.setId(1);
//        this.city.setCity("Atlanta");
//        address.setStreet("3480 Meadowlane Cir SW");
//        address.setState(this.state);
//        address.setZipcode(this.zipcode);
//        address.setId(1);
//        invoiceInformation.setNotes("testing");
//
//        // Define the input date string
//        String paymentDueDateString = "2024-06-01", startDateString = "2024-07-01", endDateString="2024-08-01";
//
//        // Define the desired date pattern
//        String pattern = "yyyy-MM-dd";
//        try {
//            // Create a SimpleDateFormat object
//            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//
//            // Parse the input string to a Date object
//            sdf.parse(paymentDueDateString);
//            Date paymentDueDate  =  new Date(sdf.parse(paymentDueDateString).getTime());
//            Date startDate  =  new Date(sdf.parse(startDateString).getTime());
//            Date endDate  =  new Date(sdf.parse(endDateString).getTime());
//
//            invoiceInformation.setPaymentDueDate((java.sql.Date) paymentDueDate);
//            invoiceInformation.setStartDate((java.sql.Date) startDate);
//            invoiceInformation.setEndDate((java.sql.Date) endDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        int i = 1, j = 1;
//
//        for(Unit unit : Unit.values()){
//            if(i > 3){ break; }
//            i++;
//            Treatment t = new Treatment();
//            t.setId(i);
//            t.setQty(i+1.0);
//            t.setUnit(unit.getFullName());
//         this.invoiceInformation.getTreatments().add(t);
//        }//for(int i = 1; i < 4; i++)
//
//        for(Unit unit : Unit.values()){
//            if(j > 4){ break; }
//            j++;
//            AdditionalCostService a = new AdditionalCostService();
//            a.setId(j);
//            a.setQty(j+1.0);
//            a.setUnit(unit.getFullName());
//            this.invoiceInformation.getAdditionalCostServices().add(a);
//        }//for(int j = 1; j < 3; j++)
//        invoiceInformation.setNo(this.invoiceNumberGenerator.generateInvoiceNo());
//        List<InvoiceInformation> invoiceInformations = this.lcsService.saveInvoiceInformation(this.invoiceInformation,this.client,this.address,this.admin);
//
//        /*####################################################################
//         *   Very important make sure to add 1 to x before running the tests!##
//         *       x = n + 1                                                   ##
//         *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
//         *  if:                                                              ##
//         *       int n = 26;                                                 ##
//         *       before running tests:                                       ##
//         *                               int n = 27;                         ##
//         * ####################################################################*/
//        int n = 0;//make sure to add 1 to x be for running tests!!!!
//        assertThat(invoiceInformations.get(0).getNo()).isEqualTo(invoiceInformation.getNo());
//    }
//
//    @Test//means method is meant to be tested
//    public void saveAdditionalCostServiceForInvoiceInformationTest() throws Exception {
//        this.client.setId(2);
//        additionalCostService.setId(6);
//        additionalCostService.setUnit("Fluid Ounce");
//        this.invoiceInformation.setNo("JB788715");
//        additionalCostServices = this.lcsService.saveAdditionalCostServiceForInvoiceInformation(this.additionalCostService,this.client,this.invoiceInformation);
//
//        /*####################################################################
//         *   Very important make sure to add 1 to x before running the tests!##
//         *       x = n + 1                                                   ##
//         *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
//         *  if:                                                              ##
//         *       int n = 26;                                                 ##
//         *       before running tests:                                       ##
//         *                               int n = 27;                         ##
//         * ####################################################################*/
//        int n = 0;//make sure to add 1 to x be for running tests!!!!
//        assertThat(additionalCostServices.size()).isGreaterThan(n);
//    }
//
//    @Test//means method is meant to be tested
//    public void saveTreatmentForInvoiceInformationTest() throws Exception {
//        this.client.setId(2);
//        treatment.setId(5);
//        treatment.setQty(67);
//        treatment.setUnit("Fluid Ounce");
//
//        this.invoiceInformation.setNo("JB788715");
//
//        this.treatments = this.lcsService.saveTreatmentForInvoiceInformation(this.treatment,this.client,invoiceInformation);
//
//        /*####################################################################
//         *   Very important make sure to add 1 to x before running the tests!##
//         *       x = n + 1                                                   ##
//         *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
//         *  if:                                                              ##
//         *       int n = 26;                                                 ##
//         *       before running tests:                                       ##
//         *                               int n = 27;                         ##
//         * ####################################################################*/
//        int n = 1;//make sure to add 1 to x be for running tests!!!!
//        assertThat(this.treatments.size()).isGreaterThan(n);
//    }
//
//    @Test//means method is meant to be tested
//    public void getInvoiceInformationByClientIdTest() throws Exception {
//        try {
//            client.setId(4);
//            invoiceInformations = this.lcsService.getInvoiceInformationByClientId(client);
//            assertThat(invoiceInformations).isNotNull();
//            assertTrue(invoiceInformations.size() > 0, "Array length should be more than 6");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getAllInvoiceInformationsTest() throws Exception {
//        try {
//            invoiceInformations = this.lcsService.getAllInvoiceInformations();
//            assertThat(invoiceInformations).isNotNull();
//            assertTrue(invoiceInformations.size() > 1, "Array length should be more than 6");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getAdditionalCostServicesByClientIdTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            this.invoiceInformation.setNo("TE287026");
//            additionalCostServices = this.lcsService.getAdditionalCostServicesByClientId(invoiceInformation);
//            assertThat(additionalCostServices).isNotNull();
//            int n = 1;
//            assertTrue(additionalCostServices.size() > n, "Array length should be more than n");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getTreatmentsByClientIdTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            this.client.setId(4);
//            this.invoiceInformation.setNo("JB788715");
//            treatments = this.lcsService.getTreatmentsByClientId(invoiceInformation);
//            assertThat(treatments).isNotNull();
//            int n = 0;
//            assertTrue(treatments.size() > n, "Array length should be more than n: "+n);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getAllAdditionalCostServicesTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            additionalCostServices = this.lcsService.getAllAdditionalCostServices();
//            assertThat(additionalCostServices).isNotNull();
//            int n = 6;
//            assertTrue(additionalCostServices.size() > n, "Array length should be more than n");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void updateAdditionalCostServiceTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            this.additionalCostService.setId(7);
//            this.additionalCostService.setTreatmentName("treatment name 7");
//            this.additionalCostService.setTreatmentDescription("treatment description 7");
//            this.additionalCostService.setPrice(35.91);
//            AdditionalCostService updatedAdditionalCostService = this.lcsService.updateAdditionalCostService(additionalCostService,this.admin);
//            assertThat(updatedAdditionalCostService).isNotNull();
//            assertThat(additionalCostService.getId()).isEqualTo(updatedAdditionalCostService.getId());
//            assertThat(additionalCostService.getTreatmentName()).isEqualTo(updatedAdditionalCostService.getTreatmentName());
//            assertThat(additionalCostService.getTreatmentDescription()).isEqualTo(updatedAdditionalCostService.getTreatmentDescription());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void saveAdditionalCostServiceTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            try {
//                try {
//
//                    AdditionalCostService savedObj = this.lcsService.saveAdditionalCostService(this.additionalCostService,this.admin);//save additionalCostService
//                    assertThat(savedObj).isNotNull();//see if it's empty or not
//
//                    /*####################################################################
//                     *   Very important make sure to add 1 to x before running the tests!##
//                     *       x = n + 1                                                   ##
//                     *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
//                     *  if:                                                              ##
//                     *       int n = 26;                                                 ##
//                     *       before running tests:                                       ##
//                     *                               int n = 27;                         ##
//                     * ####################################################################*/
//                    int n = 7;//make sure to add 1 to x be for running tests!!!!
//                    assertThat(savedObj.getId()).isGreaterThan(n);
//
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                } //catch
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getAdditionalCostServicesByIdTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            additionalCostService = this.lcsService.getAdditionalCostServicesById(1);
//            assertThat(additionalCostService).isNotNull();
//            assertThat(additionalCostService.getId()).isEqualTo(1);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getCityTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            city.setId(39);
//            city.setCity("Cartersville");
//            City retrievedObject = this.lcsService.getCity(city);
//            assertThat(retrievedObject).isNotNull();
//            assertThat(city.getId()).isEqualTo(retrievedObject.getId());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void saveAddressTest() throws Exception {
//        try {
//            try {
//                this.client.setId(1);
//                this.address.getCity().setId(2);
//                this.address.getZipcode().setId(239);
//                Address savedObj = this.lcsService.saveAddress(this.address,this.client,this.admin);//save treatment
//                assertThat(savedObj).isNotNull();//see if it's empty or not
//
//                /*####################################################################
//                 *   Very important make sure to add 1 to x before running the tests!##
//                 *       x = n + 1                                                   ##
//                 *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
//                 *  if:                                                              ##
//                 *       int n = 26;                                                 ##
//                 *       before running tests:                                       ##
//                 *                               int n = 27;                         ##
//                 * ####################################################################*/
//                int n = 0;//make sure to add 1 to x be for running tests!!!!
//                assertThat(savedObj.getId()).isGreaterThan(n);
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            } //catch
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void updateAddressTest() throws Exception {
//        try {
//            this.client.setId(1);
//            this.city.setId(2);
//            this.city.setCity("Augusta");
//            this.zipcode.setId(233);
//            this.zipcode.setZipcode("30805");
//
//            address.setId(1);
//            address.setStreet("5554 Saluda Ct NW");
//            address.setCity(this.city);
//            address.setState(this.state);
//            address.setZipcode(this.zipcode);
//            address.setBilling(true);
//
//            Address updatedAddress = this.lcsService.updateAddress(this.address,this.client,this.admin);
//            assertThat(updatedAddress).isNotNull();
//            assertThat(city.getCity()).isEqualTo(updatedAddress.getCity().getCity());
//            assertThat(state.getState()).isEqualTo(updatedAddress.getState().getState());
//            assertThat(zipcode.getZipcode()).isEqualTo(updatedAddress.getZipcode().getZipcode());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getAddressesByClientIdTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            this.client.setId(5);
//            addresses = this.lcsService.getAddressesByClientId(this.client.getId());
//            assertThat(addresses).isNotNull();
//            assertTrue(addresses.size() > 0, "Array length should be more than 49");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getAddressesTest() throws Exception {
//        try {
//            addresses = this.lcsService.getAddresses();
//            assertThat(addresses).isNotNull();
//            assertTrue(addresses.size() > 1, "Array length should be more than 1");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void saveZipcodeTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            try {
//                this.city.setId(38);
//                Zipcode savedObj = this.lcsService.saveZipcode(this.zipcode,this.city);//save treatment
//                assertThat(savedObj).isNotNull();//see if it's empty or not
//
//                /*####################################################################
//                 *   Very important make sure to add 1 to x before running the tests!##
//                 *       x = n + 1                                                   ##
//                 *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
//                 *  if:                                                              ##
//                 *       int n = 26;                                                 ##
//                 *       before running tests:                                       ##
//                 *                               int n = 27;                         ##
//                 * ####################################################################*/
//                int n = 60;//make sure to add 1 to x be for running tests!!!!
//                assertThat(savedObj.getId()).isGreaterThan(n);
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            } //catch
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getZipcodesByIdTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            zipcode = this.lcsService.getZipcodesById(13);
//            assertThat(zipcode).isNotNull();
//            assertThat(zipcode.getId()).isEqualTo(13);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    public void getZipcodesByCityTest() throws Exception {
//        try {
//            zipcodes = this.lcsService.getZipcodesByCity("Acworth");
//            assertThat(zipcodes).isNotNull();
//            int n = 0;
//            assertTrue(zipcodes.size() > n, "Array length should be more than n");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    public void getAllZipcodesTest() throws Exception {
//        try {
//            zipcodes = this.lcsService.getAllZipcodes();
//            assertThat(zipcodes).isNotNull();
//            assertTrue(zipcodes.size() > 30, "Array length should be more than 30");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void saveTreatmentTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            try {
//                this.admin.setId(1);
//                Treatment savedObj = this.lcsService.saveTreatment(this.treatment,this.admin);//save additionalCostService
//                assertThat(savedObj).isNotNull();//see if it's empty or not
//
//                /*####################################################################
//                 *   Very important make sure to add 1 to x before running the tests!##
//                 *       x = n + 1                                                   ##
//                 *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
//                 *  if:                                                              ##
//                 *       int n = 26;                                                 ##
//                 *       before running tests:                                       ##
//                 *                               int n = 27;                         ##
//                 * ####################################################################*/
//                int n = 7;//make sure to add 1 to x be for running tests!!!!
//                assertThat(savedObj.getId()).isGreaterThan(n);
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            } //catch
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void updateTreatmentTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            this.treatment.setId(8);
//            this.treatment.setTreatmentName("treatment name 8");
//            this.treatment.setTreatmentDescription("treatment 8 description");
//            this.treatment.setPrice(86.9);
//            Treatment updatedTreatment = this.lcsService.updateTreatment(treatment,admin);
//            assertThat(updatedTreatment).isNotNull();
//            assertThat(treatment.getId()).isEqualTo(updatedTreatment.getId());
//            assertThat(treatment.getTreatmentName()).isEqualTo(updatedTreatment.getTreatmentName());
//            assertThat(treatment.getTreatmentDescription()).isEqualTo(updatedTreatment.getTreatmentDescription());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getTreatmentByIdTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            treatment = this.lcsService.getTreatmentById(1);
//            assertThat(treatment).isNotNull();
//            assertThat(treatment.getId()).isEqualTo(1);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getAllTreatmentsTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            treatments = this.lcsService.getAllTreatments();
//            assertThat(treatments).isNotNull();
//            assertTrue(treatments.size() > 6, "Array length should be more than 6");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getAllStatesTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            states = this.lcsService.getAllStates();
//            assertThat(states).isNotNull();
//            assertTrue(states.size() > 49, "Array length should be more than 49");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void saveCityTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            try {
//
//                City savedObj = this.lcsService.saveCity(this.city);//save treatment
//                assertThat(savedObj).isNotNull();//see if it's empty or not
//
//                /*####################################################################
//                 *   Very important make sure to add 1 to x before running the tests!##
//                 *       x = n + 1                                                   ##
//                 *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
//                 *  if:                                                              ##
//                 *       int n = 26;                                                 ##
//                 *       before running tests:                                       ##
//                 *                               int n = 27;                         ##
//                 * ####################################################################*/
//                int n = 53;//make sure to add 1 to x be for running tests!!!!
//                assertThat(savedObj.getId()).isGreaterThan(n);
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            } //catch
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getCitiesTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            cities = this.lcsService.getCities();
//            assertThat(cities).isNotNull();
//            assertTrue(cities.size() > 10, "Array length should be more than 10");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void saveAdminTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            try {
//
//                Admin savedObj = this.lcsService.saveAdmin(this.admin);//save treatment
//                assertThat(savedObj).isNotNull();//see if it's empty or not
//
//                /*####################################################################
//                 *   Very important make sure to add 1 to x before running the tests!##
//                 *       x = n + 1                                                   ##
//                 *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
//                 *  if:                                                              ##
//                 *       int n = 26;                                                 ##
//                 *       before running tests:                                       ##
//                 *                               int n = 27;                         ##
//                 * ####################################################################*/
//                int n = 0;//make sure to add 1 to x be for running tests!!!!
//                assertThat(savedObj.getId()).isGreaterThan(n);
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            } //catch
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getAdminById() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            admin = this.lcsService.getAdminById(2);
//            assertThat(admin).isNotNull();
//            assertThat(admin.getId()).isEqualTo(2);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getAllAdminTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            adminList = this.lcsService.getAllAdmin();
//            assertThat(adminList).isNotNull();
//            assertTrue(adminList.size() > 1, "Array length should be more than 1");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void saveClientTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            try {
//
//                Client savedObj = this.lcsService.saveClient(this.client,this.admin);//save treatment
//                assertThat(savedObj).isNotNull();//see if it's empty or not
//
//                /*####################################################################
//                 *   Very important make sure to add 1 to x before running the tests!##
//                 *       x = n + 1                                                   ##
//                 *   for an example: In this case n is 26. So x = 26 + 1 = 27        ##
//                 *  if:                                                              ##
//                 *       int n = 26;                                                 ##
//                 *       before running tests:                                       ##
//                 *                               int n = 27;                         ##
//                 * ####################################################################*/
//                int n = 1;//make sure to add 1 to x be for running tests!!!!
//                assertThat(savedObj.getId()).isGreaterThan(n);
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            } //catch
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//        //TODO create updateAdmin
//    @Test//means method is meant to be tested
//    public void updateClientTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            this.client.setId(3);
//            this.client.setFirstName("Emmanuel");
//            this.client.setLastName("Damour");
//            this.client.setEmail("clownprince1961@gmail.com");
//            this.client.setPhoneNumber("4702256306");
//            Client updatedClient = this.lcsService.updateClient(client,this.admin);
//            assertThat(updatedClient).isNotNull();
//            assertThat(client.getId()).isEqualTo(updatedClient.getId());
//            assertThat(client.getFirstName()).isEqualTo(updatedClient.getFirstName());
//            assertThat(client.getMiddleName()).isEqualTo(updatedClient.getMiddleName());
//            assertThat(client.getLastName()).isEqualTo(updatedClient.getLastName());
//            assertThat(client.getEmail()).isEqualTo(updatedClient.getEmail());
//            assertThat(client.getPhoneNumber()).isEqualTo(updatedClient.getPhoneNumber());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test//means method is meant to be tested
//    public void getClientByIdTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            client = this.lcsService.getClientById(2);
//            assertThat(client).isNotNull();
//            assertThat(client.getId()).isEqualTo(2);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    public void getClientsTest() throws Exception {//becuase were talking to the database we must throw an exception
//        try {
//            clients = this.lcsService.getClients();
//            assertThat(clients).isNotNull();
//            assertTrue(clients.size() > 2, "Array length should be more than 5");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

}
