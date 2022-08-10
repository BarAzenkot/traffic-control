package components;

import utilities.Timer;
import utilities.Utilities;

import java.awt.*;
import java.util.ArrayList;

public abstract class TrafficLights extends Thread implements Timer, Utilities {
    //================================VARIABLES================================

    static private int objectCount;
    private int delay;
    private int greenLightIndex;
    private int ID;
    final private int minDelay = 2;
    final private int maxDelay = 6;
    private ArrayList<Road> roads = new ArrayList<Road>();
    private boolean trafficLightOn;
    private double workingTime;

    //================================CONSTRUCTORS================================

    public TrafficLights(ArrayList<Road> roads){
        this.roads = roads;
        objectCount++;
        this.ID = objectCount;
        this.delay = getRandomInt(minDelay, maxDelay);
        this.greenLightIndex = -1;
    }

    //================================METHODS================================

    public int getObjectCount() {
        return objectCount;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getGreenLightIndex() {
        return greenLightIndex;
    }

    public void setGreenLightIndex(int greenLightIndex) {
        this.greenLightIndex = greenLightIndex;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMinDelay() {
        return minDelay;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public boolean isTrafficLightOn() {
        return trafficLightOn;
    }

    public void setTrafficLightOn(boolean trafficLightOn) {
        this.trafficLightOn = trafficLightOn;
    }

    public double getWorkingTime() {
        return workingTime;
    }

    public void setWorkingTime(double workingTime) {
        this.workingTime = workingTime;
    }

    abstract public void changeIndex();

    public void changeLights(){
        this.trafficLightOn = true;
        if(this.greenLightIndex == -1){
            setGreenLightIndex(getRandomInt(0, this.roads.size()));
        }
        for(Road road : this.roads)
            road.setGreenLight(false);
        changeIndex();
        this.roads.get(greenLightIndex).setGreenLight(true);
    }

    public void incrementDrivingTime(){
        this.workingTime ++;
        if(this.workingTime % this.delay == 0) {
            changeLights();
            System.out.println("- " + this.roads.get(greenLightIndex) + ":\nGreen light.");
        }
        else System.out.println("- on delay.");
    }


}
