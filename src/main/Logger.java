package main;

import java.time.LocalTime;

public class Logger {

    // log level
    public static final int ALWAYS = 0;
    public static final int DEBUG = 1;

    public static void always(String msg){
        Logger.log(msg, "INFO");
    }

    public static void help(String msg){
        Logger.log(msg, "HELP");
    }

    public static void error(String msg){
        Logger.log(msg, "ERROR");
    }

    public static void debug(String msg){
        if(Config.logLevel >= DEBUG){
            Logger.log(msg, "DEBUG");
        }
    }

    private static void log(String msg, String level){
        // https://docs.oracle.com/javase/8/docs/api/java/util/Formatter.html#syntax
        System.out.printf("%1$tH:%1$tM:%1$tS - %2$5s - %3$s\n", LocalTime.now(), level, msg);
    }


}
