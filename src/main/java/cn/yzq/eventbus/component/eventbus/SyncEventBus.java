package cn.yzq.eventbus.component.eventbus;

import cn.yzq.eventbus.EventExceptionHandler;

import java.util.concurrent.Executor;

public class SyncEventBus extends BaseEventBus {

    private static final Executor SYN_EXECUTOR = SynExecutor.INSTANCE;
    private final static String DEFAULT_BUS_NAME="default";

    public SyncEventBus() {
        this(DEFAULT_BUS_NAME);
    }
    public SyncEventBus(String busName) {
        this(busName,null);
    }
    public SyncEventBus(EventExceptionHandler exceptionHandler) {
        this(DEFAULT_BUS_NAME,exceptionHandler);
    }
    public SyncEventBus(String busName, EventExceptionHandler eventExceptionHandler) {
        super(busName,eventExceptionHandler,SYN_EXECUTOR);
    }



    //单例
    private static class SynExecutor implements Executor {

        private final static SynExecutor INSTANCE = new SynExecutor();
        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }
}
