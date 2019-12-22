package testEventBus;

import cn.yzq.eventbus.YzqEventBus;
import org.junit.Test;

public class SimpleEventBus {


    @Test
    public void test(){
        YzqEventBus bus = new YzqEventBus();
        bus.register(new SimpleListener());
        bus.post("test @EventSubscriber successfully");
        bus.post(new Student(23,"replux"));
        Integer integer = new Integer(100);
        bus.post(integer,"new");
        bus.post("test @EventSubscriber successfully","new");

    }
}
