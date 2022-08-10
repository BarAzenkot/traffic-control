package components;

import utilities.Point;

import java.awt.*;
import java.util.ArrayList;

public class Junction extends Point implements RouteParts {
    //================================VARIABLES================================

    static private int objectCount;
    private ArrayList<Road> enteringRoads = new ArrayList<Road>();
    private ArrayList<Road> exitingRoads = new ArrayList<Road>();
    private String junctionName;

    //================================CONSTRUCTORS================================

    public Junction(){
        super();
        this.objectCount++;
        this.junctionName = String.valueOf(objectCount);
        printJunction();
    }

    public Junction(String junctionName, double x, double y){
        super(x, y);
        this.objectCount++;
        this.junctionName = junctionName;
        printJunction();
    }

    //================================METHODS================================


    public static int getObjectCount() { return objectCount; }

    public static void setObjectCount(int objectCount) { Junction.objectCount = objectCount; }

    public ArrayList<Road> getEnteringRoads() { return enteringRoads; }

    public void setEnteringRoads(ArrayList<Road> enteringRoads) { this.enteringRoads = enteringRoads; }

    public ArrayList<Road> getExitingRoads() { return exitingRoads; }

    public void setExitingRoads(ArrayList<Road> exitingRoads) { this.exitingRoads = exitingRoads; }

    public String getJunctionName() { return junctionName; }

    public void setJunctionName(String junctionName) { this.junctionName = junctionName; }

    public void addEnteringRoad(Road road){
        this.enteringRoads.add(road);
    }

    public void addExitingRoad(Road road){
        this.exitingRoads.add(road);
    }

    public double calcEstimatedTime(Object obj){
        return this.enteringRoads.indexOf(obj) + 1;
    }

    @Override
    public boolean canLeave(Vehicle vehicle) {
        for(int i = 0; i < this.getEnteringRoads().size(); i++) {
            if(!this.getEnteringRoads().get(i).getWaitingVehicles().isEmpty())
                if (this.getEnteringRoads().get(i).getWaitingVehicles().get(0).equals(vehicle))
                    return checkAvailability(vehicle);
        }
        return false;
    }

    public boolean checkAvailability(Vehicle vehicle){
        if(this.exitingRoads.isEmpty())
            return false;
        if(vehicle.getLastRoad().getWaitingVehicles().get(0).equals(vehicle))
            return true;
        return false;
    }

    @Override
    public void checkIn(Vehicle vehicle) {
        vehicle.setCurrentRoutePart(this);
        vehicle.setTimeOnCurrentPart(0);
        System.out.println("- Has arrived to " + this + ".");
   }

    @Override
    public void checkOut(Vehicle vehicle) {
        vehicle.getLastRoad().removeVehicleFromWaitingVehicles(vehicle);
        System.out.println("- Has left the " + this + ".");
    }

    @Override
    public RouteParts findNextPart(Vehicle vehicle) {
        int randIndex = getRandomInt(0, this.exitingRoads.size());
        int count = 0;

        while (!this.exitingRoads.get(randIndex).isEnable()) {
            randIndex = (randIndex + 1) % this.exitingRoads.size();
            count++;
            if (count == this.exitingRoads.size())
                return null;
            for(int i = 0; i < this.exitingRoads.get(randIndex).getVehicleTypes().length; i++)
                if (vehicle.getVehicleType().equals(this.exitingRoads.get(randIndex).getVehicleTypes()[i]))
                    if(this.exitingRoads.get(randIndex).isEnable())
                        break;
        }
        return this.exitingRoads.get(randIndex);
    }

    @Override
    public void stayOnCurrentPart(Vehicle vehicle) {
        System.out.println("- Is waiting at " + this + " for green light. time left : " + (calcEstimatedTime(vehicle) - vehicle.getTimeOnCurrentPart()));
    }

    private void printJunction(){
        System.out.print(this);
        System.out.println(" has been created.");
    }

    /**
     * prints a non lighted junction.
     * @param g
     */
    public void paintJunction(Graphics g){
        g.setColor(Color.BLACK);
        g.fillOval((int) getX() + 20, (int) getY() + 60, 20, 20);
    }

    @Override
    public String toString() {
        String to = junctionName;
        if(this instanceof LightedJunction)
            to += " (Lighted)";
        return to;
    }

}
