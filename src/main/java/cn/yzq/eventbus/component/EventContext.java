package cn.yzq.eventbus.component;

import java.lang.reflect.Method;

public interface EventContext {

    Object getEvent();

    Object getSubscriber();

    Method getMethod();

    String getBusName();

}
