package logger.data;

import logger.pojo.Log;

import java.util.concurrent.TimeoutException;

public class FileStore implements Datastore{

    @Override
    public void addLog(Log log){

    }

    @Override
    public void appendLog() throws TimeoutException {
        // here is where io will happen
    }


}
