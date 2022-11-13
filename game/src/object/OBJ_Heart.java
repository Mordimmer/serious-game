package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Heart extends SuperObject {

public OBJ_Heart() {
        name = "Heart";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("heart.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("heart-empty.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
    
}
