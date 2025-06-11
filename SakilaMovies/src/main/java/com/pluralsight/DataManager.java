package com.pluralsight;

import com.pluralsight.Actor;
import com.pluralsight.Film;
import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private BasicDataSource dataSource;

    public DataManager(DataSource dataSource) {
        this.dataSource = (BasicDataSource) dataSource;
    }

    public DataManager(String url, String username, String password) {
        this.dataSource = new BasicDataSource();
        this.dataSource.setUrl(url);
        this.dataSource.setUsername(username);
        this.dataSource.setPassword(password);
    }

    public List<Actor> searchActorByName (String name){
        List<Actor> actors = new ArrayList<>();

        String query = "SELECT actor_id, first_name, last_name FROM actor WHERE first_name LIKE ? OR last_name LIKE ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1,"%" + name + "%");
            preparedStatement.setString(2, "%" + name + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int actorId = resultSet.getInt("actor_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                actors.add(new Actor(actorId, firstName ,lastName));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return actors;
    }

    public List <Film> getFilmsByActorId (int actorID){
        
    }

}
