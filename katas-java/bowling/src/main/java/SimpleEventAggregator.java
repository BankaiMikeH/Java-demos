import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class SimpleEventAggregator implements EventAggregator{
    private final static String DEFAULT_GROUP = "default";

    WeakHashMap<String, WeakHashMap<String, List<EventSubscription>>> allSubscriptions = new WeakHashMap<>();

    public void subscribe(Type messageType, EventSubscriber subscriber) {
        subscribe(DEFAULT_GROUP, messageType, subscriber, 0);
    }

    public void subscribe(String subscriberGroup, Type messageType, EventSubscriber subscriber) {
        subscribe(subscriberGroup, messageType, subscriber, 0);
    }

    public void subscribe(Type messageType, EventSubscriber subscriber, int messageCountUntilAutoUnSubscribe) {
        subscribe(DEFAULT_GROUP, messageType, subscriber, messageCountUntilAutoUnSubscribe);
    }

    public void subscribe(String subscriberGroup, Type messageType, EventSubscriber subscriber, int messageCountUntilAutoUnSubscribe) {
        String messageKey = messageType.toString();

        if(!allSubscriptions.containsKey(messageKey)) {
            allSubscriptions.put(messageKey, new WeakHashMap<>());
        }

        if(!allSubscriptions.get(messageKey).containsKey(subscriberGroup)) {
            allSubscriptions.get(messageKey).put(subscriberGroup, new ArrayList<>());
        }

        allSubscriptions.get(messageKey).get(subscriberGroup)
            .add(new EventSubscription(subscriber, messageCountUntilAutoUnSubscribe));
    }

    public void unSubscribe(Type messageType, EventSubscriber subscriber) {
        unSubscribe(DEFAULT_GROUP, messageType, subscriber);
    }

    public void unSubscribe(String subscriberGroup, Type messageType, EventSubscriber subscriber) {
        String messageKey = messageType.toString();

        if(!allSubscriptions.containsKey(messageKey)){
            return;
        }

        List<EventSubscription> subscriptions = allSubscriptions.get(messageKey).get(subscriberGroup);

        EventSubscription subscriptionToRemove = null;
        for(EventSubscription s : subscriptions) {
            if(subscriber == s.getSubscriber()) {
                subscriptionToRemove = s;
            }
        }

        if(subscriptionToRemove != null) {
            allSubscriptions.get(messageKey).get(subscriberGroup).remove(subscriptionToRemove);
        }
    }

    public void send(Object message) {
        send(DEFAULT_GROUP, message);
    }

    public void send(String subscriberGroup, Object message) {
        String messageKey = message.getClass().toString();

        if(allSubscriptions.containsKey(messageKey) && allSubscriptions.get(messageKey).containsKey(subscriberGroup)) {
            List<EventSubscription> subscriptions = allSubscriptions.get(messageKey).get(subscriberGroup);

            List<EventSubscription> expiredSubscriptions = new ArrayList<>();

            for(EventSubscription subscription : subscriptions) {
                subscription.getSubscriber().onEvent(message);
                subscription.incrementTriggerCount();

                if(subscription.isExpired()) {
                    expiredSubscriptions.add(subscription);
                }
            }

            for(EventSubscription subscription : expiredSubscriptions) {
                unSubscribe(subscriberGroup, message.getClass(), subscription.getSubscriber());
            }
        }
    }
}
