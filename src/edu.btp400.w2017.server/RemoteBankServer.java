package edu.btp400.w2017.server;

import java.rmi.*;
import java.rmi.registry.*;
import edu.btp400.w2017.common.*;
import edu.btp400.w2017.server.*;
public class RemoteBankServer {
	 public static void main(String[] args)
	 {
		 try
		 {
			Registry reg = LocateRegistry.createRegistry(5678);
			RemoteBankImpl bank = new RemoteBankImpl("TeamDP");
			loadBank(bank);
			Naming.rebind("rmi://localhost:5678/TeamDP", bank); // Pavan
			System.out.println( "Starting a server..." ); 
			
		 }
		 catch(Exception e)
		 {
			System.out.println( "Error: " + e );
		 }
	 }

	 private static void loadBank (RemoteBankImpl b)
	{
		Chequing check1 = new Chequing ("John Doe", "0123456789", 7500, 0.50, 15);
		Chequing check2 = new Chequing ("Mary Ryan", "1234567890", 8000, 0.50, 10); 
		Savings save1 = new Savings ("John Doe", "2345678901", 9500, 0.15);
		Savings save2 = new Savings ("Mary Ryan", "3456789012", 2000, 0.25);
		GIC gic1 = new GIC ("John Doe", "4567890123", 15000, 12, 1.50);
		GIC gic2 = new GIC ("Mary Ryan", "5678901234", 6000, 24, 2.50);
		
		   try {
		        b.addAccount(check1);
		        b.addAccount(check2);
		        b.addAccount(save1);
		        b.addAccount(save2);
		        b.addAccount(gic1);
		        b.addAccount(gic2);
		    } catch(Exception e) {
		        System.out.println("Error with loading Bank!" + e);
		    }
		
	}
}
