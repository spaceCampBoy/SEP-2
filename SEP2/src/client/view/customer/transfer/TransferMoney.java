package client.view.customer.transfer;

import java.rmi.RemoteException;
import java.time.LocalDate;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import client.ClientMain;
import client.view.ClientMainView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import shared.Account;
import shared.Customer;
import shared.DateUtil;
import shared.Transaction;

public class TransferMoney {

	@FXML
	private JFXTextField accNoField;
	@FXML
	private JFXTextField regNoField;
	@FXML
	private JFXPasswordField passwordField;
	@FXML
	private ChoiceBox<String> accountChoiceBox;
	@FXML
	private JFXTextField describtionField;
	@FXML
	private JFXTextField amountField;
	@FXML
	private JFXTextField dateField;
	@FXML
	private CheckBox checkSave;
	@FXML
	private ChoiceBox<String> savedTransactionsBox;


	private ClientMainView viewController;
	private ClientMain clientController;
	private Customer customer;


    @FXML
    private void initialize() {}

    @FXML
    public void handleSubmit(){

    	if(inputValid()){

    		Transaction transaction = new Transaction();

    		Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(viewController.getPrimaryStage());

            int transferCheck = -1;

    		Account account = customer.getAccountAtIndex(accountChoiceBox.getSelectionModel().getSelectedIndex());
    		transaction.setTransactionAccNo(accNoField.getText());
    		transaction.setRegNo(regNoField.getText());
    		transaction.setDescription(describtionField.getText());
    		transaction.setAmount(Double.parseDouble(amountField.getText()));
    		transaction.setTransferDate(DateUtil.parse(dateField.getText()));

    		try {
				transferCheck = clientController.transferMoney(account,customer.getCPRNo(), transaction, checkSave.isSelected());
			} catch (RemoteException e) {
				e.printStackTrace();
				e.printStackTrace();
	            // Show error message.
	            alert.setTitle("Exception");
	            alert.setHeaderText("Exception while transfering");
	            alert.setContentText("An exception was thrown!");
	            alert.showAndWait();
	            return;
			}

    		if(transferCheck == 0){
    			alert.setTitle("Creditlimit or daily transferlimit exceed");
                alert.setHeaderText("Creditlimit or daily transferlimit exceed");
                alert.setContentText("Creditlimit or daily transferlimit exceed!");
                alert.showAndWait();
                return;
    		}
    		else if(transferCheck == 1){
    			alert.setTitle("The account doesn't exist");
                alert.setHeaderText("The account doesn't exist");
                alert.setContentText("The account doesn't exist!");
                alert.showAndWait();
                return;
    		}
    		else if(transferCheck == 2){

    			if(checkSave.isSelected()){
                	customer.addTransaction(transaction);
                }

    			transaction.setAmount(-transaction.getAmount());
    			account.addTransactions(transaction);

                customer.getAccountAtIndex(accountChoiceBox.getSelectionModel().getSelectedIndex()).setBalance(
                		customer.getAccountAtIndex(accountChoiceBox.getSelectionModel().getSelectedIndex()).getBalance() + transaction.getAmount());

    			alert.setTitle("The amount was transfered");
                alert.setHeaderText("The amount was transfered");
                alert.setContentText("The amount was succesfully transfered!");
                alert.showAndWait();

                updateChoiceBox();
                handleCancel();
    		}
    	}
    }

    @FXML
    public void handleCancel(){
    	accNoField.setText("");
    	regNoField.setText("");
    	passwordField.setText("");
    	describtionField.setText("");
    	amountField.setText("");
    	dateField.setText("");
    }

    @FXML
    public void handleDelete(){
    	int index = savedTransactionsBox.getSelectionModel().getSelectedIndex();
    	try {
			clientController.deleteSavedTransaction(customer.getCPRNo(), customer.getTransactionAtIndex(index));
		} catch (RemoteException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(viewController.getPrimaryStage());
            alert.setTitle("Exception");
            alert.setHeaderText("Excpetion while deleting transaction");
            alert.setContentText("Excpetion while deleting transaction!");
            alert.showAndWait();
		}
    	customer.deleteTransactionAtIndex(index);
    	updateChoiceBox();
    }

    @FXML
    public void handleApply(){
    	Transaction ta = customer.getTransactionAtIndex(savedTransactionsBox.getSelectionModel().getSelectedIndex());
    	accNoField.setText(ta.getTransactionAccNo());
    	regNoField.setText(ta.getRegNo());
    	describtionField.setText(ta.getDescription());
    	amountField.setText(Double.toString(ta.getAmount()));
    	dateField.setText(DateUtil.format(ta.getTransferDate()));
    }

    private boolean inputValid() {

    	String errorMessage = "";

        if (accNoField.getText() == null || accNoField.getText().length() == 0) {
            errorMessage += "No valid account number!\n";
        }
        if (regNoField.getText() == null || regNoField.getText().length() == 0) {
            errorMessage += "No valid registration number!\n";
        }
        if (passwordField.getText() == null || passwordField.getText().length() == 0) {
            errorMessage += "No valid password!\n";
        }
        if (describtionField.getText() == null || describtionField.getText().length() == 0) {
            errorMessage += "No valid describtion!\n";
        }
        if (amountField.getText() == null || amountField.getText().length() == 0) {
            errorMessage += "No valid amount!\n";
        }
        if(accountChoiceBox.getSelectionModel().getSelectedItem() == null){
       	 errorMessage += "No valid account choosen!\n";
       }

        try {
			if(clientController.login(clientController.getUsername(), passwordField.getText()).equals(null)){
				errorMessage += "Password was wrong!\n";
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}

        if (dateField.getText() == null || dateField.getText().length() == 0) {
            errorMessage += "No valid date!\n";
        } else {
            if (!DateUtil.validDate(dateField.getText())) {
                errorMessage += "No valid date. Use the format dd/mm/yyyy!\n";
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

    public void setCustomer(Customer customer){
    	this.customer = customer;
    	updateChoiceBox();
    }

    private void updateChoiceBox(){
    	accountChoiceBox.getItems().clear();
    	for(int i=0; i<customer.getAccountSize(); i++){
    		accountChoiceBox.getItems().add(customer.getAccountAtIndex(i).getName() + " - " + customer.getAccountAtIndex(i).getBalance()+ " DKK");
    	}
    	savedTransactionsBox.getItems().clear();
    	for(int i=0; i<customer.getsavedTransactionsSize(); i++){
    		savedTransactionsBox.getItems().add(customer.getTransactionAtIndex(i).getRegNo() + "-" + customer.getTransactionAtIndex(i).getTransactionAccNo() +
    		" - " + customer.getTransactionAtIndex(i).getAmount() + " DKK");
    	}
    }
}
