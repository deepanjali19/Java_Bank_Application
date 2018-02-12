package edu.btp400.w2017.common;

import java.math.BigDecimal;
import java.util.Arrays;
import java.io.Serializable;

public class Savings  extends Account implements Serializable{
	// Variable Declaration
		private double interestR; 
		private double taxAmount;
		private double interestIncome;
		private double finalBalance;
		
		// Default Constructor
		public Savings ()
		{
			super();
			interestR = 0.10; 
		}
		
		//Overloaded constructor
		public Savings (String name, String accNum, double bal, double interest)
		{
			super(name, accNum, bal);
			interestR = interest;
			
			BigDecimal interestIn = new BigDecimal(super.getBalance() * (interestR/100));
			BigDecimal balance = new BigDecimal(super.getBalance());
			BigDecimal finalBal = interestIn.add(balance);
			finalBalance = finalBal.doubleValue();
			
		}	
		
		//equals method that checks if two saving accounts are the same
		public boolean equals(Object z) 
		{
			boolean result = false; 
			if (z instanceof Savings)
			{
				Savings z2 = (Savings) z;
				result = (z2.getFullName().equals(getFullName()))&& 
					(z2.getAccountNumber().equals(getAccountNumber()))&&
					(z2.getBalance() == getBalance())&&
					(z2.interestR == interestR);
			}
			return result;
		}
		
		//toString method overloads the display for the Saving account
		public String toString()
		{
			String s; 
			String intIncome = String.format("%.2f", interestIncome);
			String fBalance = String.format("%.2f", finalBalance);
			String intR = String.format("%.2f", interestR);
			s = super.toString() + 
			"* Type: SAV\n" +
			"* Interest Rate: " + intR + "%\n"+
			"* Interest Income: $" + intIncome  + "\n" +
			"* Final Balance: $" + fBalance;
			setAccountBalance(finalBalance);
			return s;
		}
			
		
		@Override
	    public int hashCode() 
		{
			int hash = super.hashCode(); 
			
			long doubleFieldBits = Double.doubleToLongBits(interestR);
			hash = hash * 37 + (int)(doubleFieldBits ^ (doubleFieldBits >>> 32)); 
			
			long doubleFieldBits2 = Double.doubleToLongBits(taxAmount);
			hash = hash * 37 + (int)(doubleFieldBits2 ^ (doubleFieldBits2 >>> 32)); 
			
			long doubleFieldBits3 = Double.doubleToLongBits(interestIncome);
			hash = hash * 37 + (int)(doubleFieldBits3 ^ (doubleFieldBits3 >>> 32)); 
			
			long doubleFieldBits4 = Double.doubleToLongBits(finalBalance);
			hash = hash * 37 + (int)(doubleFieldBits4 ^ (doubleFieldBits4 >>> 32)); 
				
			return hash; 
		} 
		
		/*// This calculates the amount of tax on the interestIncome. It only takes affect if the interest 
		// income is over $50. 
		@Override
		public void calculateTax(double taxRate) 
		{
			BigDecimal taxR = new BigDecimal ((taxRate/100));
			BigDecimal interestInc = new BigDecimal (interestIncome);
			BigDecimal taxAm = taxR.multiply(interestInc);
			
			if (interestInc.doubleValue() >= 50)
				taxAmount = taxAm.doubleValue();
			else
				taxAmount = 0;
		}

		// returns the Tax Amount. 
		@Override
		public double getTaxAmount() 
		{
			calculateTax(15);
			return (taxAmount);
		}

		// This displays all the details for the tax information. 
		@Override
		public String createTaxStatement() 
		{
			String s; 
			String intIncome = String.format("%.2f", getBalance() * (interestR/100));
			String taxAmt = String.format("%.2f", getTaxAmount());
			s = "* Tax Rate: 15% \n" +
				"* Account Number: " + getAccountNumber() + "\n" +
				"* Interest Income: $" + intIncome + "\n" +
				"* Amount of tax: $" + taxAmt;
			return s;
		}
		*/
		@Override
		public double getBalance() {
			return finalBalance;
		}
}
