package CarSimulator.View;

import CarSimulator.Controller.CarController;
import CarSimulator.DrawPanel;
import CarSimulator.Models.VehicleObserver;
import CarSimulator.Models.Motor_vehicle;
import CarSimulator.Models.VehicleModel;
import CarSimulator.Models.Vehicle_models.Saab95;
import CarSimulator.Models.Vehicle_models.Scania;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * CarView represents the View in the MVC architecture.
 *
 * Responsibilities:
 * - Display the cars on the screen (DrawPanel)
 * - Contain all GUI components (buttons, sliders, etc.)
 * - Send user actions to the Controller
 * - Observe the Model and update the GUI when the model changes
 *
 * Important design change:
 * CarView now reads the vehicle list directly from VehicleModel
 * instead of going through CarController. This reduces coupling
 * between the Controller and the View.
 */
public class CarView extends JFrame implements VehicleObserver {

    // Window size
    private static final int X = 1200;
    private static final int Y = 900;

    // Buttons for adding/removing cars
    private JButton addCarButton;
    private JButton removeCarButton;

    // Controller reference (used to send user actions)
    private final CarController carC;

    // Direct reference to the Model
    // View reads model state directly instead of asking the controller
    private final VehicleModel model;

    // Panel responsible for drawing cars
    DrawPanel drawPanel;

    // GUI panels
    JPanel controlPanel = new JPanel();
    JPanel gasPanel = new JPanel();

    // Gas input
    JSpinner gasSpinner = new JSpinner();
    int gasAmount = 0;
    JLabel gasLabel = new JLabel("Amount of gas");

    // Control buttons
    JButton gasButton = new JButton("Gas");
    JButton brakeButton = new JButton("Brake");
    JButton turboOnButton = new JButton("Saab Turbo on");
    JButton turboOffButton = new JButton("Saab Turbo off");
    JButton liftBedButton = new JButton("Scania Lift Bed");
    JButton lowerBedButton = new JButton("Lower Lift Bed");

    JButton startButton = new JButton("Start all cars");
    JButton stopButton = new JButton("Stop all cars");

    /**
     * Constructor for CarView
     *
     * @param framename window title
     * @param cc controller (used to send user actions)
     * @param model the model (contains all cars)
     */
    public CarView(String framename, CarController cc, VehicleModel model) {

        // Save controller and model references
        this.carC = cc;
        this.model = model;

        // DrawPanel receives the vehicle list from the model
        this.drawPanel = new DrawPanel(X, Y - 180, model.getVehicles());

        // Build GUI
        initComponents(framename);

        // Update button states based on model state
        updateButtonStatesFromModel();
    }

