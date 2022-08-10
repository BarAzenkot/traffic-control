package utilities;

import components.Vehicle;

public class Report {

    private ReportState state = new UnApprovedState();
    private int reportID;
    private Vehicle vehicle;

    public Report(int rID, Vehicle v) {
        reportID = rID;
        vehicle= v;

    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ReportState getState() {
        return state;
    }

}


