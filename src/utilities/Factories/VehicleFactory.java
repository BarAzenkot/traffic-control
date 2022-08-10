package utilities.Factories;

import components.Vehicle;

public interface VehicleFactory {
    public Vehicle getVehicle(String type);
}