    /**
     * Builds and arranges all GUI components.
     */
    private void initComponents(String title) {

        addCarButton = new JButton("Add car");
        removeCarButton = new JButton("Remove car");

        this.setTitle(title);
        this.setPreferredSize(new Dimension(X, Y));
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        // Add the drawing area to the frame
        this.add(drawPanel);

        // Gas spinner (0-100)
        SpinnerModel spinnerModel =
                new SpinnerNumberModel(0, 0, 100, 1);

        gasSpinner = new JSpinner(spinnerModel);

        gasSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                gasAmount = (int) ((JSpinner) e.getSource()).getValue();
            }
        });

        gasPanel.setLayout(new BorderLayout());
        gasPanel.add(gasLabel, BorderLayout.PAGE_START);
        gasPanel.add(gasSpinner, BorderLayout.PAGE_END);

        this.add(gasPanel);

        // Panel for main control buttons
        controlPanel.setLayout(new GridLayout(2, 4));

        controlPanel.add(gasButton);
        controlPanel.add(turboOnButton);
        controlPanel.add(liftBedButton);
        controlPanel.add(brakeButton);
        controlPanel.add(turboOffButton);
        controlPanel.add(lowerBedButton);
        controlPanel.add(addCarButton);
        controlPanel.add(removeCarButton);

        controlPanel.setPreferredSize(new Dimension((X / 2) + 4, 100));
        controlPanel.setBackground(Color.CYAN);

        this.add(controlPanel);

        // Start button
        startButton.setBackground(Color.blue);
        startButton.setForeground(Color.green);
        startButton.setPreferredSize(new Dimension(180, 80));
        this.add(startButton);

        // Stop button
        stopButton.setBackground(Color.red);
        stopButton.setForeground(Color.black);
        stopButton.setPreferredSize(new Dimension(180, 80));
        this.add(stopButton);

        /**
         * Button listeners
         * The View does NOT modify the model directly.
         * Instead it sends the user action to the Controller.
         */

        gasButton.addActionListener(e -> carC.gas(gasAmount));
        brakeButton.addActionListener(e -> carC.brake(gasAmount));
        startButton.addActionListener(e -> carC.start());
        stopButton.addActionListener(e -> carC.stop());

        turboOnButton.addActionListener(e -> carC.turboOn());
        turboOffButton.addActionListener(e -> carC.turboOff());

        liftBedButton.addActionListener(e -> carC.liftBed());
        lowerBedButton.addActionListener(e -> carC.lowerBed());

        addCarButton.addActionListener(e -> carC.addCar());
        removeCarButton.addActionListener(e -> carC.removeCar());

        this.pack();

        // Center window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2,
                dim.height / 2 - this.getSize().height / 2);

        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Observer callbacks
     *
     * These methods are called when the model notifies observers.
     * The view reacts by repainting and updating button states.
     */

    @Override
    public void vehicleMoved() {
        drawPanel.repaint();
        updateButtonStatesFromModel();
    }

    @Override
    public void vehicleRemoved() {
        drawPanel.repaint();
        updateButtonStatesFromModel();
    }

    @Override
    public void vehicleAdded() {
        drawPanel.repaint();
        updateButtonStatesFromModel();
    }

    @Override
    public void vehicleStateChanged() {
        drawPanel.repaint();
        updateButtonStatesFromModel();
    }

    public Point getVolvoWorkshopPoint() {
        return drawPanel.getVolvoWorkshopPoint();
    }

    public int getMaxX() {
        return drawPanel.getWidth();
    }

    public int getMaxY() {
        return drawPanel.getHeight();
    }

    /**
     * Reads the model state and enables/disables buttons accordingly.
     *
     * This logic is now inside the View instead of the Controller.
     * That means the Controller no longer controls GUI details.
     */
    private void updateButtonStatesFromModel() {

        boolean hasCars = !model.getVehicles().isEmpty();

        boolean canTurboOn = false;
        boolean canTurboOff = false;
        boolean canLiftBed = false;
        boolean canLowerBed = false;

        // Inspect vehicles to determine allowed actions
        for (Motor_vehicle car : model.getVehicles()) {

            if (car instanceof Saab95 saab) {
                if (!saab.isTurboOn()) {
                    canTurboOn = true;
                }
                if (saab.isTurboOn()) {
                    canTurboOff = true;
                }
            }

            if (car instanceof Scania scania) {
                if (scania.getFlatBedAngle() == 0) {
                    canLiftBed = true;
                }
                if (scania.getFlatBedAngle() > 0) {
                    canLowerBed = true;
                }
            }
        }

        // Enable/disable buttons
        addCarButton.setEnabled(model.getVehicles().size() < 10);
        removeCarButton.setEnabled(hasCars);

        gasButton.setEnabled(hasCars);
        brakeButton.setEnabled(hasCars);
        startButton.setEnabled(hasCars);
        stopButton.setEnabled(hasCars);

        turboOnButton.setEnabled(canTurboOn);
        turboOffButton.setEnabled(canTurboOff);

        liftBedButton.setEnabled(canLiftBed);
        lowerBedButton.setEnabled(canLowerBed);
    }
}