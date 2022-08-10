package components;

import utilities.*;
import utilities.Builders.Engineer;
import utilities.Builders.MapBuilder;
import utilities.Factories.VFactory;
import utilities.Factories.WheelsFactory;

import java.util.ArrayList;
import java.util.Objects;


public class Driving extends Thread implements Utilities, Timer {
    //================================VARIABLES================================

    public static Map map;
    private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
    private int DrivingTime;
    private ArrayList<Timer> allTimedElements = new ArrayList<Timer>();
    private boolean stop;

    //================================CONSTRUCTORS================================

    public Driving(MapBuilder builder, int numOfVehicles) {
        Engineer e = new Engineer(builder);
        e.constructMap();
        map = e.getMap();
        for (Junction junc : map.getJuctions()) {
            ArrayList<Road> delete = new ArrayList<Road>();
            for (Road road : junc.getEnteringRoads()) {
                if (!road.isEnable())
                    delete.add(road);
            }
            for (Road road : delete) {
                junc.getEnteringRoads().remove(road);
            }

        }
        addSpecificVehicles(numOfVehicles, e.getType());
        addTimedElements();
    }

    public Driving(int numOfJunctions, int numOfVehicles){
        map = new Map(numOfJunctions);
        addSpecificVehicles(numOfVehicles, "");
        addTimedElements();
    }

    //================================METHODS================================

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        map = map;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public double getDrivingTime() {
        return DrivingTime;
    }

    public void setDrivingTime(int drivingTime) {
        DrivingTime = drivingTime;
    }

    public ArrayList<Timer> getAllTimedElements() {
        return allTimedElements;
    }

    public void setAllTimedElements(ArrayList<Timer> allTimedElements) {
        this.allTimedElements = allTimedElements;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void addSpecificVehicles(int vehicles, String t){
        Road road;
        VFactory vf = new VFactory();
        int[] wheels = {2, 4, 10};
        String[] types = {"fast", "slow", "private", "public", "work"};
        int wheel;
        String type;
        System.out.println("================ CREATING VEHICLES ================");
        System.out.println();

        if (t.equals("City")) {
            for (int i = 0; i < vehicles; i++) {
                wheel = wheels[getRandomInt(0, 3)];
                if (wheel == 2) {
                    type = types[getRandomInt(0, 2)];
                    this.vehicles.add(vf.getFactory(2).getVehicle(type));
                }
                if (wheel == 4) {
                    type = types[getRandomInt(2, 4)];
                    this.vehicles.add(vf.getFactory(4).getVehicle(type));
                } else
                    this.vehicles.add(vf.getFactory(10).getVehicle("public"));
            }
        }

        else if (t.equals("Country")) {
            for (int i = 0; i < vehicles; i++) {
                wheel = wheels[getRandomInt(0, 3)];
                if (wheel == 2) {
                    this.vehicles.add(vf.getFactory(2).getVehicle("fast"));
                }
                if (wheel == 4) {
                    type = types[getRandomInt(2, 5)];
                    this.vehicles.add(vf.getFactory(4).getVehicle(type));
                } else
                    this.vehicles.add(vf.getFactory(10).getVehicle("work"));
            }
        }

        else {
            for (int i = 0; i < vehicles; i++) {
                do {
                    road = map.getRoads().get(getRandomInt(0, map.getRoads().size()));
                } while (!road.isEnable());
                this.vehicles.add(new Vehicle(road));
            }
        }
    }

    private void addTimedElements(){
        for(int i = 0; i < this.vehicles.size(); i++) {
            this.allTimedElements.add(vehicles.get(i));
        }
        for(int i = 0; i < map.getLights().size(); i++) {
            allTimedElements.add(map.getLights().get(i));
        }
    }

    public void drive(int numOfTurns){
        System.out.println("================ START DRIVING ================");
        System.out.println();
        for(int i = 0; i < numOfTurns; i++){
            System.out.println("**************** TURN " + i + " ****************");
            incrementDrivingTime();
        }
    }

    public void incrementDrivingTime(){
        DrivingTime ++;
        for(int i = 0; i < this.allTimedElements.size(); i++) {
//            System.out.println(allTimedElements.get(i));
            this.allTimedElements.get(i).incrementDrivingTime();
            System.out.println();
        }
    }

    public static Road addRandomRoad() {
        int index;
        do {
            index = GameDriver.r.nextInt(map.getRoads().size());
        } while (!map.getRoads().get(index).isEnable());
        return map.getRoads().get(index);
    }


    public synchronized void resumeDriving() {
        this.stop = false;
        notifyAll();
    }

    private synchronized void stopDriving() {
        while (stop) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            drive(1);
            stopDriving();
            try {
                sleep(GameDriver.runtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param o
     * @return true if this instance of object equals to another.
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Driving)) return false;
        Driving driving = (Driving) o;
        return DrivingTime == driving.DrivingTime &&
                Objects.equals(map, driving.map) &&
                Objects.equals(vehicles, driving.vehicles) &&
                Objects.equals(allTimedElements, driving.allTimedElements);
    }

}
