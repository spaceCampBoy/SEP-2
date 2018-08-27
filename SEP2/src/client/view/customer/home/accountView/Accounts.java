package client.view.customer.home.accountView;


import java.util.ArrayList;

import client.view.customer.home.CustomerHome;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import shared.Account;
import shared.Customer;

public class Accounts {

	@FXML
	private Label regNoLabel;
	@FXML
	private Label nameLabel;
	@FXML
	private Label balanceLabel;
	@FXML
	private ChoiceBox<String> sharedWithBox;
	@FXML
	private Label sharedWithLabel;

	private int index;
	private CustomerHome controller;

	@FXML
	public void initialize() {
		sharedWithBox.setVisible(false);
		sharedWithLabel.setVisible(false);
	}

	@FXML
	public void handleShow(){
		controller.showTransactions(index);
	}

	public void setAccount(Account account){
		regNoLabel.setText(account.getRegNo() + "   " + account.getAccNo());
		nameLabel.setText(account.getName());
		balanceLabel.setText(Double.toString(account.getBalance()) + " DKK");
	}

	public void setIndex(int index){
		this.index = index;
	}

	public void setController(CustomerHome controller){
		this.controller = controller;
	}

	public void showSharedWith(ArrayList<Customer> sharedWith){
		sharedWithBox.setVisible(true);
		sharedWithLabel.setVisible(true);
		for(int i=0; i<sharedWith.size(); i++){
			sharedWithBox.getItems().add(sharedWith.get(i).getfName() + " " + sharedWith.get(i).getlName());
		}
	}
}
