package client.view.customer.home.transactions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import client.view.customer.home.CustomerHome;
import client.view.customer.home.accountView.Accounts;
import client.view.customer.home.transactions.transactionsView.Transactions;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import shared.Account;
import shared.DateUtil;

public class TransactionsList {

	@FXML
	private Label accName;
	@FXML
	private Label accNoLabel;
	@FXML
	private Label regNoLabel;
	@FXML
	private Label balanceLabel;
	@FXML
	private Label creditLimitLabel;
	@FXML
	private Label transferLimitLabel;
	@FXML
	private JFXTextField dateTo;
	@FXML
	private JFXTextField dateFrom;
	@FXML
	private JFXListView<AnchorPane> listView;

	private Account account;

	@FXML
	public void initialize() {
		listView.depthProperty().set(3);
        listView.setExpanded(true);
        listView.setVerticalGap(5.0);
	}

	@FXML
	public void handleShow() throws FileNotFoundException{

		if(inputValid()){
		listView.getItems().clear();

		LocalDate from = DateUtil.parse(dateFrom.getText());
		LocalDate to = DateUtil.parse(dateTo.getText());

		for(int i=0; i<account.getTransactions().size(); i++) {

			LocalDate transferDate = account.getTransactions().get(i).getTransferDate();

			if((transferDate.isAfter(from) && transferDate.isBefore(to)) || transferDate.isEqual(to) || transferDate.isEqual(from)){

				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(TransactionsList.class.getResource("transactionsView/Transactions.fxml"));
				AnchorPane transaction = null;
				try {
					transaction = (AnchorPane) loader.load();
				} catch (IOException e) {e.printStackTrace();}

				//Give the create new user controller access to the client main class and this class.
				Transactions controller = loader.getController();
				controller.setTransaction(account.getTransactions().get(i));

	         listView.getItems().add(transaction);
			}
		}
		}
	}

	public void setAccount(Account account){
		this.account = account;
		accName.setText(account.getName());
		regNoLabel.setText(account.getRegNo());
		accNoLabel.setText(account.getAccNo());
		balanceLabel.setText(Double.toString(account.getBalance()) + " DKK");
		creditLimitLabel.setText(Integer.toString(account.getCreditLimit()));
		transferLimitLabel.setText(Integer.toString(account.getTransferLimit()));

		LocalDate date = LocalDate.now();
		dateTo.setText(DateUtil.format(date));
		dateFrom.setText(DateUtil.format(date.minusMonths(1)));
	}

	private boolean inputValid(){

		String errorMessage = "";

        if ((dateFrom.getText() == null || dateFrom.getText().length() == 0) || (dateTo.getText() == null || dateTo.getText().length() == 0)) {
            errorMessage += "No valid date!\n";
        } else {
            if (!DateUtil.validDate(dateFrom.getText()) || !DateUtil.validDate(dateTo.getText())) {
                errorMessage += "No valid date. Use the format dd/mm/yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(null);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
	}

	public void setTransactions() throws IOException{
		handleShow();
	}
}
