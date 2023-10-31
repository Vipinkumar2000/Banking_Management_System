package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Account {
	private Connection connection;
	private Scanner scanner;

	public Account(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}

	public boolean account_exist(String email) {
		try {
			String query = "SELECT  account_number from accounts WHERE email=?";
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setString(1, email);
			ResultSet resultset = preparedstatement.executeQuery();
			if (resultset.next()) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return false;
	}

	public long open_account(String email) {
		try {
			if (!account_exist(email)) {
				String open_account_query = "INSERT INTO accounts(account_number,full_name,email,balance,security_pin)VALUES(?,?,?,?,?)";

				scanner.nextLine();
				System.out.print("Enter ur fullname:");
				String full_name = scanner.nextLine();
				System.out.print("Enter Initial amount: ");
				double balance = scanner.nextDouble();
				scanner.nextLine();
				System.out.print("Enter ur security pin: ");
				String security_pin = scanner.nextLine();

				long account_number = genrateAccountNumber();
				PreparedStatement preparedstatement = connection.prepareStatement(open_account_query);

				preparedstatement.setLong(1, account_number);
				preparedstatement.setString(2, full_name);
				preparedstatement.setString(3, email);
				preparedstatement.setDouble(4, balance);
				preparedstatement.setString(5, security_pin);

				int rowAffected = preparedstatement.executeUpdate();

				if (rowAffected > 0) {

					return account_number;

				} else {
					throw new RuntimeException("Account creation failed");
				}
			}
		} catch (SQLException e) {

			e.printStackTrace();

		}
		throw new RuntimeException("Account already exist");

	}

	private long genrateAccountNumber() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultset = statement
					.executeQuery("SELECT account_number from accounts ORDER BY account_number LIMIT 1");
			if (resultset.next()) {
				long last_account_number = resultset.getLong("account_number");
				return last_account_number + 1;
			} else {
				return 10000100;
			}
		} catch (SQLException e) {

		}
		return 10000100;
	}

	public long getAccountNumber(String email) {
		try {
			String query = "SELECT account_number FROM accounts WHERE email=?";
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			preparedstatement.setString(1, email);

			ResultSet resultset = preparedstatement.executeQuery();
			if (resultset.next()) {
				return resultset.getLong("account_number");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Account number doen't Exist!");
	}

}
