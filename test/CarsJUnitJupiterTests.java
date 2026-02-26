import Lab1and2.*;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class CarsJUnitJupiterTests {
    private final Car volvo240 = new Volvo240();
    private final Car saab95 = new Saab95();
    private final Scania scania = new Scania();

    @Test
    public void initTest(){
        // tests initialization
        System.out.println("___\nTests initialization:");
        System.out.println(volvo240);
        System.out.println(saab95);
        System.out.println(scania);
    }

    @Test
    public void getterTest(){
        // tests getters
        assertEquals(4, volvo240.getNrDoors());
        assertEquals(2, saab95.getNrDoors());
        assertEquals(100, volvo240.getEnginePower());
        assertEquals(125,saab95.getEnginePower());
        assertEquals(Color.red,saab95.getColor());
        assertEquals(Color.black,volvo240.getColor());
        assertFalse(saab95.getCurrentSpeed() >0);
        assertFalse(volvo240.getCurrentSpeed() >0);
        assertTrue(scania.getFlatBedAngle()>=0 && scania.getFlatBedAngle() <=70);
    }

    @Test
    public void setterTest(){
        // tests setters
        Color beforeS = saab95.getColor();
        saab95.setColor(Color.magenta);
        assertNotSame(beforeS, saab95.getColor());

        Color beforeV = volvo240.getColor();
        volvo240.setColor(Color.cyan);
        assertNotSame(beforeV, volvo240.getColor());
    }

    @Test
    public void movement_And_rotationTest(){
        // tests the movement of the cars
        volvo240.startEngine();
        assertEquals(0.1, volvo240.getCurrentSpeed());

        volvo240.incrementSpeed(10);
        double nowV = volvo240.getCurrentSpeed();
        assertNotSame(0.1, nowV);


        for (int i = 0; i < 2; i++){
            volvo240.turnRight();
            volvo240.move();
            volvo240.turnLeft();
            volvo240.move();
        }
        Point cordVnow = new Point(volvo240.getCoordinates().x, volvo240.getCoordinates().y);
        assertNotSame(new Point(0, 0), cordVnow);

        for (int i = 1; i < 3; i++){
            volvo240.decrementSpeed(i);
            volvo240.move();
            assertNotSame(cordVnow, volvo240.getCoordinates());
        }

        volvo240.stopEngine();
        assertEquals(0, volvo240.getCurrentSpeed());
        Point befcord = volvo240.getCoordinates();
        volvo240.move();
        assertSame(befcord, volvo240.getCoordinates());

    }

    @Test
    public void gas_And_brake(){
        volvo240.startEngine();
        volvo240.incrementSpeed(30);
        assertTrue(volvo240.getCurrentSpeed()>0.1);

        double beforeVol = volvo240.getCurrentSpeed();
        volvo240.gas(0.5);
        assertTrue(volvo240.getCurrentSpeed() > beforeVol);

        double beforeVol2 = volvo240.getCurrentSpeed();
        volvo240.gas(2);
        assertTrue(volvo240.getCurrentSpeed() > beforeVol2);

        double beforeVol3 = volvo240.getCurrentSpeed();
        volvo240.brake(4);
        assertTrue(volvo240.getCurrentSpeed() < beforeVol3);

        volvo240.brake(0.3);
        System.out.printf("Volvo240s speed after 2nd brake call: %s.\n", volvo240.getCurrentSpeed());
    }

    @Test
    public void testRaiseFlatbedPreventsDriving() {
        Scania scania = new Scania();

        scania.RaiseFlatbed(50);
        scania.startEngine();
        scania.gas(1.0);

        assertEquals(0, scania.getCurrentSpeed());
    }

    @Test
    public void testCanDriveWhenFlatbedIsDown() {
        Scania scania = new Scania();
        scania.startEngine();

        scania.gas(1.0);

        assertTrue(scania.getCurrentSpeed() > 0);
    }

    @Test
    public void testCannotRaiseFlatbedWhileMoving() {
        Scania scania = new Scania();
        scania.startEngine();
        scania.gas(1.0);

        scania.RaiseFlatbed(50);

        assertEquals(0, scania.getFlatBedAngle());
    }

    @Test
    public void testCannotLoadCarWhenRampIsUp() {
        MAN man = new MAN(3);
        Car volvo = new Volvo240();

        // är rampen uppe?
        man.raiseRamp();
        assertFalse(man.isRampLowered());

        boolean loaded = man.loadCar(volvo);

        // Ska inte gå att lasta när rampen är uppe
        assertFalse(loaded);
        assertEquals(0, man.getTruckCargo().size());
    }

    @Test
    public void testUnloadIsFILO() {
        MAN man = new MAN(5);
        Car car1 = new Volvo240();
        Car car2 = new Saab95();

        // Rampen måste vara nere för last/loss
        man.lowerRamp();
        assertTrue(man.isRampLowered());

        assertTrue(man.loadCar(car1));
        assertTrue(man.loadCar(car2));

        // FILO: sista in (car2) ska ut först
        Car firstOut = man.unloadCar();
        assertSame(car2, firstOut);

        Car secondOut = man.unloadCar();
        assertSame(car1, secondOut);
    }

    @Test
    public void testLoadedCarFollowsMANPosition() {
        MAN man = new MAN(2);
        Car volvo = new Volvo240();

        // Lasta bilen (ramp nere)
        man.lowerRamp();
        assertTrue(man.loadCar(volvo));

        // För att kunna köra: ramp upp
        man.raiseRamp();
        assertFalse(man.isRampLowered());

        // Kör och flytta Lab1and2.MAN
        man.startEngine();
        man.gas(1.0);
        man.move();

        // Lastad bil ska alltid ha samma position som Lab1and2.MAN
        assertEquals(man.getCoordinates(), volvo.getCoordinates());
    }

    @Test
    public void testCannotDriveWhenRampIsDown() {
        MAN man = new MAN(2);
        man.lowerRamp();
        assertTrue(man.isRampLowered());

        man.startEngine();
        double before = man.getCurrentSpeed(); // t.ex. 0.1

        man.gas(1.0);

        // Ska INTE accelerera när rampen är nere
        assertEquals(before, man.getCurrentSpeed());
    }

    @Test
    public void testCannotLowerRampWhileMoving() {
        MAN man = new MAN(2);

        man.raiseRamp();
        man.startEngine();
        man.gas(1.0);
        assertTrue(man.getCurrentSpeed() > 0);

        man.lowerRamp();

        assertFalse(man.isRampLowered());
    }

    @Test
    public void testCannotLoadMoreThanMaxCars() {
        MAN man = new MAN(1); // max 1 bil
        Car car1 = new Volvo240();
        Car car2 = new Saab95();

        // Rampen nere för lastning
        man.lowerRamp();
        assertTrue(man.isRampLowered());

        // Första bilen ska gå att lasta
        assertTrue(man.loadCar(car1));
        assertEquals(1, man.getTruckCargo().size());

        // Andra bilen ska INTE gå att lasta
        assertFalse(man.loadCar(car2));
        assertEquals(1, man.getTruckCargo().size());
    }

    @Test
    public void testCannotLoadWhileMoving() {
        MAN man = new MAN(2);
        Car car = new Volvo240();

        man.lowerRamp();
        man.raiseRamp();
        man.startEngine();
        man.gas(1.0);

        man.lowerRamp(); // ska misslyckas, ramp ska vara uppe
        assertFalse(man.isRampLowered());

        // även om man försöker lasta ska det faila
        assertFalse(man.loadCar(car));
    }
    @Test
    public void testWorkshopOnlyAcceptsCorrectType() {
        Workshop<Volvo240> volvoWorkshop = new Workshop<>(2);

        Volvo240 v = new Volvo240();
        assertTrue(volvoWorkshop.load(v));
        assertEquals(1, volvoWorkshop.getStoredCount());

        Volvo240 out = volvoWorkshop.unload();
        assertSame(v, out);

        // Följande rad ska INTE kompilera (compile-time error) och är hela poängen med uppgift 3:
        // volvoWorkshop.load(new Lab1and2.Saab95());
    }

    @Test
    public void testWorkshopCanAcceptAnyCarTypeWhenUsingCar() {
        Workshop<Car> generalWorkshop = new Workshop<>(3);

        assertTrue(generalWorkshop.load(new Volvo240()));
        assertTrue(generalWorkshop.load(new Saab95()));
        assertEquals(2, generalWorkshop.getStoredCount());

        Car out = generalWorkshop.unload();
        assertNotNull(out);
    }

    @Test
    public void testWorkshopRespectsMaxCapacity() {
        Workshop<Car> workshop = new Workshop<>(1);

        assertTrue(workshop.load(new Volvo240()));
        assertFalse(workshop.load(new Saab95()));
        assertEquals(1, workshop.getStoredCount());
    }


}