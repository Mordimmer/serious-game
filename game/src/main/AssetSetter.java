package main;

import object.OBJ_Door;
import object.OBJ_Enemy;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0][0] = new OBJ_Door();
        gp.obj[0][0].worldX = 1 * gp.tileSize;
        gp.obj[0][0].worldY = 1 * gp.tileSize;

        gp.obj[0][1] = new OBJ_Enemy();
        gp.obj[0][1].worldX = 13 * gp.tileSize;
        gp.obj[0][1].worldY = 12 * gp.tileSize;

        gp.obj[0][2] = new OBJ_Enemy();
        gp.obj[0][2].worldX = 13 * gp.tileSize;
        gp.obj[0][2].worldY = 5 * gp.tileSize;

        gp.obj[0][3] = new OBJ_Enemy();
        gp.obj[0][3].worldX = 5 * gp.tileSize;
        gp.obj[0][3].worldY = 10 * gp.tileSize;

        gp.obj[0][4] = new OBJ_Enemy();
        gp.obj[0][4].worldX = 17 * gp.tileSize;
        gp.obj[0][4].worldY = 10 * gp.tileSize;

        gp.obj[1][0] = new OBJ_Door();
        gp.obj[1][0].worldX = 18 * gp.tileSize;
        gp.obj[1][0].worldY = 14 * gp.tileSize;

        gp.obj[1][1] = new OBJ_Enemy();
        gp.obj[1][1].worldX = 14 * gp.tileSize;
        gp.obj[1][1].worldY = 11 * gp.tileSize;

        gp.obj[1][2] = new OBJ_Enemy();
        gp.obj[1][2].worldX = 15 * gp.tileSize;
        gp.obj[1][2].worldY = 9 * gp.tileSize;

        gp.obj[1][3] = new OBJ_Enemy();
        gp.obj[1][3].worldX = 5 * gp.tileSize;
        gp.obj[1][3].worldY = 10 * gp.tileSize;

        gp.obj[1][4] = new OBJ_Enemy();
        gp.obj[1][4].worldX = 12 * gp.tileSize;
        gp.obj[1][4].worldY = 8 * gp.tileSize;

        gp.obj[1][5] = new OBJ_Enemy();
        gp.obj[1][5].worldX = 4 * gp.tileSize;
        gp.obj[1][5].worldY = 1 * gp.tileSize;
    }
}
