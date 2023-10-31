package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {

	private Connection connection;
	private Scanner scanner;

	public AccountManager(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;

	}

	public void debit_money(long account_number) {
		try {
			scanner.nextLine();
			System.out.print("Enter amount :");
			double amount = scanner.nextDouble();
			scanner.nextLine();
			System.out.print("Enter ur Security Pin:");
			String security_pin = scanner.nextLine();
			connection.setAutoCommit(false);
			if (account_number != 0) {

				// Checking if security pin is valid or not
				PreparedStatement preparedstatement = connection
						.prepareStatement("SELECT*FROM accounts WHERE account_number=? AND security_pin=?");
				preparedstatement.setLong(1, account_number);
				preparedstatement.setString(2, security_pin);
				ResultSet resultset = preparedstatement.executeQuery();

				if (resultset.next()) {
					double current_balance = resultset.getDouble("balance");

					if (amount <= current_balance) {

						// Write Debit Query
						String debit_query = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
						PreparedStatement preparedstatement1 = connection.prepareStatement(debit_query);

						// Set the values in query
						preparedstatement1.setDouble(1, amount);
						preparedstatement1.setLong(2, account_number);
						int rowAffected = preparedstatement1.executeUpdate();
						if (rowAffected > 0) {
							System.out.println("RS" + amount + "Debited Succesfully");
							connection.commit();
							connection.setAutoCommit(true);

						} else {
							System.out.println("Transaction failed");
							connection.rollback();
							connection.setAutoCommit(true);
						}
					} else {
						System.out.println("Insufficent balance ");
					}
				} else {

					System.out.println("Invalid pin ");

				}

			}
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public void credit_money(long account_number) {
		try {
			scanner.nextLine();
			System.out.print("Enter amount :");
			double amount = scanner.nextDouble();
			scanner.nextLine();
			System.out.print("Enter ur Security Pin:");
			String security_pin = scanner.nextLine();
			connection.setAutoCommit(false);
			if (account_number != 0) {

				// Checking if security pin is valid or not
				PreparedStatement preparedstatement = connection
						.prepareStatement("SELECT*FROM accounts WHERE account_number=? AND security_pin=?");
				preparedstatement.setLong(1, account_number);
				preparedstatement.setString(2, security_pin);
				ResultSet resultset = preparedstatement.executeQuery();

				if (resultset.next()) {

					// Write Credit Query
					String credit_query = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
					PreparedStatement preparedstatement1 = connection.prepareStatement(credit_query);

					// Set values in the query
					preparedstatement1.setDouble(1, amount);
					preparedstatement1.setLong(2, account_number);
					int rowAffected = preparedstatement1.executeUpdate();
					if (rowAffected > 0) {
						System.out.println("RS" + amount + "Debited Succesfully");
						connection.commit();
						connection.setAutoCommit(true);

					} else {
						System.out.println("Transaction failed");
						connection.rollback();
						connection.setAutoCommit(true);
					}

				} else {

					System.out.println("Invalid pin ");

				}

			}
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public void transfer_money(long sender_account_number) {
		scanner.nextLine();
		System.out.print("Enter Sender account number:");
		long reciever_account_number = scanner.nextLong();
		System.out.print("Enter Amount :");
		double amount = scanner.nextDouble();
		scanner.nextLine();
		System.out.print("Enter Security pin:");
		String security_pin = scanner.nextLine();
		try {
			connection.setAutoCommit(false);
			if (sender_account_number != 0 && reciever_account_number != 0) {
				PreparedStatement preparedstatement1 = connection
						.prepareStatement("SELECT *FROM accounts WHERE account_number=? AND security_pin=?");
				preparedstatement1.setLong(1, sender_account_number);
				preparedstatement1.setString(2, security_pin);
				ResultSet resultset = preparedstatement1.executeQuery();

				if (resultset.next()) {
					double current_balance = resultset.getDouble("balance");
					if (amount <= current_balance) {

						// Write Query for Debit and Credit
						String debit_query = "UPDATE accounts SET balance=balance-? WHERE account_number=?";
						String credit_query = "UPDATE accounts SET balance=balance+? WHERE account_number=?";

						// Debit and Credit preparedStatement
						PreparedStatement debitquery = connection.prepareStatement(debit_query);
						PreparedStatement creditquery = connection.prepareStatement(credit_query);

						// Set Values for debit and credit prepared statements
						creditquery.setDouble(1, amount);
						creditquery.setLong(2, reciever_account_number);
						debitquery.setDouble(1, amount);
						debitquery.setLong(2, sender_account_number);

						int rowAffected1 = debitquery.executeUpdate();
						int rowAffected2 = creditquery.executeUpdate();

						if (rowAffected1 > 0 && rowAffected2 > 0) {

							System.out.println("Transfer Succesfull");
							System.out.println("RS:" + amount);
							connection.commit();
							connection.setAutoCommit(true);

						} else {
							System.out.println("Transfer Failed ");
							connection.rollback();
							connection.setAutoCommit(true);
						}

					} else {
						System.out.println("Insufficient Balance");
					}

				} else {
					System.out.println("Invaild Security pin");
				}

			} else {

				System.out.println("Invalid account number");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public void getBalance(long account_number) {
		try {
			scanner.nextLine();
			System.out.print("Enter ur Security Pin");
			String security_pin = scanner.nextLine();

			PreparedStatement preparedstatement = connection
					.prepareStatement("SELECT balance from accounts where account_number=? AND security_pin=? ");
			preparedstatement.setLong(1, account_number);
			preparedstatement.setString(2, security_pin);

			ResultSet resultset = preparedstatement.executeQuery();
			if (resultset.next()) {

				double balance = resultset.getDouble("balance");
				System.out.println("Balance : " + balance);

			} else {
				System.out.println("Invalid pin");
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

}
