package cn.yzq.eventbus.component;


import lombok.var;
import cn.yzq.eventbus.Bus;
import cn.yzq.eventbus.EventExceptionHandler;
import cn.yzq.eventbus.model.Subscriber;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class EventDispatcher {

    private final Executor executor;

    private final EventExceptionHandler exceptionHandler;

    private static final Executor SYN_EXECUTOR = SynExecutor.INSTANCE;
    private static final Executor PRE_THREAD_EXECUTOR = PreThreadExecutor.INSTANCE;

    private EventDispatcher(EventExceptionHandler exceptionHandler,Executor executor) {
        this.executor = executor;
        this.exceptionHandler = exceptionHandler;
    }









    //* * * * factory method * * * *
    public static EventDispatcher newCustomizedDispatcher(EventExceptionHandler exceptionHandler,Executor executor){
        return new EventDispatcher(exceptionHandler,executor);
    }

    public static EventDispatcher newSeqDispatcher(EventExceptionHandler exceptionHandler){
        return new EventDispatcher(exceptionHandler,SYN_EXECUTOR);
    }

    public static EventDispatcher newPreThreadDispatcher(EventExceptionHandler exceptionHandler){
        return new EventDispatcher(exceptionHandler,PRE_THREAD_EXECUTOR);
    }









    public void dispatch(Bus bus, SubscriberRegistry registry, String topic, Object event){
        var subscribers = registry.getSubscriberByTopic(topic);
        if(subscribers==null){
            if(exceptionHandler!=null){
                exceptionHandler.handle(
                        new IllegalArgumentException("The topic ["+topic+"] not bind yet"),
                        new BaseEventContext(bus.getBusName(),null,event)
                );
            }
            return;
        }

        subscribers.stream()
                .filter(subscriber -> {
                    Method method = subscriber.getSubscriberMethod();
                    // Preventive judgment
                    if(method==null){
                        return false;
                    }
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Class<?> genericClz = parameterTypes[0];
                    if(genericClz==null){
                        return false;
                    }else{
                        return genericClz.isAssignableFrom(event.getClass());
                    }
                }).forEach(subscriber -> invokeMethod(bus,subscriber,event));

    }

    public void close(){
        if(executor instanceof ExecutorService){
            ((ExecutorService) executor).shutdown();
        }
    }









    private void invokeMethod(Bus bus, Subscriber subscriber, Object event){
        Method method = subscriber.getSubscriberMethod();
        Object object = subscriber.getSubscriberObject();
        executor.execute(()->{
            try {
                // ignore result
                method.invoke(object, event);
            } catch (Exception e) {
                if(exceptionHandler!=null){
                    exceptionHandler.handle(e,new BaseEventContext(bus.getBusName(),subscriber,event));
                }
            }
        });

    }











    //单例
    private static class SynExecutor implements Executor{

        private final static SynExecutor INSTANCE = new SynExecutor();
        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    //单例
    private static class PreThreadExecutor implements Executor{

        private final static PreThreadExecutor INSTANCE = new PreThreadExecutor();
        @Override
        public void execute(Runnable command) {
            new Thread(command).start();
        }
    }


    private static class BaseEventContext implements EventContext{

        private final String busName;
        private final Subscriber subscriber;
        private final Object event;

        public BaseEventContext(String busName, Subscriber subscriber, Object event) {
            this.busName = busName;
            this.subscriber = subscriber;
            this.event = event;
        }

        @Override
        public Object getEvent() {
            return event;
        }

        @Override
        public Object getSubscriber() {
            return subscriber!=null?subscriber.getSubscriberObject():null;
        }

        @Override
        public Method getMethod() {
            return subscriber!=null?subscriber.getSubscriberMethod():null;
        }

        @Override
        public String getBusName() {
            return busName;
        }
    }
}
