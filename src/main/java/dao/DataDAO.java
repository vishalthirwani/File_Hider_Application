package dao;

import db.MyConnection;
import model.Data;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataDAO {
    public static List<Data> getAllFiles(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("select * from data where email = ?");
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        List<Data> files = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String fileName = resultSet.getString("file_name");
            String path = resultSet.getString("path");
            Data data = new Data(id, fileName, path);
            files.add(data);
        }
        return files;
    }

    public static void hideFile(Data file) throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "insert into data(file_name, path, email, bin_data) values(?, ?, ?, ?)");
        statement.setString(1, file.getFileName());
        statement.setString(2, file.getPath());
        statement.setString(3, file.getEmail());
        File f = new File(file.getPath());
        FileReader fr = new FileReader(f);
        statement.setCharacterStream(4, fr, f.length());
        statement.executeUpdate();
        fr.close();
        f.delete();
    }

    public static void unhideFile(int id) throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("select path, bin_data from data where id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        String path = resultSet.getString("path");
        Clob clob = resultSet.getClob("bin_data");

        Reader r = clob.getCharacterStream();
        FileWriter fileWriter = new FileWriter(path);
        int i;
        while ((i = r.read()) != -1) {
            fileWriter.write((char)i);
        }
        fileWriter.close();
        statement = connection.prepareStatement("delete from data where id = ?");
        statement.setInt(1, id);
        statement.executeUpdate();
        System.out.println("File unhidden successfully");
    }
}
