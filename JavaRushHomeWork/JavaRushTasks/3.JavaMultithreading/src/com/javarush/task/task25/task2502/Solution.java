package com.javarush.task.task25.task2502;

import java.util.*;

/* 
Машину на СТО не повезем!
*/
public class Solution {
    public static enum Wheel {
        FRONT_LEFT,
        FRONT_RIGHT,
        BACK_LEFT,
        BACK_RIGHT
    }

    public static class Car {
        protected List<Wheel> wheels;

        public Car() {
            //init wheels here
            wheels = new ArrayList<Wheel>();
            String[] loadWheel = loadWheelNamesFromDB();
            if (loadWheel.length != 4) {
                throw new IllegalArgumentException("Это не машина");
            }
            for (String wheel : loadWheel) {
                for (Wheel w : wheels) {
                    if (wheel.equals(w.toString())) {
                        throw new IllegalArgumentException("Это не машина");
                    }
                }

                wheels.add(Wheel.valueOf(wheel));
            }
        }

        protected String[] loadWheelNamesFromDB() {
            //this method returns mock data
            return new String[]{"FRONT_LEFT", "FRONT_RIGHT", "BACK_LEFT", "BACK_RIGHT"};
        }
    }

    public static void main(String[] args) {
        Car car = new Car();
        System.out.println(car.wheels);
    }
}
