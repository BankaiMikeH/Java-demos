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
        prepSubscription(messageKey, subscriberGroup);
        addSubscription(messageKey, subscriberGroup, subscriber, messageCountUntilAutoUnSubscribe);
    }

    private void addSubscription(String messageKey, String subscriberGroup, EventSubscriber subscriber, int messageCountUntilAutoUnSubscribe) {
        getSubscriptionList(messageKey, subscriberGroup)
            .add(new EventSubscription(subscriber, messageCountUntilAutoUnSubscribe));
    }

    private void prepSubscription(String messageKey, String subscriberGroup) {
        if(!allSubscriptions.containsKey(messageKey)) {
            allSubscriptions.put(messageKey, new WeakHashMap<>());
        }

        if(!allSubscriptions.get(messageKey).containsKey(subscriberGroup)) {
            allSubscriptions.get(messageKey).put(subscriberGroup, new ArrayList<>());
        }
    }

    public void unSubscribe(Type messageType, EventSubscriber subscriber) {
        unSubscribe(DEFAULT_GROUP, messageType, subscriber);
    }

    public void unSubscribe(String subscriberGroup, Type messageType, EventSubscriber subscriber) {
        String messageKey = messageType.toString();

        List<EventSubscription> subscriptions = getSubscriptionList(messageKey, subscriberGroup);
        EventSubscription subscriptionToRemove = null;

        for(EventSubscription subscription : subscriptions) {
            if(subscriber == subscription.getSubscriber()) {
                subscriptionToRemove = subscription;
            }
        }

        if(subscriptionToRemove != null) {
            subscriptions.remove(subscriptionToRemove);
        }
    }

    public void send(Object message) {
        send(DEFAULT_GROUP, message);
    }

    public void send(String subscriberGroup, Object message) {
        String messageKey = message.getClass().toString();

        if(areSubscriptionsRegistered(subscriberGroup, messageKey)) {
            List<EventSubscription> subscriptions = getSubscriptionList(messageKey, subscriberGroup);
            List<EventSubscription> expiredSubscriptions = new ArrayList<>();

            for(EventSubscription subscription : subscriptions) {
                subscription.sendMessage(message);

                if(subscription.isExpired()) {
                    expiredSubscriptions.add(subscription);
                }
            }

            for(EventSubscription subscription : expiredSubscriptions) {
                unSubscribe(subscriberGroup, message.getClass(), subscription.getSubscriber());
            }
        }
    }

    private boolean areSubscriptionsRegistered(String subscriberGroup, String messageKey) {
        return allSubscriptions.containsKey(messageKey) && allSubscriptions.get(messageKey).containsKey(subscriberGroup);
    }

    private List<EventSubscription> getSubscriptionList(String messageKey, String subscriberGroup) {
        return allSubscriptions.get(messageKey).get(subscriberGroup);
    }
}
