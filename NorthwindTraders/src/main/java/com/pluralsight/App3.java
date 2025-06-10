package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class App3 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = "root";
        String password = "yearup";

        ResultSet results = null;
        Connection connection = null;
        Statement statement = null;

        try{
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            boolean run = true;

            while (run){
                System.out.println("What do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";
                        results = statement.executeQuery(query);

                        System.out.printf("%-5s %-35s %-10s %-5s%n", "Id", "Name", "Price", "Stock");
                        System.out.println("-------------------------------------------------------------");


                        while (results.next()) {
                            int id = results.getInt("ProductID");
                            String name = results.getString("ProductName");
                            double price = results.getDouble("UnitPrice");
                            int stock = results.getInt("UnitsInStock");

                            System.out.printf("%-5d %-35s $%-9.2f %-5d%n", id, name, price, stock);
                        }
                        results.close();
                        break;
                    case 2:
                        String query2 = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers";
                        results = statement.executeQuery(query2);

                        System.out.printf("%-20s %-35s %-20s %-35s %-20s%n", "Contact Name", " Company Name", "City",
                                "Country" , "Phone");
                        System.out.println(
                                "--------------------------------------------------------------------------------------------------------------------------------");

                        while (results.next()){
                            String contactName = results.getString("ContactName");
                            String companyName = results.getString("CompanyName");
                            String city = results.getString("City");
                            String country = results.getString("Country");
                            String number = results.getString("Phone");

                            System.out.printf("%20s %-35s %-20s %-35s %-20s%n", contactName, companyName, city,
                                    country, number);
                        }
                        results.close();
                        break;
                    case 0:
                        System.out.println("Thank you!");
                        run = false;
                        break;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();

        }finally {
            if (results != null) {
                try {
                    results.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e ) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

    }
    }

