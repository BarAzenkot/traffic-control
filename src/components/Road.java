package components;

import utilities.GameDriver;
import utilities.Utilities;
import utilities.VehicleType;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Road implements RouteParts, Utilities {
    //================================VARIABLES================================

    private int[] allowedSpeedOptions = {30 / 10, 40 / 10, 50 / 10, 55 / 10, 60 / 10, 70 / 10, 80 / 10, 90 / 10};
    private boolean enable;
    private Junction startJunction;
    private Junction endJunction;
    private boolean greenLight;
    private double length;
    private int maxSpeed;
    private VehicleType[] vehicleTypes;
    private ArrayList<Vehicle> waitingVehicles = new ArrayList<Vehicle>();

    //================================CONSTRUCTORS================================

    public Road(Junction start, Junction end) {
        this.startJunction = start;
        this.endJunction = end;
        this.length = Math.round(calcLength());
        int numOfTypes = GameDriver.r.nextInt(VehicleType.values().length);
        vehicleTypes = Arrays.stream(VehicleType.values()).filter(type -> getRandomBoolean()).toArray(VehicleType[]::new);
        this.enable = getRandomBoolean();
        this.maxSpeed = allowedSpeedOptions[GameDriver.r.nextInt(this.allowedSpeedOptions.length)];
        startJunction.addExitingRoad(this);
        endJunction.addEnteringRoad(this);
        this.greenLight = false;

    }


    //================================METHODS================================


    public int[] getAllowedSpeedOptions() {
        return allowedSpeedOptions;
    }

    public void setAllowedSpeedOptions(int[] allowedSpeedOptions) {
        this.allowedSpeedOptions = allowedSpeedOptions;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Junction getStartJunction() {
        return startJunction;
    }

    public void setStartJunction(Junction startJunction) {
        this.startJunction = startJunction;
    }

    public Junction getEndJunction() {
        return endJunction;
    }

    public void setEndJunction(Junction endJunction) {
        this.endJunction = endJunction;
    }

    public boolean isGreenLight() {
        return greenLight;
    }

    public void setGreenLight(boolean greenLight) {
        this.greenLight = greenLight;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public VehicleType[] getVehicleTypes() {
        return vehicleTypes;
    }

    public void setVehicleTypes(VehicleType[] vehicleTypes) {
        this.vehicleTypes = vehicleTypes;
    }

    public ArrayList<Vehicle> getWaitingVehicles() {
        return waitingVehicles;
    }

    public void setWaitingVehicles(ArrayList<Vehicle> waitingVehicles) {
        this.waitingVehicles = waitingVehicles;
    }

    public double calcLength() {
        return Math.sqrt(Math.pow(startJunction.getX() - endJunction.getX(), 2) + Math.pow(startJunction.getY() - endJunction.getY(), 2));
    }

    public void addVehicleToWaitingVehicles(Vehicle vehicle) {
        waitingVehicles.add(vehicle);
    }

    public void removeVehicleFromWaitingVehicles(Vehicle vehicle) {
        waitingVehicles.remove(vehicle);
    }

    @Override
    public double calcEstimatedTime(Object obj) {
        return Math.round(this.length / ((Vehicle) obj).getCurrentSpeed());

    }

    @Override
    public boolean canLeave(Vehicle vehicle) {
        return vehicle.getTimeOnCurrentPart() >= calcEstimatedTime(vehicle);
    }

    @Override
    public void checkIn(Vehicle vehicle) {
        vehicle.setLastRoad(this);
        vehicle.setTimeOnCurrentPart(0);
        vehicle.setCurrentRoutePart(this);
        vehicle.setCurrentSpeed(Math.round((int) (vehicle.getVehicleType().getAverageSpeed() * getRandomDouble(0.5, 1.5))));
        System.out.println("- Is starting to move on " + this);
    }

    @Override
    public void checkOut(Vehicle vehicle) {
        addVehicleToWaitingVehicles(vehicle);
        vehicle.change();
        vehicle.notifyObservers(vehicle);
        System.out.println("- Has finished the " + this + ", time spent on the road: " + vehicle.getTimeOnCurrentPart() + ".");
    }

    @Override
    public RouteParts findNextPart(Vehicle vehicle) {
        return endJunction;
    }

    @Override
    public void stayOnCurrentPart(Vehicle vehicle) {
        vehicle.updateMove();
        System.out.println("- Is still moving on " + this  + ". time left : " + (calcEstimatedTime(vehicle) - vehicle.getTimeOnCurrentPart()));
    }

    public void printRoad(){
        System.out.println(this + ", length: " + length + ", max speed: " + maxSpeed + ", has been created.");
    }

    /**
     * prints the roads.
     * @param g
     */
    public void paintRoad(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int) getStartJunction().getX() + 30, (int) getStartJunction().getY() + 70,
                (int) getEndJunction().getX() + 30, (int) getEndJunction().getY() + 70);
    }

    @Override
    public String toString() {
        return "Road " + startJunction + " - " + endJunction;
    }

}
