package main.foodtrackerapp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.*;

import java.io.File;
import java.util.ArrayList;

public class FoodTrackerController {
    //Creating objects that will be used in application
    private User user;
    private Food food = new Food();
    private Exercise exercise = new Exercise();
    private Water water = new Water();

    public static String[] args;

    /**
     * Function to load user info from file given in command line
     * @param file file containing info about user.
     */
    public void loadCMDUser(File file){
        //Loading user info
        user = Reader.loadUser(file);
        //Updating status
        status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
        status.setText("User's info Loaded");
    }

    //Text where information about activity rates will be shown
    @FXML
    private Text activityInfo;

    //ComboBox for user to select their activity rate
    @FXML
    private ComboBox<String> userActivityRate;

    //TextField for user to input age
    @FXML
    private TextField userAge;

    //ComboBox for user to select their gender
    @FXML
    private ComboBox<String> userGender;

    //TextField for user to input height
    @FXML
    private TextField userHeight;

    //TextField for user to input weight
    @FXML
    private TextField userWeight;

    //TextField for user to input how many grams of a certain food they've consumed
    @FXML
    private TextField selectFoodGrams;

    //ComboBox for user to select a certain food they've consumed
    @FXML
    private ComboBox<String> selectFoodName;

    //TextFields for user to input information about a new food
    @FXML
    private TextField newFoodCals;

    @FXML
    private TextField newFoodCarbs;

    @FXML
    private TextField newFoodFat;

    @FXML
    private TextField newFoodProtein;

    @FXML
    private TextField newFoodSugar;

    @FXML
    private TextField newFoodName;

    //ComboBox for user to select a food that they would like to know the information about
    @FXML
    private ComboBox<String> foodDetailList;

    //Text to show information of a certain food
    @FXML
    private Text foodDetailText;

    //TextField for user to input how many cups of water they've drunk
    @FXML
    private TextField cups;

    //ComboBox for user to select an exercise that they would like to know the information about
    @FXML
    private ComboBox<String> selectExerciseDetail;

    //TextField for user to input how many hours they've done a certain exercise
    @FXML
    private TextField selectExerciseHours;

    //ComboBox for user to select an exercise that they have done
    @FXML
    private ComboBox<String> selectExerciseName;

    //TextField for user to enter a new exercise
    @FXML
    private TextField newExerciseName;

    //Text to show details about a certain exercise
    @FXML
    private Text exerciseDetailText;

    //TExtField for user to input how many calories a certain exercise burns per hour
    @FXML
    private TextField newExerciseCalories;

    //Text to show details from a certain option in the health chart
    @FXML
    private Text healthChartDetails;

    @FXML
    private Label status;

    @FXML
    private Text userInfoText;




    /**
     * Setting up certain elements in the window
     */
    @FXML
    public void initialize() {
        //Adding genders to comboBox
        userGender.getItems().addAll("M", "F");
        //Adding activity rate to comboBox
        userActivityRate.getItems().addAll("1", "2", "3", "4");
        //Getting food and exercise names and adding to respective comboBoxes
        ArrayList<String> foodNames = food.GetFoodName();
        ArrayList<String> exerciseNames = exercise.GetExerciseName();
        selectFoodName.getItems().addAll(foodNames);
        foodDetailList.getItems().addAll(foodNames);
        selectExerciseName.getItems().addAll(exerciseNames);
        selectExerciseDetail.getItems().addAll(exerciseNames);
        //Checking for arguments and loading to user info if argument given
        if (args.length == 1) {
            File file = new File(args[0]);
            loadCMDUser(file);
        }
    }

