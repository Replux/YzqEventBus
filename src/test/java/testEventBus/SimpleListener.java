package testEventBus;

import cn.yzq.eventbus.annotation.EventSubscriber;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class SimpleListener {


    @EventSubscriber
    public void testEventSubscriber(String event){
        assertNotNull(event);
    }

    @EventSubscriber
    public void testEventSubscriber(Student event){
        assertNotNull(event);
    }

    @EventSubscriber(topic = "new")
    public void testTopic(String event) throws Exception{
        assertNotNull(event);
        System.out.println("卡5秒");
        Thread.sleep(5000);
        System.out.println("5秒到");
    }

    @EventSubscriber(topic = "new")
    public void testTopic(Integer event){
        assertNotNull(event);
    }

    @EventSubscriber(topic = "exception")
    public void testexception(String event){
        throw new RuntimeException("test Exception");
    }
}
