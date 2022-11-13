package main;

import object.OBJ_Door;
import object.OBJ_Enemy;

public class AssetSetter {
    
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_Enemy();
        gp.obj[0].worldX = 10 * gp.tileSize;
        gp.obj[0].worldY = 6 * gp.tileSize;

        gp.obj[1] = new OBJ_Enemy();
        gp.obj[1].worldX = 4 * gp.tileSize;
        gp.obj[1].worldY = 10 * gp.tileSize;
    
        gp.obj[2] = new OBJ_Enemy();
        gp.obj[2].worldX = 7 * gp.tileSize;
        gp.obj[2].worldY = 9 * gp.tileSize;

        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = 1 * gp.tileSize;
        gp.obj[3].worldY = 1 * gp.tileSize;
    }
}