    /**
     * Button to create a User
     */
    @FXML
    void createUser() {
        try {
            //Getting information about user
            int age = Integer.parseInt(userAge.getText());
            double height = Double.parseDouble(userHeight.getText());
            double weight = Double.parseDouble(userWeight.getText());
            String gender = userGender.getValue();
            int activityRate = Integer.parseInt(userActivityRate.getValue());
            //Checking to see if correct arguments have been input
            if (gender != null && age > 0 && height > 0 && weight > 0 && activityRate >= 1 && activityRate <= 4) {
                //Creating user
                User new_user = new User(age, weight, height, gender, activityRate);
                //Checking if a user already exists
                if (user != null){
                    //Checking whether old user and new user are equal
                    if (user.equals(new_user)){
                        //Updating status
                        status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                        status.setText("User already exists");
                    }
                    //Users are different
                    else {
                        //Replacing old user with new user
                        user = new_user;
                        //Calculating calories needed
                        user.calorie_calc();
                        //Updating status
                        status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
                        status.setText("Replaced user!");
                    }
                }
                //User does not exist
                else {
                    //Setting user to new user
                    user = new_user;
                    //Calculating calories needed
                    user.calorie_calc();
                    //Updating status
                    status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
                    status.setText("User Created");
                }
            }
            //Invalid arguments were entered
            else {
                //Updating status
                status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                status.setText("Please enter valid arguments");
            }
        }
        //Invalid arguments were entered
        catch (IllegalArgumentException e){
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Please enter valid arguments");
        }
    }

    /**
     * Button that shows what each activity rate means in Text
     */
    @FXML
    void showActivityInfo() {
        //Showing info in Text
        activityInfo.setText("1 - Sedentary (0 days a week)\n" +
                "2 - Light (1-2 days a week)\n" +
                "3 - Moderate (3-5 days a week)\n" +
                "4 - Very Active (Every day)\n");
    }

