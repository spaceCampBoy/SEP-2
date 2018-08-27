package client.view.admin.menu;

import client.ClientMain;
import client.view.ClientMainView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;

public class MenuNavigation {

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
	    public void showCreateNewUser(){
	    	viewController.showCreateNewUser();
	    }

	    @FXML
	    public void showAddAccount(){
	    	viewController.showAddAccount();
	    }

	    @FXML
	    public void showAssignAccToCus(){
	    	viewController.showAssignAccToCus();
	    }

	    public void showSearch(){
	    	viewController.showSearch();
	    }

	    @FXML
	    public void logOut(){
	    	viewController.showLogin();
	    	viewController.adminLogout();
	    }

}