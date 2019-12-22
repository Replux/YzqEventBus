package cn.yzq.eventbus;


/**
 * writing an EventBus, referring to guava provided by Google
 *
 * @author Replux(杨哲庆)
 * @since 2019-12-22 12:57:33
 */
public interface Bus {

    void register(Object subscriber);

    void unregister(Object subscriber);

    void post(Object event);

    void post(Object event, String topic);

    void close();

    String getBusName();

}
