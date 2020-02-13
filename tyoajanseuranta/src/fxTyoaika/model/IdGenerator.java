package fxTyoaika.model;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger userIdGenerator = new AtomicInteger(1000);
    private static final AtomicInteger projectIdGenerator = new AtomicInteger(1000);
    private static final AtomicInteger entryIdGenerator = new AtomicInteger(1000);
    
    
    public static int getNewUserId() {
        return userIdGenerator.getAndIncrement();
    }
    
    
    public static int getNewProjectId() {
        return projectIdGenerator.getAndIncrement();
    }
    
    
    public static int getNewEntryId() {
        return entryIdGenerator.getAndIncrement();
    }

}
