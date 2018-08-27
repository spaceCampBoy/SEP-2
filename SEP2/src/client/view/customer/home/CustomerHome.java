package client.view.customer.home;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import client.ClientMain;
import client.view.ClientMainView;
import client.view.customer.home.accountView.Accounts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import shared.Customer;

public class CustomerHome  {

	@FXML
	private JFXListView<AnchorPane> listView;
	@FXML
	private JFXButton updateButton;


	private ClientMainView viewController;
	private ClientMain clientController;
	private Customer customer;

	@FXML
	public void initialize() throws IOException {
		listView.depthProperty().set(5);
        listView.setExpanded(true);
        listView.setVerticalGap(8.0);
	}

	public void showTransactions(int index){
		viewController.showTransactions(customer.getAccountAtIndex(index));
	}

	public void setListView() throws IOException{
		listView.getItems().clear();
		for(int i=0; i<customer.getAccountSize(); i++){

			 FXMLLoader loader = new FXMLLoader();
	         loader.setLocation(CustomerHome.class.getResource("accountView/Accounts.fxml"));
	         AnchorPane account = (AnchorPane) loader.load();

	         //Give the create new user controller access to the client main class and this class.
	         Accounts controller = loader.getController();
	         controller.setController(this);
	         controller.setAccount(customer.getAccountAtIndex(i));
	         controller.setIndex(i);

	         if(customer.getAccountAtIndex(i).getSharedWith().size() != 0){
	        	 controller.showSharedWith(customer.getAccountAtIndex(i).getSharedWith());
	         }

	         listView.getItems().add(account);
		}
	}

	public void handleUpdate(){
		try {
			clientController.updateCustomerData();
		} catch (RemoteException e){
			e.printStackTrace();
		}
		//viewController.showCustomerHome();
		setCustomer();
		try {
			setListView();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCustomer(){
		this.customer = clientController.getCustomerData();
	}

	public void setCustomer(Customer customer){
		this.customer = customer;
		updateButton.setVisible(false);
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
