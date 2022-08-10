package utilities;

import components.Vehicle;

import java.io.*;
import java.nio.file.StandardOpenOption;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Moked extends Colleague{

    public static final String address = System.getProperty("user.dir") + "\\reports.txt";
    private int counter = 0;
    private ReadWriteLock lock;
    private static ArrayList<Report> reports = new ArrayList<Report>();

    public Moked() {
        super(GameDriver.mediator);
        ((ApplicationMediator) getMediator()).addColleague(this);
        lock = new ReentrantReadWriteLock();
    }

    public void Write(Vehicle vehicle, LocalDateTime t) {
        lock.writeLock().lock();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(address, true));
            writer.write("Report No." + (++counter) + " to vehicle " + vehicle.getID() + " for driving at " + vehicle.getCurrentSpeed() * 10
                    + " MPH. Road's speed limit: " + vehicle.getLastRoad().getMaxSpeed() * 10 + " MPH. Time: " + t + ".");
            writer.newLine();
            writer.close();
            reports.add(new Report(counter, vehicle));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public String Read(int rID) {
        String line = null;
        lock.readLock().lock();
        try {
            FileReader reader = new FileReader(address);
            BufferedReader lineReader = new BufferedReader(reader);
            for(int i = 1; i < rID; i++)
                lineReader.readLine();
            line = lineReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
            return line;
        }
    }

    public static boolean allApproved() {
        for (Report report : reports) {
            if(report.getState() instanceof UnApprovedState)
                return false;
        }
        return true;
    }

    @Override
    public void receive(Object obj) {
        Write(((Vehicle) obj), java.time.LocalDateTime.now());
        send(reports.get(reports.size() - 1));
    }
}
