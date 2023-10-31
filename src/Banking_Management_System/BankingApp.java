/**
 * 
 */
package Banking_Management_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 
 */
public class BankingApp {

	/**
	 * @VipinKumar
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		try {
			Connection connection = JDBCutils.getConnection();
			Scanner scanner = new Scanner(System.in);
			User user = new User(connection, scanner);
			Account accounts = new Account(connection, scanner);
			AccountManager accountManager = new AccountManager(connection, scanner);

			String email;
			long account_number;

			while (true) {
				System.out.println("***Welcome to Banking System");
				System.out.println();
				System.out.println("1 Register");
				System.out.println("2 Login");
				System.out.println("3 Exit");
				System.out.println("Enter ur choice:");
				int choice = scanner.nextInt();

				switch (choice) {
				case 1:
					user.register();
					break;
				case 2:
					email = user.login();
					if (email != null) {
						System.out.println();
						System.out.println("User logged In!");
						if (!accounts.account_exist(email)) {
							System.out.println();
							System.out.println("1 Open a new Bank account");
							System.out.println("2 Exit ");
							if (scanner.nextInt() == 1) {
								account_number = accounts.open_account(email);
								System.out.println("Account Created Sucessfully");
								System.out.println("Your account Number is: " + account_number);
							} else {
								break;
							}

						}
						account_number = accounts.getAccountNumber(email);
						int choice1 = 0;
						while (choice1 != 5) {
							System.out.println("1 Debit Money");
							System.out.println("2 Credit Money");
							System.out.println("3 Transfer Money ");
							System.out.println("4 Check Blance");
							System.out.println("5 Log out");
							System.out.println("Enter your choice ");

							choice1 = scanner.nextInt();
							switch (choice1) {
							case 1:
								accountManager.debit_money(account_number);
								break;
							case 2:
								accountManager.credit_money(account_number);
								break;
							case 3:
								accountManager.transfer_money(account_number);
								break;
							case 4:
								accountManager.getBalance(account_number);
								break;

							case 5:
								break;
							default:
								System.out.println("Enter valid Choice!");
								break;
							}

						}
					} else {
						System.out.println(" Incorrect Email or Password");
					}
				case 3:
					System.out.println("THANK YOU FOR USING BANKING SYSTEM !!!");
					System.out.println("Exiting System!");
					return;
				default:
					System.out.println("Enter a valid choice ");
					break;
				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
