package logger.service;

import logger.data.Datastore;
import logger.data.FileStore;
import logger.enums.Severity;
import logger.pojo.Log;
import logger.utils.DeepCopyUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Logger {

    private Datastore datastore = new FileStore();

    private Set<Log> logTrackSet = new HashSet<>(); // capacity

    private Queue<Set<Log>> logsProcessingQueue = new LinkedList<Set<Log>>(); // capacity

    private Integer timeout = 1000;  // if time taken is greater than 12 ms, we will just delete some logs from our file.

    private static Logger logger = null;

    private final ExecutorService service = Executors.newSingleThreadExecutor();

    public static Logger getInstance(){
        if(logger == null){
            logger = new Logger();
        }
        return logger;
    }

    public void addLog(Log log){
        synchronized (Logger.class){
            Timestamp timestamp = new Timestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            int count = 0;
            for(StackTraceElement element:elements){
                if(count == 0){
                    // just doing this for the first element
                    sb.append(element.toString()).append("\n");
                    count = 1;
                }
                else
                    sb.append("\t at").append(element.toString()).append("\n");
            }
            String stackTraceString = sb.toString();
            log.setStackTrace(stackTraceString);
            log.setTimestamp(timestamp);
            log.setThreadId(Long.toString(Thread.currentThread().getId()));
            log.setThreadName(Thread.currentThread().getName());
            log.setSeverity(log.getSeverity() == null? Severity.LOW:log.getSeverity());
            this.put(logTrackSet,log);
        }
    }

    public void appendLog(){
        // first get logs from set and put that in queue.
        synchronized (Logger.class){
            try{
                long start = System.currentTimeMillis();
                Set<Log> tempLog = DeepCopyUtil.deepCopy(this.logTrackSet);
                this.put(this.logsProcessingQueue,tempLog);
                this.flushLogTrackSet();
                service.submit(() -> {
                    try {
                        this.datastore.appendLog(this.logsProcessingQueue.peek());
                        this.flushLogProcessingQueue();
                        long end = System.currentTimeMillis();
                    } catch(Exception ex) {
                        // handle exception
                    }
                });
            }
            catch (Exception ex){
                //
            }
        }
    }

    private void flushLogTrackSet(){
        this.logTrackSet.clear();
    }

    private void flushLogProcessingQueue(){
        this.logsProcessingQueue.poll();
    }

    private <T> void put(Collection<T> collection,T item){
        collection.add(item);
    }

}
