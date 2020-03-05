package donjon.Game;

import donjon.Manager.TileManager;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

import java.io.*;
import java.util.ArrayList;

public class Map {
    /* Cropped tiles from the file */
    private Tile[] tilesCropped;

    /* Tiles of the map */
    private Tile[][] mapTiles;

    private int tileSize;

    private int width;
    private int height;

    public Map() {
        this.tilesCropped = new Tile[0];
        this.mapTiles = new Tile[0][0];
        this.tileSize = 0;
        this.width = 0;
        this.height = 0;
    }

    public Map(String tilesetFile, String mapFile) {
        try {
            cropFromFile(tilesetFile);
            initMapFromFile(mapFile);
        } catch (Exception e) {
            System.out.println("[E] Error: can't load the map");
        }
    }

    private void cropFromFile(String tilesetFile) {
        try {
            InputStream is = getClass().getResourceAsStream(("/Resources/Tileset/" + tilesetFile));
            BufferedReader brTileset = new BufferedReader(new InputStreamReader(is));
            String tilesetImageFileName = brTileset.readLine();

            Image tileset = new Image(
                    getClass().getResourceAsStream("/Resources/Sprites/" + tilesetImageFileName)
            );
            int tilesetWidth = (int) tileset.getWidth();

            /* The number of rows and cols */
            String[] rct = brTileset.readLine().split(" ");
            int rt = Integer.parseInt(rct[0]);
            int ct = Integer.parseInt(rct[1]);

            this.tileSize = tilesetWidth / ct;
            System.out.println("Info: size of tile = " + tileSize);

            this.tilesCropped = new Tile[rt * ct];

            /* Let's crop the tiles */
            int pos = 0;
            for (int i = 0; i < rt; i++) {
                for (int j = 0; j < ct; j++) {
                    PixelReader pixelReader = tileset.getPixelReader();

                    WritableImage croppedTile = new WritableImage(
                            pixelReader,
                            j * tileSize,
                            i * tileSize,
                            tileSize,
                            tileSize
                    );

                    tilesCropped[pos] = new Tile(
                            croppedTile,
                            Integer.parseInt(brTileset.readLine().split(" ")[1]),
                            pos
                    );
                    pos += 1;
                }
            }

            TileManager.TILES_LIST = tilesCropped;

            brTileset.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initMapFromFile(String mapFile) {
        try {
            InputStream is = getClass().getResourceAsStream(("/Resources/Map/" + mapFile));
            BufferedReader brMap = new BufferedReader(new InputStreamReader(is));
            String[] mrc = brMap.readLine().split(" ");
            int cm = Integer.parseInt(mrc[0]);
            int rm = Integer.parseInt(mrc[1]);

            this.mapTiles = new Tile[rm][cm];

            /* Let's load the tiles from the map */
            for (int i = 0; i < rm; i++) {
                String[] line = brMap.readLine().split(" ");
                for (int j = 0; j < line.length; j++) {
                    int tileNb = Integer.parseInt(line[j]);
                    mapTiles[i][j] = new Tile(
                            tilesCropped[tileNb].getImageView().getImage(),
                            tilesCropped[tileNb].getType(), tileNb
                    );
                }
            }

            width = cm * tileSize;
            height = rm * tileSize;

            brMap.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Index> getIndexOfTilesBetweenPositions(double x, double y, double w, double h) {
        ArrayList<Index> tilesOn = new ArrayList<>();

        int xDownRight = (int) (w - w % tileSize);
        int yDownRight = (int) (h - h % tileSize);
        int xUpperLeft = (int) (x - x % tileSize);
        int yUpperLeft = (int) (y - y % tileSize);

        int xdr = 0;
        int ydr = 0;
        int xul = 0;
        int yul = 0;

        if (xUpperLeft != 0) xul = xUpperLeft / tileSize;
        if (yUpperLeft != 0) yul = yUpperLeft / tileSize;
        if (xDownRight != 0) xdr = xDownRight / tileSize;
        if (yDownRight != 0) ydr = yDownRight / tileSize;

        for (int i = yul; i <= ydr; i++) {
            for (int j = xul; j <= xdr; j++) {
                tilesOn.add(new Index(i, j));
            }
        }

        return tilesOn;
    }

    public Index getIndexFromPosition(double posX, double posY) {
        int zoneX = (int) (posX - posX % tileSize);
        int zoneY = (int) (posY - posY % tileSize);

        int x = 0;
        int y = 0;

        if (zoneX != 0) x = zoneX / tileSize;
        if (zoneY != 0) y = zoneY / tileSize;

        /* This is normal don't panick */
        return new Index(y, x);
    }

    public Tile getSingleTileFromPosition(double posX, double posY) {
        Index index = getIndexFromPosition(posX, posY);

        return mapTiles[index.getX()][index.getY()];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getMapTiles() {
        return mapTiles;
    }

    public int getTileSize() {
        return tileSize;
    }
}
