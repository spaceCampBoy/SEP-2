package client.view.customer.settings;

import java.rmi.RemoteException;

import com.jfoenix.controls.JFXTextField;

import client.ClientMain;
import client.view.ClientMainView;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import shared.Account;
import shared.Customer;
import shared.DateUtil;

public class Settings {

	@FXML
	private JFXTextField firstNameField;
	@FXML
	private JFXTextField lastNameField;
	@FXML
	private JFXTextField cityField;
	@FXML
	private JFXTextField addressField;
	@FXML
	private JFXTextField emailField;
	@FXML
	private JFXTextField phoneField;
	@FXML
	private JFXTextField countryField;
	@FXML
	private JFXTextField postalCodeField;
	@FXML
	private JFXTextField accountNameField;
	@FXML
	private ChoiceBox<String> choiceBox;

	private ClientMain clientController;
	private ClientMainView viewController;
	private Customer customer;

	@FXML
	public void initialize(){}


	@FXML
	public void handleSave(){

		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(viewController.getPrimaryStage());

        Account account;

		if(inputValid()){
		if(accountNameField.getText() == null || accountNameField.getText().length() != 0){
			account = customer.getAccountAtIndex(choiceBox.getSelectionModel().getSelectedIndex());
			account.setName(accountNameField.getText());
			try {
				clientController.changeAccountName(account);
			} catch (Exception e) {
				e.printStackTrace();
				alert.setTitle("Exception");
	            alert.setHeaderText("Exception while changing account name");
	            alert.setContentText("Exception while updating information!");
	            alert.showAndWait();
	            return;
			}
		}

		if(hasChanged()){
				customer.setfName(firstNameField.getText());
				customer.setlName(lastNameField.getText());
				customer.setCity(cityField.getText());
				customer.setAddress(addressField.getText());
				customer.setEmail(emailField.getText());
				customer.setPhoneNumber(phoneField.getText());
				customer.setCountry(countryField.getText());
				customer.setPosteCode(Integer.parseInt(postalCodeField.getText()));

				try {
					clientController.changeCustomer(customer);
				} catch (RemoteException e) {
					e.printStackTrace();
					e.printStackTrace();
					alert.setTitle("Exception");
		            alert.setHeaderText("Exception while updating information");
		            alert.setContentText("Exception while updating information!");
		            alert.showAndWait();
		            return;
				}
			}
		alert.setTitle("Information updated");
        alert.setHeaderText("Information updated");
        alert.setContentText("Information updated!");
        alert.showAndWait();
		handleReset();
		}
	}

	@FXML
	public void handleReset(){
		firstNameField.setText(customer.getfName());
		lastNameField.setText(customer.getlName());
		cityField.setText(customer.getCity());
		addressField.setText(customer.getAddress());
		emailField.setText(customer.getEmail());
		phoneField.setText(customer.getPhoneNumber());
		countryField.setText(customer.getCountry());
		postalCodeField.setText(Integer.toString(customer.getPostalCode()));
		accountNameField.setText("");

		choiceBox.getItems().clear();
		for(int i=0; i<customer.getAccountSize(); i++){
			choiceBox.getItems().add(customer.getAccountAtIndex(i).getName());
		}
	}

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


	public boolean hasChanged(){
		return (!firstNameField.getText().equals(customer.getfName()) ||  !lastNameField.getText().equals(customer.getlName()) ||
				!cityField.getText().equals(customer.getCity()) || !addressField.getText().equals(customer.getAddress()) ||
				!emailField.getText().equals(customer.getEmail()) || !phoneField.getText().equals(customer.getPhoneNumber()) ||
				!countryField.getText().equals(customer.getCountry()) || Integer.parseInt(postalCodeField.getText()) != customer.getPostalCode());
	}

	public void setCustomer(){
		this.customer = clientController.getCustomerData();
		handleReset();
	}

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

}
