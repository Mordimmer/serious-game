package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_Enemy extends SuperObject {

    public OBJ_Enemy() {
        name = "Enemy";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("res/enemy-front-1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}