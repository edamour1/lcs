package com.warner.lcs.app.controller;

import com.warner.lcs.app.domain.*;
import com.warner.lcs.app.domain.table_ui.StateStringConverter;
import com.warner.lcs.app.service.LcsService;
import com.warner.lcs.common.util.FxmlView;
import com.warner.lcs.common.util.SceneController;
import com.warner.lcs.common.util.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.controlsfx.control.textfield.AutoCompletionBinding;



import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.paint.Color;

@Component
public class AddressRegisterController implements Initializable {

    private Client client;
    private Admin admin;
    private Address address;
    private City city;
    private State state;
    private Zipcode zipcode;
    private List<City> cities;
    private List<State> states;
    private List<Zipcode> zipcodes;
    private List<Unit> units;
    private Unit unit;
    private double qty;

    private boolean isBilling;
    private FxmlView CLIENT_MENU, CLIENT_VIEW;

    @Autowired
    private LcsService lcsService;
    @Autowired
    private SceneController sceneController;

    @FXML
    private TextField streetTextField;

    @FXML
    private ChoiceBox unitComboBox;

    @FXML
    private TextField cityTextField;

    @FXML
    private ChoiceBox<State> stateComboBox;

    @FXML
    private TextField zipcodeTextField;

    @FXML
    private CheckBox billingCheckBox;

    @FXML
    private TextField quantityTextField;

    @FXML
    private Label streetErrorLabel;

    @FXML
    private Label cityErrorLabel;

    @FXML
    private Label zipcodeErrorLabel;

