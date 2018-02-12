package edu.btp400.w2017.common;

import java.math.BigDecimal;
import java.util.Arrays;
import java.text.DecimalFormat;
import java.io.Serializable;


public class Chequing extends Account implements Serializable{
	//variable declaration
		double serviceChargeptrans;
		int maxTransactions;
		double cheqTrans[];
		private int counter;
		
		//Default Constructor
		public Chequing() 
		{
			super(); 
			serviceChargeptrans = 0.25;
			maxTransactions = 3;
			cheqTrans = new double[maxTransactions];
			counter = 0;
		}
		
		//Overloaded Constructor 
		public Chequing(String fName, String AccNum, double bal, double sC, int mT )
		{
			super(fName, AccNum, bal);
			serviceChargeptrans = sC;
			maxTransactions = mT;
			cheqTrans = new double[maxTransactions];
			counter = 0;
		}
		
		
		//This function is used to display the bank information for a chequing account 
		public String toString()
		{
			String s;
			double result;
			DecimalFormat df = new DecimalFormat("0.00");
			s = super.toString() +
			"* Type: CHQ\n" +
			"* Service Charge: $" + df.format(serviceChargeptrans) + "\n" +
			"* Total Service Charges: $" + df.format((serviceChargeptrans * counter)) + "\n" +
			"* Number of Transactions Allowed: " + maxTransactions + "\n"+
			"* List of Transactions: "; 
				
				for (int i =0; i<counter; i++)
				{
					s+= df.format(cheqTrans[i]) + ", ";
					result = getBalance() + cheqTrans[i];
					setAccountBalance(result - serviceChargeptrans);
				}
			s+="\n* Final Balance: $" + df.format(getBalance());
			return s;
		}
		
		// this method checks if two Chequing accounts are the same
		public boolean equals(Object otherCheq) {
			boolean result = false;
			if (otherCheq instanceof Chequing)
				{
					Chequing temp = (Chequing) otherCheq;
					result = (getFullName().equals(getFullName())&& 
							getAccountNumber().equals(getAccountNumber()) &&
							getBalance() == temp.getBalance() && serviceChargeptrans == temp.serviceChargeptrans
							&& maxTransactions == temp.maxTransactions);
				}
			return result;
		}
		
		// this withdraws money from a Chequing account
		@Override
		public boolean withdraw (double amount)
		{
		boolean withdraw = false;
			if (counter < maxTransactions)
			{
				if (amount > 0)
				{
						if(getBalance() >= 0) 
						{
							if (amount <= (getBalance() - serviceChargeptrans)) 
							{
								BigDecimal withdrawal = new BigDecimal(amount); 
								BigDecimal currentBalance = new BigDecimal(getBalance());
								BigDecimal ServCharge = new BigDecimal (serviceChargeptrans); 
								BigDecimal updatedBalance = currentBalance.subtract(withdrawal).subtract(ServCharge);
								updatedBalance.setScale(2,BigDecimal.ROUND_HALF_DOWN);
								cheqTrans[counter++] = (amount * -1);
								withdraw = true;
							}
						}
				}
			}
			else 
			{
				System.out.println("You have reached the maximum Transactions. Error!");
			}
		return withdraw;
		}
		
		// this deposits money into a chequing account
		@Override
		public boolean deposit (double amount)
		{
			boolean deposit = false; 
			if (counter < maxTransactions)
			{
				if (amount > 0){
				BigDecimal transaction = new BigDecimal(amount); 
				BigDecimal currentBalance = new BigDecimal(getBalance());
				BigDecimal servCharge = new BigDecimal(serviceChargeptrans);
				BigDecimal newBalance = currentBalance.add(transaction).subtract(servCharge);
				newBalance.setScale(2, BigDecimal.ROUND_HALF_EVEN);
				
				if(newBalance.doubleValue() > 0){
					//setAccountBalance(newBalance.doubleValue()); 
					deposit = true; 
					cheqTrans[counter++] = amount;
				 }
				}
			}
			else {
				System.out.println("The maximum Transaction Limit has been reached");
			}		
			return deposit;
		}
		
		@Override
	    public int hashCode() 
		{
			int hash = super.hashCode(); 
			
			hash = hash * 37 + maxTransactions; 
			
			long doubleFieldBits = Double.doubleToLongBits(serviceChargeptrans);
			hash = hash * 37 + (int)(doubleFieldBits ^ (doubleFieldBits >>> 32)); 
			
			hash = hash * 37  + Arrays.hashCode(cheqTrans);
			
			return hash; 
		} 
		 
}
