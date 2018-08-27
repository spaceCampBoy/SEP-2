package client.view.admin.search;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXTextField;

import client.ClientMain;
import client.view.ClientMainView;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import shared.Customer;
import shared.DateUtil;

public class Search {

	@FXML
	private TableView<Customer> tableView;
    @FXML
    private TableColumn<Customer, String> firstNameColumn;
    @FXML
    private TableColumn<Customer, String> lastNameColumn;
	@FXML
	private JFXTextField searchField;
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
	 private ChoiceBox<Character> genderField;

	 private Customer customer;

	 private ClientMainView viewController;
	 private ClientMain clientController;


	@FXML
	private void initialize(){
		genderField.setItems(FXCollections.observableArrayList('M', 'F'));

		// Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getfName()));
        lastNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getlName()));

     // Listen for selection changes and show the person details when changed.
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showCustomerDetails(newValue));
	}


	@FXML
	public void handleSearch(){
		ArrayList<Customer> customers = null;
		if(searchField.getText() != null && !searchField.getText().equals("")){
			try {
				customers = clientController.getCustomerWithLastname(searchField.getText());
			} catch (RemoteException e) { e.printStackTrace();
											return;}


			ObservableList<Customer> observableList = FXCollections.observableArrayList(customers);
			tableView.setItems(observableList);

		}else{
			// Show the error message.
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(viewController.getPrimaryStage());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText("Search field can't be empty!");
            alert.showAndWait();
		}

	}

	public void showCustomerDetails(Customer customer){
		 if (customer != null) {
			this.customer = customer;
			firstNameField.setText(customer.getfName());
			lastNameField.setText(customer.getlName());
			cityField.setText(customer.getCity());
			addressField.setText(customer.getAddress());
			emailField.setText(customer.getEmail());
			phoneField.setText(customer.getPhoneNumber());
			countryField.setText(customer.getCountry());
			postalCodeField.setText(Integer.toString(customer.getPostalCode()));
			genderField.setValue(customer.getGender());
			birthdayField.setText(DateUtil.format(customer.getBirthday()));
			nationalityField.setText(customer.getNationality());
		 }
	}

	@FXML
	public void handleShowAccounts(){
		viewController.showCustomerHome(customer);
	}

	@FXML
	public void handleChange(){

		if(inputValid()){

			customer.setfName(firstNameField.getText());
			customer.setlName(lastNameField.getText());
			customer.setCity(cityField.getText());
			customer.setAddress(addressField.getText());
			customer.setEmail(emailField.getText());
			customer.setPhoneNumber(phoneField.getText());
			customer.setCountry(countryField.getText());
			customer.setPosteCode(Integer.parseInt(postalCodeField.getText()));
			customer.setBirthday(DateUtil.parse(birthdayField.getText()));
			customer.setNationality(nationalityField.getText());
			customer.setGender(genderField.getValue());


			try {
				clientController.changeCustomer(customer);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}

			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.initOwner(viewController.getPrimaryStage());
			alert.setTitle("Information updated");
	        alert.setHeaderText("Information updated");
	        alert.setContentText("Information updated!");
	        alert.showAndWait();

			handleSearch();
		}


	}

	@FXML
	public void handleReset(){
		 if (customer != null) {
			firstNameField.setText(customer.getfName());
			lastNameField.setText(customer.getlName());
			cityField.setText(customer.getCity());
			addressField.setText(customer.getAddress());
			emailField.setText(customer.getEmail());
			phoneField.setText(customer.getPhoneNumber());
			countryField.setText(customer.getCountry());
			postalCodeField.setText(Integer.toString(customer.getPostalCode()));
			genderField.setValue(customer.getGender());
			birthdayField.setText(DateUtil.format(customer.getBirthday()));
			nationalityField.setText(customer.getNationality());
		 }
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
            errorMessage += "No valid email!\n";
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