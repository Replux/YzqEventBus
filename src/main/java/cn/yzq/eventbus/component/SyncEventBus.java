package cn.yzq.eventbus.component;


import cn.yzq.eventbus.EventBus;
import cn.yzq.eventbus.EventExceptionHandler;

import java.util.concurrent.Executor;

/**
 * the facade of dispatcher
 */
public class SyncEventBus implements EventBus {

    // * * * * * * * * * * constant * * * * * * * * * *
    private final static String DEFAULT_BUS_NAME="default";
    private final static String DEFAULT_TOPIC_NAME="default";

    // * * * * * * * * * * component * * * * * * * * * *
    private final EventDispatcher dispatcher;
    private final SubscriberRegistry registry = new SubscriberRegistry();

    // * * * * * * * * * * attribute * * * * * * * * * *
    private String busName;

    // * * * * * constructor * * * * *
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
        this.busName = busName;
        this.dispatcher=EventDispatcher.newSeqDispatcher(eventExceptionHandler);
    }


    SyncEventBus(String busName, EventExceptionHandler eventExceptionHandler, Executor executor) {
        this.busName = busName;
        this.dispatcher=EventDispatcher.newCustomizedDispatcher(eventExceptionHandler,executor);
    }


    @Override
    public void register(Object subscriber) {
        this.registry.bind(subscriber);
    }

    @Override
    public void unregister(Object subscriber) {
        this.registry.unbind(subscriber);
    }

    @Override
    public void post(Object event) {
        this.post(event,DEFAULT_TOPIC_NAME);
    }

    @Override
    public void post(Object event, String topic) {
        this.dispatcher.dispatch(this,registry,topic,event);
    }

    @Override
    public void close() {
        this.dispatcher.close();
    }

    @Override
    public String getBusName() {
        return this.busName;
    }
}
