package edu.btp400.w2017.client;

import java.util.Scanner;

import edu.btp400.w2017.common.*;

//import java.net.MalformedURLException;

import java.rmi.*;

public class RemoteBankClient {
	// variable declaration 
		public enum AccountType{ SAVINGS, CHEQUING, GIC }
		
		//Scanner declaration, this is for inputting data. 
		static Scanner scan = new Scanner(System.in);
		

		public static void displayMenu (String bankName)
		{
			String menu = "\nWelcome to " +bankName+ " Banking!\n\n" +
								"1. Open an account.\n" +
								"2. Close an account.\n" +
								"3. Display accounts.\n" +
								"4. Exit";
		   System.out.println(menu);
		}
		
		public static String getDataValues(String message)
		{
			System.out.print(message);
			String line = scan.nextLine(); 
			return line;
		}
		
		public static void displayAccount (Account account)	
		{
			System.out.println(account);
		}
		
		public static int menuChoice()
		{
			int choice;
			boolean keepgoing = true;
			do{
				choice = scan.nextInt();
				if (choice > 0 && choice <= 7)
					keepgoing=false;
			}while(keepgoing);
			return choice;
		}
		
		public static Account addAcc (AccountType type, String accInfor)
		{
			String name, accountNumber; 
			double balance; 
			while(true)
			{
			try {
				Scanner lineScan = new Scanner(accInfor);
				lineScan.useDelimiter("; *");
				Account account = null; 
				
				switch(type) 
				{
					case SAVINGS: 
					{
					   double interest; 
					   name = lineScan.next(); 
					   System.out.println(name);
					   accountNumber = lineScan.next();
					   System.out.println(accountNumber);
					   balance = lineScan.nextDouble();
					   System.out.println(balance);
					   interest = lineScan.nextDouble();
					   System.out.println(interest);
					   account = new Savings (name, accountNumber, balance, interest);
					   displayAccount(account);
					   System.out.println("Account has been created!\n");
					}
					   break; 
					case CHEQUING: 
					{
						double serviceCharge; int maxTrans;
						name = lineScan.next();
						accountNumber = lineScan.next(); 
						balance = lineScan.nextDouble();
						serviceCharge = lineScan.nextDouble();
						maxTrans = lineScan.nextInt();
						account = new Chequing (name, accountNumber, balance,
								serviceCharge, maxTrans);
						displayAccount(account); 
						System.out.println("Account has been created!\n");
					}
					   break; 
					case GIC: 
					{
						double interest; 
						int InvestmentPeriod;
						name = lineScan.next();
						accountNumber = lineScan.next();
						balance = lineScan.nextDouble();
						InvestmentPeriod = lineScan.nextInt();
						interest = lineScan.nextDouble();
						lineScan.close();
						account = new GIC(name, accountNumber, balance, InvestmentPeriod, interest);
						displayAccount(account); 
						System.out.println("Account has been created!\n");
					}
					break; 
				}
				lineScan.close();
				return account;
			}catch (Exception e){
				System.out.println("\nError: Invalid Format!\n");
			}
		}
	}

