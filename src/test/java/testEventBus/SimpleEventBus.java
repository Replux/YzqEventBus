package testEventBus;

import cn.yzq.eventbus.EventBus;
import cn.yzq.eventbus.component.eventbus.AsyncEventBus;
import cn.yzq.eventbus.component.eventbus.BaseEventBus;
import cn.yzq.eventbus.component.eventbus.SyncEventBus;
import org.junit.Test;

public class SimpleEventBus {


    @Test
    public void test(){
        EventBus bus = new SyncEventBus();
        bus.register(new SimpleListener());
        bus.post("test @EventSubscriber successfully");
        bus.post(new Student(23,"replux"));
        Integer integer = new Integer(100);
        bus.post(integer,"new");
        bus.post("test @EventSubscriber successfully","new");
        bus.post("test @EventSubscriber failure","null");
        bus.post("test @EventSubscriber failure","exception");
    }

    @Test
    public void testAsync(){
        EventBus bus = new AsyncEventBus();
        bus.register(new SimpleListener());
        bus.post("test @EventSubscriber successfully","20s");
        bus.post("test @EventSubscriber successfully","10s");
        bus.post("test @EventSubscriber successfully","5s");
    }

    @Test
    public void testExceptionHandler(){
        EventBus bus = new SyncEventBus((cause, context)->{
            cause.printStackTrace();
            System.out.println("________________________________");
            System.out.println(context.getBusName());
            System.out.println(context.getMethod());
            System.out.println(context.getEvent());
            System.out.println(context.getSubscriber());
        });
        bus.register(new SimpleListener());
        bus.post("test @EventSubscriber failure","exception");
    }


}
