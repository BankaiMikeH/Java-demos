package net.gartee.messaging;

import java.lang.reflect.Type;

public interface EventAggregator {
    void subscribe(Type messageType, EventSubscriber subscriber);
    void subscribe(String subscriberGroup, Type messageType, EventSubscriber subscriber);
    void subscribe(Type messageType, EventSubscriber subscriber, int messageCountUntilAutoUnSubscribe);
    void subscribe(String subscriberGroup, Type messageType, EventSubscriber subscriber, int messageCountUntilAutoUnSubscribe);

    void unSubscribe(Type messageType, EventSubscriber subscriber);
    void unSubscribe(String subscriberGroup, Type messageType, EventSubscriber subscriber);

    void send(Object message);
    void send(String subscriberGroup, Object message);
}
