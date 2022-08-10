package components;

import java.util.ArrayList;

public class Route implements RouteParts {
    //================================VARIABLES================================

    private ArrayList<RouteParts> RouteParts = new ArrayList<RouteParts>();
    private Vehicle vehicle;

    //================================CONSTRUCTORS================================

    public Route(RouteParts start, Vehicle vehicle) {
        this.vehicle = vehicle;
        this.vehicle.setTimeFromRouteStart(0);
        this.RouteParts.add(start);
        for (int i = 1; i < 10; i++) {
            if (RouteParts.get(RouteParts.size() - 1) instanceof Junction)
                if (((Junction) RouteParts.get(RouteParts.size() - 1)).getExitingRoads().isEmpty())
                    break;
            try {
                RouteParts.add(RouteParts.get(RouteParts.size() - 1).findNextPart(vehicle)); //checkkkkkkk
            } catch (NullPointerException ex) {
                System.out.println("NullPointerException");
            }
        }
        checkIn(vehicle);
        start.checkIn(vehicle);
    }

    //================================METHODS================================


    public ArrayList<components.RouteParts> getRouteParts() {
        return RouteParts;
    }

    public void setRouteParts(ArrayList<components.RouteParts> routeParts) {
        RouteParts = routeParts;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int maxSpeedCalculate(){
        int speed = 0;
        for(int i = 0; i < RouteParts.size(); i++){
            if(RouteParts.get(i) instanceof Road)
                if(((Road) RouteParts.get(i)).getMaxSpeed() > speed)
                    speed = ((Road) RouteParts.get(i)).getMaxSpeed();
        }
        return speed;
    }

    @Override
    public double calcEstimatedTime(Object obj) {
        double time = 0;
        try {
            for (int i = 0; i < RouteParts.size(); i++) {
                time += RouteParts.get(i).calcEstimatedTime(obj);
            }
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException");
        }
        return time;
    }

    @Override
    public boolean canLeave(Vehicle vehicle) {
        try {
            return RouteParts.get(RouteParts.size() - 1).equals(vehicle.getCurrentRoutePart());
        } catch (NullPointerException ex) {
            System.out.println("NullPointerException");
            return false;
        }
    }

    @Override
    public void checkIn(Vehicle vehicle) {
        vehicle.setCurrentRoute(this);
        vehicle.setCurrentRoutePart(this.RouteParts.get(0));
        vehicle.setTimeFromRouteStart(0);
        System.out.println("- Is starting a new " + this);
    }

    @Override
    public void checkOut(Vehicle vehicle) {
        System.out.println("- Has finished the " + this + " Time spent on the route: " + this.vehicle.getTimeFromRouteStart() + ".");
    }

    @Override
    public components.RouteParts findNextPart(Vehicle vehicle) {
        RouteParts next;
        if (canLeave(vehicle)) {
            next = vehicle.getCurrentRoutePart().findNextPart(vehicle);
            if(next != null){
                vehicle.setCurrentRoute(new Route(vehicle.getLastRoad(), vehicle));
            }
            else {
                vehicle.setCurrentRoute(new Route(RouteParts.get(0), vehicle));
            }
            return RouteParts.get(0);
        }
        return vehicle.getCurrentRoutePart().findNextPart(vehicle);
    }

    private int getRouteLength(){
        int length = 0;
        for(int i = 0; i < this.RouteParts.size(); i++){
            if(this.RouteParts.get(i) instanceof Road)
                length += ((Road) this.RouteParts.get(i)).getLength();
        }
        return length;
    }

    @Override
    public void stayOnCurrentPart(Vehicle vehicle) {
        System.out.println(vehicle + "Is still on his route part.");
    }

    @Override
    public String toString() {
        return "Route from " + RouteParts.get(0) + ", max speed: " + maxSpeedCalculate()
                + ", to " + RouteParts.get(RouteParts.size() - 1) + ", estimated time for route: " + calcEstimatedTime(this.vehicle) + ".";
    }

    //add
    public RouteParts getStartRoute() {
        return this.RouteParts.get(0);

    }

}
