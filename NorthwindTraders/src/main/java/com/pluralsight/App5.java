package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.Scanner;

public class App5 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String url = "jdbc:mysql://127.0.0.1:3306/northwind";
        String user = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        ResultSet results = null;
        Statement statement = null;

        try (Connection connection = dataSource.getConnection()) {
            boolean run = true;

            while (run){
                System.out.println("What do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("3) Display all categories");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        displayProducts(connection);
                        break;
                    case 2:
                        displayCustomers(connection);
                        break;
                    case 3:
                        displayCategories(connection, scanner);
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
        }
    }
    public static void displayProducts (Connection connection){
        String query = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            System.out.printf("%-5s %-35s %-10s %-5s%n", "Id", "Name", "Price", "Stock");
            System.out.println("-------------------------------------------------------------");


            while (resultSet.next()) {
                int id = resultSet.getInt("ProductID");
                String name = resultSet.getString("ProductName");
                double price = resultSet.getDouble("UnitPrice");
                int stock = resultSet.getInt("UnitsInStock");

                System.out.printf("%-5d %-35s $%-9.2f %-5d%n", id, name, price, stock);
            }
        }catch (SQLException e) {
            System.out.println("Error displaying products: " + e.getMessage());
        }

    }

    public static void displayCustomers (Connection connection){
        String query2 = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers";
        try(Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(query2)){
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
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void displayCategories (Connection connection, Scanner scanner){
        String query3 = "SELECT CategoryID, CategoryName FROM Categories";
        try(Statement statement = connection.createStatement();
            ResultSet categories = statement.executeQuery(query3)) {
            System.out.printf("%-20s %-20s%n", "CategoryID", "CategoryName");
            System.out.println("------------------------------------------");

            while (categories.next()){
                int catId = categories.getInt("CategoryID");
                String catName = categories.getString("CategoryName");
                System.out.printf("%-20s %-20s%n" , catId, catName);
            }
            System.out.print("Enter category id: ");
            int categoryID = scanner.nextInt();

            String queryWithID = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products WHERE " +
                    "CategoryID = ?";

            try(PreparedStatement ps = connection.prepareStatement(queryWithID)){
                ps.setInt(1,categoryID);

                try(ResultSet resultSet = ps.executeQuery()){
                    System.out.printf("%-5s %-35s %-10s %-5s%n", "Id", "Name", "Price", "Stock");
                    System.out.println("-------------------------------------------------------------");

                    boolean run2;
                    while (resultSet.next()){
                        run2 = true;
                        int id = resultSet.getInt("ProductID");
                        String name = resultSet.getString("ProductName");
                        double price = resultSet.getDouble("UnitPrice");
                        int stock = resultSet.getInt("UnitsInStock");

                        System.out.printf("%-5d %-35s $%-9.2f %-5d%n", id, name, price, stock);
                    }

                    if (run2 = false){
                        System.out.println("Invalid input, try again!");
                    }
                }
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

