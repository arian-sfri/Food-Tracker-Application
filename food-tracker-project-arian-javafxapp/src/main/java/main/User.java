package main;

import java.util.ArrayList;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class User {
    //Global variables to store info about User
    protected double height, weight;
    protected int age, activity_rate, cups_needed;
    private int cups;
    protected String gender;
    private double calories_sum = 0;
    protected double calories_needed = 0;
    private double calories_burned = 0;
    private double fat_sum = 0;
    private double protein_sum = 0;
    private double carbohydrate_sum = 0;
    private double sugars_sum = 0;

    /**
     * Adder method to add to variables when select_food is called
     *
     * @param calories_sum     total sum of calories
     * @param fat_sum          total sum of fat
     * @param protein_sum      total sum of proteins
     * @param carbohydrate_sum total sum of carbs
     * @param sugars_sum       total sum of sugars
     */
    public void select_food_add(double calories_sum, double fat_sum, double protein_sum, double carbohydrate_sum, double sugars_sum) {
        this.calories_sum += calories_sum;
        this.fat_sum += fat_sum;
        this.protein_sum += protein_sum;
        this.carbohydrate_sum += carbohydrate_sum;
        this.sugars_sum += sugars_sum;
    }

    /**
     * Adder method to add to calories_burned when exercise is called
     *
     * @param calories_burned total calories_burned
     */
    public void addCaloriesBurned(double calories_burned) {
        this.calories_burned += calories_burned;
    }

    /**
     * Adder method to add to cups when add_water is called
     *
     * @param cups total cups of water drank
     */
    public void addCups(int cups) {
        this.cups += cups;
    }

    //ArrayLists to keep track of consumption to get the average
    protected ArrayList<Double> calories_input = new ArrayList<Double>();
    protected ArrayList<Double> fat_input = new ArrayList<Double>();
    protected ArrayList<Double> protein_input = new ArrayList<Double>();
    protected ArrayList<Double> carbohydrate_input = new ArrayList<Double>();
    protected ArrayList<Double> sugars_input = new ArrayList<Double>();
    protected ArrayList<Double> exercise_input = new ArrayList<Double>();

    /**
     * User constructor when file is given in command line
     *
     * @param age           age of user
     * @param weight        weight of user
     * @param height        height of user
     * @param gender        gender of user
     * @param activity_rate activity rate of user
     */
    public User(int age, double weight, double height, String gender, int activity_rate) {
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.activity_rate = activity_rate;
        //Calculating calories_needed
        this.calorie_calc();
    }

    /**
     * Method to calculate calories needed in a day
     */
    public void calorie_calc() {
        //Basal Metabolic Rate
        double BMR = 0;
        //Calculating BMR for when the user is a woman
        if (this.gender.equals("F")) {
            BMR = (this.height * 4.35) + (this.weight * 4.7) - (this.age * 4.7) + 655;
        }
        //Calculating BMR for when the user is a man
        else if (this.gender.equals("M")) {
            BMR = (this.height * 6.23) + (this.weight * 12.7) - (this.age * 6.8) + 66;
        }

        //Calculating the amount of calories a person needs to consume based on their BMR and activity rate
        switch (this.activity_rate) {
            case 1:
                //Sedentary
                this.calories_needed = BMR * 1.2;
                break;
            case 2:
                //Light exercise
                this.calories_needed = BMR * 1.375;
                break;
            case 3:
                //Moderate exercise
                this.calories_needed = BMR * 1.55;
                break;
            case 4:
                //Exercises daily
                this.calories_needed = BMR * 1.725;
                break;
        }
    }

    /**
     * Calculating cups needed in a day
     */
    public String cup_calc() {
        String output = "";
        //User is under 18
        if (this.age < 18) {
            this.cups_needed = 8;
            int cups_left = this.cups_needed - this.cups;
            if (this.cups < this.cups_needed) {
                output = String.format("\nYou need to drink %d more cups of water!\n", cups_left);
            } else {
                output = ("\nYou have had enough water today\n");
            }
            //User is over 18
        } else {
            //User is female
            if (this.gender.equals("F")) {
                this.cups_needed = 9;
                int cups_left = this.cups_needed - this.cups;
                if (this.cups < this.cups_needed) {
                    output = String.format("\nYou need to drink %d more cups of water!\n", cups_left);
                } else {
                    output = ("\nYou have had enough water today\n");
                }
                //User is male
            } else if (this.gender.equals("M")) {
                this.cups_needed = 13;
                int cups_left = this.cups_needed - this.cups;
                if (this.cups < this.cups_needed) {
                    output = String.format("\nYou need to drink %d more cups of water!\n", cups_left);
                } else {
                    output = ("\nYou have had enough water today\n");
                }
            }
        }
        return output;
    }

    /**
     * Calculates the average of all the values in an ArrayList
     *
     * @param list ArrayList with values we are going to use to calculate the average
     * @return average of all the values in the ArrayList
     */
    public double get_list_average(ArrayList<Double> list) {
        double sum = 0;
        //Looping through ArrayList to calculate the sum of all the values
        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }
        //Calculating the average of the ArrayList by dividing the sum by the Lists size
        double average = (sum / list.size());
        return average;
    }

    /**
     * New menu that shows different statistics about the user's diet
     * @param input health chart item selected
     */
    public String health_chart(int input) {
        String output = "";
        switch (input) {
            case 1:
                //Prints how many calories the user has burned and how much more they need to eat or if they've eaten enough
                output = String.format("You have consumed %.2f calories today\n", this.calories_sum);
                if (this.calories_sum < this.calories_needed) {
                    double calories_left = this.calories_needed - this.calories_sum;
                    output += String.format("\nYou need to consume %.2f more calories\n", calories_left);
                } else {
                    output += String.format("\nYou have consumed enough calories!\n");
                }
                double average_calories = get_list_average(this.calories_input);
                output += String.format("\nYou have consumed an average of %.2f calories\n\n", average_calories);
                break;
            case 2:
                //Prints total and average fat consumption
                output = String.format("\nYou have consumed %.2f g of fat\n", this.fat_sum);
                double fat_average = get_list_average(this.fat_input);
                output += String.format("\nYou have consumed an average of %.2f g of fat\n", fat_average);
                break;
            case 3:
                //Prints total and average protein consumption
                output = String.format("\nYou have consumed %.2f g of protein\n", this.protein_sum);
                double protein_average = get_list_average(this.protein_input);
                output += String.format("\nYou have consumed an average of %.2f g of protein\n", protein_average);
                break;
            case 4:
                //Prints total and average carbohydrate consumption
                output = String.format("\nYou have consumed %.2f g of carbohydrates\n", this.carbohydrate_sum);
                double carb_average = get_list_average(this.carbohydrate_input);
                output += String.format("\nYou have consumed an average of %.2f g of carbohydrates\n", carb_average);
                break;
            case 5:
                //Prints total and average sugar consumption
                output = String.format("\nYou have consumed %.2f g of sugar\n", this.sugars_sum);
                double sugar_average = get_list_average(this.sugars_input);
                output += String.format("\nYou have consumed an average of %.2f g of sugar\n", sugar_average);
                break;
            case 6:
                //prints how many cups of water the user has had and if they need to drink more or if they've had enough
                int cups_needed;
                output = String.format("\nYou have had %d cups of water today\n", this.cups);
                output += this.cup_calc();
                break;
            case 7:
                //Prints total and average calories burned by exercising
                output = String.format("You have burned %.2f calories by exercising\n", this.calories_burned);
                double exercise_average = get_list_average(this.exercise_input);
                output += String.format("You have burned an average of %.2f calories", exercise_average);
                break;
            case 8:
                //Prints a total list of all the user's inputs
                output = String.format("\nYou have consumed a total of:\n");
                output += String.format("%.2f Calories\n", this.calories_sum);
                output += String.format("%.2f g of Fat\n", this.fat_sum);
                output += String.format("%.2f g of Protein\n", this.protein_sum);
                output += String.format("%.2f g of Carbohydrates\n", this.carbohydrate_sum);
                output += String.format("%.2f g of Sugar\n", this.sugars_sum);
                output += String.format("%d cups of Water\n", this.cups);
                output += String.format("And you have burned %.2f calories by exercising\n", this.calories_burned);
                break;
        }
        //Returning string containing information to be shown based on health chart item selected
        return output;
    }

    /**
     * Method to save information about user
     * @param user_file file where user info will be saved
     */
    public void save_user(File user_file) {
        //Saving user info
        if (!user_file.exists()) {
            //creates the user_info file if it does not exist
            try {
                user_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (user_file.exists() && user_file.isFile() && user_file.canWrite()) {
            //if the user_info file exists, we overwrite the user's information
            try {
                String user_information = "";
                user_information += Integer.toString(this.age) + "," + Double.toString(this.weight) + "," + Double.toString(this.height) + "," + this.gender
                        + "," + Integer.toString(this.activity_rate);
                FileWriter FW = new FileWriter(user_file);
                PrintWriter PW = new PrintWriter(FW);
                PW.println(user_information);
                PW.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Cannot write to the file!");
                System.exit(1);
            }
        }
    }

    @Override
    /**
     * The toString method return a string in which we have the info of the user
     * @return userInfo
     */
    public String toString(){
        String userInfo = "";
        userInfo+="Age: "+this.age+"\n";
        userInfo+="Height: "+this.height+" in.\n";
        userInfo+="Weight: "+this.weight+" lbs.\n";
        if (gender.equals("M")){
            userInfo+="Gender: Male"+"\n";
        }
        else if (gender.equals("F")){
            userInfo+="Gender: Female"+"\n";
        }
        userInfo+="Activity Rate: "+this.activity_rate;
        //returns the user's info in a string
        return userInfo;
    }


    @Override
    /**
     * equals method that checks whether 2 users are equal to each other based on their age, height, weight, gender and activity rate
     * @return true or false based on whether the 2 users are equal or not
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        //Comparing age, height, weight, gender and activity rate
        return Double.compare(user.height, height) == 0 && Double.compare(user.weight, weight) == 0 && age == user.age && activity_rate == user.activity_rate && gender.equals(user.gender);
    }

    @Override
    /**
     * Hashcode method that returns that hashcode of height, weight, age, activity_rate and gender
     */
    public int hashCode() {
        return Objects.hash(height, weight, age, activity_rate, gender);
    }
}