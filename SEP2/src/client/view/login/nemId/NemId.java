package client.view.login.nemId;

import java.rmi.RemoteException;

import com.jfoenix.controls.JFXTextField;

import client.ClientMain;
import client.view.ClientMainView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class NemId {

	@FXML
	private Label keyLabel;
	@FXML
	private JFXTextField valueField;
	@FXML
	private Label wrongValueLabel;

	private String key;
	private String value;
	private Stage dialogStage;
	private ClientMainView viewController;
	private ClientMain clientController;


 	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	value = "-1";
    }

    @FXML
    public void handleSubmit() throws RemoteException {
    	try{
    		value = valueField.getText();
        	dialogStage.close();

    	}catch(NumberFormatException err){
    		wrongValueLabel.setVisible(true);
    	}
    }

    @FXML
    public void handleCancel(){
    	dialogStage.close();
    }

    public String getValue(){
    	return value;
    }


    public void setKey(String key){
    	keyLabel.setText(key);
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        dialogStage.getIcons().add(new Image("file:resources/images/ContentImageHandler.png"));
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
