package utilities.Factories;

import components.Driving;
import components.Vehicle;
import utilities.VehicleType;

public class TwoWheelsVehicle implements VehicleFactory{

    @Override
    public Vehicle getVehicle(String type) {
        if (type == "fast")
            return new Vehicle(VehicleType.motorcycle);
        return new Vehicle(VehicleType.bicycle);
    }

}
