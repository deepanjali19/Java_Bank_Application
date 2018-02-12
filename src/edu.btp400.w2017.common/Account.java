package edu.btp400.w2017.common;

import java.math.BigDecimal;
import java.util.regex.Pattern; 
import java.util.regex.*;
import java.io.Serializable;

public class Account implements Serializable{
	private String fullName; 
	private String firstName; 
	private String lastName; 
	private String accountNumber;
	private double balance; 
	
	//default constructor 
	public Account() 
	{
		fullName = ""; 
		accountNumber = ""; 
		balance = 0.0; 
	}
	
	// overloaded constructor that takes the full Name, account Number, and balance 
	public Account(String fName, String accNumber, double bal)
	{
				fullName = fName; 
				accountNumber = accNumber; 
				StringBuffer fullN = new StringBuffer (fullName);
				if (bal > 0)
				{
					balance = bal;
				}	 
				
				if (fullN != null && !fullN.equals(" +"))
				{
					int space = fullN.indexOf(" ");
					firstName = fullN.substring(0, space);
					lastName = fullN.substring(space + 1, fullN.length());
				}	
	}
	
	// This method helps with the display of the account details
	public String toString()
	{
		String s; 
		String result = String.format("%.2f", balance); 
		s = "\n********************************************\n" + 
		    "*           Account Information            *\n" +
			"********************************************\n" +
			"* Name: " +lastName+ ", " + firstName +"\n" +
			"* Number: " + accountNumber + "\n" +
			"* Current Balance: $" + result + "\n"; 
		return s; 	
	}
	
    @Override
    public int hashCode() 
	{
		int hash = 17; 
		
		hash = hash * 37 + fullName.hashCode(); 
		hash = hash * 37 + accountNumber.hashCode();
		
		long doubleFieldBits = Double.doubleToLongBits(balance);
		hash = hash * 37 + (int)(doubleFieldBits ^ (doubleFieldBits >>> 32)); 
		
		return hash; 
	} 
	
	// the equals method checks if two account objects are equivalent. 
	public boolean equals(Object z) 
	{
		boolean result = false; 
		
		if (z instanceof Account)
		{
			Account z2 = (Account) z; 
			if ((z2.fullName.equals(fullName)) 
			    && (z2.accountNumber.equals(accountNumber))
				&& (z2.balance == balance))
			
				result = true;
		}
		return result; 
	}
	
	// This function withdraws money from the account. 
	public boolean withdraw (double amount) 
	{
		boolean validW = false; 
		
		if(amount > 0)
		{
			if (balance >= 0)
			{
				if (amount <= balance )
				{
					BigDecimal transaction = new BigDecimal(amount); 
					BigDecimal bal = new BigDecimal(balance); 
					
					balance = bal.subtract(transaction).doubleValue();
					validW = true;
				}
			}
		}
		return validW; 
	}
	
	
	// This function deposits money into the account. 
	public boolean deposit (double amount) 
	{
		boolean valid = false; 
		
		if (amount > 0)
		{
			BigDecimal transaction = new BigDecimal(amount); 
			BigDecimal bal = new BigDecimal(balance); 
			
			balance = bal.add(transaction).doubleValue(); 
			valid = true;
		}
		return valid; 
	}
	
	//setters and getters methods for the Account.
	public void setFirstName (String fName) {firstName = fName;}
	public void setLastName (String lName){lastName = lName;}
	public void setFullName (String fullN) {fullName = fullN;}
	public void setAccountNumber (String accNum) {accountNumber = accNum;}
	public void setAccountBalance (double bal) {balance = bal;}
	public String getFullName() {return fullName;}
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
	public String getAccountNumber() {return accountNumber;}
	public double getBalance() {return balance;}
	
}
