package client.view.admin.addAccount;

import java.rmi.RemoteException;

import com.jfoenix.controls.JFXTextField;

import client.ClientMain;
import client.view.ClientMainView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import shared.Account;
import shared.DateUtil;

public class AddAccount {

	 @FXML
	 private JFXTextField cprNoField;
	 @FXML
	 private JFXTextField creditLimitField;
	 @FXML
	 private JFXTextField transferLimitField;
	 @FXML
	 private JFXTextField accountNameField;

	 private ClientMainView viewController;
	 private ClientMain clientController;


	 	/**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	    }


	    @FXML
	    public void handleAddAccount(){

	    	Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(viewController.getPrimaryStage());

	    	if(inputValid()){

	    	Account account = new Account();

	    	account.setRegNo("9522");
	    	account.setAccNo(Integer.toString(generateAccNo()));
	    	account.setCreditLimit(Integer.parseInt(creditLimitField.getText()));
	    	account.setTransferLimit(Integer.parseInt(transferLimitField.getText()));
	    	account.setName(accountNameField.getText());

	    	try {
				clientController.newAccount(account, cprNoField.getText());
			} catch (RemoteException e) {
				e.printStackTrace();
				// Show error message.
	            alert.setTitle("Exception");
	            alert.setHeaderText("Exception while adding new account");
	            alert.setContentText("An exception was thrown while adding the new account!");
	            alert.showAndWait();
    			return;
    		}
	    	alert.setTitle("New account assiged to customer");
            alert.setHeaderText("New account assiged to customer");
            alert.setContentText("An new account assiged to customer!");
            alert.showAndWait();

            System.out.println(account);

            handleReset();
	    	}
	    }

	    @FXML
	    public void handleReset(){
	    	cprNoField.setText("");
	    	accountNameField.setText("");
	    	creditLimitField.setText("");
	    	transferLimitField.setText("");
	    }

	    public int generateAccNo(){
	    	return (int) Math.floor(Math.random() * 2147483647);
	    }


	    /**
	     * Validates the user input in the text fields.
	     *
	     * @return true if the input is valid
	     */
	    private boolean inputValid() {
	        String errorMessage = "";

	        if (cprNoField.getText() == null || cprNoField.getText().length() == 0 || cprNoField.getText().length() > 11) {
	            errorMessage += "No valid cprNo!\n";
	        }
	        if (creditLimitField.getText() == null || creditLimitField.getText().length() == 0) {
	            errorMessage += "No valid last name!\n";
	        }
	        if (transferLimitField.getText() == null || transferLimitField.getText().length() == 0) {
	            errorMessage += "No valid street!\n";
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
}
