package Banking_Management_System;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class User {
	private Connection connection;
	private Scanner scanner;

	public User(Connection connection, Scanner scanner) {
		this.connection = connection;
		this.scanner = scanner;
	}

	public void register() {
		try {
			scanner.nextLine();
			System.out.println("Full name: ");
			String full_name = scanner.nextLine();
			System.out.println("Email: ");
			String email = scanner.nextLine();
			System.out.println("Password: ");
			String password = scanner.nextLine();
			if (user_exist(email)) {
				System.out.println("User Already Exist for this Email Address!!");
				return;
			}
			String register_query = "INSERT INTO user(full_name,email,password) VALUES(?,?,?)";
			PreparedStatement preparedstatement = connection.prepareStatement(register_query);
			preparedstatement.setString(1, full_name);
			preparedstatement.setString(2, email);
			preparedstatement.setString(3, password);
			int row_affected = preparedstatement.executeUpdate();
			if (row_affected > 0) {
				System.out.println("Registration Succesfull");
			} else {
				System.out.println("Registration Failed");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public String login() {
		try {
			scanner.nextLine();
			System.out.println("Email: ");
			String email = scanner.nextLine();
			System.out.println("Password: ");
			String password = scanner.nextLine();
			String login_query = "SELECT *FROM user WHERE email=? AND password=?";

			PreparedStatement preparedstatement = connection.prepareStatement(login_query);
			preparedstatement.setString(1, email);
			preparedstatement.setString(2, password);
			ResultSet resultset = preparedstatement.executeQuery();
			if (resultset.next()) {
				return email;
			} else {
				return null;
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
		return null;
	}

	public boolean user_exist(String email) {
		try {
			String query = "SELECT * FROM user WHERE email=?";
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

}