		public static void main (String[] args)
		{
			
			
			String url = "rmi://localhost:5678/";
			
			try {
				String names[] = Naming.list("rmi://localhost:5678");
				for (int i=0; i < names.length; i++)
				{
					System.out.println(names[i]);
				}
			RemoteBank bank = (RemoteBank) Naming.lookup(url + "TeamDP");
			
			int menuSelect;
			boolean exit = false;
			do {
				displayMenu("TeamDP");
				menuSelect = menuChoice();
				switch(menuSelect)
				{
					// open an account
					case 1:
					{
						boolean quit = false; 
						int selectOption = 0; 
						do {
							  System.out.println(
									  "\tOPEN NEW ACCOUNT\n\n"
							  + "1. Saving Account\n"
						      + "2. Chequings Account\n" 
							  + "3. GIC Account\n" 
						      + "4. Cancel\n" );
							  System.out.println("Select option (1-4)\n");
							   
							  if (scan.hasNext()){
								  selectOption = scan.nextInt();
								  scan.nextLine();
								  
									  switch(selectOption)
									  {
									  	// add a Savings account
									  	case 1: 
									  	{
									  		String s = "\t\t****Savings Account Application Form****\n\n" +
													"Enter information\nName(string); Account number(string); Balance(double); Interest rate(double)\n";
											
											bank.addAccount(addAcc(AccountType.SAVINGS,getDataValues(s)));
											//System.out.println(bank);
											System.out.println("\n");
									  	}
									  	break;			
									  	// add a Chequing Account
									  	case 2:
									  	{
									  		String s = "\t\t****Chequing Account Application Form****\n\n" +
													"Enter information\nName(string); Account number(string); Balance(double); Service charge(double); Max Transcations Allowed(int)\n";										
											bank.addAccount(addAcc(AccountType.CHEQUING,getDataValues(s)));
										//	System.out.println(bank);
									  	}
									  	break;
									  	// add a GIC account 	
									  	case 3:
									  	{
									  		String s = "\t\t****GIC Account Application Form****\n\n" +
													"Enter information\nName(String); Account number(string); Balance(double); Period of investment(int); Interest rate(double)\n";
											
											bank.addAccount(addAcc(AccountType.GIC,getDataValues(s)));
											//System.out.println(bank);
									  	}
									  	break;
									  	// exit 
									  	case 4:
									  	{
									  		quit = true;
									  	}
									  	break;
									  	default:
											System.out.println("Wrong Choice.\n");
									  }
							  }
							  else 
								  System.out.println("Please Choose again. Error Occured!");
						   }while(!quit);
					}
					break;
					// close an account 
					case 2: 
					{
						int select = 0;
						boolean quit = false;
						do {
							System.out.println("\tREMOVE ACCOUNT\n\n"
									+ "1. Search For Account to remove\n"
									+ "2. Cancel\n");
							
							System.out.println("Select option (1-2)\n");
							if(scan.hasNextInt()) {
								select = scan.nextInt();
								scan.nextLine();
								switch(select) {
									case 1: 
										{
											System.out.println("Enter Account Number : ");
											if(scan.hasNext()) {
												// add more 
												String accountName = scan.next();
												
												Account account = bank.searchByAccountNumber(accountName);
												if(account != null) {
													bank.removeAccount(account.getAccountNumber());
													System.out.println("\nAccount Removed Successfully\n");
													displayAccount(account);
													System.out.println("Account Removed Successfully\n");
													quit = true;
												} else {
													System.out.println("\nError: Could not find specified account\n");
												}
											}
										}
										break;
									case 2:
										quit = true;
									default:
										System.out.println("Wrong Choice.\n");
								}
							}
						} while(!quit);
					}
					break;
					
					// Display Accounts
					case 3: 
					{
						System.out.println("\tSEARCH ACCOUNT\n");
						int select = 0;
						boolean quit = false;
						do {
							System.out.println("1. Search by Account Name.\n"
									+ "2. Search by Account Balance.\n"
									+ "3. Cancel\n");
							
							System.out.println("Select option (1-3)\n");
							if(scan.hasNextInt()) {
								select = scan.nextInt();
								scan.nextLine();
								switch(select) {
									case 1: 
										{
											System.out.println("Enter Account Name: ");
											if(scan.hasNext()) {
												// add more 
												String accountName = scan.nextLine();
												
												Account[] accounts = bank.searchByAccountName(accountName);
												if(accounts != null) {
													for(Account a : accounts) {
														displayAccount(a);
													}
													System.out.println("\n");
												} else {
													System.out.println("\nError: Could not find specified account\n");
												}
											}
										}
										break;
									case 2:
									{
										System.out.println("Enter Account Balance: ");
										if(scan.hasNext()) {
											// add more 
											int balance = scan.nextInt();
											
											Account[] accounts = bank.searchByBalance(balance);
											if(accounts != null) {
												for(Account a : accounts) {
													displayAccount(a);
												}
												System.out.println("\n");
											} else {
												System.out.println("\nError: Could not find specified account\n");
											}
										}
									}
										break;
								
									case 3:
										quit = true;
										break;
									default:
										System.out.println("Wrong Choice.\n");
								}
							}
						} while(!quit);
					}
					break;
					
					// Exit
					case 4:
						exit = true;
				}
			} while(!exit);
			System.out.println("Thank You!");
			scan.close(); 
			}catch (Exception e){ System.out.println("error:" + e); }
			}
}
