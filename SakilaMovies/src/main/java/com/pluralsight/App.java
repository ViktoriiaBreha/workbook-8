package com.pluralsight;

import java.sql.*;
import org.apache.commons.dbcp2.BasicDataSource;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String url = "jdbc:mysql://127.0.0.1:3306/sakila";
        String user = args[0];
        String password = args[1];

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        ResultSet results = null;
        Statement statement = null;

        try(Connection connection = dataSource.getConnection()){
            boolean run = true;

            while (run){
                System.out.println("Welcome to our \"SEARCH MOVIE\"");
                System.out.println("Choose from the next options");
                System.out.println("1) Choose movie by last name of actor that you like");
                System.out.println("2) Choose movie by first and last names of actor that you like");
                System.out.println("0) Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice){
                    case 1:
                        searchByLastName(connection, scanner);
                        break;
                    case 2:
                        searchByFullName(connection, scanner);
                        break;
                    case 0:
                        System.out.println("Thank you for using our app!");
                        run = false;
                        break;
                    default:
                        System.out.println("Invalid input, try again");
                        break;

                }
            }
        } catch (SQLException e){
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

    public static void searchByLastName (Connection connection, Scanner scanner){

        System.out.print("Enter actor's last name: ");
        String lastName = scanner.nextLine().toLowerCase();

        String query = "SELECT first_name , last_name FROM actor WHERE last_name = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, lastName);

            try (ResultSet resultSet = statement.executeQuery()){
                System.out.printf("%-20s %-20s%n", "first_name", "last_name");

                while(resultSet.next()){

                    String firstName = resultSet.getString("first_name");
                    String lastNamePrint = resultSet.getString("last_name");

                    System.out.printf("%-20s %-5s%n", firstName, lastNamePrint);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void searchByFullName (Connection connection, Scanner scanner){
        System.out.print("Enter actor's first name: ");
        String firstName1 = scanner.nextLine().toLowerCase();

        System.out.print("Enter actor's last name: ");
        String lastName1 = scanner.nextLine().toLowerCase();

        String query = "SELECT first_name, last_name, title FROM actor AS a JOIN film_actor AS fa JOIN film AS f ON a" +
                ".actor_id=fa.actor_id AND fa.film_id = f.film_id WHERE first_name = ? AND last_name =? ";

        try (PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, firstName1);
            statement.setString(2, lastName1);

            try(ResultSet resultSet = statement.executeQuery()){

                System.out.printf("%-20s %-20s %-5s%n", "first_name", "last_name", "title");

                while (resultSet.next()){
                    String firstName = resultSet.getString("first_name");
                    String lastNamePrint = resultSet.getString("last_name");
                    String title = resultSet.getString("title");

                    System.out.printf("%-20s %-20s %-5s%n", firstName,lastNamePrint,title);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
