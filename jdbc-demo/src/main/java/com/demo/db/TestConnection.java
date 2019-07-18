package com.demo.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {

	public static void main(String[] args) throws Exception {
		String URL = "jdbc:mysql://localhost:3306/springdb";
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection(URL, "root", "root");
		if (conn != null) {
			System.out.println("Got the connection");
		}

	}

}
