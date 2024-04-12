package views;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;


public class Welcome {
    public void welcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to the Application");
        System.out.println("Press 1 to Login");
        System.out.println("Press 2 to Sign Up");
        System.out.println("Press 0 to Exit");

        int choice = 0;

        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (choice) {
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your name.");
        String name = sc.nextLine();
        System.out.println("Please enter your email.");
        String email = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.println("Please enter the OTP");
        String OTP = sc.nextLine();
        if (OTP.equals(genOTP)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);
            switch (response) {
                case 0 -> System.out.println("User already exists.");
                case 1 -> System.out.println("User successfully registered.");
            }
        } else {
            System.out.println("Invalid OTP.");
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your email address: ");
        String email = sc.nextLine();
        try {
            if (UserDAO.isExists(email)) {
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.println("Please enter the OTP");
                String OTP = sc.nextLine();
                if (OTP.equals(genOTP)) {
                    new UserView(email).home();
                } else {
                    System.out.println("Invalid OTP.");
                }
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
