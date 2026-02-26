import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel{

    // list of carImages
    private List<BufferedImage> carImages = new ArrayList<>(); //lista med bilder för alla bilar

    // hashmap of carPositions
    private HashMap<BufferedImage, Point> carPositions = new HashMap<>();

    BufferedImage volvoWorkshopImage;
    Point volvoWorkshopPoint = new Point(300,0);

    // TODO: Make this general for all cars
    void moveit(int x, int y, int index){
        this.carPositions.put(carImages.get(index), new Point(x, y));
    }

    void carMovedToWorkshop(int index){
        this.carPositions.remove(carImages.get(index));
        this.carImages.remove(index);
    }

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);
        // Print an error message in case file is not found with a try/catch block
        try {

            //laddar alla bilder i listan
            carImages.add(ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg")));
            carImages.add(ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Saab95.jpg")));
            carImages.add(ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Scania.jpg")));

            volvoWorkshopImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        create_vehiclePosDict();

    }

    // This method is called each time the panel updates/refreshes/repaints itself
    // TODOD: Change to suit your needs.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < carImages.size(); i++){ //för varje bild, hämtas pos med samma index
            g.drawImage(carImages.get(i),
                    carPositions.get(carImages.get(i)).x,
                    carPositions.get(carImages.get(i)).y, null);
        }

        //g.drawImage(volvoImage, volvoPoint.x, volvoPoint.y, null); // see javadoc for more info on the parameters

        g.drawImage(volvoWorkshopImage, volvoWorkshopPoint.x, volvoWorkshopPoint.y, null);
    }

    void create_vehiclePosDict(){
        for(int i = 0; i < carImages.size(); i++){
            carPositions.put(carImages.get(i), new Point(0, i*100));
        }
    }
}
