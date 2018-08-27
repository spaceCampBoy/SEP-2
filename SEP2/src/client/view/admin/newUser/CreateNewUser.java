package client.view.admin.newUser;

import java.rmi.RemoteException;

import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import client.ClientMain;
import client.view.ClientMainView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import shared.Admin;
import shared.Customer;
import shared.DateUtil;
/**
 *
 *
 * @author Tor Jacobsen
 */
public class CreateNewUser {

	 @FXML
	 private JFXTextField firstNameField;
	 @FXML
	 private JFXTextField lastNameField;
	 @FXML
	 private JFXTextField addressField;
	 @FXML
	 private JFXTextField postalCodeField;
	 @FXML
	 private JFXTextField cityField;
	 @FXML
	 private JFXTextField countryField;
	 @FXML
	 private JFXTextField birthdayField;
	 @FXML
	 private JFXTextField phoneField;
	 @FXML
	 private JFXTextField emailField;
	 @FXML
	 private JFXTextField nationalityField;
	 @FXML
	 private JFXTextField cprNoField;
	 @FXML
	 private ChoiceBox<Character> genderField;
	 @FXML
	 private JFXRadioButton customerRadioButton;
	 @FXML
	 private JFXRadioButton adminRadioButton;

	 private ClientMainView viewController;
	 private ClientMain clientController;

	 	/**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    	genderField.setItems(FXCollections.observableArrayList('M', 'F'));
	    }

	    @FXML
	    public void handleCreateNewUser(){

	    	if(inputValid()){

	    		Alert alert = new Alert(AlertType.INFORMATION);
	            alert.initOwner(viewController.getPrimaryStage());

	    		if(customerRadioButton.isSelected()){


	    		Customer customer = new Customer();

	    		customer.setGender(genderField.getSelectionModel().getSelectedItem());
	    		customer.setCPRNo(cprNoField.getText());
	    		customer.setfName(firstNameField.getText());
	    		customer.setlName(lastNameField.getText());
	    		customer.setAddress(addressField.getText());
	    		customer.setPosteCode(Integer.parseInt(postalCodeField.getText()));
	    		customer.setCity(cityField.getText());
	    		customer.setCountry(countryField.getText());
	    		customer.setBirthday(DateUtil.parse(birthdayField.getText()));
	    		customer.setPhoneNumber(phoneField.getText());
	    		customer.setEmail(emailField.getText());
	    		customer.setNationality(nationalityField.getText());

	    		try {
	    			clientController.addCustomer(customer);
	    		} catch (RemoteException e) {
	    			e.printStackTrace();
		            // Show error message.
		            alert.setTitle("Exception");
		            alert.setHeaderText("Exception while adding new customer");
		            alert.setContentText("An exception was thrown while adding the new customer!");
		            alert.showAndWait();
	    			return;
	    		}

	    		alert.setTitle("New Customer Added");
	            alert.setHeaderText("New customer added");
	            alert.setContentText("An new customer was succesfully added!");
	            alert.showAndWait();
	    		}

	    		else if(adminRadioButton.isSelected()){
	    			Admin admin = new Admin();

		    		admin.setGender(genderField.getSelectionModel().getSelectedItem());
		    		admin.setCPRNo(cprNoField.getText());
		    		admin.setfName(firstNameField.getText());
		    		admin.setlName(lastNameField.getText());
		    		admin.setAddress(addressField.getText());
		    		admin.setPosteCode(Integer.parseInt(postalCodeField.getText()));
		    		admin.setCity(cityField.getText());
		    		admin.setCountry(countryField.getText());
		    		admin.setBirthday(DateUtil.parse(birthdayField.getText()));
		    		admin.setPhoneNumber(phoneField.getText());
		    		admin.setEmail(emailField.getText());
		    		admin.setNationality(nationalityField.getText());

		    		try {
		    			clientController.addAdmin(admin);
		    		} catch (RemoteException e) {
		    			e.printStackTrace();
			            // Show error message.
			            alert.setTitle("Exception");
			            alert.setHeaderText("Exception while adding new admin");
			            alert.setContentText("An exception was thrown while adding the new admin!");
			            alert.showAndWait();
		    			return;
		    		}

		    		alert.setTitle("New Admin Added");
		            alert.setHeaderText("New admin added");
		            alert.setContentText("An new admin was succesfully added!");
		            alert.showAndWait();
	    		}
	    		handleReset();
	    	}
	    }

	    @FXML
	    public void handleReset(){
	        // Remove text after user was created.

	        firstNameField.setText("");
	        lastNameField.setText("");
	        cprNoField.setText("");
	        addressField.setText("");
	        postalCodeField.setText("");
	        cityField.setText("");
	        countryField.setText("");
	        birthdayField.setText("");
	   	 	phoneField.setText("");
	   	 	emailField.setText("");
	   	 	nationalityField.setText("");
	    }

	    /**
	     * Is called by the main application to give a reference back to itself.
	     *
	     * @param ClientMainView viewController
	     */
	    public void setViewController(ClientMainView viewController) {
	         this.viewController = viewController;
	    }

	    /**
	     * Is called by the main application to give a reference back to itself.
	     *
	     * @param ClientMainView viewController
	     */
	    public void setClientController(ClientMain clientController) {
	         this.clientController = clientController;
	    }

	    /**
	     * Validates the user input in the text fields.
	     *
	     * @return true if the input is valid
	     */
	    private boolean inputValid() {
	        String errorMessage = "";

	        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
	            errorMessage += "No valid first name!\n";
	        }
	        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
	            errorMessage += "No valid last name!\n";
	        }
	        if (addressField.getText() == null || addressField.getText().length() == 0) {
	            errorMessage += "No valid street!\n";
	        }

	        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
	            errorMessage += "No valid postal code!\n";
	        }

	        if (cityField.getText() == null || cityField.getText().length() == 0) {
	            errorMessage += "No valid city!\n";
	        }

	        if (countryField.getText() == null || countryField.getText().length() == 0) {
	            errorMessage += "No valid country!\n";
	        }

	        if (phoneField.getText() == null || phoneField.getText().length() == 0) {
	            errorMessage += "No valid phone number!\n";
	        }

	        if (emailField.getText() == null || emailField.getText().length() == 0) {
	            errorMessage += "No valid email!\n";
	        }

	        if (nationalityField.getText() == null || nationalityField.getText().length() == 0) {
	            errorMessage += "No valid nationality!\n";
	        }

	        if(genderField.getSelectionModel().getSelectedItem() == null){
	        	 errorMessage += "No valid gender!\n";
	        }

	        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
	            errorMessage += "No valid birthday!\n";
	        } else {
	            if (!DateUtil.validDate(birthdayField.getText())) {
	                errorMessage += "No valid birthday. Use the format dd/mm/yyyy!\n";
	            }
	        }


	        if (errorMessage.length() == 0) {
	            return true;
	        } else {
	            // Show the error message.
	            Alert alert = new Alert(AlertType.INFORMATION);
	            alert.initOwner(viewController.getPrimaryStage());
	            alert.setTitle("Invalid Fields");
	            alert.setHeaderText("Please correct invalid fields");
	            alert.setContentText(errorMessage);
	            alert.showAndWait();
	            return false;
	        }
	    }
}
