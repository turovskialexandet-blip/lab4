package CarSimulator.Controller;

import CarSimulator.CollisionHandler;
import CarSimulator.Models.*;
import CarSimulator.Models.VehicleModel;
import CarSimulator.Models.Vehicle_models.Volvo240;
import CarSimulator.Tick;
import CarSimulator.View.CarView;

public class CarController {
    private final VehicleModel model;

    CarView frame;

    final Workshop<Volvo240> volvoBrand = new Workshop<>(2);

    public CarController(VehicleModel model) {
        this.model = model;
    }

    public static void main(String[] args) {
        VehicleModel model = new VehicleModel();
        CarController cc = new CarController(model);

        model.addVehicle(Motor_vehicleFactory.createVolvo240());
        model.addVehicle(Motor_vehicleFactory.createSaab95());
        model.addVehicle(Motor_vehicleFactory.createScania());

        cc.carStartPositions();

        cc.frame = new CarView("CarSim 1.0", cc, model);

        // CarView observerar modellen
        model.addObserver(cc.frame);

        // CarView observerar även varje enskilt fordon
        for (Motor_vehicle car : model.getVehicles()) {
            car.addObserver(cc.frame);
        }

        CollisionHandler collisionHandler = new CollisionHandler();

        Tick tick = new Tick(
                model.getVehicles(),
                collisionHandler,
                cc.volvoBrand,
                cc.frame.getVolvoWorkshopPoint(),
                cc.frame.getMaxX(),
                cc.frame.getMaxY()
        );
        tick.start();
    }

    public void carStartPositions() {
        int startX = 0;
        int startY = 0;
        int ySpacing = 65;

        for (int i = 0; i < model.getVehicles().size(); i++) {
            Motor_vehicle car = model.getVehicles().get(i);
            car.getCoordinates().x = startX;
            car.getCoordinates().y = startY + i * ySpacing;
        }

        model.notifyVehicleMoved();
    }

    public void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Motor_vehicle car : model.getVehicles()) {
            car.gas(gas);
        }
    }

    public void brake(int amount) {
        double brakeAmount = ((double) amount) / 100;
        for (Motor_vehicle car : model.getVehicles()) {
            car.brake(brakeAmount);
        }
    }

    public void start() {
        for (Motor_vehicle car : model.getVehicles()) {
            car.startEngine();
        }
    }

    public void stop() {
        for (Motor_vehicle car : model.getVehicles()) {
            car.stopEngine();
        }
    }

    public void turboOn() {
        for (Motor_vehicle car : model.getVehicles()) {
            if (car instanceof hasTurbo) {
                ((hasTurbo) car).setTurboOn();
                car.stateChanged();
            }
        }
        model.notifyVehicleStateChanged();
    }

    public void  turboOff() {
        for (Motor_vehicle car : model.getVehicles()) {
            if (car instanceof hasTurbo) {
                ((hasTurbo) car).setTurboOff();
                car.stateChanged();
            }
        }
        model.notifyVehicleStateChanged();
    }

    public void liftBed() {
        for (Motor_vehicle car : model.getVehicles()) {
            if (car instanceof hasFlatbed) {
                ((hasFlatbed) car).RaiseFlatbed(45);
                car.stateChanged();
            }
        }
        model.notifyVehicleStateChanged();
    }

    public void lowerBed() {
        for (Motor_vehicle car : model.getVehicles()) {
            if (car instanceof hasFlatbed) {
                ((hasFlatbed) car).LowerFlatbed(45);
                car.stateChanged();
            }
        }
        model.notifyVehicleStateChanged();
    }

    public void addCar() {
        if (model.getVehicles().size() >= 10) {
            return;
        }

        Motor_vehicle newCar;
        int randomNum = (int)(Math.random() * 3);

        if (randomNum == 0) {
            newCar = Motor_vehicleFactory.createVolvo240();
        } else if (randomNum == 1) {
            newCar = Motor_vehicleFactory.createSaab95();
        } else {
            newCar = Motor_vehicleFactory.createScania();
        }

        newCar.addObserver(frame);

        // Lägg till bilen via modellen
        model.addVehicle(newCar);

        carStartPositions();
        newCar.add();

        System.out.println(model.getVehicles().size());
    }

    public void removeCar() {
        if (model.getVehicles().isEmpty()) {
            return;
        }

        Motor_vehicle removedCar = model.removeLastVehicle();

        if (removedCar != null) {
            removedCar.remove();
        }

        carStartPositions();

        System.out.println(model.getVehicles().size());
    }
}