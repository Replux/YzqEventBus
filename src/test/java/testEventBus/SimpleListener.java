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
        System.out.println("卡3秒");
        Thread.sleep(3000);
        System.out.println("3秒到");
    }

    @EventSubscriber(topic = "new")
    public void testTopic(Integer event){
        assertNotNull(event);
    }

    @EventSubscriber(topic = "exception")
    public void testexception(String event){
        throw new RuntimeException("test Exception");
    }


    @EventSubscriber(topic = "20s")
    public void testAsyncWith20s(String event) throws Exception{
        assertNotNull(event);
        System.out.println("卡20秒");
        Thread.sleep(20000);
        System.out.println("20秒到");
    }
    @EventSubscriber(topic = "10s")
    public void testAsyncWith10s(String event) throws Exception{
        assertNotNull(event);
        System.out.println("卡10秒");
        Thread.sleep(10000);
        System.out.println("10秒到");
    }
    @EventSubscriber(topic = "5s")
    public void testAsyncWith5s(String event) throws Exception{
        assertNotNull(event);
        System.out.println("卡5秒");
        Thread.sleep(5000);
        System.out.println("5秒到");
    }
}
