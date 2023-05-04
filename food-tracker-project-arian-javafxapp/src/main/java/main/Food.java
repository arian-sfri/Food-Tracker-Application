package main;

import java.io.*;
import java.util.*;
import java.lang.*;

public class Food {

    //Hashmap to store information about different food
    public static HashMap<String, double[]> foods = new HashMap<>();

    /**
     * Constructor that reads the file containing information about different food and stores them in a hashmap
     */
    public Food() {
        File file = new File("food_info.csv");

        if (file.exists() && file.isFile() && file.canRead()) {

            try {
                //reads the file
                FileReader FR = new FileReader(file);
                BufferedReader buffered_reader = new BufferedReader(FR);
                String line = buffered_reader.readLine();

                while (line != null) {
                    String[] parts = line.split(",");
                    String name = parts[0];

                    //Storing values in array
                    double[] AR = new double[5];
                    for (int i = 1 ; i<6; i++){

                        AR[i-1] = Double.parseDouble(parts[i]);

                    }
                    //puts the file's info in a hashmap
                    foods.put(name, AR);

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
     * Method for user to select food they've consumed
     * @param user User object
     * @param food name of food
     * @param amount amount consumed
     */
    public void select_food(User user, String food, double amount) {
        //adds the food information to the global variables. (For example, adds the consumed fat to the total consumed fat.)
        try {
            food = food.toLowerCase();
            double ratio = amount / 100;
            double[] food_val = foods.get(food);

            double food_cal = food_val[0];
            double calories_sum = food_cal * ratio;

            double food_fat = food_val[1];
            double fat_sum = food_fat * ratio;

            double food_protein = food_val[2];
            double protein_sum = food_protein * ratio;

            double food_carbohydrate = food_val[3];
            double carbohydrate_sum = food_carbohydrate * ratio;

            double food_sugars = food_val[4];
            double sugars_sum = food_sugars * ratio;

            user.select_food_add(calories_sum, fat_sum, protein_sum, carbohydrate_sum, sugars_sum);
            //Adding consumed food nutrients to ArrayLists
            user.calories_input.add(food_cal * ratio);
            user.fat_input.add(food_fat * ratio);
            user.protein_input.add(food_protein * ratio);
            user.carbohydrate_input.add(food_carbohydrate * ratio);
            user.sugars_input.add(food_sugars * ratio);
            //User does not enter food that is in the list
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Input!");
            System.exit(1);
        }
    }

    /**
     * Through this function, the user can add a new food and its information (such as calories, fat, etc.)
     * @param food name of food
     * @param calories number of calories in food
     * @param fat amount of fat in food
     * @param protein amount of protein in food
     * @param carbohydrate amount of carbs in food
     * @param sugars amount of sugar in food
     */
    public void input_food(String food, double calories, double fat, double protein, double carbohydrate, double sugars) {
        //gets the food and its info and stores them in a HashMap.
        double[] food_info = {calories, fat, protein, carbohydrate, sugars};
        food = food.toLowerCase();
        foods.put(food, food_info);
    }

    /**
     * this function prints the nutrition of the entered food
     * @param entered_food name of food
     */
    public String print_food_info(String entered_food) {

        //gets the information of the entered food
        double[] food_val = foods.get(entered_food);
        double food_cal = food_val[0];
        double food_fat = food_val[1];
        double food_protein = food_val[2];
        double food_carbohydrate = food_val[3];
        double food_sugars = food_val[4];
        String details = String.format("In every 100g, %s has:\n" +
                "%.2f calories\n" +
                "%.2f g fats\n" +
                "%.2f g protein\n" +
                "%.2f g carbohydrates\n" +
                "%.2f g sugars", entered_food, food_cal, food_fat, food_protein, food_carbohydrate, food_sugars);
        return details;
    }

    /**
     * Method to save hashmap containing food info to file
     */
    public void save_food(){
        File food_file = new File("food_info.csv");

        //Saving food info
        if (food_file.exists() && food_file.isFile() && food_file.canWrite()){
            try{
                FileWriter FW = new FileWriter(food_file);
                PrintWriter PW = new PrintWriter(FW);

                for (String item : Food.foods.keySet()){
                    //here, we loop through the foods key and save the name of the food and its nutrition
                    String s = "";
                    s+=item;
                    s+=",";
                    s+=Arrays.toString(Food.foods.get(item));
                    String NoLeftBracket = s.replace("[", " ");
                    String NoBracket = NoLeftBracket.replace("]", "");
                    PW.println(NoBracket);
                    s="";
                }

                PW.flush();
            }
            catch (IOException e){
                e.printStackTrace();
                System.err.println("Cannot write to the file!");
                System.exit(1);
            }
        }
        else{
            System.out.println("Cannot access the file to write it!");
            System.exit(1);
        }
    }

    /**
     * Method to get ArrayList containing the names of all the food in the hashmap
     * @return ArrayList containing the names of all the food in the hashmap
     */
    public ArrayList GetFoodName(){
        ArrayList food_names = new ArrayList();
        //Looping through keys in hashmap and adding to ArrayList
        for(String item : foods.keySet()){
            food_names.add(item);
        }
        return food_names;
    }

    /**
     * Method to check whether food already exists or not
     * @param food_name name of food
     * @return true or false based on whether name of food exists in hashmap or not
     */
    public Boolean containsKey(String food_name){
        //Checking keys of hashmap to see if food name exists
        return foods.keySet().contains(food_name);
    }

}


