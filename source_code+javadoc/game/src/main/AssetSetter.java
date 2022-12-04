package main;

import object.OBJ_Door;
import object.OBJ_Enemy;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**
     * LOADING OBJECTS
     */
    public void setObject() {

        /**
         * MAP 0
         */
        int mapNum = 0;
        gp.obj[mapNum][0] = new OBJ_Door();
        gp.obj[mapNum][0].worldX = 1 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 1 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_Enemy();
        gp.obj[mapNum][1].worldX = 14 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 14 * gp.tileSize;

        gp.obj[mapNum][2] = new OBJ_Enemy();
        gp.obj[mapNum][2].worldX = 11 * gp.tileSize;
        gp.obj[mapNum][2].worldY = 10 * gp.tileSize;

        gp.obj[mapNum][3] = new OBJ_Enemy();
        gp.obj[mapNum][3].worldX = 18 * gp.tileSize;
        gp.obj[mapNum][3].worldY = 4 * gp.tileSize;

        gp.obj[mapNum][4] = new OBJ_Enemy();
        gp.obj[mapNum][4].worldX = 18 * gp.tileSize;
        gp.obj[mapNum][4].worldY = 2 * gp.tileSize;

        gp.obj[mapNum][5] = new OBJ_Enemy();
        gp.obj[mapNum][5].worldX = 13 * gp.tileSize;
        gp.obj[mapNum][5].worldY = 3 * gp.tileSize;

        gp.obj[mapNum][6] = new OBJ_Enemy();
        gp.obj[mapNum][6].worldX = 9 * gp.tileSize;
        gp.obj[mapNum][6].worldY = 4 * gp.tileSize;

        gp.obj[mapNum][8] = new OBJ_Enemy();
        gp.obj[mapNum][8].worldX = 3 * gp.tileSize;
        gp.obj[mapNum][8].worldY = 5 * gp.tileSize;

        gp.obj[mapNum][9] = new OBJ_Enemy();
        gp.obj[mapNum][9].worldX = 8 * gp.tileSize;
        gp.obj[mapNum][9].worldY = 14 * gp.tileSize;

        gp.obj[mapNum][10] = new OBJ_Enemy();
        gp.obj[mapNum][10].worldX = 10 * gp.tileSize;
        gp.obj[mapNum][10].worldY = 6 * gp.tileSize;

        gp.obj[mapNum][11] = new OBJ_Enemy();
        gp.obj[mapNum][11].worldX = 7 * gp.tileSize;
        gp.obj[mapNum][11].worldY = 8 * gp.tileSize;

        gp.obj[mapNum][12] = new OBJ_Enemy();
        gp.obj[mapNum][12].worldX = 3 * gp.tileSize;
        gp.obj[mapNum][12].worldY = 9 * gp.tileSize;

        /**
         * MAP 1
         */
        mapNum = 1;
        gp.obj[mapNum][0] = new OBJ_Door();
        gp.obj[mapNum][0].worldX = 18 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 14 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_Enemy();
        gp.obj[mapNum][1].worldX = 7 * gp.tileSize;
        gp.obj[mapNum][1].worldY = 5 * gp.tileSize;

        gp.obj[mapNum][2] = new OBJ_Enemy();
        gp.obj[mapNum][2].worldX = 5 * gp.tileSize;
        gp.obj[mapNum][2].worldY = 9 * gp.tileSize;

        gp.obj[mapNum][3] = new OBJ_Enemy();
        gp.obj[mapNum][3].worldX = 9 * gp.tileSize;
        gp.obj[mapNum][3].worldY = 3 * gp.tileSize;

        gp.obj[mapNum][4] = new OBJ_Enemy();
        gp.obj[mapNum][4].worldX = 18 * gp.tileSize;
        gp.obj[mapNum][4].worldY = 5 * gp.tileSize;

        gp.obj[mapNum][5] = new OBJ_Enemy();
        gp.obj[mapNum][5].worldX = 13 * gp.tileSize;
        gp.obj[mapNum][5].worldY = 5 * gp.tileSize;

        gp.obj[mapNum][6] = new OBJ_Enemy();
        gp.obj[mapNum][6].worldX = 18 * gp.tileSize;
        gp.obj[mapNum][6].worldY = 12 * gp.tileSize;

        gp.obj[mapNum][7] = new OBJ_Enemy();
        gp.obj[mapNum][7].worldX = 13 * gp.tileSize;
        gp.obj[mapNum][7].worldY = 11 * gp.tileSize;

        gp.obj[mapNum][8] = new OBJ_Enemy();
        gp.obj[mapNum][8].worldX = 13 * gp.tileSize;
        gp.obj[mapNum][8].worldY = 9 * gp.tileSize;

        gp.obj[mapNum][9] = new OBJ_Enemy();
        gp.obj[mapNum][9].worldX = 13 * gp.tileSize;
        gp.obj[mapNum][9].worldY = 7 * gp.tileSize;

        gp.obj[mapNum][10] = new OBJ_Enemy();
        gp.obj[mapNum][10].worldX = 1 * gp.tileSize;
        gp.obj[mapNum][10].worldY = 10 * gp.tileSize;

        gp.obj[mapNum][12] = new OBJ_Enemy();
        gp.obj[mapNum][12].worldX = 3 * gp.tileSize;
        gp.obj[mapNum][12].worldY = 7 * gp.tileSize;

        gp.obj[mapNum][13] = new OBJ_Enemy();
        gp.obj[mapNum][13].worldX = 9 * gp.tileSize;
        gp.obj[mapNum][13].worldY = 14 * gp.tileSize;

        gp.obj[mapNum][14] = new OBJ_Enemy();
        gp.obj[mapNum][14].worldX = 9 * gp.tileSize;
        gp.obj[mapNum][14].worldY = 12 * gp.tileSize;

        gp.obj[mapNum][15] = new OBJ_Enemy();
        gp.obj[mapNum][15].worldX = 11 * gp.tileSize;
        gp.obj[mapNum][15].worldY = 1 * gp.tileSize;

        /**
         * MAP 2
         */
        mapNum = 2;
        gp.obj[mapNum][0] = new OBJ_Door();
        gp.obj[mapNum][0].worldX = 1 * gp.tileSize;
        gp.obj[mapNum][0].worldY = 4 * gp.tileSize;

        gp.obj[mapNum][1] = new OBJ_Enemy();
        gp.obj[mapNum][1].worldX = (17 - 1) / 2 * gp.tileSize;
        gp.obj[mapNum][1].worldY = (4 - 1) * gp.tileSize;

        gp.obj[mapNum][2] = new OBJ_Enemy();
        gp.obj[mapNum][2].worldX = (37 - 1) / 2 * gp.tileSize;
        gp.obj[mapNum][2].worldY = (2 - 1) * gp.tileSize;

        gp.obj[mapNum][3] = new OBJ_Enemy();
        gp.obj[mapNum][3].worldX = (31 - 1) / 2 * gp.tileSize;
        gp.obj[mapNum][3].worldY = (12 - 1) * gp.tileSize;

        gp.obj[mapNum][4] = new OBJ_Enemy();
        gp.obj[mapNum][4].worldX = (15 - 1) / 2 * gp.tileSize;
        gp.obj[mapNum][4].worldY = (17 - 1) * gp.tileSize;

        gp.obj[mapNum][5] = new OBJ_Enemy();
        gp.obj[mapNum][5].worldX = (13 - 1) / 2 * gp.tileSize;
        gp.obj[mapNum][5].worldY = (15 - 1) * gp.tileSize;

        gp.obj[mapNum][6] = new OBJ_Enemy();
        gp.obj[mapNum][6].worldX = (11 - 1) / 2 * gp.tileSize;
        gp.obj[mapNum][6].worldY = (11 - 1) * gp.tileSize;
    }
}
