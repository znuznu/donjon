package donjon.Manager;

import java.io.*;

public class SettingsManager {
    public static String LEFT       = "Q";
    public static String RIGHT      = "D";
    public static String JUMP       = "SPACE";
    public static String INTERACT   = "E";

    public static void loadConfig() {
        try {
            InputStream is = SettingsManager.class.getResourceAsStream("/Resources/Misc/settings.cfg");
            BufferedReader brConfig = new BufferedReader(new InputStreamReader(is));
            LEFT = brConfig.readLine().split(" ")[1];
            RIGHT = brConfig.readLine().split(" ")[1];
            JUMP = brConfig.readLine().split(" ")[1];
            INTERACT = brConfig.readLine().split(" ")[1];
        } catch (FileNotFoundException e) {
            System.out.println("Error: configuration file not found");
        } catch (IOException e) {
            System.out.println("Error: can't read configuration file");
        }
    }
}