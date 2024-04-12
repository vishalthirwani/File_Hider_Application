package dao;

import db.MyConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static boolean isExists(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("select email from users");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String userEmail = resultSet.getString("email");
            if (userEmail.equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static int saveUser(User user) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("insert into users values(default, ?, ?)");
        statement.setString(1, user.getName());
        statement.setString(2, user.getEmail());
        return statement.executeUpdate();
    }
}
