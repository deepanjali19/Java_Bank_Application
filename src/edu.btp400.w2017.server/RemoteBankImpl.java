package edu.btp400.w2017.server;

import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Arrays;
import edu.btp400.w2017.common.*;
import edu.btp400.w2017.server.*;


public class RemoteBankImpl extends UnicastRemoteObject
							implements RemoteBank 
{
	

	private ArrayList<Account> acc; 
	private String bankName; 
	
	
	// Default Constructor
	
	public RemoteBankImpl() throws RemoteException 
	{
		bankName = "Seneca@York";
		acc = new ArrayList<Account>();
	}
	
	/**
	 * Overloaded constructor that takes in the bank Name and max Number of Accounts.
	 * @param n the bank Name
	 */
	
	public RemoteBankImpl(String n) throws RemoteException
	{
		bankName = n; 
		acc = new ArrayList<Account>();
	}
	
	
	//***************************************************************
	//***************************************************************
	// addAccount method
	
	public Account[] getAllAccounts() {
		Account[] all = new Account[acc.size()];
		for (int i=0; i<acc.size(); i++)
		{
			all[i] = acc.get(i); 
		}
	return all;
	}
	
	public boolean addAccount(Account account) throws RemoteException
	{
		boolean addedAcc = false;
		
		if(account != null) {	
			for(int i= 0; i < acc.size(); i++) 
			{
				if(account.getAccountNumber().equals(acc.get(i).getAccountNumber()) )
					return false;
			}
			if(account instanceof Savings)
			{	
				Savings act = (Savings) account;
				acc.add(act);
				
			} else if(account instanceof Chequing) {
				
				Chequing act = (Chequing) account;
				acc.add(act);
				
			} else if(account instanceof GIC) {
				
				GIC act = (GIC) account;
				acc.add(act);
			}	
			addedAcc = true;
		}
		return addedAcc;
	}
	
	
	
	//***************************************************************
	//***************************************************************
	// removeAccount method
	
	
	public Account removeAccount(String accNumber) throws RemoteException
	{
		
		for(int i = 0; i < acc.size(); i++) {
			if(acc.get(i).getAccountNumber().equals(accNumber)) {
				
				if(acc.get(i) instanceof Savings)
				{	
					Savings act = (Savings) acc.get(i);
					acc.remove(i);
					return act;
					
				} else if(acc.get(i) instanceof Chequing) {
					
					Chequing act = (Chequing) acc.get(i);
					acc.remove(i);
					return act;
					
				} else if(acc.get(i) instanceof GIC) {
					
					GIC act = (GIC) acc.get(i);
					acc.remove(i);
					return act;
				}
			}
		}
		
		return null;
	}
	
	
	//***************************************************************
	//***************************************************************
	// searchByBalance method
	
	
	
	public Account[] searchByBalance(double balance) throws RemoteException
	{
		ArrayList<Account> tmpAccounts = new ArrayList<Account>();
		Account[] tmp;
		
		for(int i = 0; i < acc.size(); i++) {
			if(acc.get(i).getBalance() == balance) {				
				tmpAccounts.add(acc.get(i));
			}
		}
		
		if(tmpAccounts.isEmpty()) {
			return null;
		}
		else
		{
			tmp = new Account[tmpAccounts.size()];
			for(int i = 0; i < tmpAccounts.size(); i++) {
				tmp[i] = tmpAccounts.get(i);
			}
			
			return tmp;
		}
	}
	
	
	//***************************************************************
	//***************************************************************
	// searchByAccountName method
	
	public Account[] searchByAccountName(String accountName) throws RemoteException
	{
		ArrayList<Account> temp = new ArrayList<Account>();
		Account[] temp2; 
		for (int i=0; i< acc.size(); i++)
		{
			if (acc.get(i).getFullName().equals(accountName))
			{
				temp.add(acc.get(i));
			}
		}
		if (!temp.isEmpty())
		{
			temp2 = new Account[temp.size()]; 
			for (int i=0; i< temp.size(); i++)
			{
				temp2[i] = temp.get(i);
			}
			return temp2;
		}
		else 
			return null;
	}
	
	
	public Account[] search(int balance) throws RemoteException
	{
		ArrayList<Account> temp = new ArrayList<Account>();
		Account[] temp2; 
		for (int i=0; i< acc.size(); i++)
		{
			if (acc.get(i).getBalance() == balance)
			{
				temp.add(acc.get(i));
			}
		}
		if (!temp.isEmpty())
		{
			temp2 = new Account[temp.size()]; 
			for (int i=0; i< temp.size(); i++)
			{
				temp2[i] = temp.get(i);
			}
			return temp2;
		}
		else 
			return null;
	}

	public Account searchByAccountNumber( String accountNumber ) throws RemoteException
	{
		Account tmp;
		
		for(int i = 0; i < acc.size(); i++) {
			if(acc.get(i).getAccountNumber().equals(accountNumber)) {				
				tmp = new Account();
				tmp = acc.get(i);
				
				return tmp;
			}
		}

		return null;
	}
	
	
	public String getBankName() { return bankName; }
}
	
