package logger.data;

import logger.pojo.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.concurrent.TimeoutException;
import java.io.ObjectInputStream;

public class FileStore implements Datastore{

    @Override
    public void addLog(Log log){

    }

    public void appendLog(Collection<Log> logs) throws TimeoutException {
        try {
            File file = new File("test.log");
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(new java.util.ArrayList<>(logs));
            oos.close();
        } catch (Exception ex) {
            System.err.println("File Not Found Exception");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteLog() {
        try {
            File file = new File("test.log");
            if (!file.exists()) return;
            java.util.List<Log> logs;
            try (ObjectInputStream ois = new ObjectInputStream(new java.io.FileInputStream(file))) {
                logs = (java.util.List<Log>) ois.readObject();
            }
            // Sort logs by timestamp
            logs.sort(java.util.Comparator.comparing(Log::getTimestamp));
            // Delete oldest 30%
            int toDelete = (int) (logs.size() * 0.3);
            java.util.List<Log> remainingLogs = logs.subList(toDelete, logs.size());
            try (ObjectOutputStream oos = new ObjectOutputStream(new java.io.FileOutputStream(file))) {
                oos.writeObject(new java.util.ArrayList<>(remainingLogs));
            }
        } catch (Exception ex) {
            System.err.println("Error deleting old logs: " + ex.getMessage());
        }
    }
}
