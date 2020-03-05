package donjon.Game;

import java.util.ArrayList;

public class Game {
    private static final int HIGHEST_LEVEL = 13;

    private ArrayList<Integer> levelsLeft;
    private Level currentLevel;
    private int totalCoins;
    private int totalDeath;

    public Game(int startLevelId, int totalCoins) {
        this.levelsLeft = new ArrayList<>();
        int idLevel = startLevelId;
        for (; idLevel <= HIGHEST_LEVEL; idLevel++) {
            levelsLeft.add(idLevel);
        }

        this.totalCoins = totalCoins;
        currentLevel = new Level(startLevelId);
    }

    public void loadCurrentLevel() {
        currentLevel = new Level(currentLevel.getId());
    }

    public boolean loadNextLevel() {
        /* We remove the level completed */
        int id = currentLevel.getId();
        levelsLeft.removeIf(idLevel -> idLevel == id);

        /* We load the next one or return false if there's no more levels */
        if (levelsLeft.size() != 0) {
            currentLevel = new Level(id + 1);
            return true;
        }

        return false;
    }

    public int getTotalCoins() {
        return totalCoins;
    }

    public int getTotalDeath() {
        return totalDeath;
    }

    public void addCoins(int coins) {
        totalCoins = totalCoins + coins;
    }

    public void addDeaths(int deaths) {
        totalDeath = totalDeath + deaths;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
