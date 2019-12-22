package cn.yzq.eventbus.model;


import lombok.Data;

import java.lang.reflect.Method;

@Data
public class Subscriber {

    private final Object subscriberObject;

    private final Method subscriberMethod;

}
