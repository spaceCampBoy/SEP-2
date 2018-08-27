package client.view.customer.contacts;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import client.ClientMain;
import client.view.ClientMainView;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import shared.Customer;
import shared.SendEmail;

public class Contacts {

	@FXML
	private JFXTextField nameField;
	@FXML
	private JFXTextField emailField;
	@FXML
	private JFXTextField subjectField;
	@FXML
	private JFXTextArea emailArea;
	@FXML
	private JFXTextField phoneField;
	@FXML
	private CheckBox checkCopy;

	private ClientMainView viewController;
	private ClientMain clientController;
	private Customer customer;

	@FXML
	public void initialize() {}


	@FXML
	public void handleSend(){

		if(checkCopy.isSelected()){
			String body = "Thank you for your email, we will try to respond within 24 hours.\n"
					+ "Have a good day.\n\nCopy of email you send\n\n";

			new SendEmail(emailField.getText(), "Copy of email from online banking - " + subjectField.getText(), body + emailArea.getText());
		}

		String subject = "New contact request from " + nameField.getText();
		String body = "Informations: \n"
				+ "Name: " + nameField.getText()+ "\n"
				+ "Phone: " + phoneField.getText() +"\n"
				+ "Email: " + emailField.getText() + "\n\n"
				+ "Subject: " + subjectField.getText() + "\n"
				+ "Email body: \n" + emailArea.getText();

		new SendEmail("studentsep2@gmail.com", subject, body);
	}

	@FXML
	public void handleReset(){
		nameField.setText(customer.getfName() + " " + customer.getlName());
		emailField.setText(customer.getEmail());
		phoneField.setText(customer.getPhoneNumber());
	}


	public void setCustomer(){
		this.customer = clientController.getCustomerData();
		handleReset();
	}

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