    /**
     * Button to enter food user has consumed
     */
    @FXML
    void inputFood() {
        if (user != null) {
            try {
                //Getting name of food from comboBox
                String name = selectFoodName.getValue();
                //Getting consumption amount from TextField
                double amount = Double.parseDouble(selectFoodGrams.getText());
                //Checking if argument is valid and name has been entered
                if (name != null && amount > 0) {
                    //Calling select_food method to calculate consumption information for user
                    food.select_food(user, name, amount);
                    //Updating status
                    status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
                    status.setText("Food Added");
                }
                //Invalid arguments were entered
                else {
                    //Updating status
                    status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                    status.setText("Please select a food and enter a valid argument");
                }
            }
            //Invalid arguments were entered
            catch (IllegalArgumentException e) {
                //Updating status
                status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                status.setText("Please enter valid arguments");
            }
        }
        //Invalid arguments were entered
        else{
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * Button to add a new food to list
     */
    @FXML
    void addFood() {
        //Getting information about food
        String foodName = newFoodName.getText();
        if (!foodName.equals("")) {
            foodName = foodName.toLowerCase();
            if (!food.containsKey(foodName)) {
                try {
                    double calories = Double.parseDouble(newFoodCals.getText());
                    double fat = Double.parseDouble(newFoodFat.getText());
                    double protein = Double.parseDouble(newFoodProtein.getText());
                    double carbohydrate = Double.parseDouble(newFoodCarbs.getText());
                    double sugars = Double.parseDouble(newFoodSugar.getText());
                    if (calories >= 0 && fat >= 0 && protein >= 0 && carbohydrate >= 0 && sugars >= 0) {
                        //Adding food to hashmap and comboBoxes
                        food.input_food(foodName, calories, fat, protein, carbohydrate, sugars);
                        selectFoodName.getItems().add(foodName);
                        foodDetailList.getItems().add(foodName);
                        status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
                        status.setText("New Food Added");
                    }
                    //Invalid arguments were entered
                    else {
                        //Updating status
                        status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                        status.setText("Please enter valid arguments");
                    }
                }
                //Invalid arguments were entered
                catch (IllegalArgumentException e) {
                    //Updating status
                    status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                    status.setText("Please enter valid arguments");
                }

            }
            //Food already exists
            else {
                //Updating status
                status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                status.setText("Food already exists!");
            }
        }
        //No name was entered for food
        else{
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Please enter a name for the food");
        }
    }

    /**
     * Button to show details about a certain food in Text
     */
    @FXML
    void showFoodDetails() {
        //Getting name of food from comboBox
        String foodName = foodDetailList.getValue();
        if (foodName != null) {
            foodName = foodName.toLowerCase();
            //Showing details in Text
            foodDetailText.setText(food.print_food_info(foodName));
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("Showing the food's detail");
        }
        //Food was not selected
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Please select a food");
        }
    }

    /**
     * Button to add cups of water user has drunk
     */
    @FXML
    void addWater() {
        //Checking if user exists
        if (user != null) {
            try {
                //Getting number of cups from textField
                int cupsAdded = Integer.parseInt(cups.getText());
                //Checking if number of cups is a positive number
                if (cupsAdded >= 0) {
                    //Adding cups of water to user
                    water.add_water(user, cupsAdded);
                    //Updating user
                    status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
                    status.setText("Cups of Water Added");
                }
                //Negative number was entered
                else {
                    //Updating status
                    status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                    status.setText("Please enter a valid argument");
                }
            }
            //Illegal argument was entered
            catch (IllegalArgumentException e){
                //Updating status
                status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                status.setText("Please enter a valid argument");
            }
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * Button to add a new exercise to list of exercises
     */
    @FXML
    void newExercise() {
        //Getting name of exercise from textField
        String exercise_name = newExerciseName.getText();
        //Checking if textField is not blank
        if (!exercise_name.equals("")) {
            //Making exercise name lowercase
            exercise_name = exercise_name.toLowerCase();
            //Checking if exercise already exists or not
            if (!exercise.containsKey(exercise_name)) {
                try {
                    //Getting number of calories the exercise burns per hour
                    double burned_cals = Double.parseDouble(newExerciseCalories.getText());
                    //Checking if positive number was entered
                    if (burned_cals >= 0) {
                        //Adding exercise to hashmap and ComboBoxes
                        exercise.input_exercise(exercise_name, burned_cals);
                        selectExerciseName.getItems().add(exercise_name);
                        selectExerciseDetail.getItems().add(exercise_name);
                        //Updating status
                        status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
                        status.setText("New Exercise Added");
                    }
                    //Negative number was entered
                    else {
                        //Updating status
                        status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                        status.setText("Please enter a valid argument");
                    }
                }
                //Illegal argument was entered
                catch (IllegalArgumentException e) {
                    //Updating status
                    status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                    status.setText("Please enter a valid argument");
                }
            }
            //Exercise already exists
            else {
                //Updating status
                status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                status.setText("Exercise already exists!");
            }
        }
        //Name was not entered for exercise
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Please enter a name for the exercise");
        }
    }

    /**
     * Button that adds completed exercise to user
     */
    @FXML
    void selectExercise() {
        //Checking if user exists
        if (user != null) {
            //Getting name of exercise
            String exercise_name = selectExerciseName.getValue();
            //Checking if name was entered for exercise
            if (exercise_name != null) {
                //Making exercise name lowercase
                exercise_name = exercise_name.toLowerCase();
                try {
                    //Getting number of hours exercise was done from textField
                    double hours = Double.parseDouble(selectExerciseHours.getText());
                    //Checking if positive value was entered
                    if (hours >= 0) {
                        //Adding exercise to user
                        exercise.exercise(user, exercise_name, hours);
                        //Updating status
                        status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
                        status.setText("Exercise Added");
                    }
                    //Negative number was entered
                    else {
                        //Updating status
                        status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                        status.setText("Please enter a valid argument");
                    }
                }
                //Illegal argument was entered
                catch (IllegalArgumentException e){
                    //Updating status
                    status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                    status.setText("Please enter a valid argument");
                }
            }
            //Exercise was not selected
            else {
                //Updating status
                status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                status.setText("Select an exercise");
            }
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * Button to show information about a certain exercise in Text
     */
    @FXML
    void showExerciseDetails() {
        //Getting name of exercise
        String exercise_name = selectExerciseDetail.getValue();
        //Checking if name was selected from ComboBox
        if (exercise_name != null) {
            //Changing name to lowercase
            exercise_name = exercise_name.toLowerCase();
            //Showing info about exercise in Text
            exerciseDetailText.setText(exercise.print_exercise_info(exercise_name));
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("Showing the exercise details");
        }
        //Exercise was not selected
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Please select an exercise");
        }
    }

    /**
     * Menu bar item that shows information about program
     */
    @FXML
    void aboutApp() {
        //Creating alert that pops up a window and shows information
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setResizable(true);
        alert.setTitle("INFORMATION");
        alert.setHeaderText("About the Food Tracker Program");
        alert.setContentText("Author: Arian Safari\n" +
                "" +"\n"+
                "This is a food and exercise tracker program.\n" +
                "The program allows you to create a user and track their daily consumption and exercise.\n" +
                "The user is then able to check statistics on what they have consumed, how many calories they have burned\n" +
                "and how they can improve their diet.");
        alert.showAndWait();
    }

    /**
     * Menu bar item that closes the program
     */
    @FXML
    void closeApp() {
        System.exit(0);
    }

    /**
     * Menu bar item that loads a user from a file
     */
    @FXML
    void loadUser() {
        //Creating FileChooser and having user select a file
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        //Checking if file was selected
        if (file != null) {
            //reading information from file and saving to user object
            user = Reader.loadUser(file);
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("User's info Loaded");
        }
        //File was not given
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("File was not selected!");
        }
    }

    /**
     * Menu bar item that saves information about user and any new food and exercise that has been added to hashmap
     */
    @FXML
    void saveUser() {
        //Checking if user has been created
        if (user != null) {
            //Creating FileChooser and getting file that user selects
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(new Stage());
            //Checking if file was given
            if (file != null) {
                //Saving user, food and exercise info to files
                user.save_user(file);
                food.save_food();
                exercise.save_exercise();
                //Updating status
                status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
                status.setText("User's info Saved");
            }
            //File was not given
            else {
                //Updating status
                status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
                status.setText("File was not selected!");
            }
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * Button in health chart tab that shows info about carbohydrate consumption
     */
    @FXML
    void chartCarbohydrates() {
        //Checking if user exists
        if (user != null) {
            //Getting information about carbohydrate consumption and showing on Text
            String text = user.health_chart(4);
            healthChartDetails.setText(text);
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("Showing the amount of carbohydrate consumed");
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * Button in health chart tab that shows info about protein consumption
     */
    @FXML
    void chartProtein() {
        //Checking if user was created
        if (user != null) {
            //Getting information about protein consumption and showing on Text
            String text = user.health_chart(3);
            healthChartDetails.setText(text);
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("Showing the amount of protein consumed");
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * Button in health chart tab that shows info about calorie consumption
     */
    @FXML
    void chartCalories() {
        //Checking if user was created
        if (user != null) {
            //Getting information about calorie consumption and showing on Text
            String text = user.health_chart(1);
            healthChartDetails.setText(text);
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("Showing the amount of calories consumed");
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * Button in health chart tab that shows info about fat consumption
     */
    @FXML
    void chartFat() {
        //Checking if user was created
        if (user != null) {
            //Getting information about fat consumption and showing on Text
            String text = user.health_chart(2);
            healthChartDetails.setText(text);
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("Showing the amount of fat consumed");
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * Button in health chart tab that shows info about all of the user's consumption
     */
    @FXML
    void chartAll() {
        //Checking if user was created
        if (user != null) {
            //Getting information about user's total consumption and showing on Text
            String text = user.health_chart(8);
            healthChartDetails.setText(text);
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("Showing the info of the user consumption");
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * Button in health chart tab that shows info about user's exercise
     */
    @FXML
    void chartExercise() {
        //Checking if user was created
        if (user != null) {
            //Getting information about user's exercise and showing on Text
            String text = user.health_chart(7);
            healthChartDetails.setText(text);
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("Showing the amount of calories burned");
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * Button in health chart tab that shows info about sugar consumption
     */
    @FXML
    void chartSugar() {
        //Checking if user was created
        if (user != null) {
            //Getting information about sugar consumption and showing on Text
            String text = user.health_chart(5);
            healthChartDetails.setText(text);
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("Showing the amount of sugar consumed");
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * Button in health chart tab that shows info about water consumption
     */
    @FXML
    void chartWater() {
        //Checking if user was created
        if (user != null) {
            //Getting information about water consumption and showing on Text
            String text = user.health_chart(6);
            healthChartDetails.setText(text);
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("Showing the cups of water drank");
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }

    /**
     * button that shows user's info on text
     */
    @FXML
    void ShowInfo() {
        if (user != null) {
            //Showing user info
            userInfoText.setText(user.toString());
            //Updating status
            status.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
            status.setText("Showing user info");
        }
        //User was not created
        else {
            //Updating status
            status.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            status.setText("Create a user first!");
        }
    }
}