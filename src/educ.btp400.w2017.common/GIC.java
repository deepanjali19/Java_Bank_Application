package edu.btp400.w2017.common;

import java.util.Arrays;
import java.math.BigDecimal;
import java.io.Serializable;

public class GIC extends Account implements Serializable{
	//variable declaration
		private int periodLife;
		private double annInterest;
		private double taxAmt;
		
		//default constructor for GIC
		public GIC()
		{
			super();
			periodLife = 1;
			annInterest = 1.25;
		}
		
		//Overloaded Constructor for GIC
		public GIC(String fName, String accNum, double bal, int life, double i) 
		{
			super(fName, accNum, bal);
			periodLife = life;
			annInterest = i;
		}
		
		//This method calculates the interest income and returns it
		public double getBalanceAtMaturity() 
		{
			BigDecimal currentBalance = new BigDecimal(getBalance());
			BigDecimal power = new BigDecimal(Math.pow((1+(annInterest/100)), periodLife));
			BigDecimal Maturity = currentBalance.multiply(power).subtract(currentBalance);
			return Maturity.doubleValue();
		}
		
		// This displays the information for a GIC bank
		public String toString() 
		{
			String s; 
			double maturity = getBalanceAtMaturity();
			String m = String.format("%.2f", maturity);
			BigDecimal finalB = new BigDecimal(getBalance() + maturity);
			String finalBal = String.format("%.2f", finalB);
			s = super.toString() + 
				"* Type: GIC\n" +
				"* Annual Interest Rate: " + annInterest + "%\n" +
				"* Period of Investment: " + periodLife + " years\n" + 
				"* Interest Income at Maturity: $" +m+ "\n" +
				"* Final Balance: $" + finalBal; 
				setAccountBalance(finalB.doubleValue());
		return s;
		}
		
		@Override
	    public int hashCode() 
		{
			int hash = super.hashCode(); 
			
			hash = hash * 37 + periodLife; 
			
			long doubleFieldBits = Double.doubleToLongBits(annInterest);
			hash = hash * 37 + (int)(doubleFieldBits ^ (doubleFieldBits >>> 32)); 
			
			long doubleFieldBits2 = Double.doubleToLongBits(taxAmt);
			hash = hash * 37 + (int)(doubleFieldBits2 ^ (doubleFieldBits2 >>> 32)); 
			
			return hash; 
		} 
		
		//withdraw function that is empty.
		public boolean withdraw(double amount)
		{
			return false;
		}
		
		//deposit method that is also empty.
		public boolean deposit(double amount)
		{
			return false;
		}
}
