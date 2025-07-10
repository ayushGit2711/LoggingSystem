package logger;


import logger.pojo.Log;
import logger.service.Logger;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("HELLLLLLOOOOOOOO");
        Logger logger = Logger.getInstance();

        logger.addLog(new Log("hurrayyy"));
        logger.addLog(new Log("We did it"));
        logger.addLog(new Log("We are going home now"));
        logger.addLog(new Log("Bye bye"));

        logger.appendLog();
    }
}
