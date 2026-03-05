package Lab1and2;

import Lab1and2.Models.Motor_vehicle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This panel represents the animated part of the view with the car images.
// Responsibility:
// - Fetch each vehicle's image path
// - Load image if needed
// - Draw image at vehicle's current position
// NOTE: This class does NOT decide which image belongs to which vehicle.
// That responsibility lies in the model (Motor_vehicle).

public class DrawPanel extends JPanel implements VehicleObserver {

    // List of vehicles to draw (comes from controller)
    private final List<Motor_vehicle> vehicles;

    // Cache to avoid reloading images every repaint
    // Key = image path, Value = loaded BufferedImage
    private final Map<String, BufferedImage> imageCache = new HashMap<>();

    // Workshop image (static background element)
    private BufferedImage volvoWorkshopImage;
    private final Point volvoWorkshopPoint = new Point(300, 0);

    // Initializes the panel and receives the vehicles to draw
    public DrawPanel(int x, int y, List<Motor_vehicle> vehicles) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);

        this.vehicles = vehicles;

        // Load workshop image
        try {
            volvoWorkshopImage = ImageIO.read(
                    DrawPanel.class.getResourceAsStream("/pics/VolvoBrand.jpg"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Could not load workshop image: /pics/VolvoBrand.jpg");
            volvoWorkshopImage = null;
        }
    }

    // Loads image based on path (from model)
    // Uses cache to avoid repeated disk reads
    private BufferedImage getImageByPath(String path) {

        if (path == null) return null;

        // If image is not already cached, load it
        if (!imageCache.containsKey(path)) {
            try {
                BufferedImage img = ImageIO.read(
                        DrawPanel.class.getResourceAsStream(path));
                imageCache.put(path, img);
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("Could not load image: " + path);
                imageCache.put(path, null);
            }
        }

        return imageCache.get(path);
    }


    //called by Motor_vehicle
    @Override
    public void positionChanged(){
        repaint();
    }

    // Called automatically when panel repaints
    // Draws all vehicles at their current positions
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // For each vehicle:
        // 1. Get its image path from model
        // 2. Load image via cache
        // 3. Draw at vehicle's coordinates
        for (Motor_vehicle vehicle : vehicles) {

            BufferedImage image = getImageByPath(vehicle.getImagePath());
            Point position = vehicle.getCoordinates();

            if (image != null && position != null) {
                g.drawImage(image, position.x, position.y, null);
            }
        }

        // Draw workshop image (static element)
        if (volvoWorkshopImage != null) {
            g.drawImage(volvoWorkshopImage,
                    volvoWorkshopPoint.x,
                    volvoWorkshopPoint.y,
                    null);
        }

    }

}