package logger.service;

import logger.data.FileStore;
import logger.pojo.Log;
import logger.utils.DeepCopyUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Logger {

    private FileStore fileStore;

    private Set<Log> logTrackSet; // capacity

    private Queue<Set<Log>> logsProcessingQueue; // capacity

    private Integer timeout;

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
            for(StackTraceElement element:elements){
                sb.append("\t at").append(element.toString()).append("\n");
            }
            String stackTraceString = sb.toString();
            log.setStackTrace(stackTraceString);
            log.setTimestamp(timestamp);
            log.setThreadId(Long.toString(Thread.currentThread().getId()));
            log.setThreadName(Thread.currentThread().getName());
            this.put(logTrackSet,log);
        }
    }

    public void appendLog(){
        // first get logs from set and put that in queue.
        synchronized (Logger.class){
            try{
                Set<Log> tempLog = DeepCopyUtil.deepCopy(this.logTrackSet);
                this.put(this.logsProcessingQueue,tempLog);
                this.flushLogTrackSet();
                service.submit(()->{
                    //perform IO operation

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

    private <T> void put(Collection<T> collection,T item){
        collection.add(item);
    }

    private void deleteLogs(){

    }



}
