package cn.yzq.eventbus.component.eventbus;


import cn.yzq.eventbus.EventBus;
import cn.yzq.eventbus.EventExceptionHandler;
import cn.yzq.eventbus.component.EventDispatcher;
import cn.yzq.eventbus.component.SubscriberRegistry;

import java.util.concurrent.Executor;

/**
 * the facade of dispatcher
 */
public class BaseEventBus implements EventBus {

    // * * * * * * * * * * constant * * * * * * * * * *
    private final static String DEFAULT_TOPIC_NAME="default";

    // * * * * * * * * * * component * * * * * * * * * *
    private final EventDispatcher dispatcher;
    private final SubscriberRegistry registry = new SubscriberRegistry();

    // * * * * * * * * * * attribute * * * * * * * * * *
    private String busName;

    // * * * * * constructor * * * * *
    BaseEventBus(String busName, EventExceptionHandler eventExceptionHandler, Executor executor) {
        this.busName = busName;
        this.dispatcher=new EventDispatcher(eventExceptionHandler,executor);
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
