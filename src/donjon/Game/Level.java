package donjon.Game;

import donjon.Entities.Characters.Blue;
import donjon.Entities.Characters.Character;
import donjon.Entities.Characters.Player;
import donjon.Entities.Engine.Direction;
import donjon.Entities.Engine.Entity;
import donjon.Entities.Interactables.Lever;
import donjon.Entities.Interactables.Lock;
import donjon.Entities.Items.Coin;
import donjon.Entities.Items.Key;
import donjon.Entities.Items.Soul;
import donjon.Entities.Kill.Fire;
import javafx.geometry.Point2D;

import java.io.*;
import java.util.HashMap;

public class Level {
    private final double GRAVITY = 0.4;

    private int id;
    private HashMap<Entity, Index> entitiesMap;
    private Map map;
    private boolean complete;
    private Player player;
    private String text;

    public Level(int id) {
        this.id = id;
        this.map = new Map();
        this.complete = false;
        this.player = null;
        this.entitiesMap = new HashMap<>();
        this.text = "Default text.";
        this.load();
        System.out.println("... Level #" + id + " loaded ...");
    }

    public void load() {
        this.map = new Map("donjon.tileset", id + ".level");
        loadEntities();
    }

    public void loadEntities() {
        try {
            InputStream is = getClass().getResourceAsStream(("/Resources/Entity/level-" + id + ".entities"));
            BufferedReader brEntities = new BufferedReader(new InputStreamReader(is));

            /* We first extract the `text` from the level. */
            this.text = brEntities.readLine();
            int entitiesNumber = Integer.parseInt(brEntities.readLine());
            while (entitiesNumber != 0) {
                /* Then we add each entities */
                getEntityFromLine(brEntities);
                entitiesNumber -= 1;
            }
            brEntities.close();
        } catch (Exception e) {
            System.out.println("[E] Error: can't load entities from level #" + id);
        }
    }

    /* Can be refact into a more generic version, but dunno if it will be clear */
    private void getEntityFromLine(BufferedReader brEntities) throws IOException {
        String entityLine = brEntities.readLine();
        String[] entityLineSplit = entityLine.split(":");
        String entityName = entityLineSplit[0];
        String[] entityData = entityLineSplit[1].split(",");
        int xEntity = Integer.parseInt(entityData[0]);
        int yEntity = Integer.parseInt(entityData[1]);

        int posX = xEntity * map.getTileSize();
        int posY = yEntity * map.getTileSize();

        switch (entityName) {
            case "Blue":
                this.player = new Blue(posX, posY);
                entitiesMap.put(this.player, new Index(xEntity, yEntity));
                break;
            case "Coin":
                Coin coin = new Coin(posX, posY);
                entitiesMap.put(coin, new Index(xEntity, yEntity));
                break;
            case "Key":
                Key key = new Key(posX, posY);
                entitiesMap.put(key, new Index(xEntity, yEntity));
                break;
            case "Soul":
                Soul soul = new Soul(posX, posY);
                entitiesMap.put(soul, new Index(xEntity, yEntity));
                break;
            case "Fire":
                Fire fire = new Fire(posX, posY);
                entitiesMap.put(fire, new Index(xEntity, yEntity));
                break;
            case "Lever":
                Tile[] tilesLinkedLever = getTilesFromFile(brEntities);
                Lever lever = new Lever(posX, posY, tilesLinkedLever);
                entitiesMap.put(lever, new Index(xEntity, yEntity));
                break;
            case "Lock":
                Tile[] tilesLinkedLock = getTilesFromFile(brEntities);
                Lock lock = new Lock(posX, posY, tilesLinkedLock);
                entitiesMap.put(lock, new Index(xEntity, yEntity));
                break;
            default:
                throw new IllegalStateException("[E] Error: can't load entity from line " + entityLine);
        }
    }

    /* Lever or Lock tiles */
    private Tile[] getTilesFromFile(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();

        int lineNumber = Integer.parseInt(line);
        Tile[] tilesFound = new Tile[lineNumber];

        int i = 0;
        while (i < lineNumber) {
            line = bufferedReader.readLine();

            String[] lineSplit = line.split(",");
            int x = Integer.parseInt(lineSplit[0]);
            int y = Integer.parseInt(lineSplit[1]);

            tilesFound[i] = this.map.getMapTiles()[y][x];
            i += 1;
        }

        return tilesFound;
    }

    public void moveCharacter(Character character, boolean up, boolean down, boolean right, boolean left) {
        character.setDirection(new Direction(up, down, right, left));
        moveCharacterAxisComplete(character, up, down, true);
        moveCharacterAxisComplete(character, right, left, false);
    }

    public void moveCharacterAxisComplete(Character character, boolean direction1, boolean direction2, boolean axis) {
        double characterMaxX = this.map.getWidth() - character.getSprite().getImage().getWidth();
        double characterMaxY = this.map.getHeight() - character.getSprite().getImage().getHeight();

        int dx = 0, dy = 0;
        double velocityX = character.getVelocityX();
        double velocityY = character.getVelocityY();

        if (axis) {
            if (direction1) dy -= 1;
            if (direction2) dy += 1;
        } else {
            if (direction1) dx += 1;
            if (direction2) dx -= 1;
        }

        /* Edge position of the character's box */
        double bbx = character.getPosX() + character.getBoundingbox().getX();
        double bby = character.getPosY() + character.getBoundingbox().getY();
        double bbw = bbx + character.getBoundingbox().getWidth();
        double bbh = bby + character.getBoundingbox().getHeight();

        /* Old positions */
        Point2D ul = new Point2D(bbx, bby);
        Point2D dr = new Point2D(bbw, bbh);

        double dxs = dx * velocityX;
        double dys = dy * velocityY;

        double drxs = dr.getX() + dxs;
        double drys = dr.getY() + dys;

        double ulxs = ul.getX() + dxs;
        double ulys = ul.getY() + dys;

        double newPosX = character.getPosX() + dxs;
        double newPosY = character.getPosY() + dys;

        if (newPosX > 0 && newPosY > 0 && newPosX < characterMaxX && newPosY < characterMaxY) {
            for (Index index : map.getIndexOfTilesBetweenPositions(ulxs, ulys, drxs, drys)) {
                int xBlockedTileIndex = index.getX();
                int yBlockedTileIndex = index.getY();

                if (map.getMapTiles()[xBlockedTileIndex][yBlockedTileIndex].getType() == Tile.BLOCKED) {
                    int tileSize = map.getTileSize();
                    double blockedTilePosX = yBlockedTileIndex * tileSize;
                    double blockedTilePosY = xBlockedTileIndex * tileSize;


                    if (axis) {
                        /* Vertical */
                        if (direction1) {
                            /* UP */
                            double difference = bby - (blockedTilePosY + tileSize) - 1;
                            if (difference > 0) {
                                character.moveBy(dxs, -difference);
                                return;
                            }
                        } else {
                            /* DOWN */
                            double difference = blockedTilePosY - bbh - 1;
                            if (difference > 0) {
                                character.moveBy(dxs, difference);
                                return;
                            }
                        }
                    } else {
                        /* Horizontal */
                        if (direction1) {
                            /* RIGHT */
                            double difference = blockedTilePosX - bbw - 1;
                            if (difference > 0) {
                                character.moveBy(difference, dys);
                                return;
                            }
                        } else {
                            /* LEFT */
                            double difference = bbx - (blockedTilePosX + tileSize) - 1;
                            if (difference > 0) {
                                character.moveBy(-difference, dys);
                                return;
                            }
                        }
                    }
                    return;
                }
            }

            character.moveBy(dxs, Math.round(dys));
        }
    }

    public boolean playerIsGrounded() {
        double bbx = player.getPosX() + player.getBoundingbox().getX();
        double bby = player.getPosY() + player.getBoundingbox().getY();

        double bbxr = bbx + player.getBoundingbox().getWidth();
        double bbyr = bby + player.getBoundingbox().getHeight();

        /* We already have the corner x left, it's bbx */
        double bbyl = bby + player.getBoundingbox().getHeight();

        Tile firstTileUnderCharacter = map.getSingleTileFromPosition(bbxr, bbyr + 1);
        Tile secondTileUnderCharacter = map.getSingleTileFromPosition(bbx, bbyl + 1);

        return firstTileUnderCharacter.getType() == Tile.BLOCKED || secondTileUnderCharacter.getType() == Tile.BLOCKED;
    }

    /* Check if there's any collision between the player and other entities and perform what is needed */
    public void handleCollisions() {
        for (Entity e : entitiesMap.keySet()) {
            if (player != e && player.isCollidingWith(e)) {
                e.collide(player);
            }
        }
    }

    /* If an entity is marked removable: good bye */
    public void removeEntities() {
        entitiesMap.keySet().removeIf(Entity::isRemovable);
    }

    public String getText() {
        return text;
    }

    public double getGravity() {
        return GRAVITY;
    }

    public Player getPlayer() {
        return player;
    }

    public HashMap<Entity, Index> getEntitiesMap() {
        return entitiesMap;
    }

    public Map getMap() {
        return map;
    }

    public int getId() {
        return id;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}
