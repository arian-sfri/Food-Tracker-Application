# Food Tracker Application
## Author: Arian Safari

Application that tracks food and water consumption, exercise and shows information about user's consumption
The user can then check the statistics of their daily consumption and exercise and the program will give further
information on how they can improve their diet (e.g. more protein intake).

To run program from command line:

```bash
java --module-path (Path to lib folder of java SDK) --add-modules javafx.controls,javafx.fxml main.foodtrackerapp.FoodTrackerApp
```
To load User file from command line:
```bash
java --module-path (Path to lib folder of java SDK) --add-modules javafx.controls,javafx.fxml main.foodtrackerapp.FoodTrackerApp (file containing user info)
```
To run program from command line with .jar file:
```bash
java --module-path (Path to lib folder of java SDK --add-modules javafx.controls,javafx.fxml -jar (Path to FoodTrackerApp.jar)
```
To load User file from command line with .jar file:
```bash
java --module-path (Path to lib folder of java SDK --add-modules javafx.controls,javafx.fxml -jar (Path to FoodTrackerApp.jar) (File containing user info)
```