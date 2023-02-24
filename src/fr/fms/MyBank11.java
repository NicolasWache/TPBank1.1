/**
 * Version 1.0 d'une appli bancaire simplifiée offrant la possibilitée de créer des clients, des comptes bancaires associés et des opérations ou
 * transactions bancaires sur ceux-ci telles que : versement, retrait ou virement 
 * + permet d'afficher l'historique des transactions sur un compte
 * + la gestion des cas particuliers est rudimentaire ici puisque la notion d'exception n'a pas encore été abordée
 * 
 * @author El babili - 2022
 * 
 */

package fr.fms;

import java.util.Date;
import java.util.Scanner;

import fr.fms.business.IBankImpl;
import fr.fms.entities.Account;
import fr.fms.entities.Current;
import fr.fms.entities.Customer;
import fr.fms.entities.Saving;
import fr.fms.entities.Transaction;

public class MyBank11 {	
	public static void main(String[] args) {
		//représente l'activité de notre banque
		IBankImpl bankJob = new IBankImpl();

		Customer robert = new Customer(1, "Dupont", "Robert", "robert.dupont@xmail.com");
		Customer julie = new Customer(2, "Jolie", "Julie", "julie.jolie@xmail.com");		
		Current firstAccount = new Current(100200300, new Date(), 1500, 200 , robert);
		Saving secondAccount = new Saving(200300400, new Date(), 2000, 5.5, julie);
		bankJob.addAccount(firstAccount);
		bankJob.addAccount(secondAccount);

		Scanner scan = new Scanner(System.in);
		Account account = null;
		int userNumAccount = -1;


		while (true) {

			try {
				System.out.println("saisissez un numéro de compte bancaire valide");
				userNumAccount = scan.nextInt();


				account = bankJob.consultAccount(userNumAccount);

				Account actualAccount =bankJob.consultAccount(userNumAccount);
				Customer actualCustomer = actualAccount.getCustomer();
				System.out.print("Bonjour " + actualCustomer.getFirstName()+ ", ");
				
				int choiceUser = 0;
				while(choiceUser!=6) {
					
					try {
						System.out.println("quelle opération souhaitez vous effectuer?");
						System.out.println("1: Versement - 2: Retrait - 3: Virement - 4: Information du compte - 5: Liste des opérations - 6: Sortir");
						int choiceMenu = scan.nextInt();

						switch (choiceMenu) {
						case 1:
							System.out.println("Saisissez le montant à déposer");
							int amountDeposit = scan.nextInt();
							bankJob.pay(actualAccount.getAccountId(), amountDeposit);
							System.out.println("Votre nouveau solde est "+ actualAccount.getBalance() + "€");
							break;
						case 2:	System.out.println("Saisissez le montant que vous souhaitez retirer");
							int amountWithdraw = scan.nextInt();
							bankJob.withdraw(userNumAccount, amountWithdraw);
							System.out.println("Votre nouveau solde est "+ actualAccount.getBalance() + "€");
							break;
						case 3: 
							System.out.println("Saisissez le montant que vous souhaitez retirer");
							int amounTransfer = scan.nextInt();
							System.out.println("Saisissez le numéro de compte destinataire");
							long accountToTransfer = scan.nextLong();
							bankJob.transfert(userNumAccount, accountToTransfer, amounTransfer);
							System.out.println("Votre nouveau solde est "+ actualAccount.getBalance() + "€");
							break;
						case 4: 
							System.out.println(bankJob.consultAccount(actualAccount.getAccountId()));
							break;
						case 5: System.out.println(bankJob.consultAccount(actualAccount.getAccountId()).getListTransactions());
							break;
						case 6: 
							choiceUser=6;
							break;
						default:
							break;
						}
					} catch (Exception e) {
						scan.nextLine();
						System.out.println(e.getMessage()); 
					}
					
				}

			} catch (java.lang.Exception e) {
				System.out.println("Le compte demandé n'existe pas ");
				scan.nextLine();

			}
		}

	}
}
