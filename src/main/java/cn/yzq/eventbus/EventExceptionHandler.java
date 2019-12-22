package cn.yzq.eventbus;


import cn.yzq.eventbus.component.EventContext;

public interface EventExceptionHandler {

     void handle(Throwable cause, EventContext context);
}
