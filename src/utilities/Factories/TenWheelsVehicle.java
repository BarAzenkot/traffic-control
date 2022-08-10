package utilities.Factories;

import components.Vehicle;
import utilities.VehicleType;

public class TenWheelsVehicle implements VehicleFactory {

    @Override
    public Vehicle getVehicle(String type) {
        if (type == "public")
            return new Vehicle(VehicleType.tram);
        return new Vehicle(VehicleType.semitrailer);
    }

}
