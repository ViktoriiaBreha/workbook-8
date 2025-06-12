package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class App2 {
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

        DataManager dataManager = new DataManager(dataSource);

        try (Connection connection = dataSource.getConnection()){
             boolean run = true;
             while (run){
                 System.out.println("Choose from the next options: ");
                 System.out.println("1) Search actor by name");
                 System.out.println("2) Search movie by actor's id");
                 System.out.println("0) Exit ");
                 System.out.print("Enter your answer: ");
                 int choice  = scanner.nextInt();
                 scanner.nextLine();

                 switch (choice){
                     case 1:
                         System.out.print("Enter the name: ");
                         String name = scanner.nextLine().toLowerCase();
                         List<Actor> actors = dataManager.searchActorByName(name);
                         if (actors.isEmpty()) {
                             System.out.println("No actors found.");
                         } else {
                             for (Actor actor : actors){
                                 System.out.println(actor.getActorID() + " " + actor.getFirstName() + " " + actor.getLastName());
                             }
                         }
                         break;
                     case 2:
                         System.out.print("Enter actor's id: ");
                         int id = scanner.nextInt();
                         List<Film> films = dataManager.getFilmsByActorId(id);
                         if (films.isEmpty()) {
                             System.out.println("No films found for this actor.");
                         } else {
                             for (Film film : films){
                                 System.out.println(film.getFilmId() + " " + film.getTitle() + " " + film.getDescription() + " " + film.getReleaseYear() + " " + film.getLength());
                             }
                         }
                         break;
                     case 0:
                         System.out.println("Thank you for using our app!");
                         run = false;
                         break;
                     default:
                         System.out.println("Invalid input, try again!");
                 }
             }

        } catch (SQLException e){
            e.printStackTrace();
        }

    }
}
