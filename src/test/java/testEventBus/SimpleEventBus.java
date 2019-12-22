package testEventBus;

import cn.yzq.eventbus.component.SyncEventBus;
import org.junit.Test;

public class SimpleEventBus {


    @Test
    public void test(){
        SyncEventBus bus = new SyncEventBus();
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
    public void testExceptionHandler(){
        SyncEventBus bus = new SyncEventBus((cause, context)->{
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
