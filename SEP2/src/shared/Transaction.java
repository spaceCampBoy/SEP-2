package shared;

import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Serializable{
	private String transactionAccNo;
	private String regNo;
	private String description;
	private LocalDate transferDate;
	private double amount;

	public Transaction(){}

	public Transaction(String transactionAccNo,String regNo,String description,LocalDate transferDate,double amount){
		this.transactionAccNo=transactionAccNo;
		this.regNo=regNo;
		this.description=description;
		this.transferDate=transferDate;
		this.amount= amount;
	}
	public String getTransactionAccNo() {
		return transactionAccNo;
	}
	public void setTransactionAccNo(String transactionAccNo) {
		this.transactionAccNo = transactionAccNo;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String string) {
		this.regNo = string;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Transaction: " + transactionAccNo + ", regNo: " + regNo + ", description: " + description
				+ ", transferDate: " + transferDate + ", amount: " + amount + "]";
	}

}
