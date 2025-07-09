package logger.data;

import logger.pojo.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.concurrent.TimeoutException;

public class FileStore implements Datastore{

    @Override
    public void addLog(Log log){

    }

    public void appendLog(Collection<Log> logs) throws TimeoutException{
        // here is where io will happen
        try{
            File file = new File("test.log");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for(Log log:logs){
                oos.writeObject(log);
            }
            fos.close();
            oos.close();
        }catch (Exception ex){
            System.err.println("File Not Found Exception");
        }
    }

    @Override
    public void deleteLog() {
        try {
            File file = new File("test.log");
            if (!file.exists()) return;
            // Read all logs
            java.util.List<Log> logs = new java.util.ArrayList<>();
            try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream(file))) {
                while (true) {
                    try {
                        Log log = (Log) ois.readObject();
                        logs.add(log);
                    } catch (java.io.EOFException e) {
                        break;
                    }
                }
            }
            // Sort logs by timestamp
            logs.sort(java.util.Comparator.comparing(Log::getTimestamp));
            // Delete oldest 30%
            int toDelete = (int) (logs.size() * 0.3);
            java.util.List<Log> remainingLogs = logs.subList(toDelete, logs.size());
            // Write remaining logs back
            try (java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(new java.io.FileOutputStream(file))) {
                for (Log log : remainingLogs) {
                    oos.writeObject(log);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error deleting old logs: " + ex.getMessage());
        }
    }
}
