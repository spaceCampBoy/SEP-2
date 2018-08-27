package client.view.customer.menu;

import client.ClientMain;
import client.view.ClientMainView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class MenuController {

	 private ClientMainView viewController;
	 private ClientMain clientController;

	 /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {}

	    /**
	     * Is called by the main application to give a reference back to itself.
	     *
	     * @param ClientMainView viewController
	     */
	    public void setViewController(ClientMainView viewController) {
	         this.viewController = viewController;
	    }

	    public void setClientController(ClientMain clientController){
	    	this.clientController = clientController;
	    }

	    @FXML
	    public void showHome() {
	    	viewController.showCustomerHome();
	    }

	    @FXML
	    public void showTrasferMoney() {
	    	viewController.showTransferMoney();
	    }

	    @FXML
	    public void showContacts() {
	    	viewController.showContacts();
	    }

	    @FXML
	    public void showSettings() {
	    	viewController.showSettings();
	    }

	    @FXML
	    public void logoff(){
	    	clientController.deleteCustomerData();
	    	viewController.showLogin();
	    	viewController.customerLogout();
	    }
}
