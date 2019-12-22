package cn.yzq.eventbus.component.eventbus;

import cn.yzq.eventbus.EventExceptionHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AsyncEventBus extends BaseEventBus {

    //private static final Executor SYN_EXECUTOR = SyncEventBus.SynExecutor.INSTANCE;
    private final static String DEFAULT_BUS_NAME="default";

    public AsyncEventBus() {
        this(DEFAULT_BUS_NAME);
    }
    public AsyncEventBus(EventExceptionHandler exceptionHandler) {
        this(DEFAULT_BUS_NAME,exceptionHandler);
    }
    public AsyncEventBus(String busName) {
        this(busName,null);
    }
    public AsyncEventBus(String busName, EventExceptionHandler eventExceptionHandler) {
        super(busName,eventExceptionHandler,
                Executors.newFixedThreadPool(2*Runtime.getRuntime().availableProcessors())
        );
    }
}
