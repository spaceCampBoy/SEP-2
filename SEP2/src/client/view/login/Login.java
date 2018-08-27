package client.view.login;

import java.rmi.RemoteException;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import client.ClientMain;
import client.view.ClientMainView;
import client.view.login.nemId.NemId;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import shared.Admin;
import shared.Customer;

public class Login {

	@FXML
	private JFXTextField userNameField;
	@FXML
	private JFXPasswordField passwordField;
	@FXML
	private JFXRadioButton customerSelection;
	@FXML
	private JFXRadioButton adminSelection;
	@FXML
	private Label loginFailed;

	private ClientMainView viewController;
	private ClientMain clientController;

	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	loginFailed.setVisible(false);
    }


    @FXML
    public void handleLogin() {

    	String key = "-1";
    	String value = "-1";
    	String userName = userNameField.getText();
    	String password = passwordField.getText();
    	Object obj = null;
    	int check = 0;

    	try {
			key = clientController.login(userName, password);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

    	if(key == null){
    		loginFailed.setVisible(true);
    		loginFailed.setText("Wrong user name or password");
    	}

    	else{
    		value = viewController.showNemIdDialog(key);

    		if(adminSelection.isSelected()){
    			obj = new Admin();
    		}
    		if(customerSelection.isSelected()){
    			obj = new Customer();
    		}
    		try {
				check = clientController.checkValue(userName, password, key, value, obj);
			} catch (RemoteException e) {
				e.printStackTrace();
			}

    		if(check == 1){
    			loginFailed.setVisible(true);
        		loginFailed.setText("Wrong nem-id");
    		}else if(check == 2){
    			viewController.showCustomerMenu();
    			viewController.showBanner();
    			viewController.showCustomerHome();
    		}else if(check == 3){
    			viewController.showMenuNavigation();
    			viewController.showCreateNewUser();
    			viewController.showBanner();

    		}else{
    			loginFailed.setVisible(true);
        		loginFailed.setText("Login failed!");
    		}
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