    @FXML
    private Label quantityErrorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        this.qty = 0.0;
        this.CLIENT_MENU = FxmlView.CLIENT_MENU;
        this.CLIENT_VIEW = FxmlView.CLIENT_VIEW;
        try {
            this.stateComboBox.setConverter(new StateStringConverter());
            this.cities = this.lcsService.getCities();
            this.states = this.lcsService.getAllStates();
            this.zipcodes = this.lcsService.getAllZipcodes();

            for(State state : this.states) {
                   this.state = state;
                stateComboBox.getItems().add(state);
            }

            stateComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    this.state = newValue;
                    System.out.println("Selected item: " + this.state.getId()+", "+this.state.getState());
                    // Do something with the selected item
                }
            });

            // Set default value for the ChoiceBox
            if (!states.isEmpty()) {
                for(State s : this.states)
                {
                    if(s.getState().equals("GA"))
                    {
                        stateComboBox.setValue(s); // Assuming the first state is the default
                    }
                }
            }

            // Populate the city names
            List<String> cityNames = cities.stream().map(City::getCity).collect(Collectors.toList());

            AutoCompletionBinding<String> binding = TextFields.bindAutoCompletion(cityTextField, cityNames);

            // Set the converter to get corresponding City object when a city is selected
            binding.setOnAutoCompleted(event -> {
                String cityName = event.getCompletion();
                AutoCompletionBinding<String> zipBinding;
                List<String> zipcodeNames;
                    for (City city : cities) {
                        if (city.getCity().equals(cityName)) {
                            this.city = city;
                            System.out.println(this.city.getId()+" "+this.city.getCity());
                            try {
                                this.zipcodes = this.lcsService.getZipcodesByCity(this.city.getCity());
                                this.getZipcodesByCity(cityName);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(this.zipcodes.toString());
                            break;
                        }
                    }
            });

            // Add a listener to handle changes when the text field loses focus
            cityTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                String cityText = cityTextField.getText();
                if (!newValue) { // If the field lost focus
                    if(!cityNames.contains(cityText))
                    {
                        this.city = this.city == null ? new City() : this.city;
                        this.city.setId(0);
                        this.city.setCity(cityTextField.getText());
                        this.zipcode.setId(0);
                        this.zipcode.setZipcode(this.zipcodeTextField.getText());
                        this.zipcodes.clear();

                    }


                    // Do something here, for example:
                    System.out.println("City text field lost focus.");
                    System.out.println(this.city.getId()+" "+this.city.getCity());
                }

            });

            // Populate the city names
            List<String> zipcodesStrings = zipcodes.stream().map(Zipcode::getZipcode).collect(Collectors.toList());
            // Add a listener to handle changes when the text field loses focus
            zipcodeTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) { // If the field lost focus
                    if(this.states != null)
                    {
                        if(!zipcodesStrings.contains(zipcodeTextField.getText()))
                        {
                            this.zipcode.setId(0);
                            this.zipcode.setZipcode(this.zipcodeTextField.getText());
                            this.zipcodes.clear();
                        }
                    }

                    // Do something here, for example:
                    System.out.println("Zipcode text field lost focus.");
                    System.out.println(this.zipcode.getId()+" "+this.zipcode.getZipcode());
                }
            });

            // Set a custom string converter to display enum's full name
            unitComboBox.setConverter(new StringConverter<Unit>() {
                @Override
                public String toString(Unit unit) {
                    return unit.getFullName();
                }

                @Override
                public Unit fromString(String s) {
                    // Not needed for this example
                    return null;
                }
            });

            this.unit = Unit.SQUARE_FEET;

            // Get the values of the enum and convert them to an observable list
            ObservableList<Unit> options = FXCollections.observableArrayList(Unit.values());

            unitComboBox.setItems(options);

            unitComboBox.setValue(Unit.SQUARE_FEET);

            unitComboBox.valueProperty().addListener((observable, oldValue, newValue) ->{

                for(Unit currentUnit : Unit.values()){
                    if(currentUnit.toString().toLowerCase().equals(newValue.toString().toLowerCase()))
                    {
                        this.unit = currentUnit;
                        System.out.println(this.unit);
                        break;
                    }
                }
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.billingCheckBox.selectedProperty().addListener((observable, oldValue, newValue)->{
            this.isBilling = this.billingCheckBox.isSelected();
            System.out.println(this.isBilling);
        });

        this.quantityTextField.setText(Double.toString(this.qty));
        this.quantityTextField.focusedProperty().addListener((ob,oldValue,newVallue)->{
            if(newVallue){
                this.qty = Double.parseDouble(this.quantityTextField.getText());
                System.out.println(this.qty);
            }
        });

    }

    private void getZipcodesByCity(String city){
        List<String> zipcodeNames;
        AutoCompletionBinding<String> zipBinding;
        try {
            this.zipcodes = this.lcsService.getZipcodesByCity(city);
            zipcodeNames = zipcodes.stream().map(Zipcode::getZipcode).collect(Collectors.toList());
            zipBinding = TextFields.bindAutoCompletion(zipcodeTextField, zipcodeNames);
            zipBinding.setOnAutoCompleted(event -> {
                String zipcodeString = event.getCompletion();
                for(Zipcode z : zipcodes) {
                    if(z.getZipcode().equals(zipcodeString))
                    {
                        this.zipcode = z;
                        System.out.println(this.zipcode.getId()+" "+this.zipcode.getZipcode());
                    }
                }

            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void initData(Client client, Admin admin) {
        this.client = client;
        this.admin = admin;
    }

    @FXML
    private void increaseQuantity() {
        // Increase quantity logic
        this.qty++;
        this.quantityTextField.setText(Double.toString(this.qty));
        System.out.println(this.qty);
    }

    @FXML
    private void decreaseQuantity() {
        // Decrease quantity logic
        this.qty--;
        this.quantityTextField.setText(Double.toString(this.qty));
        System.out.println(this.qty);
    }

    @FXML
    private void handleSubmit(ActionEvent event) throws Exception {
        String street = this.streetTextField.getText(), city = this.cityTextField.getText(), zipcode = this.zipcodeTextField.getText(), qty = this.quantityTextField.getText();
        System.out.println(qty);
        if(street.isEmpty())
        {
            showError(this.streetTextField,"Street must not be empty. Example: 9322 Melody Circle sw",this.streetErrorLabel);
            return;
        } else { this.clearError(this.streetTextField,this.streetErrorLabel); }

        if(city.isEmpty())
        {
            showError(this.cityTextField,"City must not be empty. Example: Atlanta",this.cityErrorLabel);
            return;
        } else { clearError(this.cityTextField,this.cityErrorLabel); }

        if(zipcode.isEmpty())
        {
            showError(this.zipcodeTextField,"Zipcode must not be empty. Example: 30014",this.zipcodeErrorLabel);
            return;
        } else { clearError(this.zipcodeTextField,this.zipcodeErrorLabel); }

        if(qty.equals("0.0"))
        {
            showError(this.quantityTextField,"Quantity must not be empty. Example: 3.0",this.quantityErrorLabel);
            return;
        } else { clearError(this.quantityTextField,this.quantityErrorLabel); }

        Address saveAddress = new Address();
        saveAddress.setStreet(this.streetTextField.getText());
        saveAddress.setCity(this.city);
        saveAddress.setState(this.state);
        saveAddress.setZipcode(this.zipcode);
        saveAddress.setBilling(this.isBilling);
        saveAddress.setQuantity(this.qty);
        saveAddress.setUnit(this.unit.toString());
        saveAddress.setActive(true);
        this.lcsService.saveAddress(saveAddress,this.client,this.admin);
        this.sceneController.setScene(this.CLIENT_VIEW.getTitle(),this.CLIENT_VIEW.getFxmlFilePath());
        this.sceneController.switchToScene(event);
        System.out.println("Submit button clicked");
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        this.sceneController.setScene(this.CLIENT_VIEW.getTitle(), this.CLIENT_VIEW.getFxmlFilePath());
        this.sceneController.switchToScene(event);
        System.out.println("Back button clicked");
    }

    private void showError(TextField textField, String errorMessage, Label errorLabel) {
        // Apply CSS style to indicate error
        textField.getStyleClass().add("invalid-text-field");

        // Display error message
        // You can choose how to display the error message (e.g., tooltip, label)
        // Here, we'll set the prompt text to the error message temporarily
        textField.setPromptText(errorMessage);

        // Show error message
        errorLabel.setText(errorMessage);
    }

    private void clearError(TextField textField,Label errorLabel) {
        // Clear CSS style to indicate no error
        textField.getStyleClass().remove("invalid-text-field");
        textField.setPromptText(null);
        // Clear error message
        errorLabel.setText("");
    }

}