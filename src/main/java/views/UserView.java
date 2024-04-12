package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    public UserView(String email) {
        this.email = email;
    }
    public void home() {
        do {
            System.out.println("Welcome " + this.email);
            System.out.println("Press 1 to show all hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");
            Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> {
                    try {
                        List<Data> Files = DataDAO.getAllFiles(this.email);
                        System.out.println("Id - File Name");
                        for (Data data : Files) {
                            System.out.println(data.getId() + " - " + data.getFileName());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path");
                    String path = scanner.nextLine();
                    File f = new File(path);
                    Data file = new Data(f.getName(), path, this.email);
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 -> {
                    List<Data> files;
                    try {
                        files = DataDAO.getAllFiles(this.email);
                        System.out.println("Id - File Name");
                        for (Data data : files) {
                            System.out.println(data.getId() + " - " + data.getFileName());
                        }
                        System.out.println("Enter the id of the file unhide");
                        int id = Integer.parseInt(scanner.nextLine());
                        boolean isValidId = false;
                        for (Data data : files) {
                            if (data.getId() == id) {
                                isValidId = true;
                                break;
                            }
                        }
                        if (isValidId) {
                            DataDAO.unhideFile(id);
                        } else {
                            System.out.println("Invalid id");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 0 -> System.exit(0);
            }
        } while (true);
    }
}
