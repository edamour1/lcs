package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.domain.table_ui.AddressTableData;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.warner.lcs.common.util.TableUtil.createTableColumn;
import static com.warner.lcs.common.util.TableUtil.getObservableList;

@Component
public class ClientViewController implements Initializable {

    private FxmlView CLIENT_MENU, ADDRESS_VIEW, ADDRESS_REGISTER, ADDRESS_UPDATE, ADDRESS_DELETE;
    private Client client;
    private AddressTableData selectedAddress;
    private Address address;

    @Autowired
    private LcsService lcsService;

    @Autowired
    private SceneController sceneController;
    @Autowired
    private AddressViewController addressViewController;
    @Autowired
    private AddressRegisterController addressRegisterController;
    @Autowired
    private AddressUpdateController addressUpdateController;

    @Autowired
    private AddressDeleteController addressDeleteController;

    private Admin admin;

    private List<Address> addresses;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<AddressTableData> tableView;

    // Paragraph tags corresponding to client information
    @FXML
    private Text clientFirstNameParagraph;

    @FXML
    private Text clientLastNameParagraph;

    @FXML
    private Text clientMiddleNameParagraph;

    @FXML
    private Text clientPhoneNumberParagraph;

    @FXML
    private Text clientEmailParagraph;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
        this.ADDRESS_REGISTER = FxmlView.ADDRESS_REGISTER;
        this.ADDRESS_VIEW = FxmlView.ADDRESS_VIEW;
        this.ADDRESS_UPDATE = FxmlView.ADDRESS_UPDATE;
        this.ADDRESS_DELETE = FxmlView.ADDRESS_DELETE;

        // Initialize paragraph tags with client information
        if (client != null) {
            clientFirstNameParagraph.setText(client.getFirstName());
            clientLastNameParagraph.setText(client.getLastName());
            clientMiddleNameParagraph.setText(client.getMiddleName());
            clientPhoneNumberParagraph.setText(client.getPhoneNumber());
            clientEmailParagraph.setText(client.getEmail());
        }
        try {
            this.addresses = this.lcsService.getAddressesByClientId((int) this.client.getId());

            if(!addresses.isEmpty())
            {
                List<AddressTableData> list = new ArrayList<>();
                for(Address address : addresses)
                {
                    list.add(new AddressTableData(Integer.valueOf(address.getId()),
                            address.getStreet(),
                            address.getUnit(),
                            address.getCity().getCity(),
                            address.getState().getState(),
                            address.getZipcode().getZipcode(),
                            address.isBilling() ,
                            address.getQuantity()));
                }

                // Converting the list of strings to an observable list of strings
                ObservableList<AddressTableData> observableStringList = getObservableList(list);

                TableColumn<AddressTableData, Integer> idColumn = createTableColumn("Address ID", "id");
                TableColumn<AddressTableData, String> streetColumn = createTableColumn("Street", "street");
                TableColumn<AddressTableData, String> cityColumn = createTableColumn("City", "city");
                TableColumn<AddressTableData, String> stateColumn = createTableColumn("State", "state");
                TableColumn<AddressTableData, String> zipcodeColumn = createTableColumn("Zipcode", "zipcode");
                TableColumn<AddressTableData, String> unitColumn = createTableColumn("Unit", "unit");
                TableColumn<AddressTableData, Double> quantityColumn = createTableColumn("Quantity", "quantity");
                TableColumn<AddressTableData, Boolean> billingColumn = createTableColumn("Billing", "isBilling");

                // Create a TableView with a list of addresses
                tableView.setItems(observableStringList);

                // Create a TableView with a list of persons
                tableView.getColumns().addAll(streetColumn,cityColumn,stateColumn,zipcodeColumn);
            }

            // Add listener to handle row selection
            tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    this.selectedAddress = newSelection;
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Method to handle the "View Address" button action
    @FXML
    private void viewAddress(ActionEvent event) throws Exception{
        Address viewAddress = new Address();

        for(Address address : this.addresses) {
            if(address.getId() == this.selectedAddress.getId())
            {
                viewAddress = address;
            }
        }
        if (selectedAddress != null) {
            this.addressViewController.initData(this.client,this.admin,viewAddress);
            // Add your logic to view the selected client
            this.sceneController.setScene(this.ADDRESS_VIEW.getTitle(), this.ADDRESS_VIEW.getFxmlFilePath());
            this.sceneController.switchToScene(event);
        } else {
            System.out.println("No client selected.");
        }
    }
//
    @FXML
    private void updateAddress(ActionEvent event) throws Exception {
        Address updateAddress = new Address();

        for(Address address : this.addresses) {
            if(address.getId() == this.selectedAddress.getId())
            {
                updateAddress = address;
            }
        }

        // Your logic for updating client
        this.addressUpdateController.initData(this.client,this.admin,updateAddress);
        this.sceneController.setScene(this.ADDRESS_UPDATE.getTitle(),this.ADDRESS_UPDATE.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @FXML
    private void deleteAddress(ActionEvent event) throws Exception {
        Address deleteAddress = new Address();

        for(Address address : this.addresses) {
            if(address.getId() == this.selectedAddress.getId())
            {
                deleteAddress = address;
            }
        }

        this.addressDeleteController.initData(this.client,this.admin,deleteAddress);

        // Your logic for deleting client
        this.sceneController.setScene(this.ADDRESS_DELETE.getTitle(),this.ADDRESS_DELETE.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    @FXML
    private void registerAddress(ActionEvent event) throws Exception {
        if(this.selectedAddress != null) {
            City city = new City();
            city.setCity(this.selectedAddress.getCity());
            State state = new State();
            state.setState(selectedAddress.getState());
            Zipcode zipcode;
            zipcode = new Zipcode();
            zipcode.setZipcode(selectedAddress.getZipcode());

            city = this.lcsService.getCity(city);
            state = this.lcsService.getState(selectedAddress.getState());
            zipcode = this.lcsService.getZipcode(selectedAddress.getZipcode());
            Address registerAddress = new Address();
            registerAddress.setStreet(selectedAddress.getStreet());
            registerAddress.setCity(city);
            registerAddress.setState(state);
            registerAddress.setZipcode(zipcode);
        }

        // Your logic for registering client
        this.addressRegisterController.initData(this.client,this.admin);
        this.sceneController.setScene(this.ADDRESS_REGISTER.getTitle(),this.ADDRESS_REGISTER.getFxmlFilePath());
        this.sceneController.switchToScene(event);
    }

    void initData(Client client, Admin admin) {
        this.admin = admin;
        this.client = client;
    }
}