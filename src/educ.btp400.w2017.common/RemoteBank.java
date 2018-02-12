package edu.btp400.w2017.common;

import java.rmi.*;

public interface RemoteBank extends Remote
{	
	public boolean addAccount(Account a) throws RemoteException;
	
	public Account removeAccount(String b) throws RemoteException;
	
	public Account[] searchByBalance(double c) throws RemoteException;
	
	public Account[] searchByAccountName(String d) throws RemoteException;

	public Account searchByAccountNumber( String e ) throws RemoteException;
}
