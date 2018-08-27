package client.view.customer.home.transactions.transactionsView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import shared.DateUtil;
import shared.Transaction;

public class Transactions {

	@FXML
	private Label dateLabel;
	@FXML
	private Label regNoLabel;
	@FXML
	private Label accNoLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Label amountLabel;
	@FXML
	private ImageView image;

	@FXML
	public void initialize() {}

	public void setTransaction(Transaction transaction) throws FileNotFoundException{
		dateLabel.setText(DateUtil.format(transaction.getTransferDate()));
		regNoLabel.setText(transaction.getRegNo());
		accNoLabel.setText(transaction.getTransactionAccNo());
		descriptionLabel.setText(transaction.getDescription());
		amountLabel.setText(Double.toString(transaction.getAmount()) + " DKK");

		if(transaction.getAmount() > 0){
			FileInputStream input = new FileInputStream("resources/images/menu/24829276_1543704275719836_621530279_n.png");
			Image img = new Image(input);
			image.setImage(img);
		}else{
			FileInputStream input = new FileInputStream("resources/images/menu/24581393_1543704092386521_1660212391_n.png");
			Image img = new Image(input);
			image.setImage(img);
		}
	}
}
