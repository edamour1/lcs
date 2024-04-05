package com.warner.lcs.app.repository;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.common.util.SQL;
import com.warner.lcs.common.util.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LcsRepositoryImpl implements LcsRepository {

    @Autowired
    @Qualifier("lcsDataSourceTemplate")
    private JdbcTemplate lcsDataSourceTemplate;


    @Override
    public Business updateBusiness(Business business, Admin admin) throws Exception {
        String sql = SQL.get("lcsSql","updateBusiness");

         this.lcsDataSourceTemplate.update(sql,
                                                 business.getName(),
                                                 business.getPhoneNo(),
                                                 business.getFaxPhoneNo(),
                                                 business.getEmail(),
                                                 admin.getUsername(),
                                                 business.getId());
         return this.getBusiness();
    }

    @Override
    public Business getBusiness() throws Exception {
        String sql = SQL.get("lcsSql","getBusiness");

        RowMapper<Business> mapper = new RowMapper<Business>(){
            public Business mapRow(ResultSet rs, int rowNum) throws SQLException {

                Business business = new Business(rs);

                return business;
            }
        };
        int businessId = 3;

        List<Business> businesses  = this.lcsDataSourceTemplate.query(sql,mapper,businessId);

        for(Business business: businesses){
            Address businessAddress = this.getAddressById(business.getAddress());
            business.setAddress(businessAddress);
        }

        return businesses.get(0);
    }

    @Override
    public AdditionalCostService getAdditionalCostService(AdditionalCostService additionalCostService, InvoiceInformation invoiceInformation) throws Exception {

        String sql = SQL.get("lcsSql","getAdditionalCostService");
        RowMapper<AdditionalCostService> mapper = new RowMapper<AdditionalCostService>(){
            public AdditionalCostService mapRow(ResultSet rs, int rowNum) throws SQLException {

                AdditionalCostService additionalCostService = new AdditionalCostService(rs);
                additionalCostService.setQty(rs.getDouble("quantity"));

                return additionalCostService;
            }
        };

        List<AdditionalCostService> additionalCostServices = this.lcsDataSourceTemplate.query(sql,mapper,invoiceInformation.getNo(),additionalCostService.getId());
        AdditionalCostService retrievedAdditionalCostService = additionalCostServices.get(0);
            return retrievedAdditionalCostService;

    }

    @Override
    public AdditionalCostService updateAdditionalCostServiceQty(AdditionalCostService additionalCostService, InvoiceInformation invoiceInformation) throws Exception {
        String sql = SQL.get("lcsSql","updateAdditionalCostServiceQty");
        String enumName = additionalCostService.getUnit().toUpperCase(); // Replace with the desired enum constant name
        StringBuilder sb = new StringBuilder();
        char character;
        for (int i = 0;  i < enumName.length(); i++){
            character = enumName.charAt(i);

            if(character == ' '){
                sb.append('_');
            } else { sb.append(character); }
        }

        try {
            Unit unitsEnum = Unit.valueOf(sb.toString());
            int unitId = unitsEnum.getId();

            this.lcsDataSourceTemplate.update(sql,additionalCostService.getQty(),unitId,invoiceInformation.getNo(),additionalCostService.getId());
            AdditionalCostService updatedAdditionalCostService = this.getAdditionalCostService(additionalCostService,invoiceInformation);
            return updatedAdditionalCostService;

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid enum constant name: " + sb.toString());
        }

        return null;
    }

    @Override
    public Treatment getTreatment(Treatment treatment, InvoiceInformation invoiceInformation) throws Exception {
        RowMapper<Treatment> mapper = new RowMapper<Treatment>(){
            public Treatment mapRow(ResultSet rs, int rowNum) throws SQLException {

                Treatment treatment = new Treatment(rs);
                treatment.setQty(rs.getDouble("quantity"));
                treatment.setUnit(rs.getString("unit"));
                return treatment;
            }
        };

        String sql = SQL.get("lcsSql","getTreatment");
        List<Treatment> treatments = this.lcsDataSourceTemplate.query(sql,mapper,invoiceInformation.getNo(),treatment.getId());
        Treatment retrievedTreatment = treatments.get(0);

        return retrievedTreatment;
    }

    @Override
    public Treatment updateTreatmentQty(Treatment treatment, InvoiceInformation invoiceInformation) throws Exception {
        String sql = SQL.get("lcsSql","updateTreatmentQty");

        String enumName = treatment.getUnit().toUpperCase(); // Replace with the desired enum constant name
        StringBuilder sb = new StringBuilder();
        char character;
        for (int i = 0;  i < enumName.length(); i++){
            character = enumName.charAt(i);

            if(character == ' '){
                sb.append('_');
            } else { sb.append(character); }
        }

        try {
            Unit unitsEnum = Unit.valueOf(sb.toString());
            int unitId = unitsEnum.getId();

            this.lcsDataSourceTemplate.update(sql,treatment.getQty(),unitId,invoiceInformation.getNo(),treatment.getId());
            Treatment updatedTreatment = this.getTreatment(treatment,invoiceInformation);
            return updatedTreatment;

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid enum constant name: " + sb.toString());
        }

        return null;
    }

    @Override
    public InvoiceInformation getInvoiceInformation(String invoiceNo) throws Exception {
        RowMapper<InvoiceInformation> mapper = new RowMapper<InvoiceInformation>(){
            public InvoiceInformation mapRow(ResultSet rs, int rowNum) throws SQLException {

                InvoiceInformation invoiceInformation = new InvoiceInformation(rs);

                return invoiceInformation;
            }
        };

        String sql = SQL.get("lcsSql","getInvoiceInformation");
        List<InvoiceInformation> invoiceInformations = this.lcsDataSourceTemplate.query(sql,mapper,invoiceNo);

        for(InvoiceInformation invoiceInformation : invoiceInformations){
            invoiceInformation.setTreatments(this.getTreatmentsByClientId(invoiceInformation));
            invoiceInformation.setAdditionalCostServices(this.getAdditionalCostServicesByClientId(invoiceInformation));
        }

        return invoiceInformations.get(0);
    }


    @Override
    public Address getAddressesByInvoiceInformation(InvoiceInformation invoiceInformation) throws Exception {
        RowMapper<Address> mapper = new RowMapper<Address>(){
            public Address mapRow(ResultSet rs, int rowNum) throws SQLException {

                Address address = new Address(rs);

                return address;
            }
        };

        String sql = SQL.get("lcsSql","getAddressesByInvoiceInformation");
        List<Address> addresses = this.lcsDataSourceTemplate.query(sql,mapper,invoiceInformation.getNo());

        return addresses.get(0);
    }

    @Override
    public InvoiceInformation getInvoiceInformationByAddress(Address address) throws Exception {

        RowMapper<InvoiceInformation> mapper = new RowMapper<InvoiceInformation>(){
            public InvoiceInformation mapRow(ResultSet rs, int rowNum) throws SQLException {

                InvoiceInformation invoiceInformation = new InvoiceInformation(rs);

                return invoiceInformation;
            }
        };

        String sql = SQL.get("lcsSql","getInvoiceInformationByAddress");
        List<InvoiceInformation> invoiceInformations = this.lcsDataSourceTemplate.query(sql,mapper,address.getId());

        for(InvoiceInformation invoiceInformation : invoiceInformations){
            invoiceInformation.setTreatments(this.getTreatmentsByClientId(invoiceInformation));
            invoiceInformation.setAdditionalCostServices(this.getAdditionalCostServicesByClientId(invoiceInformation));
        }

        return invoiceInformations.get(0);
    }

    @Override
    public InvoiceInformation updateInvoiceInformation(InvoiceInformation invoiceInformation, Client client, Admin admin) throws Exception {
//        this.disableOrEnableForeignKeyChecks(true);
        for(Treatment treatment : invoiceInformation.getTreatments()){//update Treatment
            if(treatment.getRemoveFromList()) {
                this.removeTreatmentFromList(treatment,invoiceInformation);
            }
            else {
                if(!treatment.isUpdateQty()){
                    this.saveTreatmentForInvoiceInformation(treatment,client,invoiceInformation);
                }else{ this.updateTreatmentQty(treatment,invoiceInformation); }
            }
        }

        for(AdditionalCostService additionalCostService : invoiceInformation.getAdditionalCostServices()){//update AdditionalCostServices
            if(additionalCostService.getRemoveFromList()) {
                this.removeAdditionalCostServiceFromList(additionalCostService,invoiceInformation);
            }
            else {
                if(!additionalCostService.isUpdateQty()){
                    this.saveAdditionalCostServiceForInvoiceInformation(additionalCostService,client,invoiceInformation);
                } else {
                    this.updateAdditionalCostServiceQty(additionalCostService,invoiceInformation);
                }

            }

        }

        String sql = SQL.get("lcsSql","updateInvoiceInformation");

        this.lcsDataSourceTemplate.update(sql,
                invoiceInformation.getPaymentDueDate(),
                invoiceInformation.getStartDate(),
                invoiceInformation.getEndDate(),
                invoiceInformation.getNotes(),
                admin.getUsername(),
                invoiceInformation.getNo());

       List<InvoiceInformation>  retrievedInvoiceInformations = this.getInvoiceInformationByClientId(client);



        return retrievedInvoiceInformations.get(0);
    }



// ...

    @Transactional
    @Override
    public List<Treatment> removeTreatmentFromList(Treatment treatment, InvoiceInformation invoiceInformation) throws Exception {
        try {
            // Disable foreign key checks
            String disableForeignKeyChecksSql = "SET foreign_key_checks = 0";
            this.lcsDataSourceTemplate.execute(disableForeignKeyChecksSql);

            // Perform the DELETE operation
            String deleteSql = SQL.get("lcsSql", "removeTreatmentFromList");
            this.lcsDataSourceTemplate.update(deleteSql, invoiceInformation.getNo(), treatment.getId());

            // Enable foreign key checks
            String enableForeignKeyChecksSql = "SET foreign_key_checks = 1";
            this.lcsDataSourceTemplate.execute(enableForeignKeyChecksSql);

            // Retrieve the updated treatments
            List<Treatment> retrievedTreatments = this.getTreatmentsByClientId(invoiceInformation);
            return retrievedTreatments;
        } catch (Exception e) {
            // Handle exceptions
            throw e;
        }
    }


    @Transactional
    @Override
    public List<AdditionalCostService> removeAdditionalCostServiceFromList(AdditionalCostService additionalCostService, InvoiceInformation invoiceInformation) throws Exception {
        try {
            // Disable foreign key checks
            String disableForeignKeyChecksSql = "SET foreign_key_checks = 0";
            this.lcsDataSourceTemplate.execute(disableForeignKeyChecksSql);

            // Perform the DELETE operation
            String deleteSql = SQL.get("lcsSql", "removeAdditionalCostServiceFromList");
            this.lcsDataSourceTemplate.update(deleteSql, invoiceInformation.getNo(), additionalCostService.getId());

            // Enable foreign key checks
            String enableForeignKeyChecksSql = "SET foreign_key_checks = 1";
            this.lcsDataSourceTemplate.execute(enableForeignKeyChecksSql);

            // Retrieve the updated treatments
            List<AdditionalCostService> retrievedAdditionalCostServices = this.getAdditionalCostServicesByClientId(invoiceInformation);
            return retrievedAdditionalCostServices;
        } catch (Exception e) {
            // Handle exceptions
            throw e;
        }
    }

    @Override
    public List<AdditionalCostService> saveAdditionalCostServiceForInvoiceInformation(AdditionalCostService additionalCostService, Client client,InvoiceInformation invoiceInformation) throws Exception {
        String sql = SQL.get("lcsSql","saveAdditionalCostServiceForInvoiceInformation");
        KeyHolder keyHolder = new GeneratedKeyHolder();//used in order to hold primary key value that's returned after the db insert is performed.
        this.lcsDataSourceTemplate.update(connection -> {
            /* *******************************************************************************************************************************************************
             *  PreparedStatement is not specific to the Spring JDBC Template;
             *  it is part of the default Java Database Connectivity (JDBC) API,  which is a standard part of the Java Standard Edition (SE) platform.
             *
             * PreparedStatement is an interface in the java.sql package and is used to execute precompiled SQL statements.
             *
             * It extends the Statement interface and provides additional methods for setting parameters in a SQL statement.
             *
             * The primary advantage of using PreparedStatement over Statement is that it helps prevent SQL injection attacks.
             *
             * It also can improve performance by allowing the database to reuse previously compiled statements.
             ********************************************************************************************************************************************************/
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, invoiceInformation.getNo());
            ps.setInt(2, additionalCostService.getId());
            ps.setDouble(3,additionalCostService.getQty());
            String enumName = additionalCostService.getUnit().toUpperCase(); // Replace with the desired enum constant name

            StringBuilder sb = new StringBuilder();
            char character;
            for (int i = 0;  i < enumName.length(); i++){
                character = enumName.charAt(i);

                if(character == ' '){
                    sb.append('_');
                } else { sb.append(character); }
            }

            try {
                Unit unitsEnum = Unit.valueOf(sb.toString());
                int unitId = unitsEnum.getId();
                ps.setInt(4,unitId);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid enum constant name: " + sb.toString());
            }

            return ps;
        }, keyHolder);
        List<AdditionalCostService> additionalCostServices = this.getAdditionalCostServicesByClientId(invoiceInformation);
        return additionalCostServices;
    }


    @Override
    public List <Treatment> saveTreatmentForInvoiceInformation(Treatment treatment, Client client, InvoiceInformation invoiceInformation) throws Exception {
        String sql = SQL.get("lcsSql","saveTreatmentForInvoiceInformation");
        KeyHolder keyHolder = new GeneratedKeyHolder();//used in order to hold primary key value that's returned after the db insert is performed.
        this.lcsDataSourceTemplate.update(connection -> {
            /* *******************************************************************************************************************************************************
             *  PreparedStatement is not specific to the Spring JDBC Template;
             *  it is part of the default Java Database Connectivity (JDBC) API,  which is a standard part of the Java Standard Edition (SE) platform.
             *
             * PreparedStatement is an interface in the java.sql package and is used to execute precompiled SQL statements.
             *
             * It extends the Statement interface and provides additional methods for setting parameters in a SQL statement.
             *
             * The primary advantage of using PreparedStatement over Statement is that it helps prevent SQL injection attacks.
             *
             * It also can improve performance by allowing the database to reuse previously compiled statements.
             ********************************************************************************************************************************************************/
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,invoiceInformation.getNo());
            ps.setInt(2, treatment.getId());
            ps.setDouble(3, treatment.getQty());
            String enumName = treatment.getUnit().toUpperCase(); // Replace with the desired enum constant name
            StringBuilder sb = new StringBuilder();
            char character;
            for (int i = 0;  i < enumName.length(); i++){
                character = enumName.charAt(i);

                if(character == ' '){
                    sb.append('_');
                } else { sb.append(character); }
            }
            try {
                Unit unitsEnum = Unit.valueOf(sb.toString());

                int unitId = unitsEnum.getId();
                ps.setInt(4,unitId);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid enum constant name: " + sb.toString());
            }
            return ps;
        }, keyHolder);

        return this.getTreatmentsByClientId(invoiceInformation);
    }

    @Override
    public List<InvoiceInformation> saveInvoiceInformation(InvoiceInformation invoiceInformation, Client client, Address address, Admin admin) throws Exception {

        String sql = SQL.get("lcsSql","saveInvoiceInformation");
        KeyHolder keyHolder = new GeneratedKeyHolder();//used in order to hold primary key value that's returned after the db insert is performed.
        this.lcsDataSourceTemplate.update(connection -> {
            /* *******************************************************************************************************************************************************
             *  PreparedStatement is not specific to the Spring JDBC Template;
             *  it is part of the default Java Database Connectivity (JDBC) API,  which is a standard part of the Java Standard Edition (SE) platform.
             *
             * PreparedStatement is an interface in the java.sql package and is used to execute precompiled SQL statements.
             *
             * It extends the Statement interface and provides additional methods for setting parameters in a SQL statement.
             *
             * The primary advantage of using PreparedStatement over Statement is that it helps prevent SQL injection attacks.
             *
             * It also can improve performance by allowing the database to reuse previously compiled statements.
             ********************************************************************************************************************************************************/
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate(1, invoiceInformation.getPaymentDueDate());
            ps.setDate(2, invoiceInformation.getStartDate());
            ps.setDate(3, invoiceInformation.getEndDate());
            ps.setInt(4, client.getId());
            ps.setString(5, invoiceInformation.getNotes());

            ps.setInt(6,address.getId());
            ps.setString(7, admin.getUsername());
            ps.setString(8, invoiceInformation.getNo());


            return ps;
        }, keyHolder);

        for(Treatment treatment : invoiceInformation.getTreatments()){
            this.saveTreatmentForInvoiceInformation(treatment,client,invoiceInformation);
        }

        for(AdditionalCostService additionalCostService : invoiceInformation.getAdditionalCostServices()) {
            this.saveAdditionalCostServiceForInvoiceInformation(additionalCostService,client,invoiceInformation);
        }


        List<InvoiceInformation>  retrievedInvoiceInformation = this.getInvoiceInformationByClientId(client);

        return retrievedInvoiceInformation;
    }

    @Override
    public List<InvoiceInformation> getInvoiceInformationByClientId(Client client) throws Exception {
        RowMapper<InvoiceInformation> mapper = new RowMapper<InvoiceInformation>(){
            public InvoiceInformation mapRow(ResultSet rs, int rowNum) throws SQLException {

                InvoiceInformation invoiceInformation = new InvoiceInformation(rs);

                return invoiceInformation;
            }
        };

        String sql = SQL.get("lcsSql","getInvoiceInformationByClientId");
        List<InvoiceInformation> invoiceInformations = this.lcsDataSourceTemplate.query(sql,mapper,client.getId());

        for(InvoiceInformation invoiceInformation : invoiceInformations){
            invoiceInformation.setTreatments(this.getTreatmentsByClientId(invoiceInformation));
            invoiceInformation.setAdditionalCostServices(this.getAdditionalCostServicesByClientId(invoiceInformation));
        }

        return invoiceInformations;
    }

    @Override
    public List<InvoiceInformation> getAllInvoiceInformations() throws Exception {
        RowMapper<InvoiceInformation> mapper = new RowMapper<InvoiceInformation>(){
            public InvoiceInformation mapRow(ResultSet rs, int rowNum) throws SQLException {

                InvoiceInformation invoiceInformation = new InvoiceInformation(rs);

                return invoiceInformation;
            }
        };

        String sql = SQL.get("lcsSql","getAllInvoiceInformations");
        List<InvoiceInformation> invoiceInformations = this.lcsDataSourceTemplate.query(sql,mapper);

        for(InvoiceInformation invoiceInformation : invoiceInformations){
            invoiceInformation.setTreatments(this.getTreatmentsByClientId(invoiceInformation));
            invoiceInformation.setAdditionalCostServices(this.getAdditionalCostServicesByClientId(invoiceInformation));
        }
        return invoiceInformations;
    }

    @Override
    public List<AdditionalCostService> getAllAdditionalCostServices() throws Exception {
        RowMapper<AdditionalCostService> mapper = new RowMapper<AdditionalCostService>(){
            public AdditionalCostService mapRow(ResultSet rs, int rowNum) throws SQLException {

                AdditionalCostService additionalCostService = new AdditionalCostService(rs);

                return additionalCostService;
            }
        };

        String sql = SQL.get("lcsSql","getAllAdditionalCostServices");
        List<AdditionalCostService> additionalCostServices = this.lcsDataSourceTemplate.query(sql,mapper);

        return additionalCostServices;
    }

    @Override
    public List<AdditionalCostService> getAdditionalCostServicesByClientId(InvoiceInformation invoiceInformation) throws Exception {
        RowMapper<AdditionalCostService> mapper = new RowMapper<AdditionalCostService>(){
            public AdditionalCostService mapRow(ResultSet rs, int rowNum) throws SQLException {

                AdditionalCostService additionalCostService = new AdditionalCostService(rs);
                additionalCostService.setQty(rs.getInt("quantity"));
                additionalCostService.setUnit(rs.getString("unit"));
                return additionalCostService;
            }
        };

        String sql = SQL.get("lcsSql","getAdditionalCostServicesByClientId");
        List<AdditionalCostService> additionalCostServices = this.lcsDataSourceTemplate.query(sql,mapper,invoiceInformation.getNo());

        return additionalCostServices;
    }

    @Override
    public AdditionalCostService updateAdditionalCostService(AdditionalCostService additionalCostService, Admin admin) throws Exception {
        String sql = SQL.get("lcsSql","updateAdditionalCostService");
                this.lcsDataSourceTemplate.update(sql,
                additionalCostService.getTreatmentName(),
                additionalCostService.getTreatmentDescription(),
                additionalCostService.getPrice(),
                admin.getUsername(),
                additionalCostService.getId());
        AdditionalCostService retrievedAdditionalCostService = this.getAdditionalCostServicesById(additionalCostService.getId());

        return retrievedAdditionalCostService;
    }

    @Override
    public AdditionalCostService saveAdditionalCostService(AdditionalCostService additionalCostService, Admin admin) throws Exception {
        String sql = SQL.get("lcsSql","saveAdditionalCostService");
        KeyHolder keyHolder = new GeneratedKeyHolder();//used in order to hold primary key value that's returned after the db insert is performed.
       this.lcsDataSourceTemplate.update(connection -> {
            /* *******************************************************************************************************************************************************
             *  PreparedStatement is not specific to the Spring JDBC Template;
             *  it is part of the default Java Database Connectivity (JDBC) API,  which is a standard part of the Java Standard Edition (SE) platform.
             *
             * PreparedStatement is an interface in the java.sql package and is used to execute precompiled SQL statements.
             *
             * It extends the Statement interface and provides additional methods for setting parameters in a SQL statement.
             *
             * The primary advantage of using PreparedStatement over Statement is that it helps prevent SQL injection attacks.
             *
             * It also can improve performance by allowing the database to reuse previously compiled statements.
             ********************************************************************************************************************************************************/
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, additionalCostService.getTreatmentName());
            ps.setString(2, additionalCostService.getTreatmentDescription());
            ps.setDouble(3, additionalCostService.getPrice());
            ps.setString(4, admin.getUsername());
            return ps;
        }, keyHolder);

        AdditionalCostService retrievedAdditionalCostService = this.getAdditionalCostServicesById((Integer) keyHolder.getKey().intValue());

        return retrievedAdditionalCostService;
    }

    @Override
    public AdditionalCostService getAdditionalCostServicesById(int id) throws Exception {RowMapper<AdditionalCostService> mapper = new RowMapper<AdditionalCostService>(){
        public AdditionalCostService mapRow(ResultSet rs, int rowNum) throws SQLException {

            AdditionalCostService additionalCostService = new AdditionalCostService(rs);

            return additionalCostService;
        }
    };

        String sql = SQL.get("lcsSql","getAdditionalCostServicesById");
        List<AdditionalCostService> additionalCostServices = this.lcsDataSourceTemplate.query(sql,mapper,id);

        return additionalCostServices.get(0);
    }

    @Override
    public Address updateAddress(Address address, Client client, Admin admin) throws Exception {

        String sql = SQL.get("lcsSql","updateAddress");
        int isActiveArgument = address.getIsActive() ? 1 : 0;
        int isBillingArgument = address.isBilling() ? 1 : 0;
        this.lcsDataSourceTemplate.update(sql,
                address.getStreet(),
                address.getCity().getId(),
                address.getState().getId(),
                address.getZipcode().getId(),
                isActiveArgument,
                isBillingArgument,
                address.getQuantity(),
                Unit.SQUARE_FEET.getId(),
               admin.getUsername(),
                address.getId());

        Address retrievedAddress = this.getAddressById(address);

        return retrievedAddress;
    }

    @Override
    public Address saveAddress(Address address, Client client,Admin admin) throws Exception {

        City city = this.getCity(address.getCity());
        if(city.getId() == 0) { address.setCity(this.saveCity(address.getCity())); }

        Zipcode zipcode = address.getZipcode();
        if(zipcode.getId() == 0){ address.setZipcode(this.saveZipcode(zipcode,city)); }

        String sql = SQL.get("lcsSql","saveAddress");

        int id = this.lcsDataSourceTemplate.update(connection -> {

            /* *******************************************************************************************************************************************************
             *  PreparedStatement is not specific to the Spring JDBC Template;
             *  it is part of the default Java Database Connectivity (JDBC) API,  which is a standard part of the Java Standard Edition (SE) platform.
             *
             * PreparedStatement is an interface in the java.sql package and is used to execute precompiled SQL statements.
             *
             * It extends the Statement interface and provides additional methods for setting parameters in a SQL statement.
             *
             * The primary advantage of using PreparedStatement over Statement is that it helps prevent SQL injection attacks.
             *
             * It also can improve performance by allowing the database to reuse previously compiled statements.
             ********************************************************************************************************************************************************/
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, client.getId());
            ps.setString(2, address.getStreet());
            ps.setInt(3, address.getCity().getId());
            ps.setInt(4, address.getState().getId());
            ps.setInt(5, address.getZipcode().getId());
            int isActive = address.getIsActive() ? 1 : 0;
            int isBilling = address.isBilling() ? 1 : 0;
            ps.setInt(6,isBilling);
            ps.setInt(7,isActive);
            ps.setDouble(8,address.getQuantity());
            ps.setDouble(9, Unit.SQUARE_FEET.getId());
            ps.setString(10, String.valueOf(admin.getUsername()));

            return ps;

        });

        List<Address> retrievedAddress = this.getAddressesByClientId(client.getId());

        return retrievedAddress.get(0);

    }

    @Override
    public List<Address> getAddressesByClientId(int id) throws Exception {
        RowMapper<Address> mapper = new RowMapper<Address>() {
            public Address mapRow(ResultSet rs, int rowNum) throws SQLException {

                Address address = new Address(rs);

                return address;
            }
        };

        String sql = SQL.get("lcsSql","getAddressesByClientId");
        List<Address> addresses = this.lcsDataSourceTemplate.query(sql,mapper,id);

        return addresses;
    }

    @Override
    public List<Address> getAddresses() throws Exception {
        RowMapper<Address> mapper = new RowMapper<Address>(){
            public Address mapRow(ResultSet rs, int rowNum) throws SQLException {

                Address address = new Address(rs);

                return address;
            }
        };

        String sql = SQL.get("lcsSql","getAddresses");
        List<Address> addresses = this.lcsDataSourceTemplate.query(sql,mapper);

        return addresses;
    }

    @Override
    public boolean doesAddressExists(Address address) throws Exception {
        RowMapper<Address> mapper = new RowMapper<Address>(){
            public Address mapRow(ResultSet rs, int rowNum) throws SQLException {

                Address address = new Address(rs);

                return address;
            }
        };

        String sql = SQL.get("lcsSql","doesAddressExists");
        List<Address> addresses = this.lcsDataSourceTemplate.query(sql,mapper,address.getStreet());
        boolean doesAddressExists = addresses.size() > 0 ? true : false;

        return doesAddressExists;
    }

    @Override
    public Address getAddressById(Address address) throws Exception {
        RowMapper<Address> mapper = new RowMapper<Address>(){
            public Address mapRow(ResultSet rs, int rowNum) throws SQLException {

                Address address = new Address(rs);

                return address;
            }
        };

        String sql = SQL.get("lcsSql","getAddressById");
        List<Address> addresses = this.lcsDataSourceTemplate.query(sql,mapper,address.getId());


        return addresses.get(0);
    }

    @Override
    public Zipcode saveZipcode(Zipcode zipcode, City city) throws Exception {
        String sql = SQL.get("lcsSql","saveZipcode");
        KeyHolder keyHolder = new GeneratedKeyHolder();//used in order to hold primary key value that's returned after the db insert is performed.
        int id = this.lcsDataSourceTemplate.update(connection -> {
            /* *******************************************************************************************************************************************************
             *  PreparedStatement is not specific to the Spring JDBC Template;
             *  it is part of the default Java Database Connectivity (JDBC) API,  which is a standard part of the Java Standard Edition (SE) platform.
             *
             * PreparedStatement is an interface in the java.sql package and is used to execute precompiled SQL statements.
             *
             * It extends the Statement interface and provides additional methods for setting parameters in a SQL statement.
             *
             * The primary advantage of using PreparedStatement over Statement is that it helps prevent SQL injection attacks.
             *
             * It also can improve performance by allowing the database to reuse previously compiled statements.
             ********************************************************************************************************************************************************/
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, zipcode.getZipcode());
            ps.setInt(2, city.getId());
            return ps;
        }, keyHolder);

        zipcode.setId((Integer) keyHolder.getKey().intValue());

        return zipcode;
    }

    @Override
    public Zipcode getZipcode(String zipcode) throws Exception {
        RowMapper<Zipcode> mapper = new RowMapper<Zipcode>() {
            public Zipcode mapRow(ResultSet rs, int rowNum) throws SQLException {

                Zipcode zipcode = new Zipcode(rs,false);

                return zipcode;
            }
        };

        String sql = SQL.get("lcsSql","getZipcode");
        List<Zipcode> zipcodes = this.lcsDataSourceTemplate.query(sql,mapper,zipcode);

        return zipcodes.get(0);
    }

    @Override
    public Zipcode getZipcodesById(int id) throws Exception {
        RowMapper<Zipcode> mapper = new RowMapper<Zipcode>() {
            public Zipcode mapRow(ResultSet rs, int rowNum) throws SQLException {

                Zipcode zipcode = new Zipcode(rs,false);

                return zipcode;
            }
        };

        String sql = SQL.get("lcsSql","getZipcodesById");
        List<Zipcode> zipcodes = this.lcsDataSourceTemplate.query(sql,mapper,id);

        return zipcodes.get(0);
    }

    @Override
    public List<Zipcode> getZipcodesByCity(String city) throws Exception {
        RowMapper<Zipcode> mapper = new RowMapper<Zipcode>() {
            public Zipcode mapRow(ResultSet rs, int rowNum) throws SQLException {

                Zipcode zipcode = new Zipcode(rs,false);

                return zipcode;
            }
        };

        String sql = SQL.get("lcsSql","getZipcodesByCity");
        List<Zipcode> zipcodes = this.lcsDataSourceTemplate.query(sql,mapper,city);

        return zipcodes;
    }

    @Override
    public List<Zipcode> getAllZipcodes() throws Exception {
        RowMapper<Zipcode> mapper = new RowMapper<Zipcode>(){
            public Zipcode mapRow(ResultSet rs, int rowNum) throws SQLException {

                Zipcode zipcode = new Zipcode(rs,false);

                return zipcode;
            }
        };

        String sql = SQL.get("lcsSql","getAllZipcodes");
        List<Zipcode> zipcodes = this.lcsDataSourceTemplate.query(sql,mapper);

        return zipcodes;
    }

    @Override
    public Treatment saveTreatment(Treatment treatment, Admin admin) throws Exception {
        String sql = SQL.get("lcsSql","saveTreatment");
        KeyHolder keyHolder = new GeneratedKeyHolder();//used in order to hold primary key value that's returned after the db insert is performed.
        int id = this.lcsDataSourceTemplate.update(connection -> {
            /* *******************************************************************************************************************************************************
             *  PreparedStatement is not specific to the Spring JDBC Template;
             *  it is part of the default Java Database Connectivity (JDBC) API,  which is a standard part of the Java Standard Edition (SE) platform.
             *
             * PreparedStatement is an interface in the java.sql package and is used to execute precompiled SQL statements.
             *
             * It extends the Statement interface and provides additional methods for setting parameters in a SQL statement.
             *
             * The primary advantage of using PreparedStatement over Statement is that it helps prevent SQL injection attacks.
             *
             * It also can improve performance by allowing the database to reuse previously compiled statements.
             ********************************************************************************************************************************************************/
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, treatment.getTreatmentName());
            ps.setString(2, treatment.getTreatmentDescription());
            ps.setDouble(3, treatment.getPrice());
            ps.setString(4, admin.getUsername());
            return ps;
        }, keyHolder);

        Treatment retrievedTreatment = this.getTreatmentById((Integer) keyHolder.getKey().intValue());

        return retrievedTreatment;
    }

    @Override
    public Treatment getTreatmentById(int id) throws Exception {
        RowMapper<Treatment> mapper = new RowMapper<Treatment>(){
            public Treatment mapRow(ResultSet rs, int rowNum) throws SQLException {

                Treatment treatment = new Treatment(rs);

                return treatment;
            }
        };

        String sql = SQL.get("lcsSql","getTreatmentById");
        List<Treatment> treatments = this.lcsDataSourceTemplate.query(sql,mapper,id);

        return treatments.get(0);
    }

    @Override
    public List<Treatment> getTreatmentsByClientId(InvoiceInformation invoiceInformation) throws Exception {
        RowMapper<Treatment> mapper = new RowMapper<Treatment>(){
            public Treatment mapRow(ResultSet rs, int rowNum) throws SQLException {

                Treatment treatment = new Treatment(rs);
                treatment.setQty(rs.getInt("quantity"));
                treatment.setUnit(rs.getString("unit"));
                return treatment;
            }
        };

        String sql = SQL.get("lcsSql","getTreatmentsByClientId");
        List<Treatment> treatments = this.lcsDataSourceTemplate.query(sql,mapper,invoiceInformation.getNo());

        return treatments;
    }

    @Override
    public Treatment updateTreatment(Treatment treatment, Admin admin) throws Exception {
        String sql = SQL.get("lcsSql","updateTreatment");
        int id = this.lcsDataSourceTemplate.update(sql,
                treatment.getTreatmentName(),
                treatment.getTreatmentDescription(),
                treatment.getPrice(),
                admin.getUsername(),
                treatment.getId());

        Treatment retrievedTreatment = this.getTreatmentById(treatment.getId());

        return retrievedTreatment;
    }

    @Override
    public List<Treatment> getAllTreatments() throws Exception {
        RowMapper<Treatment> mapper = new RowMapper<Treatment>(){
            public Treatment mapRow(ResultSet rs, int rowNum) throws SQLException {

                Treatment treatment = new Treatment(rs);

                return treatment;
            }
        };

        String sql = SQL.get("lcsSql","getAllTreatments");
        List<Treatment> treatments = this.lcsDataSourceTemplate.query(sql,mapper);

        return treatments;
    }

    @Override
    public State getState(String state) throws Exception {
        RowMapper<State> mapper = new RowMapper<State>() {

            public State mapRow(ResultSet rs, int rowNum) throws SQLException {

                State state = new State(rs,false);

                return state;
            }
        };

        String sql = SQL.get("lcsSql","getState");
        List<State> states = this.lcsDataSourceTemplate.query(sql,mapper,state.toString().toUpperCase());

        return states.get(0);
    }

    @Override
    public List<State> getAllStates() throws Exception {
        RowMapper<State> mapper = new RowMapper<State>() {

            public State mapRow(ResultSet rs, int rowNum) throws SQLException {

                State state = new State(rs,false);

                return state;
            }
        };

        String sql = SQL.get("lcsSql","getAllStates");
        List<State> states = this.lcsDataSourceTemplate.query(sql,mapper);

        return states;
    }

    @Override
    public City saveCity(City city) throws Exception {
        String sql = SQL.get("lcsSql","saveCity");
        KeyHolder keyHolder = new GeneratedKeyHolder();//used in order to hold primary key value that's returned after the db insert is performed.
        int id = this.lcsDataSourceTemplate.update(connection -> {
            /* *******************************************************************************************************************************************************
             *  PreparedStatement is not specific to the Spring JDBC Template;
             *  it is part of the default Java Database Connectivity (JDBC) API,  which is a standard part of the Java Standard Edition (SE) platform.
             *
             * PreparedStatement is an interface in the java.sql package and is used to execute precompiled SQL statements.
             *
             * It extends the Statement interface and provides additional methods for setting parameters in a SQL statement.
             *
             * The primary advantage of using PreparedStatement over Statement is that it helps prevent SQL injection attacks.
             *
             * It also can improve performance by allowing the database to reuse previously compiled statements.
             ********************************************************************************************************************************************************/
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, city.getCity());

            return ps;

        }, keyHolder);
        city.setId((Integer) keyHolder.getKey().intValue());

        return city;
    }

    @Override
    public List<City> getCities() throws Exception {
        RowMapper<City> mapper = new RowMapper<City>() {

            public City mapRow(ResultSet rs, int rowNum) throws SQLException {

                City city = new City(rs,false);

                return city;
            }
        };

        String sql = SQL.get("lcsSql","getCities");
        List<City> cities = this.lcsDataSourceTemplate.query(sql,mapper);

        return cities;
    }

    @Override
    public City getCity(City city) throws Exception {
        RowMapper<City> mapper = new RowMapper<City>(){
            public City mapRow(ResultSet rs, int rowNum) throws SQLException {

                City city = new City(rs,false);

                return city;
            }
        };

        String sql = SQL.get("lcsSql","getCity");
        List<City> cities = this.lcsDataSourceTemplate.query(sql,mapper,city.getCity());

        return cities.get(0);
    }

    @Override
    public Admin adminLogin(String username, String password) throws Exception {
        RowMapper<Admin> mapper = new RowMapper<Admin>() {

            public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {

                Admin admin = new Admin(rs);

                return admin;
            }
        };

        String sql = SQL.get("lcsSql","adminLogin");
        List<Admin> adminList = this.lcsDataSourceTemplate.query(sql,mapper,username,password);

        return adminList.get(0);
    }

    @Override
    public Admin saveAdmin(Admin admin) throws Exception {

        String sql = SQL.get("lcsSql","saveAdmin");
        KeyHolder keyHolder = new GeneratedKeyHolder();//used in order to hold primary key value that's returned after the db insert is performed.
        int id = this.lcsDataSourceTemplate.update(connection -> {
            /* *******************************************************************************************************************************************************
             *  PreparedStatement is not specific to the Spring JDBC Template;
             *  it is part of the default Java Database Connectivity (JDBC) API,  which is a standard part of the Java Standard Edition (SE) platform.
             *
             * PreparedStatement is an interface in the java.sql package and is used to execute precompiled SQL statements.
             *
             * It extends the Statement interface and provides additional methods for setting parameters in a SQL statement.
             *
             * The primary advantage of using PreparedStatement over Statement is that it helps prevent SQL injection attacks.
             *
             * It also can improve performance by allowing the database to reuse previously compiled statements.
             ********************************************************************************************************************************************************/
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());
            ps.setString(3, admin.getRole());
            ps.setString(4,admin.getHint());

            return ps;
        }, keyHolder);

        Admin retrievedAdmin = this.getAdminById((Integer) keyHolder.getKey().intValue());

        return retrievedAdmin;

    }

    @Override
    public Admin getAdminById(int id) throws Exception {
        RowMapper<Admin> mapper = new RowMapper<Admin>(){
            public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {

                Admin admin = new Admin(rs);

                return admin;
            }
        };

        String sql = SQL.get("lcsSql","getAdminById");
        List<Admin> admin = this.lcsDataSourceTemplate.query(sql,mapper,id);

        return admin.get(0);
    }

    @Override
    public List<Admin> getAllAdmin() throws Exception {
        RowMapper<Admin> mapper = new RowMapper<Admin>() {

            public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {

                Admin admin = new Admin(rs);

                return admin;
            }
        };

        String sql = SQL.get("lcsSql","getAllAdmin");
        List<Admin> adminList = this.lcsDataSourceTemplate.query(sql,mapper);

        return adminList;
    }

    @Override
    public Admin adminLogin(Admin admin) throws Exception {
        RowMapper<Admin> mapper = new RowMapper<Admin>() {

            public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {

                Admin admin = new Admin(rs);

                return admin;
            }
        };

        String sql = SQL.get("lcsSql","adminLogin");
        List<Admin> adminList = this.lcsDataSourceTemplate.query(sql,mapper,admin.getUsername(),admin.getPassword());

        return adminList.get(0);
    }

    @Override
    public Admin updateAdmin(Admin admin) throws Exception {

        String sql = SQL.get("lcsSql","updateAdmin");
        int id = this.lcsDataSourceTemplate.update(sql,
                admin.getUsername(),
                admin.getPassword(),
                admin.getRole(),
                admin.getHint(),
                admin.getId());

        Admin retrievedObj = this.getAdminById(admin.getId());

        return retrievedObj;

    }


    @Override
    public Client saveClient(Client client, Admin admin) throws Exception {
        String sql = SQL.get("lcsSql","saveClient");
        KeyHolder keyHolder = new GeneratedKeyHolder();//used in order to hold primary key value that's returned after the db insert is performed.
        int id = this.lcsDataSourceTemplate.update(connection -> {
            /* *******************************************************************************************************************************************************
             *  PreparedStatement is not specific to the Spring JDBC Template;
             *  it is part of the default Java Database Connectivity (JDBC) API,  which is a standard part of the Java Standard Edition (SE) platform.
             *
             * PreparedStatement is an interface in the java.sql package and is used to execute precompiled SQL statements.
             *
             * It extends the Statement interface and provides additional methods for setting parameters in a SQL statement.
             *
             * The primary advantage of using PreparedStatement over Statement is that it helps prevent SQL injection attacks.
             *
             * It also can improve performance by allowing the database to reuse previously compiled statements.
             ********************************************************************************************************************************************************/
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getMiddleName());
            ps.setString(3, client.getLastName());
            ps.setString(4, client.getEmail());
            ps.setString(5, client.getPhoneNumber());
            ps.setString(6, admin.getUsername());
            int isActive = client.isActive() ? 1 : 0;
            ps.setInt(7, isActive);

            return ps;
        }, keyHolder);

        Client retrievedClient = this.getClientById((Integer) keyHolder.getKey().intValue());

        return retrievedClient;
    }

    @Override
    public Client updateClient(Client client,Admin admin) throws Exception {
        String sql = SQL.get("lcsSql","updateClient");
        int isActive = client.isActive() ? 1 : 0;
        this.lcsDataSourceTemplate.update(sql,
                client.getFirstName(),
                client.getMiddleName(),
                client.getLastName(),
                client.getEmail(),
                client.getPhoneNumber(),
                admin.getUsername(),
                isActive,
                client.getId());

        Client retrievedClient = this.getClientById(client.getId());

        return retrievedClient;
    }

    @Override
    public Client deleteClient(Client client, Admin admin) throws Exception {
        String sql = SQL.get("lcsSql","deleteClient");
        int isActive = client.isActive() ? 1 : 0;
        this.lcsDataSourceTemplate.update(sql, admin.getUsername(), client.getId());

        Client retrievedClient = this.getClientById(client.getId());

        return retrievedClient;
    }

    @Override
    public Client getClientById(int id) throws Exception {
        RowMapper<Client> mapper = new RowMapper<Client>() {
            public Client mapRow(ResultSet rs, int rowNum) throws SQLException {

                Client client = new Client(rs);

                return client;
            }
        };

        String sql = SQL.get("lcsSql","getClientById");
        List<Client> clients = this.lcsDataSourceTemplate.query(sql,mapper,id);

        return clients.get(0);
    }

    @Override
    public List<Client> getClients() throws Exception {
        RowMapper<Client> mapper = new RowMapper<Client>() {

            public Client mapRow(ResultSet rs, int rowNum) throws SQLException {

                Client client = new Client(rs);

                return client;
            }
        };

        String sql = SQL.get("lcsSql","getClients");
        List<Client> clients = this.lcsDataSourceTemplate.query(sql,mapper);

        return clients;
    }

    @Override
    public void disableOrEnableForeignKeyChecks(boolean disableOrEnable) throws Exception {
        int enableOrDisableValue = disableOrEnable ? 0 : 1;
        String sql = SQL.get("lcsSql","foreignKeyChecks");
        this.lcsDataSourceTemplate.execute(sql);
    }

}
