package com.pluralsight;

import java.sql.*;

public class App2 {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/northwind",
                    "root",
                    "yearup"
            );

            Statement statement = connection.createStatement();

            String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

            ResultSet results = statement.executeQuery(query);


            while (results.next()) {
                int id = results.getInt("ProductID");
                String productName = results.getString("ProductName");
                double price = results.getDouble("UnitPrice");
                int stock = results.getInt("UnitsInStock");

                System.out.println("Product id: " + id);
                System.out.println("Name: " + productName);
                System.out.printf("Price: $%.2f\n ", price);
                System.out.println("Stock: " + stock);
                System.out.println(" ");
                System.out.println("------------------");
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }

