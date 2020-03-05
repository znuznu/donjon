package donjon.Controller;

import donjon.Entities.Items.Key;
import donjon.Entities.Items.Soul;
import donjon.Entities.Kill.Kill;
import donjon.Game.Game;
import donjon.Game.Level;
import donjon.Game.Map;
import donjon.Game.Tile;
import donjon.Entities.Engine.Camera;
import donjon.Entities.Engine.Entity;
import donjon.Entities.Characters.Player;
import donjon.Entities.Items.Item;
import donjon.Main.Main;
import donjon.Manager.SceneManager;
import donjon.Manager.SettingsManager;
import donjon.Manager.SpriteManager;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    private AnimationTimer gameLoop;
    private Game game;
    private Camera camera;
    private boolean right, left, jump, interact;

    private int frames = 0;
    private int currentFPS = 0;
    private long previousTime = -1;

    @FXML
    private Label framePerSecondLabel;

    @FXML
    private Label idLevelLabel;

    @FXML
    private HBox inventoryHbox;

    @FXML
    private Pane gamePane;

    @FXML
    private Label coinsLabel;

    @FXML
    private Label textLabel;

    @FXML
    public void keyPressed(KeyEvent keyEvent) {
        String key = keyEvent.getCode().getName().toUpperCase();
        if (key.equals(SettingsManager.LEFT))
            left = true;
        else if (key.equals(SettingsManager.RIGHT))
            right = true;
        else if (key.equals(SettingsManager.JUMP))
            jump = true;
        else if (key.equals(SettingsManager.INTERACT))
            interact = true;
    }

    @FXML
    public void keyReleased(KeyEvent keyEvent) {
        String key = keyEvent.getCode().getName().toUpperCase();
        if (key.equals(SettingsManager.LEFT))
            left = false;
        else if (key.equals(SettingsManager.RIGHT))
            right = false;
        else if (key.equals(SettingsManager.JUMP))
            jump = false;
        else if (key.equals(SettingsManager.INTERACT))
            interact = false;
    }

    public void startGame() {
        startLoop();
    }

    public void initGame(Game game) {
        this.game = game;
        initLevelOnView(game.getCurrentLevel());
    }

    public void initLevelOnView(Level currentLevel) {
        addLevelOnView(currentLevel);
        addEntitiesOnView(currentLevel);
        addLevelIdOnView(currentLevel.getId());
        addCoinsOnView();
        gamePane.requestFocus();
        textLabel.setText(currentLevel.getText());
        camera = new Camera(0, 0, SceneManager.GAME_WIDTH, SceneManager.GAME_HEIGHT);
    }

    private void reloadLevel() {
        game.loadCurrentLevel();
        loadLevel();
    }

    private void loadLevel() {
        gamePane.getChildren().clear();
        inventoryHbox.getChildren().clear();
        initLevelOnView(game.getCurrentLevel());
    }

    private void updateInventoryOnView(Player player) {
        coinsLabel.setText(String.valueOf(player.getCoinsCount()));

        for (Item item : player.getInventory()) {
            if (item instanceof Key) {
                Key key = ((Key) item);
                if (key.hasBeenUsed()) {
                    inventoryHbox.getChildren().remove(key.getSprite());
                }
            }
        }

        if (player.getInventory().size() != 0) {
            for (Item item : player.getInventory()) {
                if (item instanceof Key && ((Key) item).hasBeenUsed()) {
                    continue;
                }

                if (!inventoryHbox.getChildren().contains(item.getSprite())) {
                    inventoryHbox.getChildren().add(item.getSprite());
                }
            }
        }
    }

    private void updateFramesPerSecondOnView() {
        frames += 1;
        long current = System.currentTimeMillis();
        if (current - previousTime >= 1000) {
            currentFPS = frames;
            previousTime = current;
            frames = 0;
        }

        framePerSecondLabel.setText(currentFPS + " FPS");
    }

    private void updateAnimationsOfPlayerAndItems(Level level) {
        for (Entity e : level.getEntitiesMap().keySet()) {
            if (e instanceof Player || e instanceof Item || e instanceof Kill)
                e.updateAnimation();
        }
    }

    // TODO: only the visibles tiles on screen must have a node
    private void moveCamera() {
        double xDifference = Math.round(camera.getX());
        double yDifference = Math.round(camera.getY());
        Map map = game.getCurrentLevel().getMap();
        Tile[][] mTiles = map.getMapTiles();

        for (int r = 0; r < mTiles.length; r++) {
            for (int c = 0; c < mTiles[r].length; c++) {
                mTiles[r][c].getImageView().relocate(
                        c * map.getTileSize() - xDifference,
                        r * map.getTileSize() - yDifference
                );
            }
        }
    }

    private void updateCamera(Player player) {
        double playerCenterPosX = player.getPosX() + player.getBoundingbox().getCenterX();
        double playerCenterPosY = player.getPosY() + player.getBoundingbox().getCenterY();

        double cameraWidth = camera.getWidth();
        double levelWidth = game.getCurrentLevel().getMap().getWidth();

        double cameraHeight = camera.getHeight();
        double levelHeight = game.getCurrentLevel().getMap().getHeight();

        if (playerCenterPosX - (cameraWidth / 2) < 0) {
            camera.setX(0);
        } else if ((playerCenterPosX + (cameraWidth / 2)) > levelWidth) {
            camera.setX(levelWidth - cameraWidth);
        } else {
            camera.setX(playerCenterPosX - (camera.getWidth() / 2));
        }

        if (playerCenterPosY - (cameraHeight / 2) < 0) {
            camera.setY(0);
        } else if ((playerCenterPosY + (cameraHeight / 2)) > levelHeight) {
            camera.setY(levelHeight - cameraHeight);
        } else {
            camera.setY(playerCenterPosY - (camera.getHeight() / 2));
        }
    }

    private void updateLocationOnView(Node n, double x, double y) {
        n.relocate(x, y);
    }

    private void updateEntitiesLocationOnView() {
        for (Entity e : game.getCurrentLevel().getEntitiesMap().keySet()) {
            updateLocationOnView(
                    e.getSprite(),
                    e.getPosX() - Math.round(camera.getX()),
                    e.getPosY() - Math.round(camera.getY())
            );
        }
    }

    private void addCoinsOnView() {
        inventoryHbox.getChildren().add(new ImageView(SpriteManager.COIN[0][5]));
        coinsLabel = new Label("n");
        coinsLabel.getStyleClass().add("standard");
        inventoryHbox.getChildren().add(coinsLabel);
    }

    private void addLevelIdOnView(int id) {
        idLevelLabel.setText("Level " + id);
    }

    private void addLevelOnView(Level level) {
        Map map = level.getMap();
        Tile[][] mTiles = map.getMapTiles();

        for (int r = 0; r < mTiles.length; r++) {
            for (int c = 0; c < mTiles[r].length; c++) {
                gamePane.getChildren().add(mTiles[r][c].getImageView());
                int tileSize = map.getTileSize();
                mTiles[r][c].getImageView().relocate(c * tileSize, r * tileSize);
            }
        }
    }

    /* Add on gamePane all the entities, like coins or player */
    private void addEntitiesOnView(Level level) {
        for (Entity e : level.getEntitiesMap().keySet()) {
            double xPos = e.getPosX();
            double yPos = e.getPosY();
            addNodeOnView(e.getSprite(), xPos, yPos);
        }
    }

    private void addNodeOnView(Node n, double x, double y) {
        gamePane.getChildren().add(n);
        n.relocate(x, y);
    }

    private void cleanEntity(Entity e) {
        gamePane.getChildren().remove(e.getSprite());
    }

    private void cleanRemovedEntitiesOfView() {
        for (Entity e : game.getCurrentLevel().getEntitiesMap().keySet()) {
            if (e.isRemovable())
                cleanEntity(e);
        }
    }

    private void checkSoulInInventory(Player player) {
        for (Item item : player.getInventory()) {
            if (item instanceof Soul)
                game.getCurrentLevel().setComplete(true);
        }
    }

    private void loadNextLevel(int coins) {
        game.addCoins(coins);

        if (game.loadNextLevel()) {
            loadLevel();
        } else {
            System.out.println("Game complete.");
            gameLoop.stop();
            Main.sm.loadScene(3);
            SceneManager.endController.setData(game);
            Main.getPrimaryStage().setScene(Main.sm.getScene(3));
        }
    }

    private void startLoop() {
        gameLoop = new AnimationTimer() {
            /* Ugly but we need to bypass the multiple inputs due to the high frame rate */
            int doubleJumpFrameCounter = 0;

            @Override
            public void handle(long l) {
                updateFramesPerSecondOnView();
                Level currentLevel = game.getCurrentLevel();
                Player player = currentLevel.getPlayer();

                if (currentLevel.isComplete()) {
                    loadNextLevel(player.getCoinsCount());
                } else if (!player.isAlive()) {
                    game.addDeaths(1);
                    /* Instant reload if the player is dead */
                    reloadLevel();
                } else {
                    /* Player */
                    player.handleActions(doubleJumpFrameCounter, currentLevel, right, left, jump, interact);
                    player.updateViewDirection(left, right);

                    /* Camera */
                    updateCamera(player);
                    moveCamera();

                    /* Collisions */
                    currentLevel.handleCollisions();

                    /* Inventory */
                    updateInventoryOnView(player);
                    player.removeUsedKeys();

                    /* Remove and clean */
                    cleanRemovedEntitiesOfView();
                    currentLevel.removeEntities();

                    /* Entities */
                    updateAnimationsOfPlayerAndItems(currentLevel);
                    updateEntitiesLocationOnView();

                    /* Soul */
                    checkSoulInInventory(player);
                }
            }
        };

        gameLoop.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
