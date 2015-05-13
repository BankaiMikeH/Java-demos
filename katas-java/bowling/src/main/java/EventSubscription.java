public class EventSubscription {
    private EventSubscriber subscriber;
    private Integer messageCountUntilAutoUnSubscribe;
    private int messageCount;

    public EventSubscription(EventSubscriber subscriber) {
        this(subscriber, 0);
    }

    public EventSubscription(EventSubscriber subscriber, int messageCountUntilAutoUnsubscribe) {
        this.subscriber = subscriber;
        this.messageCountUntilAutoUnSubscribe = messageCountUntilAutoUnsubscribe;
    }

    public EventSubscriber getSubscriber() {
        return subscriber;
    }

    public boolean isExpired() {
        return messageCountUntilAutoUnSubscribe > 0
            && messageCount >= messageCountUntilAutoUnSubscribe;
    }

    public void incrementTriggerCount() {
        messageCount++;
    }
}
