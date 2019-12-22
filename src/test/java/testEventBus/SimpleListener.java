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
    public void testTopic(String event){
        assertNotNull(event);
    }

    @EventSubscriber(topic = "new")
    public void testTopic(Integer event){
        assertNotNull(event);
    }
}
