package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.Address;
import com.warner.lcs.app.domain.Admin;
import com.warner.lcs.app.domain.Client;
import com.warner.lcs.app.domain.InvoiceInformation;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.InvoiceNumberGenerator;
import com.warner.lcs.common.util.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class InvoiceRegisterController implements Initializable {

    private Admin admin;
    private Client client, billedClient;
    private List<Client> clients;
    private Address address;
    private List<Address> addresses;
    private InvoiceInformation invoiceInformation;
    private InvoiceNumberGenerator invoiceNumberGenerator;
    @Autowired
    private LcsService lcsService;
    @Autowired
    private SceneController sceneController;
    @FXML
    private AnchorPane anchorPane;

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

    @FXML
    private Text streetParagraph;

    @FXML
    private Text cityParagraph;

    @FXML
    private Text stateParagraph;

    @FXML
    private Text zipcodeParagraph;

    @FXML
    private DatePicker paymentDueDatePicker;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField billingClientComboBox;

    @FXML
    private ComboBox<String> billingAddressComboBox;

    @FXML
    private ComboBox<String> treatmentComboBox;

    @FXML
    private ComboBox<String> additionalCostServiceComboBox;

    @FXML
    private ComboBox<String> addressComboBox;

    @FXML
    private TextField inputTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.clients = this.lcsService.getClients();
            List<String> clientsNamesList = new ArrayList<>();
            for(Client c : this.clients)
            {
                clientsNamesList.add(c.getFirstName()+", "+c.getMiddleName()+", "+c.getLastName());
            }
            String[] possibleWords = clientsNamesList.toArray(new String[0]);
            TextFields.bindAutoCompletion(billingClientComboBox,possibleWords);

            billingClientComboBox.textProperty().addListener((observable, oldValue, newValue) -> {
                String firstName = "", middlName = "", lastName = "";
                if(clientsNamesList.contains(newValue))
                {
                    StringBuilder sb = new StringBuilder();

                    int nameCount = 0;
                    String x;
                    newValue = newValue+",";
                    for(char c : newValue.toCharArray())
                    {
                        sb.append(c);
                        x = sb.toString();
                        if(c == ',')
                        {
                            nameCount++;

                            switch (nameCount) {

                                case 1 :
                                    firstName = sb.toString().substring(0,sb.length()-1).trim();
                                    // Clear the contents of the StringBuilder
                                    sb.setLength(0);
                                    break;

                                case 2 :
                                    middlName = sb.toString().substring(0,sb.length()-1).trim();
                                    // Clear the contents of the StringBuilder
                                    sb.setLength(0);
                                    break;

                                default:
                                    lastName = sb.toString().substring(0,sb.length()-1).trim();
                                    // Clear the contents of the StringBuilder
                                    sb.setLength(0);
                                    break;
                            }
                        }
                    }

                    // newValue contains the currently entered text or the selected suggestion
                    System.out.println("Selected: " + newValue);
                    // You can now use newValue as needed, such as updating other fields based on the selection

                    for(Client c : this.clients)
                    {
                        if(c.getFirstName().toLowerCase().equals(firstName.toLowerCase())
                                && c.getMiddleName().toLowerCase().equals(middlName.toLowerCase())
                                && c.getLastName().toLowerCase().equals(lastName.toLowerCase())
                        )
                        {
                            this.billedClient = c;
                            try {
                                this.addresses = this.lcsService.getAddressesByClientId(this.billedClient.getId());
                                for(Address a : this.addresses)
                                {
                                    if(a.isBilling())
                                    {
                                        this.address = a;
                                    }
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println("Selected: " + this.client.getId()+" name: "+c.getFirstName()+" "+this.address.getStreet());
                        }
                    }

                }

            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Initialize paragraph tags with client information
        if (client != null) {
            clientFirstNameParagraph.setText(client.getFirstName());
            clientLastNameParagraph.setText(client.getLastName());
            clientMiddleNameParagraph.setText(client.getMiddleName());
            clientPhoneNumberParagraph.setText(client.getPhoneNumber());
            clientEmailParagraph.setText(client.getEmail());
        }
    }

    void initData(Client client, Admin admin) {
        this.admin = admin;
        this.client = client;
    }
}
