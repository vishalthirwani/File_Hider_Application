package service;

import java.util.Random;

public class GenerateOTP {
    public static String getOTP() {
        Random rand = new Random();
        return String.format("%04d", rand.nextInt(9999));
    }
}
