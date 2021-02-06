package main;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class StartSimpleHttpServer {

    public static void main(String[] args) {
        Logger.always("Welcome to the Simple HTTP main.Server.");
        Logger.always("For more information call this program with the 'help' parameter.");
        // Parameters: key=value
        for (String arg : args) {
            String[] keyValue = arg.split("=");
            switch (keyValue[0]) {
                case "help":
                    StartSimpleHttpServer.printHelp();
                    System.exit(0);
                case "dir":
                    // Set the directory, of which all files are to be displayed on website
                    // If the directory does not exist, exit program
                    // Default is user.dir => Location of JAR file
                    if (keyValue.length == 2) {
                        File dir = new File(keyValue[1]);
                        if (dir.exists()) {
                            if (dir.isDirectory()) {
                                Config.pathToFiles = dir.getAbsolutePath();
                                Logger.always(String.format("Files in '%s' will be displayed on the website", Config.pathToFiles));
                                break;
                            }
                        }
                    }
                    Logger.error("Invalid directory parameter: " + arg);
                    System.exit(0);
                    break;
                case "favicon":
                    // https://www.w3schools.com/charsets/ref_emoji_smileys.asp
                    if (keyValue.length == 2) {
                        String favicon = keyValue[1];
                        if (favicon.matches("&#[0-9]+;")) {
                            Config.faviconUnicodeEmoji = favicon;
                            break;
                        }
                    }
                    Logger.error("Invalid favicon parameter: " + arg);
                    Logger.error("Please provide an emoji with decimal value in pattern '&#[decimal value of emoji];'");
                    System.exit(0);
                case "title":
                    if (keyValue.length == 2) {
                        Config.title = keyValue[1];
                        break;
                    }
                    Logger.error("Invalid title parameter: " + arg);
                    System.exit(0);
                case "port":
                    if (keyValue.length == 2) {
                        try {
                            Config.PORT = Integer.parseInt(keyValue[1]);
                            if (Config.PORT < 1) throw new NumberFormatException();
                            break;
                        } catch (NumberFormatException e) {
                            Logger.error("Invalid directory parameter: " + arg);
                            Logger.error("Please provide the port as positive number.");
                            System.exit(0);
                        }
                        Logger.error("Invalid port parameter: " + arg);
                        System.exit(0);
                    }
                case "log":
                    if (keyValue.length == 2) {
                        String level = keyValue[1];
                        if (level.equals("INFO")) {
                            Config.logLevel = Logger.ALWAYS;
                            break;
                        } else if (level.equals("DEBUG")) {
                            Config.logLevel = Logger.DEBUG;
                            break;
                        }
                        Logger.error("Invalid log parameter: " + arg);
                        System.exit(0);
                    }
                default:
                    Logger.always(String.format("'%s' is an unknown argument and will be ignored!", arg));
            }
        }
        // Find local ip address for convenience
        try {
            Config.IP = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            Logger.error("Could not retrieve your IP address!");
        }
        Config.logConfig();
        // actually start the server
        try {
            new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: Exit option from command line
        // TODO: Exit option from browser /stop or something with a dialog

    }

    private static void printHelp() {
        Logger.help("");
        Logger.help("This is the help menu for the Simple HTTP main.Server");
        Logger.help("There are multiple options available.");
        Logger.help("Despite the help option, options need to be provided");
        Logger.help("as key value pair in patter [key]=[value]");
        Logger.help("If the value contains spaces, use quotes around the whole key value pair");
        Logger.help("Example: \"title=Hello World\"");
        Logger.help("");
        Logger.help("Commands:");
        Logger.help("   help     - print help menu");
        Logger.help("   dir      - set the directory of which contents should be made available on the website");
        Logger.help("              Note: this will also display subdirectory contents");
        Logger.help("              Default: Directory location of this program (jar file)");
        Logger.help("              Example: dir=C:\\Users\\my_user\\Desktop");
        Logger.help("   favicon  - set a unicode emoji as favicon icon for the website");
        Logger.help("              The parameter needs to be provided in the pattern:");
        Logger.help("                  &#[decimal value of emoji];");
        Logger.help("              Default: &#128579;");
        Logger.help("              Example: favicon=&#128579;");
        Logger.help("              Find emojis decimal values at:");
        Logger.help("                  https://www.w3schools.com/charsets/ref_emoji_smileys.asp");
        Logger.help("   title    - Set the website title");
        Logger.help("              Default: Simple HTTP main.Server");
        Logger.help("              Example: \"title=Hello World\"");
        Logger.help("   port     - Set the port to use");
        Logger.help("              Default: 80");
        Logger.help("              Note: A positive integer is required.");
        Logger.help("              Note: If the port is not free, you will get an exception.");
        Logger.help("              Example: port=8080");
        Logger.help("   log      - Set the log level");
        Logger.help("              Available log level are:");
        Logger.help("                 INFO and DEBUG");
        Logger.help("              Default: INFO");
        Logger.help("              Note: Case sensitive!");
        Logger.help("              Example: log=DEBUG");
    }

}
