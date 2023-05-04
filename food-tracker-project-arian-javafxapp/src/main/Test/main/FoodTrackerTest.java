package main;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FoodTrackerTest {
    @Test
    /**
     * tests the averageTest function. We see that the function calculates the average of the given numbers properly.
     */
    void averageTest(){
        ArrayList<Double> test_list = new ArrayList<Double>();
        test_list.add(3.4);
        test_list.add(53.7);
        test_list.add(74.45);
        test_list.add(2.5);
        User user = new User(17,56,187,"F",2);
        assertEquals(33.5125,user.get_list_average(test_list));
    }
    @Test
    /**
     * tests the calorie_calc function. We see that the function calculates the calories needed for a man properly.
     * calorie_calc() is called in User's constructor
     */
    void calories_needed_test_Man(){
        User user = new User(19,63,173,"M",3);
        assertEquals(2812.7695,user.calories_needed);
    }

    /**
     * tests the calorie_calc function. We see that the function calculates the calories needed for a woman properly.
     * calorie_calc() is called in User's constructor
     */
    @Test
    void calories_needed_test_Woman(){
        User user = new User(22,58,165,"F",4);
        assertEquals(2659.86375,user.calories_needed);
    }

    /**
     * Tests the .equals method for User. Should return true if the users are equal
     */
    @Test
    void User_equals_true(){
        User user = new User(13,123,324,"M",2);
        User user1 = new User(13,123,324,"M",2);
        assertTrue(user.equals(user1));
    }

    /**
     * Tests the .equals method for User. Should return false if the users are different
     */
    @Test
    void User_equal_false(){
        User user = new User(23,324,543,"F",3);
        User user1 = new User(43,12,43,"M",1);
        assertFalse(user.equals(user1));
    }
}