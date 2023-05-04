package main;

import java.util.Scanner;

public class Water {
    /**
     * this class adds cups of water
     * @param user User object
     * @param cups_added number of cups user has drunk
     */
    public void add_water(User user, int cups_added){
        //Adds the amount of cups the user has entered to the amount they've had in the past
        user.addCups(cups_added);
    }
}