package main;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Exercise {
    //Hashmap that stores information about different exercises
    public static HashMap<String, Double> exercises = new HashMap<>();

    /**
     * Constructor that reads file containing info about different exercises and stores in hashmap
     */
    public Exercise() {
        File file = new File("exercise_info.csv");

        if (file.exists() && file.isFile() && file.canRead()) {

            try {
                //reads the file
                FileReader FR = new FileReader(file);
                BufferedReader buffered_reader = new BufferedReader(FR);
                String line = buffered_reader.readLine();

                while (line != null) {
                    String[] parts = line.split(",");
                    String name = parts[0];
                    //puts the file's info in a hashmap
                    exercises.put(name, Double.parseDouble(parts[1]));

                    line = buffered_reader.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Exception!");
                System.exit(1);

            }

        } else {
            System.err.println("Can not access the file to read it!");
            System.exit(1);
        }
    }

    /**
     * Method for user to select an exercise they've done
     *
     * @param user user object
     * @param exercise_name name of exercise
     * @param exercise_hours number of hours exercise was done
     */
    public void exercise(User user, String exercise_name, double exercise_hours) {
        exercise_name = exercise_name.toLowerCase();
        //adds the burned calories to the total burned calories and the ArrayList
        double calories_burned = ((exercises.get(exercise_name)) * (exercise_hours));
        user.addCaloriesBurned(calories_burned);
        user.exercise_input.add((exercises.get(exercise_name)) * (exercise_hours));
    }

    /**
     * this function is called when the desirable exercise is not in the pre-made list
     *
     * @param exercise_name name of exercise
     * @param burned_cals number of calories exercise burns per hour
     */
    public void input_exercise(String exercise_name, double burned_cals) {
        exercise_name = exercise_name.toLowerCase();
        //adds the burned calories to the total burned calories and to the ArrayList
        exercises.put(exercise_name, burned_cals);
    }

    /**
     * This function tells how many calories a particular exercise burns.
     * @param entered_exercise name of exercise
     */
    public String print_exercise_info(String entered_exercise) {
        entered_exercise = entered_exercise.toLowerCase();
        String details = String.format("%s burns %.2f calories per hour", entered_exercise, exercises.get(entered_exercise));
        return details;
    }

    /**
     * Method to save information about exercise to file
     */
    public void save_exercise() {
        File exercise_file = new File("exercise_info.csv");
        //Saving exercise info
        if (exercise_file.exists() && exercise_file.isFile() && exercise_file.canWrite()) {
            try {
                FileWriter FW = new FileWriter(exercise_file);
                PrintWriter PW = new PrintWriter(FW);

                for (String item : Exercise.exercises.keySet()) {
                    //here, we loop through the exercises key and save the name of the exercise and the calorie it burns
                    String s = "";
                    s += item;
                    s += ",";
                    s += Arrays.toString(new Double[]{Exercise.exercises.get(item)});
                    String NoLeftBracket = s.replace("[", " ");
                    String NoBracket = NoLeftBracket.replace("]", "");
                    PW.println(NoBracket);
                    s = "";

                }

                PW.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Cannot write to the file!");
                System.exit(1);
            }
        } else {
            System.out.println("Cannot access the file to write it!");
            System.exit(1);
        }
    }

    /**
     * Method that returns an ArrayList containing all the exercise names in the hashmap
     * @return
     */
    public ArrayList GetExerciseName(){
        ArrayList Exercise_names = new ArrayList();
        //Looping through keys in hashmap and adding to ArrayList
        for(String item : exercises.keySet()){
            Exercise_names.add(item);
        }
        return Exercise_names;
    }

    /**
     * Method that returns whether or not an exercise already exists in hashmap
     * @param exercise_name name of exercise
     * @return true or false based on if the exercise exists in hashmap
     */
    public Boolean containsKey(String exercise_name){
        //Checking keys in hashmap to see if exercise already exists
        return exercises.keySet().contains(exercise_name);
    }
}


