package logger;


import logger.pojo.Log;
import logger.service.Logger;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println(Logger.getInstance());
        System.out.println(Logger.getInstance());

    }
}
