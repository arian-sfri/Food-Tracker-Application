package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    /**
     * Method to load user info from file
     * @param file file containing information about user
     * @return user object created with information from file
     */
    public static User loadUser(File file) {
        User user = null;
        if (file.exists() && file.isFile() && file.canRead()) {
            try {
                //Reading first line of file
                FileReader FR = new FileReader(file);
                BufferedReader buffered_reader = new BufferedReader(FR);
                String line = buffered_reader.readLine();

                //Splitting text on line from commas
                String[] parts = line.split(",");
                //Collecting information from line
                int age = Integer.parseInt(parts[0]);
                double weight = Double.parseDouble(parts[1]);
                double height = Double.parseDouble(parts[2]);
                String gender = parts[3];
                int activity_rate = Integer.parseInt(parts[4]);
                //Creating user object and returning it
                user = new User(age, weight, height, gender, activity_rate);
                return user;
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Exception!");
                System.exit(1);
            }
        }
        return user;
    }
}

