package client.view.admin.assignAccToCus;

import java.rmi.RemoteException;

import com.jfoenix.controls.JFXTextField;

import client.ClientMain;
import client.view.ClientMainView;
import javafx.fxml.FXML;

public class AccountToCustomer {

	 @FXML
	 private JFXTextField cprNoField;
	 @FXML
	 private JFXTextField AccNoField;
	 @FXML
	 private JFXTextField RegNoField;

	 private ClientMainView viewController;
	 private ClientMain clientController;


 	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    public void handleSubmit(){
    	try {
			clientController.assignAccountToCustomer(cprNoField.getText(), AccNoField.getText(), RegNoField.getText());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void handleReset(){
    	cprNoField.setText("");
    	AccNoField.setText("");
    	RegNoField.setText("");
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
