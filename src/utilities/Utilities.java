package utilities;

import java.util.ArrayList;

public interface Utilities {

    default boolean checkValue(double val, double min, double max){
        if (min < val && val < max)
            return true;
        return false;
    }

    default void correctingMessage(double wrongVal, double correctVal, String varName){
        System.out.println("The value " + wrongVal + " is illegal for " + varName + ", therefore has been replaced with " + correctVal);
    }

    default void errorMessage(double wrongVal, String varName){
        System.out.println("The value " + wrongVal + " is illegal for " + varName);
    }

    default boolean getRandomBoolean(){
        return GameDriver.r.nextBoolean();
    }

    default double getRandomDouble(double min, double max){
        return GameDriver.r.nextDouble() * (max - min) + min;
    }

    default int getRandomInt(int min, int max){
        return GameDriver.r.nextInt(max - min) + min;
    }

    default ArrayList<Integer> getRandomIntArray(int min, int max, int arraySize){
        ArrayList<Integer> list = new ArrayList<>(arraySize);
        for (int i = 0; i < arraySize; i++)
            list.add(getRandomInt(min, max));
        return list;
    }

    default void successMessage(String objName){
        System.out.println(objName + " has been created.");
    }

}
