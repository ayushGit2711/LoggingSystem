package logger.service;

import logger.data.FileStore;
import logger.pojo.Log;

import java.util.Queue;
import java.util.Set;

public class Logger {

    private FileStore fileStore;

    private Set<Log> logTrackSet; // capacity

    private Queue<Set<Log>> logsProcessingQueue; // capacity

    private Integer timeout;

    private static Logger logger = null;

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    public void addLog(Log log){
        // Todo: add timestamp and thread and stacktrace
    }

    public void appendLog(){
        // Todo: Handle exception of append Log from datastore
    }

    private void flushLogProcessingQueue(){

    }

    private void put(){

    }

    private void deleteLogs(){

    }



}
