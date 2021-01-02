import com.sun.net.httpserver.HttpServer;

public class Config {

    public static HttpServer server = null; // To be able to stop it from another thread
    public static String IP = "localhost";
    public static String fileSeparator = System.getProperty("file.separator");
    public static String pathToFiles = System.getProperty("user.dir");
    public static int PORT = 80;
    public static String title = "Simple HTTP Server";
    public static String faviconUnicodeEmoji = "&#128579;";
    public static int logLevel = 0;


    public static void logConfig(){
        Logger.debug("# Start up configuration:");
        Logger.debug(String.format("ip: %s", IP));
        Logger.debug(String.format("fileSeparator: %s", fileSeparator));
        Logger.debug(String.format("pathToFiles: %s", pathToFiles));
        Logger.debug(String.format("PORT: %s", PORT));
        Logger.debug(String.format("title: %s", title));
        Logger.debug(String.format("faviconUnicodeEmoji: %s", faviconUnicodeEmoji));
        Logger.debug(String.format("logLevel: %s", logLevel));
        Logger.debug("#");
    }


}
