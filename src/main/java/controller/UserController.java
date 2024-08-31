package controller;

import db.DBConnection;
import javafx.scene.control.Alert;
import modle.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class UserController implements UserService{
    @Override
    public boolean addTask(User user) {
        try {
            String SQL = "INSERT INTO ToDoList VALUES(?,?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setObject(1,user.getId());
            pstm.setObject(2, user.getDescription());
            pstm.setObject(3, Date.valueOf(user.getDate()));
            pstm.setObject(4,user.getCompleted());
            return pstm.executeUpdate() > 0;


        }catch (SQLException e){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully added your task..."+e.getMessage()).show();
        }
        return false;
    }

    @Override
    public boolean deleteTask(String id) {
        String SQL = "DELETE FROM ToDoList WHERE  task_id='" + id + "'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            return connection.createStatement().executeUpdate(SQL) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<User> fetchTasksFromDatabase() {
        List<User> tasks = new ArrayList<>();

        try {
            String query = "SELECT * FROM ToDoList;";
            Connection connection = DBConnection.getInstance().getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("datafech");

            while (rs.next()) {
                String description = rs.getString("task_description");
                System.out.println("Description: " + description);

                Date sqlDate = rs.getDate("completion_date");
                LocalDate date = (sqlDate != null) ? sqlDate.toLocalDate() : null;
                System.out.println("Date: " + date);

                tasks.add(new User(description, date));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Fetched " + tasks.size() + " tasks.");
        return tasks;
    }

    public static boolean updateTaskCompletion(int isCompleted, String description) {
        String query = "UPDATE tasks SET completed = ? WHERE description = ?";

        try (Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (conn == null || conn.isClosed()) {
                throw new SQLException("Connection is not valid.");
            }

            stmt.setInt(1, isCompleted);
            stmt.setString(2, description);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("SQL error during update: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }



}
