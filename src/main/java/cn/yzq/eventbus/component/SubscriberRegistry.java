package cn.yzq.eventbus.component;



import cn.yzq.eventbus.annotation.EventSubscriber;
import cn.yzq.eventbus.model.Subscriber;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SubscriberRegistry {

    //topic -> subscriber
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> container = new ConcurrentHashMap<>();


    public void bind(Object subscriber){
        List<Method> methods = getSubscribeMethods(subscriber);
        methods.forEach(m-> put(subscriber,m));
    }

    public void unbind(Object subscriber){
        List<Method> methods = getSubscribeMethods(subscriber);
        methods.forEach(m-> take(subscriber,m));
    }

    public final ConcurrentLinkedQueue<Subscriber> getSubscriberByTopic(final String topic){
        return container.get(topic);
    }







    //put subscriber into container
    private void put(Object subscriber, Method method){
        EventSubscriber annotation = method.getDeclaredAnnotation(EventSubscriber.class);
        String topic = annotation.topic();
        container.computeIfAbsent(topic,key->new ConcurrentLinkedQueue<>());
        container.get(topic).add(new Subscriber(subscriber,method));
    }

    // take subscriber out from container
    private void take(Object subscriber, Method method){
        EventSubscriber annotation = method.getDeclaredAnnotation(EventSubscriber.class);
        String topic = annotation.topic();

        container.computeIfPresent(topic,(k,queue)->{
            queue.removeIf(item ->item.getSubscriberMethod().equals(method)
                    &&item.getSubscriberObject().equals(subscriber));

            if(queue.isEmpty()){
                return container.remove(topic);
            }else{
                return queue;
            }

        });
    }

    private static List<Method> getSubscribeMethods(Object subscriber){
        final List<Method> methods = new ArrayList<>();

        Class<?> clz = subscriber.getClass();
        while(clz!=null){
            //find method
            Arrays.stream(clz.getDeclaredMethods())
                    .filter(method->method.isAnnotationPresent(EventSubscriber.class)
                            && method.getParameterCount()==1
                            && method.getModifiers()== Modifier.PUBLIC)
                    .forEach(methods::add);
            //find from superclass
            clz = clz.getSuperclass();
        }

        return methods;
    }
}
